package com.cookswp.milkstore.repository.productStaff;

import com.cookswp.milkstore.pojo.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStaffRepository extends JpaRepository<ProductCategory, Integer> {
}
