package com.autsoft.simpleblog.dto;

import com.autsoft.simpleblog.model.BlogPost;
import com.autsoft.simpleblog.model.Category;


// Using trivial setters makes the code less readable for me, so I like to make utility functions for them
public final class DTOUtilities {

    private DTOUtilities() {
    }

    public static BlogPost blogPostFromDTOWithoutRelations(final BlogPostDTO blogPostDTO) {
        var blogPost = new BlogPost();
        blogPost.setTitle(blogPostDTO.getTitle());
        blogPost.setBody(blogPostDTO.getBody());
        return blogPost;
    }

    public static void updateBlogPostWithDTO(final BlogPost blogPost, final BlogPostDTO blogPostDTO) {
        blogPost.setTitle(blogPostDTO.getTitle());
        blogPost.setBody(blogPostDTO.getBody());
    }

    // maybe this one is unnecessary but "var category = categoryFromDTOWithoutRelations(categoryDTO);"
    // still looks more readable, then "var category = new Category(); category.setName(categoryDTO.getName());"
    public static Category categoryFromDTOWithoutRelations(final CategoryDTO categoryDTO) {
        var category = new Category();
        category.setName(categoryDTO.getName());
        return category;
    }

    public static void updateCategoryWithDTO(final Category category, final CategoryDTO categoryDTO) {
        category.setName(categoryDTO.getName());
    }
}
