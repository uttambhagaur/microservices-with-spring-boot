package com.microservices.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.product_service.dto.ProductRequest;
import com.microservices.product_service.dto.ProductResponse;
import com.microservices.product_service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddProduct() throws Exception {
        ProductRequest product = ProductRequest.builder().build();
        ProductResponse productResponse = ProductResponse.builder().build();
        when(productService.addProduct(product)).thenReturn(productResponse);

        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        ).andExpect(status().isCreated());
    }

    @Test
    public void testGetAllProducts() throws Exception{
        List<ProductResponse> list = new ArrayList<>();
        list.add(ProductResponse.builder().build());
        list.add(ProductResponse.builder().build());
        list.add(ProductResponse.builder().build());
        when(productService.getAllProducts()).thenReturn(list);

        mockMvc.perform(get("/api/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)));
    }
}
