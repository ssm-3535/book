package com.ssm.book.converter;

import com.ssm.book.command.QuantityCommand;
import com.ssm.book.domain.Quantity;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class QuantityToQuantityCommand implements Converter<Quantity, QuantityCommand> {
    @Synchronized
    @Nullable
    @Override
    public QuantityCommand convert(Quantity source) {
        if(source == null)
            return null;
        final QuantityCommand quantityCommand = new QuantityCommand();
        quantityCommand.setId(source.getId());
        quantityCommand.setAmount(source.getAmount());
        return quantityCommand;
    }
}
