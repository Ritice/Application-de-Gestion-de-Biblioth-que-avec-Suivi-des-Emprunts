package cm.netconsulting.bibliotheque_service.mapper;

import cm.netconsulting.bibliotheque_service.dto.resquestDTO.AuteurRequestDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.AuteurResponseDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.LivreResponseDTO;
import cm.netconsulting.bibliotheque_service.entity.Auteur;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class AuteurMapper {

    /**
     * RequestDTO → Entité
     * BeanUtils copie : nom, prenom, adresse, nationalite
     * Exclus manuellement : livreIds (List<Long> → List<Livre> résolu dans le service)
     */
    public Auteur toEntity(AuteurRequestDTO dto) {
        Auteur auteur = new Auteur();
        BeanUtils.copyProperties(dto, auteur, "livreIds");
        return auteur;
    }

    /**
     * RequestDTO → Entité existante (pour la mise à jour)
     */
    public void updateEntityFromDTO(AuteurRequestDTO dto, Auteur auteur) {
        BeanUtils.copyProperties(dto, auteur, "livreIds");
    }

    /**
     * Entité → ResponseDTO
     * BeanUtils copie : id, nom, prenom, adresse, nationalite
     * Exclus manuellement : livres (évite récursion Auteur → Livre → Auteur)
     */
    public AuteurResponseDTO toResponseDTO(Auteur auteur) {
        AuteurResponseDTO dto = AuteurResponseDTO.builder().build();
        BeanUtils.copyProperties(auteur, dto, "livres");

        // Livres sans leurs auteurs (évite la récursion infinie)
        if (auteur.getLivres() != null) {
            List<LivreResponseDTO> livreDTOs = auteur.getLivres().stream()
                    .map(livre -> {
                        LivreResponseDTO livreDTO = LivreResponseDTO.builder().build();
                        BeanUtils.copyProperties(livre, livreDTO, "auteurs", "imgCouverture");

                        // byte[] → Base64 String
                        if (livre.getImgCouverture() != null && livre.getImgCouverture().length > 0) {
                            livreDTO.setImgCouvertureBase64(
                                    Base64.getEncoder().encodeToString(livre.getImgCouverture()));
                        }
                        return livreDTO;
                    })
                    .toList();
            dto.setLivres(livreDTOs);
        } else {
            dto.setLivres(Collections.emptyList());
        }

        return dto;
    }
}