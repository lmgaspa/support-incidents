package com.dianaglobal.tickets.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticketOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticket Backend API")
                        .description("API for support ticket creation with RabbitMQ publishing, observability, and OpenAPI documentation. Email delivery is handled by backend2.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Support Incidents Backend"))
                        .license(new License()
                                .name("Internal Use")));
    }
}
