package com.bran.app.jpa;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;


@Configuration

public class SwaggerConfig {

    @Value("${app.openapi.dev-url}")
    private String devUrl;
  
    @Value("${app.openapi.prod-url}")
    private String prodUrl;    



  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setName("code");



    Info info = new Info()
        .title("JPA example API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage tutorials.").termsOfService("");
    
    
    Server[] SERVERS =
        {devServer,prodServer};
    List<Server> list = Arrays.asList(SERVERS);

    return new OpenAPI().info(info).servers(list);
  }
}