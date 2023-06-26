package com.example.pdv.dto;

import com.example.pdv.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

    private long userId;

    List<ProductDTO> items;
}
