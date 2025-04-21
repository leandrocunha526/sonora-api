package com.sonora.sonoraapi.controllers;

import com.sonora.sonoraapi.dtos.CityDTO;
import com.sonora.sonoraapi.services.CityService;
import com.sonora.sonoraapi.entities.State;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/states")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CityDTO>> getStates() {
        List<CityDTO> cities = Arrays.stream(State.values())
                .map(city -> new CityDTO(null, city.name(), city.getFullName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(cities);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CityDTO> create(@RequestBody @Valid CityDTO dto) {
        return ResponseEntity.ok(cityService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CityDTO>> getAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

    // Busca de cidades por nome
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CityDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(cityService.searchByName(name));
    }

    // Busca de cidades por estado
    @GetMapping("/search/state")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CityDTO>> searchByState(@RequestParam State state) {
        return ResponseEntity.ok(cityService.searchByState(state));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CityDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CityDTO> update(@PathVariable Integer id, @RequestBody @Valid CityDTO dto) {
        return ResponseEntity.ok(cityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
