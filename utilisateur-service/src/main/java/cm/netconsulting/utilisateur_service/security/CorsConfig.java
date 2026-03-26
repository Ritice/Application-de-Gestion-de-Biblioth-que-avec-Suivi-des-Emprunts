package cm.netconsulting.utilisateur_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indique que cette classe contient des configurations Spring
public class CorsConfig {

    // Déclare un Bean Spring qui configure le comportement de Spring MVC
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        // Retourne une implémentation de WebMvcConfigurer
        // qui permet de personnaliser la configuration du framework MVC
        return new WebMvcConfigurer() {

            // Méthode appelée par Spring pour configurer les règles CORS
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // Applique la configuration CORS à toutes les routes de l'API
                registry.addMapping("/**")

                        // Autorise les méthodes HTTP suivantes
                        .allowedMethods("GET", "POST", "PUT", "DELETE")

                        // Autorise les requêtes provenant de n'importe quelle origine
                        // (ex: Angular, React, Postman, etc.)
                        .allowedOrigins("*");
            }
        };
    }
}
