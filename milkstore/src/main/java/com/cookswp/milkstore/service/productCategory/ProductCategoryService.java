package com.cookswp.milkstore.service.productCategory;

import com.cookswp.milkstore.pojo.dtos.ProductCategoryModel.ProductCategoryDTO;
import com.cookswp.milkstore.pojo.entities.ProductCategory;
import com.cookswp.milkstore.repository.productCategory.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService implements IProductCategoryService{

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory createProductCategory(ProductCategoryDTO requestCategory) {
        ProductCategory productCategoryEntity = new ProductCategory();
        productCategoryEntity.setCategoryName(requestCategory.getCategoryName());
        return productCategoryRepository.save(productCategoryEntity);
    }

    @Override
    public ProductCategory updateProductCategory(int id, ProductCategoryDTO requestCategory) {
        Optional<ProductCategory> findProduct = productCategoryRepository.findById(id);
        if(findProduct.isPresent()){
            ProductCategory productCategoryEntity = findProduct.get();
            productCategoryEntity.setCategoryName(requestCategory.getCategoryName());
            return productCategoryRepository.save(productCategoryEntity);
        } else {
            throw new RuntimeException("Not found category with ID " + id);
        }
    }

    @Override
    public ProductCategory deleteProductCategory(ProductCategoryDTO requestCategory) {
        return null;
    }

    @Override
    public ProductCategory findProductCategory(ProductCategoryDTO requestCategory) {
        return null;
    }

    @Override
    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }
}
