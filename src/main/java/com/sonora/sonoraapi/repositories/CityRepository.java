package com.sonora.sonoraapi.repositories;

import com.sonora.sonoraapi.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByNameContainingIgnoreCase(String name);
}