package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.CategoryDTO;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;


@RestController("/api/categories")
public class CategoryController {


    private CategoryService categoryService;

    CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable final Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid final CategoryDTO categoryDTO) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable final Long id, @RequestBody @Valid final CategoryDTO categoryDTO) throws URISyntaxException {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long id) {
        return null;
    }

}
