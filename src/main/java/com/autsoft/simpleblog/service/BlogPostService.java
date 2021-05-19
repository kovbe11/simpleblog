package com.autsoft.simpleblog.service;


import com.autsoft.simpleblog.dto.BlogPostDTO;
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

import java.util.Optional;

import static com.autsoft.simpleblog.dto.DTOUtilities.blogPostFromDTOWithoutRelations;
import static com.autsoft.simpleblog.dto.DTOUtilities.updateBlogPostWithDTO;

@Service
@Transactional
public class BlogPostService {

    private final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    private final BlogPostRepository blogPostRepository;
    private final CategoryRepository categoryRepository;

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
    public Optional<BlogPost> assignCategoryToBlogPost(final Long blogPostId, final String categoryName)
            throws TooManyCategoriesException {
        final var optionalBlogPost = blogPostRepository.findById(blogPostId);
        if (optionalBlogPost.isEmpty()) {
            return Optional.empty();
        }
        final var optionalCategory = categoryRepository.findByName(categoryName);
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
        final var optionalBlogPost = blogPostRepository.findById(blogPostId);
        if (optionalBlogPost.isEmpty()) {
            return Optional.empty();
        }
        final var optionalCategory = categoryRepository.findByName(categoryName);
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
