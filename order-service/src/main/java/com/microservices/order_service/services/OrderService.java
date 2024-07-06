package com.microservices.order_service.services;

import com.microservices.order_service.dto.InventoryResponse;
import com.microservices.order_service.dto.OrderLineItemsDto;
import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.dto.OrderResponse;
import com.microservices.order_service.exceptions.ProductNotInStock;
import com.microservices.order_service.models.Order;
import com.microservices.order_service.models.OrderLineItems;
import com.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public OrderResponse addOrder(OrderRequest orderRequest) {
        log.info("Order is going to create");
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsList()
                .stream().map(this::mapOrderLineItemsDto).collect(Collectors.toList()));

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(o->o.getSkuCode()).collect(Collectors.toList());

        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);
        if(allProductsInStock==false || inventoryResponsesArray.length==0){
            throw new ProductNotInStock("Some products are not in the stocks");
        }
        return mapOrderToOrderResponse(orderRepository.save(order));
    }

    private OrderResponse mapOrderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsList(order.getOrderLineItemsList().stream().map(this::mapOrderLineItems).collect(Collectors.toList()))
                .build();
    }

    private OrderLineItems mapOrderLineItemsDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
    private OrderLineItemsDto mapOrderLineItems(OrderLineItems orderLineItems) {
        OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
        orderLineItemsDto.setId(orderLineItems.getId());
        orderLineItemsDto.setQuantity(orderLineItems.getQuantity());
        orderLineItemsDto.setPrice(orderLineItems.getPrice());
        orderLineItemsDto.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItemsDto;
    }
}
