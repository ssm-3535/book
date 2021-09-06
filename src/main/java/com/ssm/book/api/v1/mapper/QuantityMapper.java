package com.ssm.book.api.v1.mapper;

import com.ssm.book.api.v1.dto.QuantityDTO;
import com.ssm.book.domain.Quantity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuantityMapper {
    QuantityMapper INSTANCE = Mappers.getMapper(QuantityMapper.class);

    QuantityDTO quantityToQuantityDTO(Quantity quantity);
    Quantity quantityDTOToQuantity(QuantityDTO quantityDTO);
}
