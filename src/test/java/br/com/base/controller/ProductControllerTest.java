package br.com.base.controller;

import br.com.base.SpringBootBaseApplication;
import br.com.base.domain.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootBaseApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private void postProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product 01");
        productDTO.setPrice(BigDecimal.valueOf(1));

        this.mvc.perform(post("/products")
                .content(this.objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void listShouldReturnNoContent() throws Exception {
        this.mvc.perform(get("/products"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void listShouldReturnOk() throws Exception {
        postProduct();
        this.mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Product 01")))
                .andExpect(jsonPath("$[0].price", is(1.0)));
    }

    @Test
    public void showProductShouldReturnNotFound() throws Exception {
        this.mvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void showProductShouldReturnOk() throws Exception {
        postProduct();
        this.mvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 01")))
                .andExpect(jsonPath("$.price", is(1.0)));
    }

    @Test
    public void editShouldReturnBadRequest() throws Exception {
        this.mvc.perform(put("/products/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editShouldReturnNotFound() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1);
        productDTO.setName("Product 01");
        productDTO.setPrice(BigDecimal.valueOf(1));
        String body = objectMapper.writeValueAsString(productDTO);

        this.mvc.perform(put("/products/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void editShouldReturnNoContent() throws Exception {
        postProduct();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1);
        productDTO.setName("Product 02");
        productDTO.setPrice(BigDecimal.valueOf(2));
        String body = this.objectMapper.writeValueAsString(productDTO);

        this.mvc.perform(put("/products/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void newProductShouldReturnBadRequest() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("");
        productDTO.setPrice(null);
        String body = this.objectMapper.writeValueAsString(productDTO);

        this.mvc.perform(post("/products")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void newProductShouldReturnCreated() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product 01");
        productDTO.setPrice(BigDecimal.valueOf(1));

        this.mvc.perform(post("/products")
                        .content(this.objectMapper.writeValueAsString(productDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteShouldReturnNotFound() throws Exception {
        this.mvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContent() throws Exception {
        postProduct();
        this.mvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}