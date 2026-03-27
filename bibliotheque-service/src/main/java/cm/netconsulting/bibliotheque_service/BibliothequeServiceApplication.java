package cm.netconsulting.bibliotheque_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BibliothequeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliothequeServiceApplication.class, args);
	}

}
