package com.mongodemo.Demo.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mongodemo.Demo.ResponseModel;
import com.mongodemo.Demo.ResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseModel<?> save(Product product) {
        try {
            // Save product and get the generated ID
            String p = productRepository.save(product).getId();

            // Create response data
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("id", p);

            return new ResponseModel<>(ResponseStatus.SUCCESS, "Product Created", dataMap);
        } catch (DataIntegrityViolationException e) {
            // Handles database constraint violations (e.g., unique constraints, null
            // values)
            return new ResponseModel<>(ResponseStatus.FAILURE, "Database Constraint Violation", null);
        } catch (IllegalArgumentException e) {
            // Handles cases where the input entity is null
            return new ResponseModel<>(ResponseStatus.FAILURE, "Invalid Product Data", null);
        } catch (Exception e) {
            // Generic catch for unexpected errors
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
