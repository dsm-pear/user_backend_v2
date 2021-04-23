package com.dsmpear.main.user_backend_v2.factory;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import javax.persistence.Entity;
import java.util.Optional;

public interface Factory<R extends CrudRepository, T extends Object> {
    T create(String value);

//    default Optional<T> created(String value, R repository) {
//        return repository.findById(value);
//    }
}
