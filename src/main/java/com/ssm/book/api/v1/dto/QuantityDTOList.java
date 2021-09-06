package com.ssm.book.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
public class QuantityDTOList {
    private List<QuantityDTO> quantityList;
}
