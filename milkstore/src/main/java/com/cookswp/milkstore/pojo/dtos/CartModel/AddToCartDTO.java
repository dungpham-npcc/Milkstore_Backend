package com.cookswp.milkstore.pojo.dtos.CartModel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddToCartDTO implements Serializable {

//    @NotNull(message = "User ID can not be null")
//    private int user_id;
    @NotNull(message = "Product ID can not be null")
    private int product_id;
    @NotNull(message = "Quantity can not be null")
    private int quantity;

}
