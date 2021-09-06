package com.ssm.book.service;

import com.ssm.book.api.v1.dto.QuantityDTO;
import com.ssm.book.command.QuantityCommand;

import java.util.List;
import java.util.Set;

public interface QuantityService {
    Set<QuantityCommand> showQuantityList();

    List<QuantityDTO> getQuantityDTOList();
}

