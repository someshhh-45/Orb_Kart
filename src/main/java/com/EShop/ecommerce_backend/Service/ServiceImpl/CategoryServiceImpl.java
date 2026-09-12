package com.EShop.ecommerce_backend.Service.ServiceImpl;

import com.EShop.ecommerce_backend.Exception.APIException;
import com.EShop.ecommerce_backend.Exception.ResourceNotFoundException;
import com.EShop.ecommerce_backend.Model.Category;
import com.EShop.ecommerce_backend.PayLoad.CategoryDTO;
import com.EShop.ecommerce_backend.PayLoad.CategoryResponse;
import com.EShop.ecommerce_backend.Repositories.CategoryRepo;
import com.EShop.ecommerce_backend.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.EShop.ecommerce_backend.Exception.ResourceNotFoundException;
import com.EShop.ecommerce_backend.PayLoad.CategoryDTO;
import com.EShop.ecommerce_backend.PayLoad.CategoryResponse;
import org.springframework.stereotype.Service;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;



    public interface CategoryService {
        public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) throws ResourceNotFoundException;
        CategoryDTO createCategory(CategoryDTO categoryDTO);
        CategoryDTO deleteCategory(Long categoryId) throws ResourceNotFoundException;

        CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) throws ResourceNotFoundException;
    }


    public CategoryDTO createCategory(CategoryDTO  categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with the name "+ category.getCategoryName()+ " already exists");
        }
        Category newsavedCategory=categoryRepo.save(category);
        return modelMapper.map(newsavedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }
}
