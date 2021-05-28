package com.autsoft.simpleblog.rest;

import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.service.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
     * @param pageable The page request used to query
     * @return The requested page of BlogPosts
     */
    @GetMapping("/search/{label}")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<BlogPost> searchByTags(@PathVariable final String label, Pageable pageable) {
        return blogPostService.findBlogPostsByCategoryTag(label, pageable);
    }

    /**
     * POST /blogPosts/1/addCategory/Category1 : add category with name "Category1" to the BlogPost with id 1
     *
     * @param id           : The id of the blogpost
     * @param categoryName : The name of the category
     * @return Returns the updated BlogPost if ok, BadRequest if there is more than 5 categories already, and NotFound if there is no BlogPost with given id
     */
    @PostMapping("/{id}/addCategory/{categoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    public BlogPost addCategoryToBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        return blogPostService.assignCategoryToBlogPost(id, categoryName);
    }


    /**
     * DELETE /blogPosts/1/removeCategory/Category1 : removes category with name "Category1" to the BlogPost with id 1
     *
     * @param id           : The id of the blogpost
     * @param categoryName : The name of the category
     * @return Returns the updated BlogPost if ok and NotFound if there is no BlogPost with given id
     */
    @DeleteMapping("/{id}/removeCategory/{categoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    public BlogPost removeCategoryFromBlogPost(@PathVariable final Long id, @PathVariable final String categoryName) {
        return blogPostService.removeBlogPostFromCategory(id, categoryName);
    }

    // CRUD
    /**
     * GET /blogPosts/3 : Gets the blogPost with id 3
     *
     * @param id : The id of blog post to fetch
     * @return Returns OK if exists, returns 404 if not
     */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BlogPost getBlogPost(@PathVariable final Long id) {
        return blogPostService.getBlogPostById(id);
    }

    /**
     * POST /blogPosts: Creates a new BlogPost
     *
     * @param blogPostDTO The data of the BlogPost to create with
     * @return Returns the BlogPost with all it's information
     */
    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        final var createdBlogPost = blogPostService.createBlogPost(blogPostDTO);
        return ResponseEntity.created(new URI("/api/blogPosts/" + createdBlogPost.getId())).body(createdBlogPost);
    }

    /**
     * Doesn't update blogpost's categories, so this could be a patch as well. I decided to use PUT
     * PUT /blogPosts/3 : Updates BlogPost with id 3
     *
     * @param id          The id of the blogpost to update
     * @param blogPostDTO the update information
     * @return Returns the updated blogpost if there was already a blogpost, or returns created see createBlogPost
     */
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable final Long id, @RequestBody @Valid final BlogPostDTO blogPostDTO) throws URISyntaxException {
        if (blogPostService.existsById(id)) {
            final var updatedBlogPost = blogPostService.saveBlogPost(id, blogPostDTO);
            return ResponseEntity.ok(updatedBlogPost);
        }
        return createBlogPost(blogPostDTO);
    }

    /**
     * DELETE /blogPosts/3 : delete the blogPost with id 3
     *
     * @param id : The id of blog post to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteBlogPost(@PathVariable final Long id) {
        blogPostService.deleteBlogPost(id);
    }

}
