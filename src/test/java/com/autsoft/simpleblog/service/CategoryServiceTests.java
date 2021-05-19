package com.autsoft.simpleblog.service;

import com.autsoft.simpleblog.repository.CategoryRepository;
import com.autsoft.simpleblog.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class CategoryServiceTests {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private TagRepository tagRepository;

    @BeforeEach
    public void init() {
        categoryRepository = mock(CategoryRepository.class);
        tagRepository = mock(TagRepository.class);
        categoryService = new CategoryService(categoryRepository, tagRepository);
    }

    @Test
    public void test_Get_Category() {

    }

    @Test
    public void test_Create_Category() {

    }

    @Test
    public void test_Update_Category() {

    }

    @Test
    public void test_Update_Category_Cleanup() {

    }

    @Test
    public void test_Delete_Category() {

    }
}
