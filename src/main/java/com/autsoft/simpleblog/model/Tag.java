package com.autsoft.simpleblog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "taggedCategories")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 10)
    @Column(length = 10, nullable = false)
    private String label;

    // two Tags can be equal with different tagged categories - not a big problem
    // necessary to implement EqualsAndHashcode, otherwise it's recursive
    @NotNull
    @ManyToMany(mappedBy = "tags")
    private List<Category> taggedCategories = new ArrayList<>();
}
