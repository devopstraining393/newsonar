package br.com.base.controller;

import br.com.base.domain.dto.ProductDTO;
import br.com.base.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> list() {
        List<ProductDTO> products = this.productService.listAllProducts();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> showProduct(@PathVariable Integer id) {
        ProductDTO product = this.productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> edit(@Valid @PathVariable Integer id, @RequestBody ProductDTO product) {
        this.productService.editProduct(product);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> newProduct(@Valid @RequestBody ProductDTO productDTO)
            throws URISyntaxException {
        ProductDTO product = this.productService.saveProduct(productDTO);
        URI uri = new URI("products/" + product.getId());
        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
