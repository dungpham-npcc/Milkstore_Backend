package com.cookswp.milkstore.pojo.dtos.ProductCategoryModel;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {
    @NotNull(message = "Category nam can not be null")
    private String categoryName;
}
