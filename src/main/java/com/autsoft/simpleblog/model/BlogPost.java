package com.autsoft.simpleblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

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
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    private LocalDateTime created;

    private LocalDateTime modified;

    /**
     * max 5 categories
     * two BlogPosts can be equal even if their assigned categories don't match - not a big problem
     * but it is necessary to implement EqualsAndHashcode, otherwise it's recursive
     * */
    @ManyToMany
    @JoinTable(
            name = "blog_post_categories",
            joinColumns = @JoinColumn(name = "blog_post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"blog_post_id", "category_id"}) // -> | 1 | 2 | can't be twice
    )
    @JsonManagedReference
    private Set<Category> categories = new HashSet<>();

    /**
     * @param category The category we want to assign to the BlogPost
     * */
    public void assignToCategory(final Category category) {
        categories.add(category);
        category.getBlogPosts().add(this);
    }

    /**
     * @param category The category we want to remove from the BlogPost
     * */
    public void removeFromCategory(final Category category) {
        categories.remove(category);
        category.getBlogPosts().remove(this);
    }


    // I think for a this simple application this is perfectly fine
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
