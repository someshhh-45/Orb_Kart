package com.EShop.ecommerce_backend.Controller;

import com.EShop.ecommerce_backend.Configuration.AppConstants;
import com.EShop.ecommerce_backend.PayLoad.ProductDTO;
import com.EShop.ecommerce_backend.PayLoad.ProductResponse;
import com.EShop.ecommerce_backend.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO>addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                @PathVariable Long categoryId){
        ProductDTO savedproductDTO= productService.addProduct(categoryId,productDTO);
        return ResponseEntity.ok().body(productDTO);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.Page_Number ,required = false)Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.Page_Size ,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.Sort_ProductsBy ,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.Sort_Dir,required = false)  String sortOrder
    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder, keyword, category);
        return ResponseEntity.ok().body(productResponse);
    }
}
