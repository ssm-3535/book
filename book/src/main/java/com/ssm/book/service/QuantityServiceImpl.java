package com.ssm.book.service;

import com.ssm.book.command.QuantityCommand;
import com.ssm.book.converter.QuantityToQuantityCommand;
import com.ssm.book.domain.Quantity;
import com.ssm.book.repositories.QuantityRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuantityServiceImpl implements QuantityService{
    private QuantityRepository quantityRepository;
    private QuantityToQuantityCommand quantityToQuantityCommand;

    public QuantityServiceImpl(QuantityRepository quantityRepository, QuantityToQuantityCommand quantityToQuantityCommand) {
        this.quantityRepository = quantityRepository;
        this.quantityToQuantityCommand = quantityToQuantityCommand;
    }

    @Override
    public Set<QuantityCommand> showQuantityList() {
        return StreamSupport.stream(quantityRepository.findAll()
                .spliterator(), false)
                .map(quantityToQuantityCommand::convert)
                .collect(Collectors.toSet());
    }
}
