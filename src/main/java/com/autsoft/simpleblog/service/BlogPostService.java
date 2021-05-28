package com.autsoft.simpleblog.service;


import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.model.Category;
import com.autsoft.simpleblog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.autsoft.simpleblog.dto.DTOUtilities.blogPostFromDTOWithoutRelations;
import static com.autsoft.simpleblog.dto.DTOUtilities.updateBlogPostWithDTO;

@Service
@Transactional
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final CategoryService categoryService;


    public BlogPostService(final BlogPostRepository blogPostRepository,
                           final CategoryService categoryService) {
        this.blogPostRepository = blogPostRepository;
        this.categoryService = categoryService;
    }

    // CRUD
    public BlogPost getBlogPostById(final Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BlogPost with id " + id + " was not found!"));
    }

    public BlogPost createBlogPost(final BlogPostDTO blogPostDTO) {
        final var blogPost = blogPostFromDTOWithoutRelations(blogPostDTO);
        return blogPostRepository.save(blogPost);
    }

    public BlogPost saveBlogPost(final Long id, final BlogPostDTO blogPostDTO) {
        final var toModifyBlogPost = blogPostRepository.findById(id);
        return toModifyBlogPost.map(blogPost -> {
            updateBlogPostWithDTO(blogPost, blogPostDTO);
            return blogPostRepository.save(blogPost);
        }).orElseGet(() -> createBlogPost(blogPostDTO));
    }

    public void deleteBlogPost(final Long id) {
        blogPostRepository.findById(id)
                .ifPresent(blogPostRepository::delete);
    }

    public boolean existsById(final Long id) {
        return blogPostRepository.existsById(id);
    }

    // Functionality
    public BlogPost assignCategoryToBlogPost(final Long blogPostId, final String categoryName) {
        final var blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new EntityNotFoundException("BlogPost with id " + blogPostId + " was not found!"));

        final var category = categoryService.findCategoryByName(categoryName);
        return assignCategoryToBlogPostWithEntities(blogPost, category);
    }

    public BlogPost assignCategoryToBlogPostWithEntities(final BlogPost blogPost, final Category category) {
        if (blogPost.getCategories().size() >= 5) {
            throw new TooManyCategoriesException(blogPost);
        }
        blogPost.assignToCategory(category);
        return blogPostRepository.save(blogPost);
    }

    public BlogPost removeBlogPostFromCategory(final Long blogPostId, final String categoryName) {
        final var blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new EntityNotFoundException("BlogPost with id " + blogPostId + " was not found!"));
        final var category = categoryService.findCategoryByName(categoryName);
        return removeBlogPostFromCategoryWithEntities(blogPost, category);
    }

    public BlogPost removeBlogPostFromCategoryWithEntities(final BlogPost blogPost, final Category category) {
        blogPost.removeFromCategory(category);
        return blogPostRepository.save(blogPost);
    }

    public Page<BlogPost> findBlogPostsByCategoryTag(final String label, Pageable pageable) {
        return blogPostRepository.findBlogPostsWithCategoriesTaggedWithLabel(label, pageable);
    }

}
