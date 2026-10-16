package com.example.DigitalWallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Digital Wallet - Swagger API Documentation")
                        .description("Description - Digital Wallet - Swagger API Documentation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Pandit")
                                .email("info@digialwallet.com")
                                .url("###########")
                        )
                );
    }
}
