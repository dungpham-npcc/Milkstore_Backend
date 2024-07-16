package com.cookswp.milkstore.service.shoppingcart;

import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCartDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModelDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCartDTO;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;

import java.util.List;

public interface IShoppingCartService {
    List<ShowCartModelDTO> getCartByUserId(int userId);

    ShoppingCart addToCart(AddToCartDTO addToCartDTO, int userId);

    ShoppingCart deleteItemFromCart(int cartId, int userId, int itemId);

    ShoppingCart updateItem(UpdateToCartDTO updateToCartDTO, int cartId, int userId);

    ShoppingCart findCartByUserID(int cartId);


}
