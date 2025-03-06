package com.accenture.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion de location de véhicules")
                        .description("Interface de gestion du parc locatif véhicule")
                        .license(new License()
                                .name("AGPLv3")
                                .url("https://www.gnu.org/licenses/agpl-3.0.html")));
    }
}
