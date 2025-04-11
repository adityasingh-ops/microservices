package com.aditya.orderservice.services;

import com.aditya.orderservice.dto.InventoryResponseDto;
import com.aditya.orderservice.dto.OrderLineItemsDto;
import com.aditya.orderservice.dto.OrderRequest;
import com.aditya.orderservice.model.Order;
import com.aditya.orderservice.model.OrderLineItems;
import com.aditya.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineList = orderRequest.getItems()
                .stream()
                .map(orderLineItemsDto-> mapToDto(orderLineItemsDto)).toList();

        order.setItems(orderLineList);

        List<String> skuCode = order.getItems().stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());

        // checking the stock in the inverntory microvervice
        InventoryResponseDto[] inventoryResponseDtos = webClient.get()
                        .uri("http://localhost:5052/api/inventory/",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCode).build()
                                )
                                .retrieve()
                                        .bodyToMono(InventoryResponseDto[].class)
                                                .block();

        boolean allProducts = Arrays.stream(inventoryResponseDtos).allMatch(InventoryResponseDto::isInStock);

    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .id(orderLineItemsDto.getId())
                .skuCode(orderLineItemsDto.getSkuCode())
                .price(orderLineItemsDto.getPrice())
                .build();
    }
}
