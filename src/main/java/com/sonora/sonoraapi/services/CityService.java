package com.sonora.sonoraapi.services;

import com.sonora.sonoraapi.dtos.CityDTO;
import com.sonora.sonoraapi.entities.City;
import com.sonora.sonoraapi.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public CityDTO create(CityDTO dto) {
        City city = new City();
        city.setName(dto.getName());
        city.setState(dto.getState());
        City saved = cityRepository.save(city);
        return toDTO(saved);
    }

    public List<CityDTO> findAll() {
        return cityRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<CityDTO> searchByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::toDTO).toList();
    }

    public CityDTO findById(Integer id) {
        return cityRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cidade não encontrada"));
    }

    public CityDTO update(Integer id, CityDTO dto) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cidade não encontrada"));

        city.setName(dto.getName());
        city.setState(dto.getState());

        return toDTO(cityRepository.save(city));
    }

    public void delete(Integer id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cidade não encontrada");
        }
        cityRepository.deleteById(id);
    }

    private CityDTO toDTO(City city) {
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .state(city.getState())
                .build();
    }
}