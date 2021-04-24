package com.dsmpear.main.user_backend_v2.entity.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findAllByAuthStatusIsTrueAndNameContainingOrderByName(String name);
    List<User> findAllByNameContainsAndAuthStatusOrderByName(String name, boolean authStatus);
}
