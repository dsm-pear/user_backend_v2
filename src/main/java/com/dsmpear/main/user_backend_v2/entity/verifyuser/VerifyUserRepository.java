package com.dsmpear.main.user_backend_v2.entity.verifyuser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyUserRepository extends CrudRepository<VerifyUser, Integer> {
    Optional<VerifyUser> findByEmail(String email);
}
