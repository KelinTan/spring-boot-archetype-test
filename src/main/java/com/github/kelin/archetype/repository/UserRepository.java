package com.github.kelin.archetype.repository;

import com.github.kelin.archetype.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
