package com.EShop.ecommerce_backend.Service;

import com.EShop.ecommerce_backend.PayLoad.ProductDTO;
import com.EShop.ecommerce_backend.PayLoad.ProductResponse;
import jakarta.validation.Valid;

public interface ProductService {
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword, String category);

    ProductDTO addProduct(Long categoryId, @Valid ProductDTO productDTO);

}
