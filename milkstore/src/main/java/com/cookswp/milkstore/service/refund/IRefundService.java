package com.cookswp.milkstore.service.refund;

import com.cookswp.milkstore.pojo.dtos.RefundModel.RefundDTO;
import com.cookswp.milkstore.pojo.entities.Refund;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IRefundService {

    Refund getRefundRequestByUserId(int userId);

    List<Refund> getAllRefundRequest();

    Refund updateRefundImage(int refundId, MultipartFile refundImage);


    //Customer
    Refund cancelRefundRequestForCustomer(int refundId);

    Refund createRefundRequest(int userID, RefundDTO refundDTO, MultipartFile refundImageFile);


    //Staff
    Refund canNotConfirmRefundRequest(int refundId, String cancelReason);

    Refund confirmRequestRefund(int refundId);

    Refund completeTakingRefundOrder(int refundId, MultipartFile refundEvidence);

    Refund changeStatusToRefundMoney(int refundId, String staffNote);

    Refund changeStatusToShopProcess(int refundId);

    Refund denyRequestRefund(int refundId, MultipartFile denyImage, String staffRejectReason);

    Refund turnBackRefundProducts(int refundId);

    Refund completeDeliveryBackRefundOrder(int refundId, MultipartFile imgShip);

}
