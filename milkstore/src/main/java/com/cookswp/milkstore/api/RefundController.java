package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.RefundModel.RefundDTO;
import com.cookswp.milkstore.pojo.entities.Refund;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.refund.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/refund")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create", consumes = "multipart/form-data")
    public ResponseData<Refund> createRefundRequest(@RequestParam int userID, @RequestParam("refundImageFile") MultipartFile refundImageFile, @ModelAttribute RefundDTO refundDTO) {
        return new ResponseData<>(HttpStatus.OK.value(), "Create Refund Request Successful", refundService.createRefundRequest(userID, refundDTO, refundImageFile));
    }

    @GetMapping("/{userId}")
    public ResponseData<Refund> getRefundRequestByUserId(@PathVariable int userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get Refund Request By User ID", refundService.getRefundRequestByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseData<List<Refund>> getAllRefundRequests() {
        return new ResponseData<>(HttpStatus.OK.value(), "Get All Refund Requests", refundService.getAllRefundRequest());
    }

    @PatchMapping("/{refundId}/updateImage")
    public ResponseData<Refund> updateRefundImage(@PathVariable int refundId, @RequestParam("refundImage") MultipartFile refundImage) {
        return new ResponseData<>(HttpStatus.OK.value(), "Update Refund Image Successful", refundService.updateRefundImage(refundId, refundImage));
    }

    @PatchMapping("/{refundId}/cancel")
    public ResponseData<Refund> cancelRefundRequestForCustomer(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Cancel Refund Request Successful", refundService.cancelRefundRequestForCustomer(refundId));
    }

    @PatchMapping("/{refundId}/deny")
    public ResponseData<Refund> denyRequestRefund(@PathVariable int refundId, @RequestParam("denyImage") MultipartFile denyImage, @RequestParam String staffRejectReason) {
        return new ResponseData<>(HttpStatus.OK.value(), "Deny Refund Request Successful", refundService.denyRequestRefund(refundId, denyImage, staffRejectReason));
    }

    @PatchMapping("/{refundId}/confirm")
    public ResponseData<Refund> confirmRequestRefund(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Confirm Refund Request Successful", refundService.confirmRequestRefund(refundId));
    }

    @PatchMapping("/{refundId}/completeTaking")
    public ResponseData<Refund> completeTakingRefundOrder(@PathVariable int refundId, @RequestParam("refundEvidence") MultipartFile refundEvidence) {
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Taking Refund Order Successful", refundService.completeTakingRefundOrder(refundId, refundEvidence));
    }

    @PatchMapping("/{refundId}/shopProcess")
    public ResponseData<Refund> changeStatusToShopProcess(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Change Status To Shop Process Successful", refundService.changeStatusToShopProcess(refundId));
    }

    @PatchMapping("/{refundId}/refundMoney")
    public ResponseData<Refund> changeStatusToRefundMoney(@PathVariable int refundId, @RequestParam String staffNote) {
        return new ResponseData<>(HttpStatus.OK.value(), "Change Status To Refund Money Successful", refundService.changeStatusToRefundMoney(refundId, staffNote));
    }

    @PatchMapping("/{refundId}/turnBack")
    public ResponseData<Refund> turnBackRefundProducts(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Turn Back Refund Products Successful", refundService.turnBackRefundProducts(refundId));
    }

    @PatchMapping("/{refundId}/completeDelivery")
    public ResponseData<Refund> completeDeliveryBackRefundOrder(@PathVariable int refundId, @RequestParam("imgShip") MultipartFile imgShip) {
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Delivery Back Refund Order Successful", refundService.completeDeliveryBackRefundOrder(refundId, imgShip));
    }
}


