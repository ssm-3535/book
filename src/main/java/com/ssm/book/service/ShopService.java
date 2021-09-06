package com.ssm.book.service;

import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.api.v1.dto.ShopDTO;
import com.ssm.book.command.ShopCommand;

import java.util.List;

public interface ShopService {
    ShopCommand findShopById(String book_id, String id);

    ShopCommand saveShopCommand(ShopCommand shopCommand);

    void deleteShop(String book_id, String id);

    List<ShopDTO> getShops();

    ResponseDTO saveShopDTO(ShopDTO shopDTO);

    ShopDTO findShopDTOById(Long id);

    ShopDTO updateShopDTO(Long id, ShopDTO shopDTO);

    ShopDTO patchShopDTO(Long id, ShopDTO shopDTO);

    void deleteById(Long id);
}
