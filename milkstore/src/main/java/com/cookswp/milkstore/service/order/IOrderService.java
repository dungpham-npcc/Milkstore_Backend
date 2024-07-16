package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;

import java.util.List;
import java.util.Map;


public interface IOrderService {

    List<Order> getAllOrders();

    Order getOrderById(String id);

    Order createOrder(int userID, CreateOrderRequest orderDTO);

    Order updateOrder(String id, OrderDTO orderDTO);

    void deleteOrder(String id);

    Order updateOrderStatus(String id);

    List<Order> getAll();

    Long getNumberOfOrdersByStatus(String status);
    Long getTotalOrders();
    Double getTotalRevenue();
    Map<Status, Long> getOrderStatusBreakdown();
    Double getAverageRevenuePerOrder();
    Long getOrdersByMonth(int startMonth, int endMonth);
    Map<Integer, Long> getOrderCountsForYear(int year);

}