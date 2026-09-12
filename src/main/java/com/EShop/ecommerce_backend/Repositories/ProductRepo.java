package com.EShop.ecommerce_backend.Repositories;


import com.EShop.ecommerce_backend.Model.Category;
import com.EShop.ecommerce_backend.Model.Product;
import com.EShop.ecommerce_backend.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);


    Page<Product> findByUser(User user,Pageable pageDetails);
}


