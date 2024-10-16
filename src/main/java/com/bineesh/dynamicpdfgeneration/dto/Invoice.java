package com.bineesh.dynamicpdfgeneration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Invoice {
    String seller;
    String sellerGstin;
    String sellerAddress;
    String buyer;
    String buyerGstin;
    String buyerAddress;
    List<Item> items;
}
