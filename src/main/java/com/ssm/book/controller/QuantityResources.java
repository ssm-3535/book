package com.ssm.book.controller;

import com.ssm.book.api.v1.dto.QuantityDTO;
import com.ssm.book.api.v1.dto.QuantityDTOList;
import com.ssm.book.service.QuantityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(QuantityResources.QUANTITY_URL)
public class QuantityResources {
    public static final String QUANTITY_URL = "/api/v1/quantities";
    private final QuantityService quantityService;

    public QuantityResources(QuantityService quantityService) {
        this.quantityService = quantityService;
    }

    @GetMapping
    public QuantityDTOList getAllQuantityList(){
        return new QuantityDTOList(quantityService.getQuantityDTOList());
    }
}
