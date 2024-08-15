package com.chlima.delivery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class OpenApiConfig {

    @Value("${app.api-gateway-url}")
    private String url;
    @Value("${server.port}")
    private Integer serverPort;

    @Bean
    public OpenAPI myOpenAPI() {

        Server prodServer = new Server();
        prodServer.setUrl(url);
        prodServer.setDescription("API Gateway Server");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:" + serverPort);
        localServer.setDescription("Localhost Server");

        Contact contact = new Contact();
        contact.setEmail("charles.costa@al.infnet.edu.br");
        contact.setName("Charles Costa");
        contact.setUrl("https://www.linkedin.com/in/charlesjlima");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.bezkoder.com/terms");

        return new OpenAPI().info(info).servers(List.of(prodServer, localServer));
    }
}
