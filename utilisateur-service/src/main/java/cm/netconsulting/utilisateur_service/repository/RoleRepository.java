package cm.netconsulting.utilisateur_service.repository;


import cm.netconsulting.utilisateur_service.Enumeration.ROLE;
import cm.netconsulting.utilisateur_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNom(ROLE nom);
}