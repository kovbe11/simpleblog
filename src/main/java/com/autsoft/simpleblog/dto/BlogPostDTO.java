package com.autsoft.simpleblog.dto;

import javax.validation.constraints.NotNull;

public class BlogPostDTO {
    @NotNull
    private String title;

    @NotNull
    private String body;

}
