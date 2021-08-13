package com.ssm.book.repositories;

import com.ssm.book.domain.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShopRepository extends CrudRepository<Shop,Long> {

    Optional<Shop> findByAddress(String address);

}
