package cm.netconsulting.utilisateur_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
@Slf4j
public class JwtUtils {

    // Durée de validité du token : 30 jours en millisecondes
    private static final long EXPIRATION_TIME = 30L * 24 * 60 * 60 * 1000;

    // Clé secrète utilisée pour signer et vérifier les JWT
    private SecretKey key;

    // Récupère la valeur de la clé secrète depuis application.yml
    @Value("${jwt.secret}")
    private String secreteJwtString;

    // Cette méthode est exécutée automatiquement après la création du bean Spring
    @PostConstruct
    private void init() {

        // Convertit la clé secrète (String) en tableau de bytes
        byte[] keyByte = secreteJwtString.getBytes(StandardCharsets.UTF_8);

        // Crée une clé HMAC SHA256 utilisée pour signer les tokens JWT
        this.key = new SecretKeySpec(keyByte, "HmacSHA256");
    }

    // Méthode qui génère un token JWT pour un utilisateur
    public String generateToken(String email) {

        return Jwts.builder()

                // Définit l'identité du token (ici l'email de l'utilisateur)
                .subject(email)

                // Date de création du token
                .issuedAt(new Date(System.currentTimeMillis()))

                // Date d'expiration du token
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                // Signature du token avec la clé secrète
                .signWith(key)

                // Génère le token final sous forme de String
                .compact();
    }

    // Méthode pour récupérer le username (email) contenu dans le token
    public String getUsernameFromToken(String token) {

        // On extrait la propriété "subject" du token
        return extractClaims(token, Claims::getSubject);
    }

    // Méthode générique pour extraire n'importe quelle information (claim) du token
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {

        // Parse le token, vérifie la signature et récupère les données (payload)
        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(key) // vérifie la signature avec la clé
                        .build()
                        .parseSignedClaims(token) // décode le token
                        .getPayload() // récupère les informations du token
        );
    }

    // Vérifie si le token est valide pour un utilisateur donné
    public boolean isTokenValid(String token, UserDetails userDetails) {

        // Récupère le username contenu dans le token
        final String username = getUsernameFromToken(token);

        // Vérifie que :
        // le username correspond à celui de l'utilisateur
        // le token n'est pas expiré
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Vérifie si le token est expiré
    private boolean isTokenExpired(String token) {

        // Compare la date d'expiration du token avec la date actuelle
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}