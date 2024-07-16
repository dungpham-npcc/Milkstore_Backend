package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.repository.post.PostRepository;
import com.cookswp.milkstore.repository.product.ProductRepository;
import com.cookswp.milkstore.repository.productCategory.ProductCategoryRepository;
import com.cookswp.milkstore.service.firebase.FirebaseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final FirebaseService firebaseService;
    private final Product product = new Product();
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, PostRepository postRepository, FirebaseService firebaseService, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.postRepository = postRepository;
        this.firebaseService = firebaseService;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Product createProduct(ProductDTO productRequest, MultipartFile productImageFile) {
        String imageURL = firebaseService.upload(productImageFile);
       // validProductRequest(productRequest, true);
        Product product = Product.builder()
                .categoryID(productRequest.getCategoryID())
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productImage(imageURL)
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .manuDate(productRequest.getManuDate())
                .expiDate(productRequest.getExpiDate())
                .deleteStatus(false)
                .visibilityStatus(true)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int productID, ProductDTO productRequest, boolean checkDuplicateName) {
        Product product = productRepository.getProductById(productID);
        if (product == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }


        validProductRequest(productRequest, checkDuplicateName);
        product.setCategoryID(productRequest.getCategoryID());
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setManuDate(productRequest.getManuDate());
        product.setExpiDate(productRequest.getExpiDate());
        product.setDeleteStatus(false);
        product.setVisibilityStatus(true);

        return productRepository.save(product);
    }

    @Override
    public Product updateProductImage(int productID, MultipartFile productImage){
        Product product = productRepository.getProductById(productID);
        if (product == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        String imageURL = firebaseService.upload(productImage);
        product.setProductImage(imageURL);
        return productRepository.save(product);
    }



    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.getProductById(id);
        if (product == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        product.setDeleteStatus(true);
        productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.getProductById(id);
        if (product == null) throw new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = productRepository.getAll();

        if (list == null) {
            throw new AppException(ErrorCode.PRODUCT_LIST_NOT_FOUND);
        }

        return list.stream()
                .peek(product -> {
                    if (product.getExpiDate().isBefore(LocalDate.now()) && product.isVisibilityStatus()) {
                        disableProduct(product.getProductID());
                    }
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<Product> searchProduct(String value) {
        List<Product> searchList = productRepository.searchProduct(value);
        if (searchList == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        else{
            return searchList;
        }
    }

    @Override
    public void reduceQuantityProduct(int productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getQuantity() < quantity) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_INVALID);
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        //add
    }

    private void validProductRequest(ProductDTO productRequest, boolean checkDuplicateName) {
        if (!productCategoryRepository.existsById(productRequest.getCategoryID())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        if (productRequest.getPrice() == null || productRequest.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException(ErrorCode.INVALID_PRICE);
        }
        if (productRequest.getProductDescription() == null || productRequest.getProductDescription().isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_DESCRIPTION_IS_NULL);
        }
        if (checkDuplicateName && productRepository.existsByProductName(productRequest.getProductName())) {
            throw new AppException(ErrorCode.PRODUCT_NAME_EXISTS);
        }
        boolean checkDate = product.dateBefore(productRequest.getManuDate(), productRequest.getExpiDate());
        if (!checkDate) {
            throw new AppException(ErrorCode.MANU_DATE_CAN_NOT_BEFORE_EXPI_DATE);
        }
        if (productRequest.getQuantity() < 0) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_INVALID);
        }
    }

    private void disableProduct(int productId){
        Product product = productRepository.getProductById(productId);
        if (product == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        product.setVisibilityStatus(false);
        productRepository.save(product);
    }

}
