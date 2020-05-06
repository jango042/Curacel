package com.curacel.vehiclelisting.services;

import com.curacel.vehiclelisting.model.Car;
import com.curacel.vehiclelisting.pojo.CarPojo;
import com.curacel.vehiclelisting.util.MessageResponses;
import org.springframework.http.ResponseEntity;

public interface CarService {
    public MessageResponses addCar(CarPojo car);

    public ResponseEntity allCars();

    public ResponseEntity allCarByName(String name);

    public ResponseEntity allCarByModel(String model);

    public ResponseEntity allSoldCars(boolean sold);

    public ResponseEntity allCarByNameModelAndYear(String name, String model, String year);

    public ResponseEntity getCar(Long id);

    public MessageResponses deleteCar(Long id);

    public ResponseEntity allCarByNameModelAndYearPredictPrice(String name, String model, String year);

    public ResponseEntity allCarByNameModelAndYearPredictPriceFromMonths(String name, String model, String year, int pastMonth);

    public ResponseEntity makeSold(Long carId);
}
