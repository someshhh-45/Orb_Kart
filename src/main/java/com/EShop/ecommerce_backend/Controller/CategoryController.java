package com.EShop.ecommerce_backend.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import com.EShop.ecommerce_backend.Configuration.AppConstants;
import com.EShop.ecommerce_backend.Exception.ResourceNotFoundException;
import com.EShop.ecommerce_backend.Model.Category;
import com.EShop.ecommerce_backend.PayLoad.CategoryDTO;
import com.EShop.ecommerce_backend.PayLoad.CategoryResponse;
import com.EShop.ecommerce_backend.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //Get Mapping
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.Page_Number ,required = false) Integer pageNumber,
            @RequestParam (name ="pageSize",defaultValue = AppConstants.Page_Size,required = false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue = AppConstants.Sort_CategoriesBy ,required = false)String sortBy,
            @RequestParam(name="sortOrder", defaultValue = AppConstants.Sort_Dir,required = false)String sortOrder
    ){
        CategoryResponse categories=categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(categories);
    }

    //Put categories to db
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO= categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    //delete category
//    @DeleteMapping("admin/categories/{categoriesId}")
//    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoriesId)  {
//        CategoryDTO statusDTO=categoryService.deleteCategory(categoriesId);
//        return ResponseEntity.ok(statusDTO);
//    }

//    //updating category
//    @PutMapping("/public/categories/{categoryId}")
//    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
//                                                      @PathVariable Long categoryId) {
//
//        CategoryDTO savedCategoryDTO=categoryService.updateCategory(categoryId,categoryDTO);
//
//        return ResponseEntity.ok(savedCategoryDTO);
//    }
}
