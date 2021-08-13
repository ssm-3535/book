package com.ssm.book.service;

import com.ssm.book.command.QuantityCommand;

import java.util.Set;

public interface QuantityService {
    Set<QuantityCommand> showQuantityList();
}
