package dev.chrisjosue.groceryrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class GroceryRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroceryRestApiApplication.class, args);
    }
}
