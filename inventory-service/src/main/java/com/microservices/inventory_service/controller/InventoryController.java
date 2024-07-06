package com.microservices.inventory_service.controller;

import com.microservices.inventory_service.dto.InventoryResponse;
import com.microservices.inventory_service.models.Inventory;
import com.microservices.inventory_service.repository.InventoryRepository;
import com.microservices.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
//    @GetMapping("/{sku-code}")
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode){
        return ResponseEntity.ok(this.inventoryService.isInStock(skuCode));
    }
}
