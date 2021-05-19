package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findByLabel(final String label);
}
