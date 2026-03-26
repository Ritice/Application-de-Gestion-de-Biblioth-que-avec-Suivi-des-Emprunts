package cm.netconsulting.bibliotheque_service.service;

import cm.netconsulting.bibliotheque_service.dto.responseDTO.LivreResponseDTO;
import cm.netconsulting.bibliotheque_service.dto.resquestDTO.LivreRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivreService {
    LivreResponseDTO creer(LivreRequestDTO dto);

    LivreResponseDTO modifier(Long id, LivreRequestDTO dto);

    LivreResponseDTO trouverParId(Long id);

    Page<LivreResponseDTO> listerTous(Pageable pageable);

    Page<LivreResponseDTO> rechercherParTitre(String titre, Pageable pageable);

    Page<LivreResponseDTO> rechercherParCategorie(String category, Pageable pageable);

    void supprimer(Long id);
}
