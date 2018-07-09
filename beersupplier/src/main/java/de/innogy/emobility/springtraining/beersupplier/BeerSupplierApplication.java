package de.innogy.emobility.springtraining.beersupplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class BeerSupplierApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerSupplierApplication.class, args);
	}
}
