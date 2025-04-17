package com.sonora.sonoraapi.controllers;

import com.sonora.sonoraapi.dtos.CityDTO;
import com.sonora.sonoraapi.services.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CityDTO> create(@RequestBody @Valid CityDTO dto) {
        return ResponseEntity.ok(cityService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<CityDTO>> getAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<CityDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(cityService.searchByName(name));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<CityDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CityDTO> update(@PathVariable Integer id, @RequestBody @Valid CityDTO dto) {
        return ResponseEntity.ok(cityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}