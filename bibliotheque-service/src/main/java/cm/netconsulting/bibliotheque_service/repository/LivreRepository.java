package cm.netconsulting.bibliotheque_service.repository;

import cm.netconsulting.bibliotheque_service.entity.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

    Page<Livre> findByTitreContainingIgnoreCase(String titre, Pageable pageable);

    Page<Livre> findByCategoryIgnoreCase(String category, Pageable pageable);
}