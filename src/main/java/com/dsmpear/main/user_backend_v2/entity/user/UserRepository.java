package com.dsmpear.main.user_backend_v2.entity.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findAllByNameContainingOrderByName(String name);
    Page<User> findAllByNameContainingOrderByName(String name, Pageable page);
}
