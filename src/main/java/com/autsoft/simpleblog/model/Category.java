package com.autsoft.simpleblog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "blogPosts")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    // two Categories can be equal with different BlogPosts - not a big problem
    // necessary to implement EqualsAndHashcode, otherwise it's recursive
    @NotNull
    private List<BlogPost> blogPosts = new ArrayList<>();

    @NotNull
    private List<Tag> tags = new ArrayList<>();

}
