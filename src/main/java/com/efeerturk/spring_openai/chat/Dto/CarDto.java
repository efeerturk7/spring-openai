package com.efeerturk.spring_openai.chat.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarDto {
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal price;
}
