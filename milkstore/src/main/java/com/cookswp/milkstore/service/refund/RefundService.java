package com.cookswp.milkstore.service.refund;

import com.cookswp.milkstore.enums.RefundStatus;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.RefundModel.RefundDTO;
import com.cookswp.milkstore.pojo.entities.Refund;
import com.cookswp.milkstore.repository.Refund.RefundRepository;
import com.cookswp.milkstore.service.firebase.FirebaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RefundService implements IRefundService {


    private final RefundRepository refundRepository;

    private final FirebaseService firebaseService;

    public RefundService(RefundRepository refundRepository, FirebaseService firebaseService) {
        this.refundRepository = refundRepository;
        this.firebaseService = firebaseService;
    }

    //This function make to customer create a refund request
    @Override
    @Transactional
    public Refund createRefundRequest(int userID, RefundDTO refundDTO, MultipartFile refundImageFile) {

        if (refundImageFile == null || refundImageFile.isEmpty()) {
            throw new AppException(ErrorCode.REFUND_REQUEST_NEED_CUSTOMER_IMAGE);
        }

        Refund refund = new Refund();
        validateRefund(refundDTO);
        refund.setUserId(userID);
        refund.setSenderName(refundDTO.getSenderName());
        refund.setSenderAddress(refundDTO.getSenderAddress());
        refund.setSenderPhone(refundDTO.getSenderPhone());
        refund.setProductName(refundDTO.getProductName());
        refund.setCustomerRefundReason(refundDTO.getCustomerRefundReason());
        refund.setCustomerNote(refundDTO.getCustomerNote());
        refund.setRequestTime(LocalDateTime.now());
        refund.setRefundStatus(RefundStatus.IN_PROGRESSING);

        try {
            String imgUrl = firebaseService.upload(refundImageFile);
            if(imgUrl.isEmpty()){
                throw new AppException(ErrorCode.REFUND_IMAGE_NOT_FOUND);
            }
            refund.setCustomerImage(imgUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload Image", e);
        }
        return refundRepository.save(refund);
    }

    private void validateRefund(RefundDTO refundDTO) {
        if(refundDTO.getCustomerRefundReason().isEmpty()) throw new AppException(ErrorCode.CUSTOMER_REFUND_REASON_EMPTY);
        if(refundDTO.getSenderPhone().isEmpty()) throw new AppException(ErrorCode.CUSTOMER_PHONE_EMPTY);
        if(refundDTO.getSenderAddress().isEmpty()) throw new AppException(ErrorCode.CUSTOMER_ADDRESS_EMPTY);
        if(refundDTO.getProductName().isEmpty()) throw new AppException(ErrorCode.PRODUCT_NAME_EMPTY);
        if(refundDTO.getSenderName().isEmpty()) throw new AppException(ErrorCode.CUSTOMER_NAME_EMPTY);
    }

    //This function make to All user can get their refund Request by User Id
    @Override
    @Transactional
    public List<Refund> getRefundRequestByUserId(int userId) {
        List<Refund> refunds = refundRepository.findByUserId(userId);
        if (refunds == null || refunds.isEmpty()) {
            throw new AppException(ErrorCode.REFUND_NOT_FOUND);
        }
        return refunds;
    }

    //This function make to Staff can get all refund request from customer
    @Override
    @Transactional
    public List<Refund> getAllRefundRequest() {
        return refundRepository.findAll();
    }

    //This function make to customer can change their refundImage again
    @Override
    @Transactional
    public Refund updateRefundImage(int refundId, MultipartFile refundImage) {
        Refund refund = refundRepository.findById(refundId).orElseThrow(() -> new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND));
        String imgUrl = firebaseService.upload(refundImage);

        refund.setCustomerImage(imgUrl);
        return refundRepository.save(refund);
    }

    //This function make to customer can cancel Refund Request in their side
    @Transactional
    public Refund cancelRefundRequestForCustomer(int refundId, String reason) {
        Refund refund = refundRepository.findById(refundId).orElseThrow(() -> new AppException(ErrorCode.REFUND_NOT_FOUND));
        if (refund.getRefundStatus() == RefundStatus.IN_PROGRESSING) {
            refund.setStaffRejectReason(reason);
            refund.setRefundStatus(RefundStatus.CANCEL_REFUND_REQUEST);
            return refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST);
        }
    }

    //This function make to staff can not confirm refund Request, phase 1, without real condition of refund product
    @Override
    @Transactional
    public Refund canNotConfirmRefundRequest(int refundId, String cancelReason) {
        Refund refund = refundRepository.findById(refundId).orElseThrow(() -> new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND));
        if (refund.getRefundStatus() == RefundStatus.IN_PROGRESSING) {
            if (cancelReason == null || cancelReason.trim().isEmpty()) {
                throw new AppException(ErrorCode.DENY_REFUND_REQUEST_NEED_REASON);
            }
            refund.setRefundStatus(RefundStatus.CANNOT_CONFIRM_REQUEST);
            refund.setStaffRejectReason(cancelReason);
            refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST);
        }

        return null;
    }

    //This function make to staff can confirm Refund Request
    @Override
    @Transactional
    public Refund confirmRequestRefund(int refundId) {
        Refund refund = refundRepository.findById(refundId).orElseThrow(() -> new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND));
        if (refund.getRefundStatus() == RefundStatus.IN_PROGRESSING) {
            refund.setRefundStatus(RefundStatus.TAKING_PRODUCT_PROGRESSING);
            return refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST);
        }
    }

    //This function make to staff can complete progress when they take refund order from customer
    @Override
    @Transactional
    public Refund completeTakingRefundOrder(int refundId, MultipartFile refundEvidence) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isEmpty()) {
            throw new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND);
        }
        Refund refund = refundOptional.get();
        String imgUrl = firebaseService.upload(refundEvidence);
        if (refund.getRefundStatus() == RefundStatus.TAKING_PRODUCT_PROGRESSING && refundEvidence != null) {
            refund.setRefundStatus(RefundStatus.CONFIRM_TAKING);
            refund.setStaffReceivedImage(imgUrl);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST_STATUS);
        }

        return refundRepository.save(refund);
    }

    //This function make to staff change status from CompleteTakingRefundOrder to ShopProcess and show it customer
    @Override
    @Transactional
    public Refund changeStatusToShopProcess(int refundId) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isEmpty()) {
            throw new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND);
        }
        Refund refund = refundOptional.get();
        if (refund.getRefundStatus() == RefundStatus.CONFIRM_TAKING) {
            refund.setRefundStatus(RefundStatus.SHOP_PROCESS);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST_STATUS);
        }

        return refundRepository.save(refund);
    }

    //This function make to Staff can change status to refund Money
    @Override
    @Transactional
    public Refund changeStatusToRefundMoney(int refundId, String staffNote) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isEmpty()) {
            throw new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND);
        }
        Refund refund = refundOptional.get();
        if (refund.getRefundStatus() == RefundStatus.SHOP_PROCESS) {
            refund.setRefundStatus(RefundStatus.CONFIRM_REFUND_MONEY);
            refund.setStaffNote(staffNote);
            return refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST_STATUS);
        }

    }

    //This function make to Staff can deny RefundRequest from customer phase 2
    @Override
    @Transactional
    public Refund denyRequestRefund(int refundId, MultipartFile denyImage, String staffRejectReason) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isEmpty()) {
            throw new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND);
        }
        Refund refund = refundOptional.get();
        if (refund.getRefundStatus() == RefundStatus.SHOP_PROCESS) {
            refund.setRefundStatus(RefundStatus.CANNOT_ACCEPT_REFUND_REQUEST);
            refund.setStaffRejectReason(staffRejectReason);
            String imgUrl = firebaseService.upload(denyImage);
            refund.setStaffRejectImage(imgUrl);
            return refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST_STATUS);
        }
    }

    //This function make to Staff can make sure for customer when they go to delivery refund product back again
    @Override
    @Transactional
    public Refund turnBackRefundProducts(int refundId) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isEmpty()) {
            throw new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND);
        }
        Refund refund = refundOptional.get();
        if (refund.getRefundStatus() == RefundStatus.CANNOT_ACCEPT_REFUND_REQUEST) {
            refund.setRefundStatus(RefundStatus.DELIVERY_TO_TURN_BACK);
            return refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST_STATUS);
        }
    }

    // This function make to Staff can prove to customer they complete Delivey Refund Product for them
    @Override
    public Refund completeDeliveryBackRefundOrder(int refundId, MultipartFile imgShip) {
        Optional<Refund> refundOptional = refundRepository.findById(refundId);
        if (refundOptional.isEmpty()) {
            throw new AppException(ErrorCode.NO_REFUND_REQUEST_FOUND);
        }
        Refund refund = refundOptional.get();
        String imgUrl = firebaseService.upload(imgShip);
        if (refund.getRefundStatus() == RefundStatus.DELIVERY_TO_TURN_BACK && imgShip != null) {
            refund.setRefundStatus(RefundStatus.COMPLETE_TURN_BACK);
            refund.setStaffReceivedImage(imgUrl);
            return refundRepository.save(refund);
        } else {
            throw new AppException(ErrorCode.INVALID_REFUND_REQUEST_STATUS);
        }
    }


}

