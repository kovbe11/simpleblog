package com.autsoft.simpleblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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


    /**
     * Two Categories can be equal with different BlogPosts - not a big problem
     * but it is necessary to implement EqualsAndHashcode, otherwise it's recursive
     */
    @NotNull
    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<BlogPost> blogPosts = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "category_tags",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "tag_id"})
    )
    @JsonManagedReference
    private Set<Tag> tags = new HashSet<>();

    void assignBlogPost(final BlogPost blogPost) throws TooManyCategoriesException {
        blogPost.assignToCategory(this);
    }

    void removeBlogPost(final BlogPost blogPost) {
        blogPost.removeFromCategory(this);
    }

}
