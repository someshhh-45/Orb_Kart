package com.EShop.ecommerce_backend.Repositories;

import com.EShop.ecommerce_backend.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findByCategoryName(String CategoryName);
}
