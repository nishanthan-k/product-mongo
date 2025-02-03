package com.mongodemo.Demo.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Document(collection = "category")
@Data
@RequiredArgsConstructor
@Builder
public class Category {
    @Id
    private String id;

    private String name;

    private List<String> products = new ArrayList<>();

    public Category(String id, String name, List<String> product) {
        this.id = id;
        this.name = name;
        this.products = product;
    }

}
