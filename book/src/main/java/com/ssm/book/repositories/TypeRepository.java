package com.ssm.book.repositories;

import com.ssm.book.domain.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Type, Long> {

    Optional<Type> findByDescription(String description);
}
