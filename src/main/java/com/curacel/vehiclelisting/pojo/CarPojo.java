package com.curacel.vehiclelisting.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarPojo {

    private Long id;
    private String name;
    private String year;
    private String model;
    private double price;
    private boolean sold;
}
