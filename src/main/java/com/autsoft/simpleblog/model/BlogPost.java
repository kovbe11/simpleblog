package com.autsoft.simpleblog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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
    @ManyToMany
    @JoinTable(
            name = "blog_post_categories",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "blog_post_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"blog_post_id", "category_id"}) // -> | 1 | 2 | can't be twice
    )
    private List<Category> categories;

    public void assignToCategory(Category category) throws TooManyCategoriesException {
        if (categories.size() >= 5) {
            throw new TooManyCategoriesException();
        }
        categories.add(category);
    }

    public void removeFromCategory(Category category) {
        categories.remove(category);
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
