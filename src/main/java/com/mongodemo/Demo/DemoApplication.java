package com.mongodemo.Demo;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;

// import com.mongodemo.Demo.product.Product;
// import com.mongodemo.Demo.product.ProductRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// @Bean
	// CommandLineRunner commandLineRunner(ProductRepository productRepository) {
	// return args -> {
	// var p = Product.builder()
	// .name("Watch")
	// .description("Men's watch")
	// .build();

	// productRepository.insert(p);
	// };
	// }
}
