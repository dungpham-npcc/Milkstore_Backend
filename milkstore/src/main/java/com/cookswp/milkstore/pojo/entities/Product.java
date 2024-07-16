package com.cookswp.milkstore.pojo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Setter@Getter
@Table(name = "milk_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productID;

//    @Column(name = "orderID", nullable = true)
//    private String orderID;

    @Column(name ="category_id", nullable = false)
    private int categoryID;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Column(name = "quantity_in_stock", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "manu_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manuDate;

    @Column(name = "expi_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiDate;

    @Column(name = "delete_status")
    private boolean deleteStatus = false;

    @Column(name = "visibility_status")
    private boolean visibilityStatus = true;

    //return true if manu date is before expi date
    public boolean dateBefore(LocalDate manuDate, LocalDate expiDate){
        return manuDate.isBefore(expiDate);
    }
}
