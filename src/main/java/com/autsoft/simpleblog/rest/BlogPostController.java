package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.service.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return null;
    }

    @PostMapping("/{id}/addCategory/{categoryName}")
    public ResponseEntity<BlogPost> addCategoryToBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        return null;
    }

    @DeleteMapping("/{id}/removeCategory/{categoryName}")
    public ResponseEntity<BlogPost> removeCategoryFromBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        return null;
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable final Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody @Valid final BlogPostDTO blogPostDTO) {
        return null;
    }

    // doesn't update blogpost's categories, so this could be a patch as well. I decided to use PUT
    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable final Long id, @RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable final Long id) {
        return null;
    }

}
