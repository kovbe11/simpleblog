package com.autsoft.simpleblog.service;

import com.autsoft.simpleblog.repository.BlogPostRepository;
import com.autsoft.simpleblog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class BlogPostServiceTests {

    private BlogPostService blogPostService;
    private CategoryService categoryService;
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    public void init(){
        categoryService = mock(CategoryService.class);
        blogPostRepository = mock(BlogPostRepository.class);
        blogPostService = new BlogPostService(blogPostRepository, categoryService);
    }

    @Test
    public void test_Get_BlogPost(){

    }

    @Test
    public void test_Create_BlogPost(){

    }

    @Test
    public void test_Update_BlogPost(){

    }

    @Test
    public void test_Delete_BlogPost(){

    }

    @Test
    public void test_Assign_Category_To_BlogPost(){

    }

    @Test
    public void test_Remove_Category_To_BlogPost(){

    }

    @Test
    public void test_Paged_Filtered_On_Category_Tags(){

    }

}
