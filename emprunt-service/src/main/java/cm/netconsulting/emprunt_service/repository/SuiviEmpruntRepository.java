package cm.netconsulting.emprunt_service.repository;


import cm.netconsulting.emprunt_service.entity.SuiviEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SuiviEmpruntRepository extends JpaRepository<SuiviEmprunt, Long> {
    List<SuiviEmprunt> findByLivreIdOrderByDateActionDesc(Long livreId);
    List<SuiviEmprunt> findAllByOrderByDateActionDesc();
}