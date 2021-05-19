package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {


    // I query all the ids for blog posts that have categories that have tags labelled the given label
    // this is probably not the best way to implement but I didn't find any built-in help for such a query
    // I'm out of time to index properly -> if i have time I'll define indexes so this query is not that slow.
    @Query(value = "SELECT DISTINCT bp.id FROM category c JOIN category_tags ct ON c.id = ct.category_id JOIN tag ON tag.id = ct.tag_id JOIN blog_post_categories bpc ON bpc.category_id = ct.category_id JOIN blog_post bp ON bp.id = bpc.blog_post_id WHERE tag.label=\"abc\"",
    nativeQuery = true)
    Page<BigInteger> findBlogPostsWithCategoriesTaggedPaged(final String label, Pageable pageable);

}
