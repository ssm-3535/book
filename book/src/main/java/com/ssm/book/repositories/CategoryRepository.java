package com.ssm.book.repositories;

import com.ssm.book.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Optional<Category> findByTitle(String title);
}
