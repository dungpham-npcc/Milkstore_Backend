package com.cookswp.milkstore.repository.shoppingCartItem;

import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
    @Query("select p from ShoppingCartItem p where p.product.deleteStatus = true")
    List<ShoppingCartItem> findById(String orderId);

    @Query("select p from ShoppingCartItem p where p.product.productID =: id")
    ShoppingCartItem getShoppingCartItemById (@Param("id") int id);

    @Query("select p from ShoppingCartItem p where p.product.productName =:value")
    List<ShoppingCartItem> searchItemFromCart(@Param("value") String value);

}
