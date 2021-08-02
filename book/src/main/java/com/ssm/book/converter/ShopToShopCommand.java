package com.ssm.book.converter;

import com.ssm.book.command.ShopCommand;
import com.ssm.book.domain.Shop;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ShopToShopCommand implements Converter<Shop, ShopCommand> {
    private QuantityToQuantityCommand quantityToQuantityCommand;

    public ShopToShopCommand(QuantityToQuantityCommand quantityToQuantityCommand) {
        this.quantityToQuantityCommand = quantityToQuantityCommand;
    }

    @Nullable
    @Synchronized
    @Override
    public ShopCommand convert(Shop source) {
        if(source == null)
            return null;
        final ShopCommand shopCommand = new ShopCommand();
        shopCommand.setId(source.getId());
        shopCommand.setName(source.getName());
        shopCommand.setPhone(source.getPhone());
        shopCommand.setAddress(source.getAddress());
        shopCommand.setQuantity(quantityToQuantityCommand.convert(source.getQuantity()));
        shopCommand.setBookId(source.getBook().getId());
        return shopCommand;
    }
}
