package com.cookswp.milkstore.api;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.order.OrderService;
import com.cookswp.milkstore.utils.AuthorizationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cookswp.milkstore.pojo.entities.Order;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class StatisticsController {

    @Autowired
    private final OrderService orderService;

    @GetMapping("/{orderStatus}/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Long> getNumberOfOrdersByStatus(@PathVariable String orderStatus){
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Retrieved successfully!", orderService.getNumberOfOrdersByStatus(orderStatus));
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Status does not exist!");
        }
    }

    @GetMapping("/orders/total")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Long> getTotalOrders() {
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved total orders successfully!", orderService.getTotalOrders());
    }

    @GetMapping("/orders/revenue/total")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Double> getTotalRevenue() {
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved total revenue successfully!", orderService.getTotalRevenue());

    }

    @GetMapping("/orders/status/breakdown")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Map<Status, Long>> getOrderStatusBreakdown() {
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved order status breakdown successfully!", orderService.getOrderStatusBreakdown());
    }

    @GetMapping("/orders/revenue/average")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Double> getAverageRevenuePerOrder() {
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved average revenue per order successfully!", orderService.getAverageRevenuePerOrder());
    }

    @GetMapping("/orders/by-month/{startMonth}/{endMonth}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Long> getOrdersByMonth(@PathVariable int startMonth, @PathVariable int endMonth) {
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved orders by month successfully!", orderService.getOrdersByMonth(startMonth, endMonth));
    }

    @GetMapping("/orders/by-year/{year}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Map<Integer, Long>> getOrdersByYear(@PathVariable int year) {
        AuthorizationUtils.checkAuthorization("ADMIN", "MANAGER");
        return new ResponseData<>(HttpStatus.OK.value(), "Retrieved orders by month successfully!", orderService.getOrderCountsForYear(year));
    }

}
