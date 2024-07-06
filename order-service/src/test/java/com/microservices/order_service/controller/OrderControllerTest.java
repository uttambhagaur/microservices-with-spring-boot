package com.microservices.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.order_service.dto.OrderLineItemsDto;
import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.dto.OrderResponse;
import com.microservices.order_service.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddOrder() throws Exception{
        OrderRequest orderRequest = new OrderRequest();
        OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
        orderLineItemsDto.setId(1L);
        orderLineItemsDto.setQuantity(100);
        orderLineItemsDto.setPrice(200.0);
        orderLineItemsDto.setSkuCode("iphone");

        orderRequest.setOrderLineItemsList(Arrays.asList(orderLineItemsDto));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        orderResponse.setOrderNumber(UUID.randomUUID().toString());
        orderResponse.setOrderLineItemsList(Arrays.asList(orderLineItemsDto));
        when(orderService.addOrder(orderRequest)).thenReturn(orderResponse);

        mockMvc.perform(post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest))
        ).andExpect(status().isCreated());
    }
}
