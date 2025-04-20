package com.sonora.sonoraapi.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import com.sonora.sonoraapi.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres")
    private String name;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double price;

    @NotNull(message = "O estoque é obrigatório")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    private Integer stock;

    @NotNull(message = "A cidade é obrigatória")
    private Integer cityId;

    private String cityName;
    
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.cityId = product.getCity().getId();
        this.cityName = product.getCity().getName();
    }
}
