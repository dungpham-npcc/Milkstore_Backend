package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCartDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModelDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCartDTO;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.shoppingcart.ShoppingCartService;
import com.cookswp.milkstore.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{userId}")
    public ResponseData<ShowCartModelDTO> getCartByUserId(@PathVariable int userId) {
        ShowCartModelDTO cart = shoppingCartService.getCartByUserId(userId).stream().findFirst().orElse(null);
        return new ResponseData<>(HttpStatus.OK.value(), "GET CART BY ID", cart);
    }

    @PostMapping
    public ResponseData<ShowCartModelDTO> createCart(){
        return new ResponseData<>(HttpStatus.OK.value(), "Cart created successfully!", mapper.map(shoppingCartService.createCart(userService.getCurrentUser().getUserId()), ShowCartModelDTO.class));
    }

    @DeleteMapping
    public ResponseData<String> deleteTemporaryCart(){
        shoppingCartService.deleteCart(userService.getCurrentUser().getUserId());
        return new ResponseData<>((HttpStatus.OK.value()), "Cart deleted successfully!", null);
    }

    @PostMapping("/{userId}/items")
    public ResponseData<ShoppingCart> addItemToCart(@PathVariable int userId, @RequestBody AddToCartDTO addToCartDTO) {
        ShoppingCart cart = shoppingCartService.addToCart(addToCartDTO, userId);
        return new ResponseData<>(HttpStatus.OK.value(), "Add Item To Cart Successful", cart);
    }

    @GetMapping("/temp")
    public ResponseData<ShowCartModelDTO> getTemporaryCartByUserId() {
        ShowCartModelDTO cart = shoppingCartService.getTemporaryCartByUserId(userService.getCurrentUser().getUserId());
        return new ResponseData<>(HttpStatus.OK.value(), "GET CART BY ID", cart);
    }

    @PostMapping("/temp/{userId}/items")
    public ResponseData<ShoppingCart> addItemToTemporaryCart(@PathVariable int userId, @RequestBody AddToCartDTO addToCartDTO) {
        ShoppingCart cart = shoppingCartService.addToTemporaryCart(addToCartDTO, userId);
        return new ResponseData<>(HttpStatus.OK.value(), "Add Item To Cart Successful", cart);
    }

    @PutMapping("/{cartId}/items")
    public ResponseData<ShoppingCart> updateItemInCart(@PathVariable int cartId, @RequestParam int userId, @RequestBody UpdateToCartDTO updateToCartDTO) {
        ShoppingCart cart = shoppingCartService.updateItem(updateToCartDTO, cartId, userId);
        return new ResponseData<>(HttpStatus.OK.value(), "Update Item In Cart Successful", cart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseData<ShoppingCart> deleteItemInCart(@PathVariable int cartId, @RequestParam int userId, @RequestParam int itemId) {
        ShoppingCart cart = shoppingCartService.deleteItemFromCart(cartId, userId, itemId);
        return new ResponseData<>(HttpStatus.OK.value(), "Delete Item From Cart Successful", cart);
    }


}
