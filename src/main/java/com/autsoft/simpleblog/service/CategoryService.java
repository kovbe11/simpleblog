package com.autsoft.simpleblog.service;

import com.autsoft.simpleblog.dto.CategoryDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.repository.CategoryRepository;
import com.autsoft.simpleblog.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private CategoryRepository categoryRepository;
    private TagRepository tagRepository;

    CategoryService(final CategoryRepository categoryRepository,
                    final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    BlogPost createCategory(final CategoryDTO categoryDTO) {
        return null;
    }

    BlogPost saveCategory(final Long id, final CategoryDTO categoryDTO) {
        return null;
    }

    void deleteCategory(final Long id) {

    }

}
