package com.sonora.sonoraapi.repositories;

import com.sonora.sonoraapi.entities.City;
import com.sonora.sonoraapi.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByNameContainingIgnoreCase(String name);

    List<City> findByState(State state);
}
