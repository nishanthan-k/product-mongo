package com.mongodemo.Demo.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodemo.Demo.ResponseModel;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ResponseModel<?>> getAllCategories() {
        ResponseModel<?> response = categoryService.getAllCategories();
        return ResponseEntity.ok().body(response);
    }
}
