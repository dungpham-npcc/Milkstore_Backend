package com.cookswp.milkstore.service.shoppingcart;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCartDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModelDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCartDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import com.cookswp.milkstore.repository.product.ProductRepository;
import com.cookswp.milkstore.repository.shoppingCart.ShoppingCartRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public List<ShowCartModelDTO> getCartByUserId(int userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        List<ShowCartModelDTO.CartItemModel> items = shoppingCart.getItems().stream()
                .map(item -> {
                    ShowCartModelDTO.CartItemModel cartItemModel = new ShowCartModelDTO.CartItemModel();
                    cartItemModel.setProductId(item.getProduct().getProductID());
                    cartItemModel.setProductName(item.getProduct().getProductName());
                    cartItemModel.setQuantity(item.getQuantity());
                    cartItemModel.setPrice(item.getProduct().getPrice());
                    cartItemModel.setProductImage(item.getProduct().getProductImage());//Update image filed in cart
                    return cartItemModel;
                })
                .collect(Collectors.toList());

        ShowCartModelDTO showCartModelDTO = new ShowCartModelDTO();
        showCartModelDTO.setCartId(shoppingCart.getId());
        showCartModelDTO.setUserId(userId);
        showCartModelDTO.setItems(items);

        return List.of(showCartModelDTO);
    }


    @Override
    @Transactional
    public ShoppingCart addToCart(AddToCartDTO addToCartDTO, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    newCart.setItems(new ArrayList<>()); //Ensure not null
                    return shoppingCartRepository.save(newCart);
                });

        Product product = productRepository.findById(addToCartDTO.getProduct_id())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        int quantityInStock = product.getQuantity();
        int requestedQuantity = addToCartDTO.getQuantity();

        //Check quantity from customer and in stock
        if (requestedQuantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }
        if (quantityInStock <= 10){
            throw new AppException(ErrorCode.PRODUCT_NOT_AVAILABLE);
        }

        Optional<ShoppingCartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductID() == product.getProductID())
                .findFirst();

        if (existingItemOpt.isPresent()) {
            ShoppingCartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + requestedQuantity;
            if (newQuantity > quantityInStock) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }
            existingItem.setQuantity(newQuantity);
            shoppingCartItemRepository.save(existingItem);
        } else {
            if (requestedQuantity > quantityInStock) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setShoppingCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(requestedQuantity);
            shoppingCartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        return shoppingCartRepository.save(cart);
    }


    @Override
    @Transactional
    public ShoppingCart deleteItemFromCart(int cartId, int userId, int itemId) {

        ShoppingCart cart = shoppingCartRepository.findByIdAndUserId(cartId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));


        ShoppingCartItem foundItem = null;
        for (ShoppingCartItem item : cart.getItems()) {
            if (item.getProduct().getProductID() == itemId) {
                foundItem = item;
                break;
            }
        }

        if (foundItem == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // Loại bỏ mục khỏi giỏ hàng
        cart.getItems().remove(foundItem);

        // Xóa mục khỏi repository
        shoppingCartItemRepository.delete(foundItem);

        // Lưu lại giỏ hàng sau khi đã xóa mục
        return shoppingCartRepository.save(cart);
    }

    @Transactional
    public void clearCartByUserId(int userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        // Xóa tất cả các mục trong giỏ hàng
        shoppingCartItemRepository.deleteAllInBatch(cart.getItems());

        // Xóa giỏ hàng
        shoppingCartRepository.delete(cart);
    }

    @Override
    @Transactional
    public ShoppingCart updateItem(UpdateToCartDTO updateToCartDTO, int cartId, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByIdAndUserId(cartId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        ShoppingCartItem foundItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductID() == updateToCartDTO.getProduct_id())
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND_IN_CART));

        Product product = foundItem.getProduct();
        int requestedQuantity = updateToCartDTO.getQuantity();
        int availableQuantity = product.getQuantity();

        //Check quantity from user greater than in stock
        if (requestedQuantity > availableQuantity) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        if (requestedQuantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        if (availableQuantity <= 10) {
            throw new AppException(ErrorCode.PRODUCT_NOT_AVAILABLE);
        }

        foundItem.setQuantity(requestedQuantity);
        shoppingCartItemRepository.save(foundItem);

        return shoppingCartRepository.save(cart);
    }


    @Override
    public ShoppingCart findCartByUserID(int cartId) {
        return shoppingCartRepository.findById(cartId).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
    }
}
