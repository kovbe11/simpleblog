package com.autsoft.simpleblog.model;

import com.autsoft.simpleblog.dto.BlogPostDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "categories")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String body;


    private LocalDateTime created;

    private LocalDateTime modified;

    // max 5 categories
    // two BlogPosts can be equal even if their assigned categories don't match - not a big problem
    // necessary to implement EqualsAndHashcode, otherwise it's recursive
    // todo: cascade type missing!
    @ManyToMany
    @JoinTable(
            name = "blog_post_categories",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "blog_post_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"blog_post_id", "category_id"}) // -> | 1 | 2 | can't be twice
    )
    @JsonManagedReference
    private Set<Category> categories = new HashSet<>();

    public void assignToCategory(final Category category) throws TooManyCategoriesException {
        if (categories.size() >= 5) {
            throw new TooManyCategoriesException();
        }
        categories.add(category);
        category.getBlogPosts().add(this);
    }

    public void removeFromCategory(final Category category) {
        categories.remove(category);
        category.getBlogPosts().remove(this);
    }

    @PrePersist
    private void onCreate() {
        final var now = LocalDateTime.now();
        created = now;
        modified = now;
    }

    @PreUpdate
    private void onUpdate() {
        modified = LocalDateTime.now();
    }


}
