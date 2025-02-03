package com.mongodemo.Demo.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodemo.Demo.ResponseModel;
import com.mongodemo.Demo.ResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseModel<?> getCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            return new ResponseModel<>(ResponseStatus.SUCCESS, "No Category Found", null);
        }

        return new ResponseModel<>(ResponseStatus.SUCCESS, "Category fetched", category);
    }

    public ResponseModel<?> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.size() == 0) {
            return new ResponseModel<>(ResponseStatus.SUCCESS, "No Categories created", categories);
        }

        return new ResponseModel<>(ResponseStatus.SUCCESS, "Categories fetched", categories);
    }
}
