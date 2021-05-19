package com.autsoft.simpleblog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class CategoryDTO {
    @NotNull
    private String name;

    private Set<String> tags = new HashSet<>();

}
