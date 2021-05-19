package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
