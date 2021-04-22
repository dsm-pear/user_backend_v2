package com.dsmpear.main.user_backend_v2.entity.question;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {
}


