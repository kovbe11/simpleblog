package com.autsoft.simpleblog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@EqualsAndHashCode
public class BlogPostDTO {
    @NotNull
    private String title;

    @NotNull
    private String body;

}
