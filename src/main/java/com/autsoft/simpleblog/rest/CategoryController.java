package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.CategoryDTO;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // CRUD
    /**
     * GET /categories/3 : Gets the category with id 3
     *
     * @param id : The id of category to fetch
     * @return Returns OK if exists, returns 404 if not
     */
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable final Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * POST /categories: Creates a new BlogPost
     *
     * @param categoryDTO The data of the Category to create with
     * @return Returns the Category with all it's information
     * */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid final CategoryDTO categoryDTO) throws URISyntaxException {
        final var createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.created(new URI("/api/categories/" + createdCategory.getId())).body(createdCategory);
    }

    /**
     * PUT /categories/5 Updates the category with id 5
     *
     * @param id The id of the category to update
     * @param categoryDTO The data to update
     * @return Returns the updated category if it was updated, and returns created category if it was created
     * */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable final Long id, @RequestBody @Valid final CategoryDTO categoryDTO) throws URISyntaxException {
        if (categoryService.existsById(id)) {
            final var updatedCategory = categoryService.saveCategory(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        }

        return createCategory(categoryDTO);
    }

    /**
     * DELETE /blogPosts/3 : delete the category with id 3
     *
     * @param id : The id of category to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCategory(@PathVariable final Long id) {
        categoryService.deleteCategory(id);
    }

}
