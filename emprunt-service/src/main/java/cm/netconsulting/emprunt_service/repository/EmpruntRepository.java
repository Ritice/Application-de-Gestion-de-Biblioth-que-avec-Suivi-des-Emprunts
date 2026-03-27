package cm.netconsulting.emprunt_service.repository;

import cm.netconsulting.emprunt_service.entity.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByUtilisateurId(Long utilisateurId);
    List<Emprunt> findByLivreId(Long livreId);
}
