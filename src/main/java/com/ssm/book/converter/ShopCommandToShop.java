package com.ssm.book.converter;

import com.ssm.book.command.ShopCommand;
import com.ssm.book.domain.Shop;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ShopCommandToShop implements Converter<ShopCommand, Shop> {
    private QuantityCommandToQuantity quantityCommandToQuantity;

    public ShopCommandToShop(QuantityCommandToQuantity quantityCommandToQuantity) {
        this.quantityCommandToQuantity = quantityCommandToQuantity;
    }

    @Nullable
    @Synchronized
    @Override
    public Shop convert(ShopCommand source) {
        if(source == null)
            return null;
        final Shop shop = new Shop();
        shop.setId(source.getId());
        shop.setName(source.getName());
        shop.setPhone(source.getPhone());
        shop.setAddress(source.getAddress());
        shop.setQuantity(quantityCommandToQuantity.convert(source.getQuantity()));
        return shop;
    }
}
