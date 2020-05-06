package com.curacel.vehiclelisting.pojo;

import com.curacel.vehiclelisting.model.Car;
import lombok.Data;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;

@Data
public class Statistics {
    private Car car;
        private DoubleSummaryStatistics stat;
}
