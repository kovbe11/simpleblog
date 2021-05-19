package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.CategoryDTO;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController("/api/categories")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable final Long id) {
        final var optionalCategory = categoryService.getCategoryById(id);

        // I'm used to kotlin ?. ?: syntax, so I use optional to not forget null checks
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalCategory.get());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid final CategoryDTO categoryDTO) throws URISyntaxException {
        final var createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.created(new URI("/api/categories/" + createdCategory.getId())).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable final Long id, @RequestBody @Valid final CategoryDTO categoryDTO) throws URISyntaxException {
        if (categoryService.existsById(id)) {
            final var updatedCategory = categoryService.saveCategory(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        }

        // even though PUT should mean you get the asked resource at that id, I haven't found a way to
        // tell hibernate what ID to use when creating a new entity. Even if you set the ID property of
        // the entity and save it that way, hibernate will know that it is a new entity and create with new ID.
        // but maybe it's just me doing something wrong.
        return createCategory(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
