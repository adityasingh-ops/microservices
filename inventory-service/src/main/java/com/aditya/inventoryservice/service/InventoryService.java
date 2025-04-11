package com.aditya.inventoryservice.service;

import com.aditya.inventoryservice.dto.InventoryResponseDto;
import com.aditya.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponseDto> isInStock(List<String> skuCode) {

        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(e ->
                    InventoryResponseDto.builder()
                            .skuCode(e.getSkuCode())
                            .isInStock(e.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
