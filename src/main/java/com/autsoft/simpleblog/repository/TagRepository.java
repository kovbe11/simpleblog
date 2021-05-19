package com.autsoft.simpleblog.repository;

import com.autsoft.simpleblog.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
}
