package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
