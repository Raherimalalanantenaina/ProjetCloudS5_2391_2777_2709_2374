package web.projet.fournisseurIdentite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
            .info(new Info()
                .title("Fournisseur d'identite api")
                .version("1.0.0")
                .description("Api pour recevoir des informations sur un utilisateur"));
    }
}
