package com.cookswp.milkstore.pojo.dtos.ProductModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductDTO {

    @NotNull(message = "Product category can not be null")
    private int categoryID;

    //validate product name unique
    @NotNull(message = "Product name can not be null")
    private String productName;

    @NotNull(message = "Product description can not be null")
    private String productDescription;

    @NotNull(message = "Manufacturing date can not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manuDate;

    @NotNull(message = "Expiration date can not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiDate;

    @NotNull(message = "Quantity can not be null")
    @Size(min = 1, message = "Quantity can not lower than 1")
    private int quantity;

    @NotNull(message = "Price can not be null")
    @Size(min = 1, message = "Price can not lower than 1")
    private BigDecimal price;

    private boolean visibilityStatus;

}
