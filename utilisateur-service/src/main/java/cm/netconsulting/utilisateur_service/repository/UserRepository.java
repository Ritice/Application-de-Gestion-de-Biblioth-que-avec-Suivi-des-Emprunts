package cm.netconsulting.utilisateur_service.repository;

import cm.netconsulting.utilisateur_service.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);
}
