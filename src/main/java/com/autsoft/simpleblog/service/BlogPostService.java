package com.autsoft.simpleblog.service;


import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.repository.BlogPostRepository;
import com.autsoft.simpleblog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogPostService {

    private final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    private BlogPostRepository blogPostRepository;
    private CategoryRepository categoryRepository;

    BlogPostService(final BlogPostRepository blogPostRepository,
                    final CategoryRepository categoryRepository) {
        this.blogPostRepository = blogPostRepository;
        this.categoryRepository = categoryRepository;
    }

    public BlogPost createBlogPost(final BlogPostDTO blogPostDTO){
        return null;
    }

    public BlogPost saveBlogPost(final Long id, final BlogPostDTO blogPostDTO){
        return null;
    }

    public void deleteBlogPost(final Long id){

    }

    public boolean existsById(final Long id){
        return true;
    }

    public BlogPost assignCategoryToBlogPost(final Long id, final String categoryName){
        return assignCategoryToBlogPostWithEntities(null, null);
    }

    public BlogPost assignCategoryToBlogPostWithEntities(final BlogPost blogPost, final Category category){
        return null;
    }

    public BlogPost removeBlogPostFromCategory(final Long id, final String categoryName){
        return removeBlogPostFromCategoryWithEntities(null, null);
    }

    public BlogPost removeBlogPostFromCategoryWithEntities(final BlogPost blogPost, final Category category) {
        return null;
    }

    public Page<BlogPost> findBlogPostsByCategoryTag(final String tag){
        return Page.empty();
    }

}
