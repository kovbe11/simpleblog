package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.service.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController("/api/blogPosts")
public class BlogPostController {

    private BlogPostService blogPostService;

    BlogPostController(final BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }


    // functionality
    @GetMapping("/search/{tag}")
    public ResponseEntity<Page<BlogPost>> searchByTags(@PathVariable final String tag) {
        return ResponseEntity.ok(blogPostService.findBlogPostsByCategoryTag(tag));
    }

    @PostMapping("/{id}/addCategory/{categoryName}")
    public ResponseEntity<BlogPost> addCategoryToBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        return ResponseEntity.ok(blogPostService.assignCategoryToBlogPost(id, categoryName));
    }

    @DeleteMapping("/{id}/removeCategory/{categoryName}")
    public ResponseEntity<BlogPost> removeCategoryFromBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        return ResponseEntity.ok(blogPostService.removeBlogPostFromCategory(id, categoryName));
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable final Long id) {
        var optionalBlogPost = blogPostService.getBlogPost(id);

        // I'm used to kotlin ?. ?: syntax, so I use optional to not forget null checks
        if(optionalBlogPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalBlogPost.get());
    }

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody @Valid final BlogPostDTO blogPostDTO) {
        return ResponseEntity.ok(blogPostService.createBlogPost(blogPostDTO));
    }

    // doesn't update blogpost's categories, so this could be a patch as well. I decided to use PUT
    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable final Long id, @RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        if (blogPostService.existsById(id)) {
            final var updatedBlogPost = blogPostService.saveBlogPost(id, blogPostDTO);
            return ResponseEntity.ok(updatedBlogPost);
        }
        final var createdBlogPost = blogPostService.createBlogPost(blogPostDTO);
        return ResponseEntity.created(new URI("/api/blogPosts/" + createdBlogPost.getId())).body(createdBlogPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable final Long id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.ok().build();
    }

}