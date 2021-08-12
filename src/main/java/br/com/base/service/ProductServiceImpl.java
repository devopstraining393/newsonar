package br.com.base.service;

import br.com.base.domain.Product;
import br.com.base.domain.dto.ProductDTO;
import br.com.base.exception.NotFoundException;
import br.com.base.mapper.ProductMapper;
import br.com.base.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return this.mapper.toDTO(products);
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return this.mapper.toDTO(product);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = this.mapper.toEntity(productDTO);
        this.productRepository.save(product);
        return this.mapper.toDTO(product);
    }

    @Override
    public void editProduct(ProductDTO productDTO) {
        this.getProductById(productDTO.getId());
        Product product = this.mapper.toEntity(productDTO);
        this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        this.getProductById(id);
        this.productRepository.deleteById(id);
    }
}
