package br.com.base.service;

import br.com.base.domain.Product;
import br.com.base.domain.dto.ProductDTO;
import br.com.base.exception.NotFoundException;
import br.com.base.mapper.ProductMapper;
import br.com.base.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void listAllProducts() {
        Product product = new Product();
        List<Product> products = Collections.singletonList(product);
        when(this.productRepository.findAll()).thenReturn(products);
        List<ProductDTO> result = this.productService.listAllProducts();
        Assert.assertEquals(this.mapper.toDTO(products), result);
    }

    @Test
    public void getProductById() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 01");
        product.setPrice(BigDecimal.valueOf(10));
        when(this.productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        ProductDTO result = this.productService.getProductById(1);
        Assert.assertEquals(this.mapper.toDTO(product), result);
    }

    @Test
    public void saveProduct() {
        Product product = new Product();
        product.setName("Product 02");
        product.setPrice(BigDecimal.valueOf(50));
        ProductDTO result = this.productService.saveProduct(new ProductDTO());
        Assert.assertEquals(this.mapper.toDTO(product), result);
    }

    @Test(expected = NotFoundException.class)
    public void editProduct_should_throw_notFoundException() {
        ProductDTO productDTO = new ProductDTO();
        this.productService.editProduct(productDTO);
    }

    @Test
    public void editProduct() {
    }

    @Test(expected = NotFoundException.class)
    public void deleteProduct_should_throw_notFoundException() {
        when(this.productRepository.findById(anyInt())).thenThrow(new NotFoundException());
        this.productService.deleteProduct(1);
    }

    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setId(1);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        when(this.productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        doNothing().when(this.productRepository).deleteById(captor.capture());
        this.productService.deleteProduct(product.getId());
        Assert.assertEquals(Integer.valueOf(1), captor.getValue());
    }
}