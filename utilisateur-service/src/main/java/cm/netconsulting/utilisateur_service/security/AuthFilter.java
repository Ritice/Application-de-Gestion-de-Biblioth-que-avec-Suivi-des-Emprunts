package cm.netconsulting.utilisateur_service.security;


import cm.netconsulting.utilisateur_service.exception.CustomAuthenticationEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component // Indique que cette classe est un composant Spring (Bean géré par Spring)
@RequiredArgsConstructor // Lombok génère automatiquement un constructeur pour les attributs final
@Slf4j // Lombok crée un logger (log.info, log.error, etc.)
public class AuthFilter extends OncePerRequestFilter { // Filtre exécuté une seule fois par requête HTTP

    // Utilitaire pour gérer les JWT (génération, validation, extraction des données)
    private final JwtUtils jwtUtils;

    // Service permettant de charger un utilisateur depuis la base de données
    private final UtilisateurDetailsService utilisateurDetailsService;

    // Classe qui gère les erreurs d'authentification (ex: token invalide, accès refusé)
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    // Méthode principale du filtre exécutée pour chaque requête HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.startsWith("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token != null) {
            String email;

            try {
                email = jwtUtils.getUsernameFromToken(token);
            } catch (Exception ex) {
                AuthenticationException authenticationException =
                        new BadCredentialsException(ex.getMessage());

                customAuthenticationEntryPoint.commence(request, response, authenticationException);
                return;
            }

            UserDetails userDetails = utilisateurDetailsService.loadUserByUsername(email);

            if (StringUtils.hasText(email) && jwtUtils.isTokenValid(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Méthode utilitaire pour récupérer le token JWT depuis le header HTTP
    private String getTokenFromRequest(HttpServletRequest request) {

        // Récupère la valeur du header Authorization
        String tokenWithBearer = request.getHeader("Authorization");

        // Vérifie que le header existe et qu'il commence par "Bearer "
        if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {

            // Supprime "Bearer " pour récupérer uniquement le token
            return tokenWithBearer.substring(7);
        }

        // Si aucun token n'est trouvé, retourne null
        return null;
    }
}