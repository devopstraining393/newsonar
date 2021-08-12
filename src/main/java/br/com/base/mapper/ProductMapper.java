package br.com.base.mapper;

import br.com.base.domain.Product;
import br.com.base.domain.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTO(List<Product> products);

    Product toEntity(ProductDTO productDTO);
}
