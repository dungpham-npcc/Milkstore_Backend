package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface IProductService {

    Product createProduct(ProductDTO product, MultipartFile productImageFile);

    Product updateProduct(int productID, ProductDTO product, boolean checkDuplicateName);

    Product updateProductImage(int productID, MultipartFile productImage);

    void deleteProduct(int id);

    Product getProductById(int id);

    List<Product> getAllProducts();

    List<Product> searchProduct(String keyword);

    void reduceQuantityProduct(int productId, int quantity);


}
