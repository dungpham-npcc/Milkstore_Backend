package com.cookswp.milkstore.repository.shoppingCart;

import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    Optional<ShoppingCart> findByUserId(int userId);

    Optional<ShoppingCart> findByIdAndUserId(int id, int userId);

    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.id = :cartId")
    Optional<ShoppingCart> findCartByIdWithItems(@Param("cartId") int cartId);

    Optional<ShoppingCart> findById(Integer integer);
}