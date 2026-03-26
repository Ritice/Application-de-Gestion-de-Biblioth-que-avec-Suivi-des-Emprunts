package cm.netconsulting.bibliotheque_service.service;

import cm.netconsulting.bibliotheque_service.dto.responseDTO.AuteurResponseDTO;
import cm.netconsulting.bibliotheque_service.dto.resquestDTO.AuteurRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface AuteurService {

    AuteurResponseDTO creer(AuteurRequestDTO dto);

    AuteurResponseDTO modifier(Long id, AuteurRequestDTO dto);

    AuteurResponseDTO trouverParId(Long id);

    Page<AuteurResponseDTO> listerTous(Pageable pageable);

    Page<AuteurResponseDTO> rechercherParNom(String nom, Pageable pageable);

    void supprimer(Long id);
}
