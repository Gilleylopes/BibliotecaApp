package br.com.tjrj.biblioteca.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()

            .info(new Info()
                .title("API da Biblioteca Digital")
                .version("v1")
                .description("Esta API fornece recursos de autenticação, consulta e manutenção de livros digitais.")
                .termsOfService("https://www.tjrj.jus.br/termos-de-uso")
                .contact(new Contact()
                    .name("Equipe de Desenvolvimento")
                    .email("dev@tjrj.jus.br")
                    .url("https://www.tjrj.jus.br")
                )
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")
                )
            )

            .servers(List.of(
                new Server().url("http://localhost:8080").description("Ambiente Local"),
                new Server().url("https://api.tjrj.jus.br").description("Produção")
            ))

            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                        .description("Informe o token JWT gerado no login.")
                )
            );
    }
}
