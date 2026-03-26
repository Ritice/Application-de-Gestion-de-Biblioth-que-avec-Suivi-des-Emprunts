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

        // Récupère le token JWT depuis le header Authorization
        String token = getTokenFromRequest(request);

        // Vérifie si un token est présent dans la requête
        if (token != null){
            String email;

            try {
                // Extrait l'email (username) contenu dans le token JWT
                email = jwtUtils.getUsernameFromToken(token);

            } catch(Exception ex){
                // Si le token est invalide ou mal formé, on déclenche une erreur d'authentification
                AuthenticationException authenticationException =
                        new BadCredentialsException(ex.getMessage());

                // Appelle le gestionnaire d'erreurs personnalisé
                customAuthenticationEntryPoint.commence(request, response, authenticationException);
                return; // Arrête le traitement de la requête
            }

            // Charge l'utilisateur depuis la base de données via son email
            UserDetails userDetails = utilisateurDetailsService.loadUserByUsername(email);

            // Vérifie que l'email existe et que le token est valide
            if (StringUtils.hasText(email) && jwtUtils.isTokenValid(token, userDetails)){

                // Crée un objet d'authentification Spring Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, // utilisateur authentifié
                                null, // mot de passe (non nécessaire ici)
                                userDetails.getAuthorities() // rôles et permissions
                        );

                // Ajoute des détails sur la requête (IP, session...)
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Enregistre l'utilisateur authentifié dans le contexte de sécurité de Spring
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        try {
            // Continue la chaîne des filtres et permet à la requête d'aller vers le controller
            filterChain.doFilter(request, response);

        } catch (Exception e){
            // En cas d'erreur, log l'erreur
            log.error(e.getMessage());
        }
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