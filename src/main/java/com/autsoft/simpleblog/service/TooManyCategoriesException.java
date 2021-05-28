package com.autsoft.simpleblog.service;

import com.autsoft.simpleblog.model.BlogPost;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class TooManyCategoriesException extends ResponseStatusException {

    public TooManyCategoriesException(BlogPost blogPost) {
        super(HttpStatus.BAD_REQUEST, "BlogPost with id " + blogPost.getId() + " already has 5 categories assigned to it");
    }
}
