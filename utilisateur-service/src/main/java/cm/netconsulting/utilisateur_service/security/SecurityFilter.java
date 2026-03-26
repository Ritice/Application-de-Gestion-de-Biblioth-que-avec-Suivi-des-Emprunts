package cm.netconsulting.utilisateur_service.security;

import cm.netconsulting.utilisateur_service.exception.CustomAccessDenialHandler;
import cm.netconsulting.utilisateur_service.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration // Indique que cette classe contient la configuration de Spring
@EnableWebSecurity // Active la sécurité Spring Security dans l'application
@EnableMethodSecurity // Permet d'utiliser des annotations de sécurité comme @PreAuthorize
@RequiredArgsConstructor // Lombok génère un constructeur pour les variables final
public class SecurityFilter {

    // Filtre personnalisé qui vérifie les tokens JWT dans chaque requête
    private final AuthFilter authFilter;

    // Gestionnaire personnalisé lorsque l'utilisateur n'a pas les permissions nécessaires (403)
    private final CustomAccessDenialHandler customAccessDenialHandler;

    // Gestionnaire personnalisé pour les erreurs d'authentification (401)
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    // Bean principal qui configure la sécurité de l'application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                // Désactive la protection CSRF (utile pour les API REST stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // Active la configuration CORS définie dans CorsConfig
                .cors(Customizer.withDefaults())

                // Configuration de la gestion des exceptions de sécurité
                .exceptionHandling(ex ->
                        ex
                                // Gère les erreurs lorsque l'utilisateur n'a pas les droits
                                .accessDeniedHandler(customAccessDenialHandler)

                                // Gère les erreurs lorsque l'utilisateur n'est pas authentifié
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                // Configuration des règles d'autorisation des routes
                .authorizeHttpRequests(req ->

                        req
                                // Routes accessibles sans authentification
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/api/v1/roles/**"

                                ).permitAll()

                                // Toutes les autres routes nécessitent une authentification
                                .anyRequest().authenticated()
                )

                // Indique que l'application ne doit pas utiliser de session (JWT = stateless)
                .sessionManagement(mag ->
                        mag.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Ajoute le filtre JWT avant le filtre d'authentification standard de Spring
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        // Construit et retourne la configuration de sécurité
        return httpSecurity.build();
    }


    // Bean pour encoder les mots de passe
    // Utilise l'algorithme BCrypt (recommandé pour la sécurité)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Bean qui permet d'utiliser AuthenticationManager dans les services
    // Utilisé notamment lors du login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}