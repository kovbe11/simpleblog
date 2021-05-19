package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(final String name);
}
