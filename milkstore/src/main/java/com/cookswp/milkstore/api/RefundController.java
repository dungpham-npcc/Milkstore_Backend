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

    //Todo This function make to customer create a refund request
    @RequestMapping(method = RequestMethod.POST, value = "/create", consumes = "multipart/form-data")
    public ResponseData<Refund> createRefundRequest(@RequestParam int userID, @RequestParam("refundImageFile") MultipartFile refundImageFile, @ModelAttribute RefundDTO refundDTO) {
        return new ResponseData<>(HttpStatus.OK.value(), "Create Refund Request Successful", refundService.createRefundRequest(userID, refundDTO, refundImageFile));
    }

    //Todo This function make to All user can get their refund request by user ID
    @GetMapping("/{userId}")
    public ResponseData<List<Refund>> getRefundRequestByUserId(@PathVariable int userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get Refund Request By User ID", refundService.getRefundRequestByUserId(userId));
    }

    //Todo This function make to Staff can get all refund request
    @GetMapping("/all")
    public ResponseData<List<Refund>> getAllRefundRequests() {
        return new ResponseData<>(HttpStatus.OK.value(), "Get All Refund Requests", refundService.getAllRefundRequest());
    }

    //Todo This function make to customer can change their refund Image again if they have request from staff
    @PatchMapping("/{refundId}/updateImage")
    public ResponseData<Refund> updateRefundImage(@PathVariable int refundId, @RequestParam("refundImage") MultipartFile refundImage) {
        return new ResponseData<>(HttpStatus.OK.value(), "Update Refund Image Successful", refundService.updateRefundImage(refundId, refundImage));
    }

    //Todo This function make to customer can cancel refund request in their side
    @PatchMapping("/{refundId}/cancel")
    public ResponseData<Refund> cancelRefundRequestForCustomer(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Cancel Refund Request Successful", refundService.cancelRefundRequestForCustomer(refundId));
    }

    //Todo this function make Staff can deny RefundRequest from customer phase 1
    @PatchMapping("/{refundId}/deny1")
    public ResponseData<Refund> denyRequestRefund1 (@PathVariable int refundId, @RequestParam String cancelReason1 ) {
        return new ResponseData<>(HttpStatus.OK.value(), "Deje Refund Request first time successful", refundService.canNotConfirmRefundRequest(refundId, cancelReason1));
    }


    //Todo This function make to Staff can deny RefundRequest from customer phase 2
    @PatchMapping("/{refundId}/deny2")
    public ResponseData<Refund> denyRequestRefund2(@PathVariable int refundId, @RequestParam("denyImage") MultipartFile denyImage, @RequestParam String staffRejectReason) {
        return new ResponseData<>(HttpStatus.OK.value(), "Deny Refund Request Successful", refundService.denyRequestRefund(refundId, denyImage, staffRejectReason));
    }

    //Todo This function make to staff can confirm Refund Request
    @PatchMapping("/{refundId}/confirm")
    public ResponseData<Refund> confirmRequestRefund(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Confirm Refund Request Successful", refundService.confirmRequestRefund(refundId));
    }

    //Todo This function make to staff can confirm
    @PatchMapping("/{refundId}/completeTaking")
    public ResponseData<Refund> completeTakingRefundOrder(@PathVariable int refundId, @RequestParam("refundEvidence") MultipartFile refundEvidence) {
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Taking Refund Order Successful", refundService.completeTakingRefundOrder(refundId, refundEvidence));
    }

    //Todo This function make to staff change status from completeTakingRefundOrder to ShopProcess and show it customer
    @PatchMapping("/{refundId}/shopProcess")
    public ResponseData<Refund> changeStatusToShopProcess(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Change Status To Shop Process Successful", refundService.changeStatusToShopProcess(refundId));
    }

    //Todo This function make to Staff can change status to refund money
    @PatchMapping("/{refundId}/refundMoney")
    public ResponseData<Refund> changeStatusToRefundMoney(@PathVariable int refundId, @RequestParam String staffNote) {
        return new ResponseData<>(HttpStatus.OK.value(), "Change Status To Refund Money Successful", refundService.changeStatusToRefundMoney(refundId, staffNote));
    }

    //Todo This function make to Staff can make sure for customer when they go to delivery refund product back again or bảo hành cho khách
    @PatchMapping("/{refundId}/turnBack")
    public ResponseData<Refund> turnBackRefundProducts(@PathVariable int refundId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Turn Back Refund Products Successful", refundService.turnBackRefundProducts(refundId));
    }

    //Todo This function make to Staff can prove to customer they complete Delivey Refund Product for them
    @PatchMapping("/{refundId}/completeDelivery")
    public ResponseData<Refund> completeDeliveryBackRefundOrder(@PathVariable int refundId, @RequestParam("imgShip") MultipartFile imgShip) {
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Delivery Back Refund Order Successful", refundService.completeDeliveryBackRefundOrder(refundId, imgShip));
    }
}


