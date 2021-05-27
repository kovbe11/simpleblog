package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.service.TooManyCategoriesException;
import com.autsoft.simpleblog.service.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/blogPosts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(final BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // Functionality

    /**
     * GET /blogPosts/search/label : get a page of blogPosts with category that has a tag labeled "label"
     *
     * @param label The label to search for
     * @param size  The size of the page optionally
     * @param page  The page count
     * @return ResponseEntity with ok, and the Page of BlogPosts as content
     */
    @GetMapping("/search/{label}")
    public ResponseEntity<Page<BlogPost>> searchByTags(@PathVariable final String label, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(blogPostService.findBlogPostsByCategoryTag(label, PageRequest.of(page, size)));
    }

    /**
     * POST /blogPosts/1/addCategory/Category1 : add category with name "Category1" to the BlogPost with id 1
     *
     * @param id           : The id of the blogpost
     * @param categoryName : The name of the category
     * @return Returns the updated BlogPost if ok, BadRequest if there is more than 5 categories already, and NotFound if there is no BlogPost with given id
     */
    @PostMapping("/{id}/addCategory/{categoryName}")
    public ResponseEntity<BlogPost> addCategoryToBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        BlogPost blogPost;
        try {
            var optionalBlogPost = blogPostService.assignCategoryToBlogPost(id, categoryName);
            if (optionalBlogPost.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            blogPost = optionalBlogPost.get();
        } catch (TooManyCategoriesException ex) {
            // can I do this better?
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(blogPost);
    }


    /**
     * DELETE /blogPosts/1/removeCategory/Category1 : removes category with name "Category1" to the BlogPost with id 1
     *
     * @param id           : The id of the blogpost
     * @param categoryName : The name of the category
     * @return Returns the updated BlogPost if ok and NotFound if there is no BlogPost with given id
     */
    @DeleteMapping("/{id}/removeCategory/{categoryName}")
    public ResponseEntity<BlogPost> removeCategoryFromBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        final var optionalBlogPost = blogPostService.removeBlogPostFromCategory(id, categoryName);
        if (optionalBlogPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalBlogPost.get());
    }

    // CRUD


    /**
     * GET /blogPosts/3 : Gets the blogPost with id 3
     *
     * @param id : The id of blog post to fetch
     * @return Returns OK if exists, returns 404 if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable final Long id) {
        final var optionalBlogPost = blogPostService.getBlogPostById(id);

        // I'm used to kotlin ?. ?: syntax, so I use optional to not forget null checks
        if (optionalBlogPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalBlogPost.get());
    }

    /**
     * POST /blogPosts: Creates a new BlogPost
     *
     * @param blogPostDTO The data of the BlogPost to create with
     * @return Returns the BlogPost with all it's information
     * */
    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        final var createdBlogPost = blogPostService.createBlogPost(blogPostDTO);
        return ResponseEntity.created(new URI("/api/blogPosts/" + createdBlogPost.getId())).body(createdBlogPost);
    }

    /**
     * Doesn't update blogpost's categories, so this could be a patch as well. I decided to use PUT
     * PUT /blogPosts/3 : Updates BlogPost with id 3
     *
     * @param id The id of the blogpost to update
     * @param blogPostDTO the update information
     * @return Returns the updated blogpost if there was already a blogpost, or returns created see createBlogPost
     */
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

    /**
     * DELETE /blogPosts/3 : delete the blogPost with id 3
     *
     * @param id : The id of blog post to delete
     * @return Returns OK if no error was thrown
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable final Long id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.ok().build();
    }

}
