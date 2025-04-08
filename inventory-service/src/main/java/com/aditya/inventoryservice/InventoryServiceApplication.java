package com.aditya.inventoryservice;

import com.aditya.inventoryservice.model.Inventory;
import com.aditya.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(InventoryServiceApplication.class, args);

    }
    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepo){
        return args -> {
            Inventory inventory = new Inventory();

            inventory.setSkuCode("ABC");
            inventory.setQuantity(1);

            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("DEF");
            inventory2.setQuantity(2);

            Inventory inventory3 = new Inventory();
            inventory3.setSkuCode("GHI");
            inventory3.setQuantity(3);

            inventoryRepo.save(inventory);
            inventoryRepo.save(inventory2);
            inventoryRepo.save(inventory3);
        };
    }

}
