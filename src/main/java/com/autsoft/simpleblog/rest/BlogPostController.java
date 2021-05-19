package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.model.TooManyCategoriesException;
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

    public BlogPostController(final BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

//    @ExceptionHandler({BlogPostNotFoundException.class, CategoryNotFoundException.class})
//    public void handleNotFoundErrors(){
//
//    }


    // functionality
    @GetMapping("/search/{tag}")
    public ResponseEntity<Page<BlogPost>> searchByTags(@PathVariable final String tag) {
        return ResponseEntity.ok(blogPostService.findBlogPostsByCategoryTag(tag));
    }

    @PostMapping("/{id}/addCategory/{categoryName}")
    public ResponseEntity<BlogPost> addCategoryToBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        BlogPost blogPost = null;
        try{
            var optionalBlogPost = blogPostService.assignCategoryToBlogPost(id, categoryName);
            if(optionalBlogPost.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            blogPost = optionalBlogPost.get();
        }catch (TooManyCategoriesException ex){
            // can I do this better?
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(blogPost);
    }

    @DeleteMapping("/{id}/removeCategory/{categoryName}")
    public ResponseEntity<BlogPost> removeCategoryFromBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        var optionalBlogPost = blogPostService.removeBlogPostFromCategory(id, categoryName);
        if(optionalBlogPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalBlogPost.get());
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable final Long id) {
        var optionalBlogPost = blogPostService.getBlogPostById(id);

        // I'm used to kotlin ?. ?: syntax, so I use optional to not forget null checks
        if(optionalBlogPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalBlogPost.get());
    }

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        final var createdBlogPost = blogPostService.createBlogPost(blogPostDTO);
        return ResponseEntity.created(new URI("/api/blogPosts/" + createdBlogPost.getId())).body(createdBlogPost);
    }

    // doesn't update blogpost's categories, so this could be a patch as well. I decided to use PUT
    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable final Long id, @RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        if (blogPostService.existsById(id)) {
            final var updatedBlogPost = blogPostService.saveBlogPost(id, blogPostDTO);
            return ResponseEntity.ok(updatedBlogPost);
        }

        // even though PUT should mean you get the asked resource at that id, I haven't found a way to
        // tell hibernate what ID to use when creating a new entity. Even if you set the ID property of
        // the entity and save it that way, hibernate will know that it is a new entity and create with new ID.
        // but maybe it's just me doing something wrong.
        return createBlogPost(blogPostDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable final Long id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.ok().build();
    }

}
