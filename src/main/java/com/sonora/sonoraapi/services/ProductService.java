package com.sonora.sonoraapi.services;

import com.sonora.sonoraapi.dtos.ProductDTO;
import com.sonora.sonoraapi.entities.City;
import com.sonora.sonoraapi.entities.Product;
import com.sonora.sonoraapi.repositories.CityRepository;
import com.sonora.sonoraapi.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CityRepository cityRepository;

    public List<ProductDTO> findAll(String name) {
        List<Product> products = name == null ?
                productRepository.findAll() :
                productRepository.findByNameContainingIgnoreCase(name);

        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID " + id));
        return toDTO(product);
    }

    public ProductDTO create(ProductDTO dto) {
        Product product = fromDTO(dto);
        product = productRepository.save(product);
        return toDTO(product);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID " + id));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada com ID " + dto.getCityId()));
        product.setCity(city);

        product = productRepository.save(product);
        return toDTO(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .cityId(product.getCity().getId())
                .cityName(product.getCity().getName())
                .build();
    }

    private Product fromDTO(ProductDTO dto) {
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada com ID " + dto.getCityId()));

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCity(city);
        return product;
    }
}