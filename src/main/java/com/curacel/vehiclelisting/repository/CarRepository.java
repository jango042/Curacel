package com.curacel.vehiclelisting.repository;

import com.curacel.vehiclelisting.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findBySold(boolean sold);

    public List<Car> findByName(String name);

    public List<Car> findByModel(String model);

    public List<Car> findByNameAndModelAndYear(String name, String model, String year);

    public List<Car> findByNameAndModelAndYearAndSoldTrue(String name, String model, String year);

    public List<Car> findByNameAndModelAndYearAndSoldTrueAndDateSoldBetween(String name, String model, String year, LocalDate pm, LocalDate today);
    public List<Car> findAllByNameAndModelAndYearAndSoldTrueAndDateSoldAfter(String name, String model, String year, LocalDate pm);
}
