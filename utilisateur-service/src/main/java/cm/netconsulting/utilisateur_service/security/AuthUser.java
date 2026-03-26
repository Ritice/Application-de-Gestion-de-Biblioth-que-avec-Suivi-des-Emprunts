package cm.netconsulting.utilisateur_service.security;

import cm.netconsulting.utilisateur_service.entity.Utilisateur;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// ✅ CORRIGÉ : suppression de l'import inutilisé UserDetails.User

import java.util.Collection;

@Data
@Builder
public class AuthUser implements UserDetails {

    private Utilisateur user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNom().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}