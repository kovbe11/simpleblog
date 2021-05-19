package com.autsoft.simpleblog.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {
    @NotNull
    private String name;

    private Set<String> tags = new HashSet<>();
}
