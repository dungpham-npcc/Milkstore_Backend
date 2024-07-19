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

    @Autowired
    private RefundService refundService;

    @RequestMapping(method = RequestMethod.POST, value = "/create", consumes="multipart/form-data")
    public ResponseData<Refund> createRefundRequest(
            @RequestParam int userID,
            @RequestParam("refundImageFile") MultipartFile refundImageFile,
            @ModelAttribute RefundDTO refundDTO) {
        Refund refund = refundService.createRefundRequest(userID, refundDTO, refundImageFile);
        return new ResponseData<>(HttpStatus.OK.value(), "Create Refund Request Successful", refund);
    }

    @GetMapping("/{userId}")
    public ResponseData<Optional<Refund>> getRefundRequestByUserId(@PathVariable int userId) {
        Optional<Refund> refund = refundService.getRefundRequestByUserId(userId);
        return new ResponseData<>(HttpStatus.OK.value(), "Get Refund Request By User ID", refund);
    }

    @GetMapping("/all")
    public ResponseData<List<Refund>> getAllRefundRequests() {
        List<Refund> refunds = refundService.getAllRefundRequest();
        return new ResponseData<>(HttpStatus.OK.value(), "Get All Refund Requests", refunds);
    }

    @PutMapping("/{refundId}/updateImage")
    public ResponseData<Refund> updateRefundImage(
            @PathVariable int refundId,
            @RequestParam("refundImage") MultipartFile refundImage) {
        Refund refund = refundService.updateRefundImage(refundId, refundId, refundImage);
        return new ResponseData<>(HttpStatus.OK.value(), "Update Refund Image Successful", refund);
    }

    @DeleteMapping("/{refundId}/cancel")
    public ResponseData<Refund> cancelRefundRequestForCustomer(@PathVariable int refundId) {
        Refund refund = refundService.cancelRefundRequestForCustomer(refundId);
        return new ResponseData<>(HttpStatus.OK.value(), "Cancel Refund Request Successful", refund);
    }

    @PostMapping("/{refundId}/deny")
    public ResponseData<Refund> denyRequestRefund(
            @PathVariable int refundId,
            @RequestParam("denyImage") MultipartFile denyImage,
            @RequestParam String staffRejectReason) {
        Refund refund = refundService.denyRequestRefund(refundId, denyImage, staffRejectReason);
        return new ResponseData<>(HttpStatus.OK.value(), "Deny Refund Request Successful", refund);
    }

    @PostMapping("/{refundId}/confirm")
    public ResponseData<Refund> confirmRequestRefund(@PathVariable int refundId) {
        Refund refund = refundService.confirmRequestRefund(refundId);
        return new ResponseData<>(HttpStatus.OK.value(), "Confirm Refund Request Successful", refund);
    }

    @PostMapping("/{refundId}/completeTaking")
    public ResponseData<Refund> completeTakingRefundOrder(
            @PathVariable int refundId,
            @RequestParam("refundEvidence") MultipartFile refundEvidence) {
        Refund refund = refundService.completeTakingRefundOrder(refundId, refundEvidence);
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Taking Refund Order Successful", refund);
    }

    @PostMapping("/{refundId}/shopProcess")
    public ResponseData<Refund> changeStatusToShopProcess(@PathVariable int refundId) {
        Refund refund = refundService.changeStatusToShopProcess(refundId);
        return new ResponseData<>(HttpStatus.OK.value(), "Change Status To Shop Process Successful", refund);
    }

    @PostMapping("/{refundId}/refundMoney")
    public ResponseData<Refund> changeStatusToRefundMoney(
            @PathVariable int refundId,
            @RequestParam String staffNote) {
        Refund refund = refundService.changeStatusToRefundMoney(refundId, staffNote);
        return new ResponseData<>(HttpStatus.OK.value(), "Change Status To Refund Money Successful", refund);
    }

    @PostMapping("/{refundId}/turnBack")
    public ResponseData<Refund> turnBackRefundProducts(@PathVariable int refundId) {
        Refund refund = refundService.turnBackRefundProducts(refundId);
        return new ResponseData<>(HttpStatus.OK.value(), "Turn Back Refund Products Successful", refund);
    }

    @PostMapping("/{refundId}/completeDelivery")
    public ResponseData<Refund> completeDeliveryBackRefundOrder(
            @PathVariable int refundId,
            @RequestParam("imgShip") MultipartFile imgShip) {
        Refund refund = refundService.completeDeliveryBackRefundOrder(refundId, imgShip);
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Delivery Back Refund Order Successful", refund);
    }
}


