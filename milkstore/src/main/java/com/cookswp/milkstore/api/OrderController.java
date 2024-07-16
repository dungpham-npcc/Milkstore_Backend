package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;
import com.cookswp.milkstore.pojo.entities.OrderItem;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    //This API use to get all orders
    @GetMapping
    public ResponseData<List<Order>> getAllOrders() {
        return new ResponseData<>(HttpStatus.OK.value(), "LIST ORDER", orderService.getAllOrders());
    }

    //This API use to get order with an order Id
    @GetMapping("/{orderId}")
    public ResponseData<Order> getOrderItemsByOrderId(@PathVariable String orderId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieve order items successfully", orderService.getOrderById(orderId));
    }

    //This API use to create Order
    @PostMapping("/{userID}")
    public ResponseData<Order> createOrder(@PathVariable int userID, @RequestBody CreateOrderRequest orderDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "CREATE ORDER", orderService.createOrder(userID, orderDTO));
    }

    //This API use to UpdateOrder information
    @PutMapping("/{id}")
    public ResponseData<Order> updateOrder(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "UPDATE ORDER", orderService.updateOrder(id, orderDTO));
    }

    //This API use to DeleteOrder for the staff
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "DELETE ORDER", null);
    }

    //This API use to update the status in the slide can not interact
    @PutMapping("/{orderID}")
    public ResponseData<Order> updateOrderStatus(@PathVariable String orderID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Update successfully", orderService.updateOrderStatus(orderID));
    }

    //This API use to get Order Status for customer in Order tag
    @GetMapping("/user/{userId}")
    public ResponseData<List<Order>> getOrderByUserId(@PathVariable int userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get Order By User Id successfully", orderService.getOrderByAnUserId(userId));
    }

    //This API use to confirm Order Status to change Shipping Status
    @PutMapping("/confirm-shipping/{orderId}")
    public ResponseData<Order> confirmOrderToShipping (@PathVariable String orderId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Confirm successfully", orderService.confirmOrderToShipping(orderId));
    }

    //This API use to cancel Order to Shipping and update Status for     this
    @PutMapping("/cancel/{orderId}")
    public ResponseData<Order> cancelOrderToShipping (@PathVariable String orderId, @RequestParam String reason) {
        return new ResponseData<>(HttpStatus.OK.value(), "Cancel successfully", orderService.cancelOrder(orderId, reason));
    }
        //Add new
    //This API use to confirm the exchange is complete with the image evidence
    @PutMapping("/complete-order/{orderId}")
    public ResponseData<Order> completeOrder (@PathVariable String orderId, @RequestParam("EvidenceImage") MultipartFile evidenceImage) {
        return new ResponseData<>(HttpStatus.OK.value(), "Complete Exchange successfully", orderService.completeOrderTransaction(orderId, evidenceImage));
    }

    @PutMapping("/change-status/{orderID}")
    public ResponseData<Order> changeOrderStatus(@PathVariable String orderID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Change status from CANNOT DELIVERY to DELIVERY successfully", orderService.cannotOrderInDelivery(orderID));
    }




}