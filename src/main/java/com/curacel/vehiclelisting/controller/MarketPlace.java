package com.curacel.vehiclelisting.controller;

import com.curacel.vehiclelisting.pojo.CarPojo;
import com.curacel.vehiclelisting.services.CarService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
public class MarketPlace {

    @Autowired
    private CarService carService;


    @ApiOperation(value = "Create Car Endpoint", notes = "Create Car endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @PostMapping("/add")
    public ResponseEntity create(@RequestBody CarPojo car) {
        return  ResponseEntity.ok(carService.addCar(car));
    }

    @ApiOperation(value = "Get all cars Endpoint", notes = "Get all cars endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/allCars")
    public ResponseEntity allCars() {
        return carService.allCars();
    }

    @ApiOperation(value = "Get all sold cars Endpoint", notes = "Get all sold Cars endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/allSoldCars")
    public ResponseEntity allSoldCar() {
        final boolean sold = true;
        return carService.allSoldCars(sold);
    }

    @ApiOperation(value = "Get all cars by Name Endpoint", notes = "Get all Cars by Name endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/allCarByName/{name}")
    public ResponseEntity allCarByName(@PathVariable("name") String name) {
        return carService.allCarByName(name);
    }

    @ApiOperation(value = "Get all cars by model Endpoint", notes = "Get all Cars by model endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/allCarByModel/{model}")
    public ResponseEntity allCarByModel(@PathVariable("model") String model) {
        return carService.allCarByModel(model);
    }

    @ApiOperation(value = "Get car by id Endpoint", notes = "Get Car by id endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/getCar/{id}")
    public ResponseEntity getCar(@PathVariable("id") Long id) {
        return carService.getCar(id);
    }

    @ApiOperation(value = "Find all Cars by Name, Model and Year Endpoint", notes = "Get all Cars by Name, Model and Year endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/allCarByNameModelYear/{name}/{model}/{year}")
    public ResponseEntity allCarByNameModelAndYear(@PathVariable("name") String name, @PathVariable("model") String model, @PathVariable("year") String year) {
        return carService.allCarByNameModelAndYear(name, model, year);
    }

    @ApiOperation(value = "Find predictions for past months", notes = "Find predictions for past months, by specifying the number of months back integer value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/predictionForPastMonth/{name}/{model}/{year}/{month}")
    public ResponseEntity predictionForPastMonth(@PathVariable("name") String name, @PathVariable("model") String model, @PathVariable("year") String year,@PathVariable("month") int month) {
        return carService.allCarByNameModelAndYearPredictPriceFromMonths(name, model, year, month);
    }

    @ApiOperation(value = "Sell Car Endpoint", notes = "Sell car endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/sellCar/{carId}")
    public ResponseEntity makeSold(@PathVariable("carId") Long carId) {
        return carService.makeSold(carId);
    }

    @ApiOperation(value = "Get predictions for car sales with statistics for price suggestion", notes = "Get predictions for car sales with statistics for price suggestion, by providing car name, model and year")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @GetMapping("/allCarByNameModelYearStat/{name}/{model}/{year}")
    public ResponseEntity allCarByNameModelAndYearStat(@PathVariable("name") String name, @PathVariable("model") String model, @PathVariable("year") String year) {
        return carService.allCarByNameModelAndYearPredictPrice(name, model, year);
    }

    @ApiOperation(value = "delete Car Endpoint", notes = "Delete Car endpoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "", paramType = "header")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deleteCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carService.deleteCar(id));
    }
}
