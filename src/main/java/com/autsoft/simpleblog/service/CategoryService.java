package com.autsoft.simpleblog.service;

import com.autsoft.simpleblog.dto.CategoryDTO;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.repository.CategoryRepository;
import com.autsoft.simpleblog.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private CategoryRepository categoryRepository;
    private TagRepository tagRepository;

    public CategoryService(final CategoryRepository categoryRepository,
                    final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> getCategoryById(final Long id){
        return Optional.empty();
    }

    public Category createCategory(final CategoryDTO categoryDTO) {
        return null;
    }

    public Category saveCategory(final Long id, final CategoryDTO categoryDTO) {
        return null;
    }

    public void deleteCategory(final Long id) {

    }

    public boolean existsById(final Long id){
        return false;
    }
}
