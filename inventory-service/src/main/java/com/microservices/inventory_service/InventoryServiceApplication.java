package com.microservices.inventory_service;

import com.microservices.inventory_service.models.Inventory;
import com.microservices.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setId(1L);
			inventory.setQuantity(10);
			inventory.setSkuCode("Iphone_15");
			inventoryRepository.save(inventory);
			inventory = new Inventory();
			inventory.setId(2L);
			inventory.setQuantity(12);
			inventory.setSkuCode("Iphone_12");
			inventoryRepository.save(inventory);
		};
	}
}
