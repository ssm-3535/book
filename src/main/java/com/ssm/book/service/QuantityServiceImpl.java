package com.ssm.book.service;

import com.ssm.book.api.v1.dto.QuantityDTO;
import com.ssm.book.api.v1.mapper.QuantityMapper;
import com.ssm.book.command.QuantityCommand;
import com.ssm.book.converter.QuantityToQuantityCommand;
import com.ssm.book.repositories.QuantityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuantityServiceImpl implements QuantityService{
    private QuantityRepository quantityRepository;
    private QuantityToQuantityCommand quantityToQuantityCommand;
    private final QuantityMapper quantityMapper;

    public QuantityServiceImpl(QuantityRepository quantityRepository, QuantityToQuantityCommand quantityToQuantityCommand, QuantityMapper quantityMapper) {
        this.quantityRepository = quantityRepository;
        this.quantityToQuantityCommand = quantityToQuantityCommand;
        this.quantityMapper = quantityMapper;
    }

    @Override
    public Set<QuantityCommand> showQuantityList() {
        return StreamSupport.stream(quantityRepository.findAll()
                .spliterator(), false)
                .map(quantityToQuantityCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public List<QuantityDTO> getQuantityDTOList() {
        return StreamSupport.stream(quantityRepository.findAll().spliterator(), false)
                .map(quantityMapper::quantityToQuantityDTO)
                .collect(Collectors.toList());
    }
}
