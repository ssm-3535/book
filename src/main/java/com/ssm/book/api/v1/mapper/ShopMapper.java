package com.ssm.book.api.v1.mapper;

import com.ssm.book.api.v1.dto.ShopDTO;
import com.ssm.book.domain.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {
    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDTO shopToShopDTO(Shop shop);

    Shop shopDTOToShop(ShopDTO shopDTO);
}
