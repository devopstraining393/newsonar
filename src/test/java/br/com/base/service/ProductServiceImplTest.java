package br.com.base.service;

import br.com.base.domain.Product;
import br.com.base.domain.dto.ProductDTO;
import br.com.base.exception.NotFoundException;
import br.com.base.mapper.ProductMapper;
import br.com.base.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void listAllProducts() {
        Product product = new Product();
        product.setId(1);
        product.setName("PRODUCT 01");
        product.setPrice(BigDecimal.TEN);

        List<Product> products = Collections.singletonList(product);

        List<ProductDTO> expectedList = this.mapper.toDTO(products);

        when(this.productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = this.productService.listAllProducts();
        Assert.assertEquals(this.mapper.toDTO(products).size(), result.size());

        expectedList.forEach(expected ->{
            Assert.assertTrue(
                    result.stream().anyMatch(resultItem -> expected.getName().equals(resultItem.getName())
                            && expected.getId().equals(resultItem.getId())
                            && expected.getPrice().equals(resultItem.getPrice()))
            );
        });
    }

    @Test
    public void getProductById() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 01");
        product.setPrice(BigDecimal.valueOf(10));
        when(this.productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        ProductDTO result = this.productService.getProductById(1);
        Assert.assertEquals(product.getId(), result.getId());
        Assert.assertEquals(product.getName(), result.getName());
        Assert.assertEquals(product.getPrice(), result.getPrice());
    }

    @Test
    public void saveProduct() {
        Product product = new Product();
        product.setName("Product 02");
        product.setPrice(BigDecimal.valueOf(50));

        ProductDTO result = this.productService.saveProduct(this.mapper.toDTO(product));
        Assert.assertEquals(product.getName(), result.getName());
        Assert.assertEquals(product.getPrice(), result.getPrice());
    }

    @Test(expected = NotFoundException.class)
    public void editProductShouldThrowNotFoundException() {
        ProductDTO productDTO = new ProductDTO();
        this.productService.editProduct(productDTO);
    }

    @Test
    public void editProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product Example");
        product.setPrice(BigDecimal.valueOf(100));

        Product productEdited = new Product();
        productEdited.setId(1);
        productEdited.setName("Product Example");
        productEdited.setPrice(BigDecimal.valueOf(50));

        when(this.productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(this.productRepository.save(any(Product.class))).thenReturn(productEdited);

        ProductDTO productDTO = this.mapper.toDTO(product);
        productDTO.setName("Product Example");
        productDTO.setPrice(BigDecimal.valueOf(50));
        ProductDTO result = this.productService.editProduct(productDTO);

        Assert.assertEquals(productEdited.getId(), result.getId());
        Assert.assertEquals(productEdited.getName(), result.getName());
        Assert.assertEquals(productEdited.getPrice(), result.getPrice());
    }

    @Test(expected = NotFoundException.class)
    public void deleteProductShouldThrowNotFoundException() {
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