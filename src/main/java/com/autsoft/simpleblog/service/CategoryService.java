package com.autsoft.simpleblog.service;

import com.autsoft.simpleblog.dto.CategoryDTO;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.model.Tag;
import com.autsoft.simpleblog.repository.CategoryRepository;
import com.autsoft.simpleblog.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

import static com.autsoft.simpleblog.dto.DTOUtilities.categoryFromDTOWithoutRelations;
import static com.autsoft.simpleblog.dto.DTOUtilities.updateCategoryWithDTO;

@Service
@Transactional
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public CategoryService(final CategoryRepository categoryRepository,
                           final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> getCategoryById(final Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(final CategoryDTO categoryDTO) {
        final var category = categoryFromDTOWithoutRelations(categoryDTO);
        return categoryRepository.save(category);
    }

    public Category saveCategory(final Long id, final CategoryDTO categoryDTO) {
        final var toModifyCategory = categoryRepository.findById(id);
        return toModifyCategory.map(category -> {
            updateCategoryWithDTO(category, categoryDTO);
            updateCategoryTags(category, categoryDTO);
            return category;
        }).orElseGet(() -> createCategory(categoryDTO));
    }

    public void deleteCategory(final Long id) {
        categoryRepository.findById(id)
                .ifPresent(categoryRepository::delete);
    }

    public boolean existsById(final Long id) {
        return categoryRepository.existsById(id);
    }

    private void updateCategoryTags(Category category, CategoryDTO categoryDTO) {
        final var tagLabels = categoryDTO.getTags();
        final var tags = new HashSet<Tag>();
        tagLabels.forEach(label -> tagRepository.findByLabel(label)
                .ifPresentOrElse(tags::add, () -> {
                    tag.setLabel(label);
                    tags.add(tagRepository.save(tag));
                }));
        category.setTags(tags);
        //TODO: cleanup orphan tags!
    }
}
