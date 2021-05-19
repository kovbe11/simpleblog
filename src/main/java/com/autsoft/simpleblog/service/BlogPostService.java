package com.autsoft.simpleblog.service;


import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.dto.DTOUtilities;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.model.TooManyCategoriesException;
import com.autsoft.simpleblog.repository.BlogPostRepository;
import com.autsoft.simpleblog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

import static com.autsoft.simpleblog.dto.DTOUtilities.*;

@Service
@Transactional
public class BlogPostService {

    private final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    private BlogPostRepository blogPostRepository;
    private CategoryRepository categoryRepository;

    public BlogPostService(final BlogPostRepository blogPostRepository,
                           final CategoryRepository categoryRepository) {
        this.blogPostRepository = blogPostRepository;
        this.categoryRepository = categoryRepository;
    }


    // CRUD
    public Optional<BlogPost> getBlogPostById(final Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPost createBlogPost(final BlogPostDTO blogPostDTO) {
        var blogPost = blogPostFromDTOWithoutRelations(blogPostDTO);
        return blogPostRepository.save(blogPost);
    }

    public BlogPost saveBlogPost(final Long id, final BlogPostDTO blogPostDTO) {
        var toModifyBlogPost = blogPostRepository.findById(id);
        return toModifyBlogPost.map(blogPost -> blogPostRepository.save(blogPost))
                .orElseGet(() -> createBlogPost(blogPostDTO));
    }

    public void deleteBlogPost(final Long id) {
        var blogPost = blogPostRepository.findById(id);
        blogPost.ifPresent(it -> blogPostRepository.delete(it));
    }

    public boolean existsById(final Long id) {
        return blogPostRepository.existsById(id);
    }

    // Functionality
    public Optional<BlogPost> assignCategoryToBlogPost(final Long blogPostId, final String categoryName)
            throws TooManyCategoriesException {
        var optionalBlogPost = blogPostRepository.findById(blogPostId);
        if (optionalBlogPost.isEmpty()) {
            return Optional.empty();
        }
        var optionalCategory = categoryRepository.findByName(categoryName);
        if (optionalCategory.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(assignCategoryToBlogPostWithEntities(optionalBlogPost.get(), optionalCategory.get()));
    }

    public BlogPost assignCategoryToBlogPostWithEntities(final BlogPost blogPost, final Category category)
            throws TooManyCategoriesException {
        blogPost.assignToCategory(category);
        return blogPostRepository.save(blogPost);
    }

    public Optional<BlogPost> removeBlogPostFromCategory(final Long blogPostId, final String categoryName) {
        var optionalBlogPost = blogPostRepository.findById(blogPostId);
        if (optionalBlogPost.isEmpty()) {
            return Optional.empty();
        }
        var optionalCategory = categoryRepository.findByName(categoryName);
        if (optionalCategory.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(removeBlogPostFromCategoryWithEntities(optionalBlogPost.get(), optionalCategory.get()));
    }

    public BlogPost removeBlogPostFromCategoryWithEntities(final BlogPost blogPost, final Category category) {
        blogPost.removeFromCategory(category);
        return blogPostRepository.save(blogPost);
    }

    public Page<BlogPost> findBlogPostsByCategoryTag(final String tag) {
        // TODO
        return Page.empty();
    }

}
