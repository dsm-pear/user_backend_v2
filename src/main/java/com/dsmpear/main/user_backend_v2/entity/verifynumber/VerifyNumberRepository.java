package com.dsmpear.main.user_backend_v2.entity.verifynumber;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyNumberRepository extends CrudRepository<VerifyNumber, String> {
    Optional<VerifyNumber> findByEmail(String email);
}
