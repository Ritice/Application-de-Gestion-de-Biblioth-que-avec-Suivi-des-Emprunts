package cm.netconsulting.Gateway_service.filter;

import cm.netconsulting.Gateway_service.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;   // ✅ import ajouté
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private final JwtUtils jwtUtils;

    private static final List<String> OPEN_ROUTES = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register"
    );

    @Override
    public ServerResponse filter(ServerRequest request,
                                 HandlerFunction<ServerResponse> next) throws Exception {

        String path = request.uri().getPath();

        if (request.method() == HttpMethod.OPTIONS) {
            return next.handle(request);
        }


        if (isOpenRoute(path)) {
            return next.handle(request);
        }

        // Vérifie la présence du header Authorization
        String authHeader = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Token manquant sur : {}", path);
            return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"status\":401,\"message\":\"Token manquant ou invalide\"}");
        }

        String token = authHeader.substring(7);

        try {
            jwtUtils.validateToken(token);
            String username = jwtUtils.getUsername(token);
            log.info("Utilisateur authentifié : {}", username);

            ServerRequest mutatedRequest = ServerRequest.from(request)
                    .header("X-User-Email", username)
                    .build();

            return next.handle(mutatedRequest);

        } catch (Exception e) {
            log.error("Token invalide : {}", e.getMessage());
            return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"status\":401,\"message\":\"Token invalide ou expiré\"}");
        }
    }

    private boolean isOpenRoute(String path) {
        return OPEN_ROUTES.stream().anyMatch(path::startsWith);
    }
}