package com.bineesh.dynamicpdfgeneration.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    String name;
    String quantity;
    double rate;
    double amount;
}
