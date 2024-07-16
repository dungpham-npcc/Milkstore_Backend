//package com.cookswp.milkstore.service.product;
//
//import com.cookswp.milkstore.exception.AppException;
//import com.cookswp.milkstore.exception.ErrorCode;
//import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
//import com.cookswp.milkstore.pojo.entities.Product;
//import com.cookswp.milkstore.repository.post.PostRepository;
//import com.cookswp.milkstore.repository.product.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
//
//    @InjectMocks
//    private ProductService productService;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private PostRepository postRepository;
//
//    //Bad case về phần tạo product
//    @Test
//    void testCreateProduct_ProductNameMustBeUnique() {
//        when(productRepository.existsByProductName("name")).thenReturn(true);
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .quantity(10)
//                    .postID(1)
//                    .categoryID(1)
//                    .price(BigDecimal.valueOf(100))
//                    .productImage("image.jpg")
//                    .build());
//        });
//
//        assertEquals("Product name already exists in the system", exception.getMessage());
//    }
//    //Happy case về phần tạo product
//    @Test
//    void testCreateProduct(){
//        Product product = Product.builder()
//                .productName("name")
//                .productDescription("description")
//                .quantity(10)
//                .postID(1)
//                .categoryID(1)
//                .price(BigDecimal.valueOf(100))
//                .productImage("image.jpg")
//                .build();
//        ProductDTO productDTO = ProductDTO.builder()
//                .productName("name")
//                .productDescription("description")
//                .quantity(10)
//                .postID(1)
//                .categoryID(1)
//                .price(BigDecimal.valueOf(100))
//                .productImage("image.jpg")
//                .build();
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//        Product createProduct = productService.createProduct(productDTO);
//
//        assertNotNull(createProduct);
//        assertEquals(product.getProductName(), createProduct.getProductName());
//        assertEquals(product.getProductDescription(), createProduct.getProductDescription());
//    }
//
//    //Bad Case về phần Descript cần phải có
//    @Test
//    void testCreateProduct_ProductDescriptionIsRequired() {
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription(null)
//                    .productImage("image.jpg")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertEquals("Product description is required", exception.getMessage());
//    }
//    //Happy Case về phần Descript cần phải có
//    @Test
//    void testCreateProduct_ProductDescriptionIsValid() {
//        // Tạo đối tượng ProductDTO hợp lệ với des của product không null
//        ProductDTO productDTO = ProductDTO.builder()
//                .productName("name")
//                .productDescription("description")
//                .productImage("image.jpg")
//                .categoryID(1)
//                .postID(1)
//                .quantity(10)
//                .price(BigDecimal.valueOf(100))
//                .build();
//
//        // Giả lập save product và trả về product đã lưu
//        Product product = Product.builder()
//                .productName(productDTO.getProductName())
//                .productDescription(productDTO.getProductDescription())
//                .productImage(productDTO.getProductImage())
//                .categoryID(productDTO.getCategoryID())
//                .postID(productDTO.getPostID())
//                .quantity(productDTO.getQuantity())
//                .price(productDTO.getPrice())
//                .build();
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        // Gọi method createProduct và check không có ngoại lệ nào được ném ra
//        assertDoesNotThrow(() -> {
//            Product createdProduct = productService.createProduct(productDTO);
//
//            // Check product được trả về từ createProduct() trùng khớp với sản phẩm đã lưu
//            assertNotNull(createdProduct);
//            assertEquals(productDTO.getProductName(), createdProduct.getProductName());
//            assertEquals(productDTO.getProductDescription(), createdProduct.getProductDescription());
//            assertEquals(productDTO.getProductImage(), createdProduct.getProductImage());
//            assertEquals(productDTO.getCategoryID(), createdProduct.getCategoryID());
//            assertEquals(productDTO.getPostID(), createdProduct.getPostID());
//            assertEquals(productDTO.getQuantity(), createdProduct.getQuantity());
//            assertEquals(productDTO.getPrice(), createdProduct.getPrice());
//        });
//    }
//
//    //Bad case của test case về JPG
//    @Test
//    void testCreateProduct_ProductImageMustBeAsTypeJPEG_JPG() {
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.exe")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertEquals("Invalid product image", exception.getMessage());
//    }
//    //Happy case của test case về JPG
//    @Test
//    void testCreateProduct_ProductImageIsValid() {
//
//        ProductDTO productDTO = ProductDTO.builder()
//                .productName("name")
//                .productDescription("description")
//                .productImage("image.jpg")
//                .categoryID(1)
//                .postID(1)
//                .quantity(10)
//                .price(BigDecimal.valueOf(100))
//                .build();
//
//        Product product = Product.builder()
//                .productName(productDTO.getProductName())
//                .productDescription(productDTO.getProductDescription())
//                .productImage(productDTO.getProductImage())
//                .categoryID(productDTO.getCategoryID())
//                .postID(productDTO.getPostID())
//                .quantity(productDTO.getQuantity())
//                .price(productDTO.getPrice())
//                .build();
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        assertDoesNotThrow(() -> {
//            Product createdProduct = productService.createProduct(productDTO);
//
//            assertNotNull(createdProduct);
//            assertEquals(productDTO.getProductName(), createdProduct.getProductName());
//            assertEquals(productDTO.getProductDescription(), createdProduct.getProductDescription());
//            assertEquals(productDTO.getProductImage(), createdProduct.getProductImage());
//            assertEquals(productDTO.getCategoryID(), createdProduct.getCategoryID());
//            assertEquals(productDTO.getPostID(), createdProduct.getPostID());
//            assertEquals(productDTO.getQuantity(), createdProduct.getQuantity());
//            assertEquals(productDTO.getPrice(), createdProduct.getPrice());
//        });
//    }
//
//    //Bad case của test case về ProductName trong test Edit
//    @Test
//    void testEditProduct_ProductNameMustBeUnique() {
//        when(productRepository.getProductById(1)).thenReturn(Product.builder()
//                .productName("name")
//                .productDescription("description")
//                .productImage("image.png")
//                .categoryID(1)
//                .postID(1)
//                .quantity(10)
//                .price(BigDecimal.valueOf(100))
//                .build());
//        when(productRepository.existsByProductName("name")).thenReturn(true);
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.updateProduct(1, ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertEquals("Product name already exists in the system", exception.getMessage());
//    }
//    //Happy case của test case ProductName trong test Edit
//    @Test
//    void testEditProduct_ProductNameIsUnique() {
//        // Giả lập sản phẩm có ID 1 trong hệ thống
//        Product existingProduct = Product.builder()
//                .productName("existing_name")
//                .productDescription("existing_description")
//                .productImage("existing_image.png")
//                .categoryID(1)
//                .postID(1)
//                .quantity(10)
//                .price(BigDecimal.valueOf(100))
//                .build();
//        when(productRepository.getProductById(1)).thenReturn(existingProduct);
//        when(productRepository.existsByProductName("name")).thenReturn(false); // Giả lập không tồn tại sản phẩm khác có tên "name"
//
//
//        ProductDTO updatedProductDTO = ProductDTO.builder()
//                .productName("name")
//                .productDescription("updated_description")
//                .productImage("updated_image.png")
//                .categoryID(1)
//                .postID(1)
//                .quantity(20)
//                .price(BigDecimal.valueOf(200))
//                .build();
//
//
//        Product updatedProduct = Product.builder()
//                .productID(1) // Giữ nguyên ID của sản phẩm cũ
//                .productName(updatedProductDTO.getProductName())
//                .productDescription(updatedProductDTO.getProductDescription())
//                .productImage(updatedProductDTO.getProductImage())
//                .categoryID(updatedProductDTO.getCategoryID())
//                .postID(updatedProductDTO.getPostID())
//                .quantity(updatedProductDTO.getQuantity())
//                .price(updatedProductDTO.getPrice())
//                .build();
//        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
//
//
//        assertDoesNotThrow(() -> {
//            Product editedProduct = productService.updateProduct(1, updatedProductDTO);
//
//            assertNotNull(editedProduct);
//            assertEquals(updatedProductDTO.getProductName(), editedProduct.getProductName());
//            assertEquals(updatedProductDTO.getProductDescription(), editedProduct.getProductDescription());
//            assertEquals(updatedProductDTO.getProductImage(), editedProduct.getProductImage());
//            assertEquals(updatedProductDTO.getCategoryID(), editedProduct.getCategoryID());
//            assertEquals(updatedProductDTO.getPostID(), editedProduct.getPostID());
//            assertEquals(updatedProductDTO.getQuantity(), editedProduct.getQuantity());
//            assertEquals(updatedProductDTO.getPrice(), editedProduct.getPrice());
//        });
//    }
//
//    //Bad case về Định dạng của ảnh
//    @Test
//    void testEditProduct_ProductImageMustBeAsType_JPEG_PNG() {
//        when(productRepository.getProductById(1)).thenReturn(Product.builder()
//                .productName("name")
//                .productDescription("description")
//                .quantity(10)
//                .postID(1)
//                .categoryID(1)
//                .price(BigDecimal.valueOf(100))
//                .productImage("image.jpg")
//                .build()
//        );
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.updateProduct(1, ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.exe")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertEquals("Invalid product image", exception.getMessage());
//    }
//    //Happy case về định dạng ảnh hợp lệ
//    @Test
//    void testEditProduct_ProductImageIsValid() {
//        // Giả lập sản phẩm có ID 1 với hình ảnh hợp lệ là image.jpg
//        when(productRepository.getProductById(1)).thenReturn(Product.builder()
//                .productName("name")
//                .productDescription("description")
//                .quantity(10)
//                .postID(1)
//                .categoryID(1)
//                .price(BigDecimal.valueOf(100))
//                .productImage("image.jpg")
//                .build());
//
//        // Không có ngoại lệ được ném ra khi chỉnh sửa sản phẩm với hình ảnh hợp lệ image.jpg hoặc image.png
//        assertDoesNotThrow(() -> {
//            productService.updateProduct(1, ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.jpg")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertDoesNotThrow(() -> {
//            productService.updateProduct(1, ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//    }
//
//    //Bad Case về Quantity
//    @Test
//    void testAddProductQuantityCanNotBeLessThanZero() {
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.createProduct(ProductDTO.builder()
//                    .price(BigDecimal.valueOf(100))
//                    .quantity(-100)
//                    .postID(1)
//                    .categoryID(1)
//                    .productImage("image.png")
//                    .productDescription("description")
//                    .productName("name")
//                    .build()
//            );
//        });
//        assertEquals("Quantity cannot be less than 0", exception.getMessage());
//    }
//    //Happy Case về Quanity
//    @Test
//    void testAddProduct_QuantityIsNotLessThanZero() {
//
//        assertDoesNotThrow(() -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(0)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertDoesNotThrow(() -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(100)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//    }
//
//
//    //Bad Case về price của product
//    @Test
//    void testAddProductPriceCanNotBeLessThanZero() {
//        AppException exception = assertThrows(AppException.class, () -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(-10))
//                    .build());
//        });
//
//        assertEquals("Price cannot be less than 0", exception.getMessage());
//    }
//    //Happy Case về price của product
//    @Test
//    void testAddProduct_PriceIsNotLessThanZero() {
//        assertDoesNotThrow(() -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(100))
//                    .build());
//        });
//
//        assertDoesNotThrow(() -> {
//            productService.createProduct(ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .productImage("image.png")
//                    .categoryID(1)
//                    .postID(1)
//                    .quantity(10)
//                    .price(BigDecimal.valueOf(0))
//                    .build());
//        });
//    }
//
//
//    //Bad Case về check Product tồn tại trong hệ thống
//    @Test
//    void testEditProduct_ProductMustExistsInTheSystem() {
//        // Giả lập rằng sản phẩm với ID 1 không tồn tại trong hệ thống
//        when(productRepository.getProductById(1)).thenReturn(null);
//
//        // Kiểm tra rằng ngoại lệ AppException sẽ được ném ra khi cập nhật sản phẩm với ID không tồn tại
//        AppException exception = assertThrows(AppException.class, () -> {
////            productService.getProductById(1);
//            productService.updateProduct(1, ProductDTO.builder()
//                    .productName("name")
//                    .productDescription("description")
//                    .quantity(10)
//                    .postID(1)
//                    .categoryID(1)
//                    .price(BigDecimal.valueOf(100))
//                    .productImage("image.jpg")
//                    .build());
//        });
//
//        // Kiểm tra msg của ngoại lệ
//        //Con mẹ m tại sao ko dùng OOP cho nhanh đi còn ngồi check thống qua string rồi bắt làm gì cho cực z ?????
//        assertEquals(ErrorCode.PRODUCT_NOT_FOUND.getMessage(), exception.getMessage());
////      assertEquals("Product ID not exists", exception.getMessage());
//
//    }
//    //Happy case về phần check Product tồn tại trong hệ thống
//    @Test
//    void testEditProduct_ProductExistsInTheSystem() {
//        // Inject product với ID 1 đã tồn tại trong hệ thống
//        Product existingProduct = Product.builder()
//                .productID(1)
//                .productName("existingName")
//                .productDescription("existingDescription")
//                .quantity(20)
//                .postID(1)
//                .categoryID(1)
//                .price(BigDecimal.valueOf(200))
//                .productImage("existingImage.jpg")
//                .build();
//        when(productRepository.getProductById(1)).thenReturn(existingProduct);
//
//        // Gọi method updateProduct và check exception
//        assertDoesNotThrow(() -> {
//            productService.updateProduct(1, ProductDTO.builder()
//                    .productName("existingName")
//                    .productDescription("existingDescription")
//                    .quantity(20)
//                    .postID(1)
//                    .categoryID(1) // Sửa categoryID
//                    .price(BigDecimal.valueOf(200))  // Sửa giá sản phẩm
//                    .productImage("existingImage.jpg")
//                    .build());
//        });
//
//        // Check product đã được update thành công trong repository
//        Product updatedProduct = productRepository.getProductById(1);
//        assertNotNull(updatedProduct);
//        assertEquals("existingName", updatedProduct.getProductName());
//        assertEquals("existingDescription", updatedProduct.getProductDescription());
//        assertEquals(20, updatedProduct.getQuantity());
//        assertEquals(1, updatedProduct.getCategoryID()); // Check categoryID đã được sửa đổi
//        assertEquals(BigDecimal.valueOf(200), updatedProduct.getPrice()); // Check giá đã được sửa đổi
//        assertEquals("existingImage.jpg", updatedProduct.getProductImage());
//    }
//
//
//
//
//}