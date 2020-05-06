package com.curacel.vehiclelisting.services.impl;

import com.curacel.vehiclelisting.model.Car;
import com.curacel.vehiclelisting.pojo.CarPojo;
import com.curacel.vehiclelisting.pojo.Statistics;
import com.curacel.vehiclelisting.repository.CarRepository;
import com.curacel.vehiclelisting.services.CarService;
import com.curacel.vehiclelisting.util.MessageResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepo;

    @Override
    public MessageResponses addCar(CarPojo car) {
        try {
            Car mCar = new ModelMapper().map(car, Car.class);
           // mCar.setDateSold(LocalDate.now());
            carRepo.save(mCar);
            return MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_CREATE);
        } catch (Exception e) {
            return MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR);
        }
    }

    @Override
    public ResponseEntity allCars() {
        try {
            List<Car> allCars = carRepo.findAll();
            if (!allCars.isEmpty()) {
                return new ResponseEntity(allCars, HttpStatus.OK);
            } else {
                return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_EMPTY), HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity allCarByName(String name) {
        try {
            List<Car> allCars = carRepo.findByName(name);
            if (!allCars.isEmpty()) {
                return new ResponseEntity(allCars, HttpStatus.OK);
            } else {
                return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_EMPTY), HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity allCarByModel(String model) {
        try {
            List<Car> allCars = carRepo.findByModel(model);
            if (!allCars.isEmpty()) {
                return new ResponseEntity(allCars, HttpStatus.OK);
            } else {
                return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_EMPTY), HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity allSoldCars(boolean sold) {
        try {
            List<Car> allCars = carRepo.findBySold(sold);
            if (!allCars.isEmpty()) {
                return new ResponseEntity(allCars, HttpStatus.OK);
            } else {
                return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_EMPTY), HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity allCarByNameModelAndYear(String name, String model, String year) {
        try {
            List<Car> allCars = carRepo.findByNameAndModelAndYear(name, model, year);
            if (!allCars.isEmpty()) {
                return new ResponseEntity(allCars, HttpStatus.OK);
            } else {
                return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_EMPTY), HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity getCar(Long id) {
        try {
            //Optional<Car> car = carRepo.findById(id);
            carRepo.findById(id).map(car -> {
                return new ResponseEntity(car, HttpStatus.OK);
            }).orElse(new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_WRONG_ID), HttpStatus.EXPECTATION_FAILED));

        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }

        return null;
    }

    @Override
    public MessageResponses deleteCar(Long id) {
        try {
            return carRepo.findById(id).map(car -> {
                carRepo.delete(car);
                return MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_CREATE);
            }).orElse(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_WRONG_ID));
        } catch (Exception e) {
            MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR);
        }
        return null;
    }

    @Override
    public ResponseEntity allCarByNameModelAndYearPredictPrice(String name, String model, String year) {
        try {
            List<Car> allCars = carRepo.findByNameAndModelAndYearAndSoldTrue(name, model, year);
            if (allCars.size() > 0) {
                DoubleSummaryStatistics c = allCars.stream()
                        .collect(Collectors.summarizingDouble(Car::getPrice));
                Statistics s = new Statistics();
                s.setCar(allCars.get(0));
                s.setStat(c);
                return new ResponseEntity(s, HttpStatus.OK);
            }

            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_EMPTY), HttpStatus.EXPECTATION_FAILED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity makeSold(Long carId) {
        try {
            Car car = carRepo.findById(carId).map(mCar -> {
                //make sales to backdate four months back
                mCar.setDateSold(LocalDate.now().minusMonths(4));
                //mCar.setDateSold(LocalDate.now());
                mCar.setSold(true);
                return carRepo.save(mCar);
            }).get();
            return ResponseEntity.ok(car);
        } catch (Exception e) {
            return new ResponseEntity(MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR), HttpStatus.EXPECTATION_FAILED);
        }

    }

    @Override
    public ResponseEntity allCarByNameModelAndYearPredictPriceFromMonths(String name, String model, String year,
                                                                         int numberOfMonthsToSubtract) {
        try {
            LocalDate today = LocalDate.now();
//			List<Car> allCars = carRepo.findAllByNameAndModelAndYearAndSoldTrueAndDateSoldBetween(name, model, year,
//					today.minusMonths(numberOfMonthsToSubtract), today);
            List<Car> allCars = carRepo.findAllByNameAndModelAndYearAndSoldTrueAndDateSoldAfter(name, model, year,
                    today.minusMonths(numberOfMonthsToSubtract));
            if (allCars.size() > 0) {
                DoubleSummaryStatistics c = allCars.stream().collect(Collectors.summarizingDouble(Car::getPrice));
                Statistics s = new Statistics();
                s.setCar(allCars.get(0));
                s.setStat(c);
                return ResponseEntity.ok(s);
            }

            return ResponseEntity.ok("Empty");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(
                    MessageResponses.response(MessageResponses.CODE_ERROR, MessageResponses.MESSAGE_ERROR),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }
}
