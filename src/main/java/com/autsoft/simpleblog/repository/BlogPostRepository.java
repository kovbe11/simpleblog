package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    /*
     * In 8 hours time, I used the native query, because I'm more comfortable with native sql syntax
     * After feedback I made the same query with JPQL because I could take my time to figure it out
     * It turned out to be much more easier to do obviously
     *
     * They also mentioned using Specification, but I think jpql made it simple enough.
     * */

    // I query all the ids for blog posts that have categories that have tags labelled the given label
    @Query(value = "SELECT DISTINCT bp.id, bp.body, bp.created, bp.modified, bp.title FROM category c JOIN category_tags ct ON c.id = ct.category_id JOIN tag ON tag.id = ct.tag_id JOIN blog_post_categories bpc ON bpc.category_id = ct.category_id JOIN blog_post bp ON bp.id = bpc.blog_post_id WHERE tag.label=?1",
            countQuery = "SELECT COUNT(DISTINCT bp.id) AS COUNT FROM category c JOIN category_tags ct ON c.id = ct.category_id JOIN tag ON tag.id = ct.tag_id JOIN blog_post_categories bpc ON bpc.category_id = ct.category_id JOIN blog_post bp ON bp.id = bpc.blog_post_id WHERE tag.label=?1",
            nativeQuery = true)
    Page<BlogPost> findBlogPostsWithCategoriesTaggedWithLabelNative(final String label, final Pageable pageable);

    // well this is much much easier to read than natively. (though it's probably slower)
    @Query("SELECT DISTINCT bp FROM BlogPost bp JOIN bp.categories c JOIN c.tags t WHERE t.label=?1")
    Page<BlogPost> findBlogPostsWithCategoriesTaggedWithLabel(final String label, final Pageable pageable);

}
