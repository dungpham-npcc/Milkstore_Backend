package com.cookswp.milkstore.repository.product;

import com.cookswp.milkstore.pojo.entities.Product;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.deleteStatus = FALSE")
    List<Product> getAll();

    @Query("SELECT p FROM Product p WHERE p.deleteStatus = FALSE AND p.productID =:id")
    Product getProductById(@Param("id") int id);

    @Query("SELECT p FROM Product p WHERE p.deleteStatus = FALSE AND p.productName LIKE %:value%")
    List<Product> searchProduct(@Param("value") String value);

//    @Query("SELECT p FROM Product p WHERE p.status = TRUE  AND p.orderID = :orderID")
//    Product getProductByOrderID(@Param("orderID") String orderID);


    boolean existsByCategoryID(int categoryID);

    boolean existsByProductName(String name);

}

