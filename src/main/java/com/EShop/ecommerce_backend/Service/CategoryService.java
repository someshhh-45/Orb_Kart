package com.EShop.ecommerce_backend.Service;

import com.EShop.ecommerce_backend.Exception.ResourceNotFoundException;
import com.EShop.ecommerce_backend.Model.Category;
import com.EShop.ecommerce_backend.PayLoad.CategoryDTO;
import com.EShop.ecommerce_backend.PayLoad.CategoryResponse;

public interface CategoryService {
//    Category getCategoryById(Long categoryId);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) throws ResourceNotFoundException;

}
