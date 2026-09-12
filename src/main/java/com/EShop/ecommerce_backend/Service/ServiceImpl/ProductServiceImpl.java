package com.EShop.ecommerce_backend.Service.ServiceImpl;

import com.EShop.ecommerce_backend.Exception.APIException;
import com.EShop.ecommerce_backend.Exception.ResourceNotFoundException;
import com.EShop.ecommerce_backend.Model.Category;
import com.EShop.ecommerce_backend.Model.Product;
import com.EShop.ecommerce_backend.PayLoad.ProductDTO;
import com.EShop.ecommerce_backend.PayLoad.ProductResponse;
import com.EShop.ecommerce_backend.Repositories.CategoryRepo;
import com.EShop.ecommerce_backend.Repositories.ProductRepo;
import com.EShop.ecommerce_backend.Service.ProductService;
import com.EShop.ecommerce_backend.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${image.base.url}")
    private String imageBaseUrl;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    @Value("${product.image}")
    private String path;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private ModelMapper modelMapper;
    private String constructImageUrl(String imageName) {
        return imageBaseUrl.endsWith("/")
                ? imageBaseUrl + imageName
                : imageBaseUrl + "/" + imageName;
    }
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        // 1. Get category from DB
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        // 2. Map DTO -> Entity
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setUser(authUtil.loggedInUser());

        // 3. Save to DB
        Product savedProduct = productRepo.save(product);

        // 4. Map back to DTO
        ProductDTO responseDTO = modelMapper.map(savedProduct, ProductDTO.class);

        // 5. Build full image URL if image is present
        if (savedProduct.getImage() != null) {
            responseDTO.setImage(constructImageUrl(savedProduct.getImage()));
        }

        return responseDTO;
    }
    @Override
    public ProductResponse getAllProducts(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            String keyword,
            String category) {

        Sort sortByAnyOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails =
                PageRequest.of(pageNumber, pageSize, sortByAnyOrder);

        Specification<Product> spec = null;

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("productName")),
                            "%" + keyword.toLowerCase() + "%"
                    ));
        }

        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            root.get("category").get("categoryName"),
                            category
                    ));
        }

        Page<Product> pageProducts =
                productRepo.findAll(spec, pageDetails);

        List<Product> products = pageProducts.getContent();

        if (products.isEmpty()) {
            throw new APIException("Not any products present");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setProducts(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }
}