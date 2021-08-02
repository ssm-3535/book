package com.ssm.book.converter;

import com.ssm.book.command.QuantityCommand;
import com.ssm.book.domain.Quantity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QuantityCommandToQuantity implements Converter<QuantityCommand, Quantity> {
    @Override
    public Quantity convert(QuantityCommand source) {
        if(source == null)
            return null;
        final Quantity quantity = new Quantity();
        quantity.setId(source.getId());
        quantity.setAmount(source.getAmount());
        return quantity;
    }
}
