package br.com.monitodehabitos.monitodehabitos.infra.infras.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Monitor de hábitos")
                        .description("API para gerenciamento de hábitos. Todos os direitos reservados.")
                        .contact(new Contact()
                                .name("Time Backend - Matheus MOzart")
                                .email("bmozart.dev@gmail.com")
                                .url("https://www.linkedin.com/in/matheus-mozart-borges/")
                        ));
    }
}