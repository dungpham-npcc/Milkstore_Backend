package com.cookswp.milkstore.repository.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <Order, String> {

    Optional<Order> findById(String id);

    List<Order> findByUserId (int userId);

    @Query("SELECT COUNT(o.id) AS numberOfOrders FROM Order o WHERE o.orderStatus = :orderStatus")
    Long getNumberOfOrdersByStatus(@Param("orderStatus") Status orderStatus);

    @Query("SELECT COUNT(DISTINCT o.id) AS totalOrders FROM Order o")
    Long getTotalOrders();

    @Query("SELECT SUM(o.totalPrice) AS totalRevenue FROM Order o")
    Double getTotalRevenue();

    @Query("SELECT o.orderStatus, COUNT(o.id) AS orderCount FROM Order o GROUP BY o.orderStatus")
    List<Object[]> getOrderStatusBreakdown();

    @Query("SELECT AVG(o.totalPrice) AS averageRevenuePerOrder FROM Order o")
    Double getAverageRevenuePerOrder();

    @Query("SELECT COUNT(o.id) FROM Order o WHERE MONTH(o.orderDate) BETWEEN :startMonth AND :endMonth")
    Long getOrdersByMonth(@Param("startMonth") Integer startMonth, @Param("endMonth") Integer endMonth);

    @Query("SELECT MONTH(o.orderDate), COUNT(o.id) FROM Order o WHERE YEAR(o.orderDate) = :year GROUP BY MONTH(o.orderDate)")
    List<Object[]> getOrderCountsByMonth(@Param("year") int year);


}