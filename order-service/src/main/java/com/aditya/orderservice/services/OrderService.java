package com.aditya.orderservice.services;

import com.aditya.orderservice.dto.OrderLineItemsDto;
import com.aditya.orderservice.dto.OrderRequest;
import com.aditya.orderservice.model.Order;
import com.aditya.orderservice.model.OrderLineItems;
import com.aditya.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

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

        // checking the stock in the inverntory microvervice
        Boolean result = webClient.get()
                        .uri("http://localhost:5052/api/inventory/")
                                .retrieve()
                                        .bodyToMono(Boolean.class)
                                                .block();

        if (result) {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not available");
        }

    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .id(orderLineItemsDto.getId())
                .skuCode(orderLineItemsDto.getSkuCode())
                .price(orderLineItemsDto.getPrice())
                .build();
    }
}
