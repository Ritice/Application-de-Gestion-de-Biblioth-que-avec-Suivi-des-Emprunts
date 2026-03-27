package cm.netconsulting.Gateway_service.config;

import cm.netconsulting.Gateway_service.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authFilter;

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return route("auth-public")
                .route(path("/api/v1/auth/**"), http())
                .before(uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> utilisateurRoutes() {
        return route("utilisateur-service")
                .route(path("/api/v1/roles/**"), http())
                .before(uri("http://localhost:8081"))
                .filter(authFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bibliothequeRoutes() {
        return route("bibliotheque-service")
                .route(path("/api/v1/livres/**").or(path("/api/v1/auteurs/**")), http())
                .before(uri("http://localhost:8082"))
                .filter(authFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> empruntRoutes() {
        return route("emprunt-service")
                .route(path("/api/v1/emprunts/**"), http())
                .before(uri("http://localhost:8083"))
                .filter(authFilter)
                .build();
    }

}