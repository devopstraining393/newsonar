package br.com.base.service;

import br.com.base.domain.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> listAllProducts();

    ProductDTO getProductById(Integer id);

    ProductDTO saveProduct(ProductDTO productDTO);

    void editProduct(ProductDTO productDTO);

    void deleteProduct(Integer id);

}
