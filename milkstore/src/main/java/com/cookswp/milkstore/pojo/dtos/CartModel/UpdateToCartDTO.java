package com.cookswp.milkstore.pojo.dtos.CartModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateToCartDTO implements Serializable {

    private int product_id;
    private int quantity;


}
