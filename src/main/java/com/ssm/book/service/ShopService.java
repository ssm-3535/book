package com.ssm.book.service;

import com.ssm.book.command.ShopCommand;
import com.ssm.book.domain.Shop;

public interface ShopService {
    ShopCommand findShopById(String book_id, String id);

    ShopCommand saveShopCommand(ShopCommand shopCommand);
}
