package com.mongodemo.Demo.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodemo.Demo.ResponseModel;
import com.mongodemo.Demo.ResponseStatus;
import com.mongodemo.Demo.category.Category;
import com.mongodemo.Demo.category.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseModel<?> save(Product product) {
        try {
            String categoryName = Optional.ofNullable(product)
                    .map(Product::getCategory)
                    .map(Category::getName)
                    .orElse(null);

            if (categoryName != null) {
                categoryName = categoryName.toLowerCase().trim();

                // Try to get the existing category
                Category existingCategory = categoryRepository.findByName(categoryName);

                // If no existing category, create a new one
                if (existingCategory == null) {
                    existingCategory = Category.builder()
                            .name(categoryName)
                            .build();
                    categoryRepository.save(existingCategory);
                }

                // Assign the category to the product
                product.setCategory(existingCategory);

                // Save the product and get its ID
                String pId = productRepository.save(product).getId();

                // Update the category with the new product if not already added
                if (!existingCategory.getProducts().contains(pId)) {
                    List<String> addedProducts = existingCategory.getProducts();
                    addedProducts.add(pId);
                    existingCategory.setProducts(addedProducts);
                    categoryRepository.save(existingCategory); // Save the updated category
                }

                return new ResponseModel<>(ResponseStatus.SUCCESS, "Product created", null);
            } else {
                // Save the product and get its ID
                String pId = productRepository.save(product).getId();

                // Response data map
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("id", pId);

                return new ResponseModel<>(ResponseStatus.SUCCESS, "Product Created", dataMap);
            }
        } catch (Exception e) {
            System.out.println("Error => " + e.getMessage());
            return new ResponseModel<>(ResponseStatus.FAILURE, "An error occurred while saving the product", null);
        }
    }

    public ResponseModel<?> findById(String id) {
        Optional<Product> p = productRepository.findById(id);

        if (p.isEmpty()) {
            return new ResponseModel<>(
                    ResponseStatus.FAILURE,
                    "Product Not Found",
                    null);
        }

        return new ResponseModel<>(ResponseStatus.SUCCESS, "Product fetched", p.get());
    }

    public ResponseModel<?> findAll() {
        List<Product> p = productRepository.findAll();

        if (p.size() == 0) {
            return new ResponseModel<>(ResponseStatus.SUCCESS, "Products list is empty", p);
        }

        return new ResponseModel<>(ResponseStatus.SUCCESS, "Products list", p);
    }

    public ResponseModel<?> updateProduct(Product product) {
        String id = product.getId();

        Product existingProduct = productRepository.findById(id).get();

        if (existingProduct == null) {
            return new ResponseModel<>(ResponseStatus.FAILURE, "Product Not Found", null);
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());

        productRepository.save(existingProduct);
        return new ResponseModel<>(ResponseStatus.SUCCESS, "Product Updated", null);
    }

    public ResponseModel<?> deleteById(String id) {
        if (!productRepository.existsById(id)) {
            return new ResponseModel<>(ResponseStatus.FAILURE, "Product Not Found", null);
        }

        productRepository.deleteById(id);
        return new ResponseModel<>(ResponseStatus.SUCCESS, "Product Deleted", null);
    }
}
