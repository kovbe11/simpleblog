package com.autsoft.simpleblog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class TagDTO {
    @NotNull
    private String label;
}
