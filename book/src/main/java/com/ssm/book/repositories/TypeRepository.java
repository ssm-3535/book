package com.ssm.book.repositories;

import com.ssm.book.domain.Quantity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Quantity, Long> {

    Optional<Quantity> findByAmount(String amount);
}
