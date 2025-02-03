package com.mongodemo.Demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodemo.Demo.ResponseModel;
import com.mongodemo.Demo.ResponseStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<?>> getProductById(@PathVariable String id) {
        ResponseModel<?> response = productService.findById(id);

        if (response.getStatus() == ResponseStatus.FAILURE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<ResponseModel<?>> getAllProducts() {
        ResponseModel<?> response = productService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseModel<?>> createProduct(@Valid @RequestBody Product product,
            BindingResult bindingResult) {
        // Check if validation errors are present
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel<>(ResponseStatus.FAILURE, errorMessages.toString(), null));
        }

        ResponseModel<?> response = productService.save(product);

        if (response.getStatus() == ResponseStatus.FAILURE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<?>> updateProduct(@Valid @RequestBody Product product) {
        ResponseModel<?> response = productService.updateProduct(product);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<?>> deleteProduct(@PathVariable String id) {
        ResponseModel<?> response = productService.deleteById(id);
        return ResponseEntity.ok().body(response);
    }
}
