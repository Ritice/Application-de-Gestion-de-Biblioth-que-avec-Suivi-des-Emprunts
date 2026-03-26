package cm.netconsulting.bibliotheque_service.mapper;

import cm.netconsulting.bibliotheque_service.dto.resquestDTO.LivreRequestDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.AuteurResponseDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.LivreResponseDTO;
import cm.netconsulting.bibliotheque_service.entity.Livre;
import cm.netconsulting.bibliotheque_service.exception.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class LivreMapper {

    /**
     * RequestDTO → Entité
     * BeanUtils copie : titre, resume, seuilMinimal,
     *                   totalExemplaires, exemplairesDisponibles, category
     * Exclus manuellement : imgCouverture (MultipartFile → byte[]), auteurIds
     */
    public Livre toEntity(LivreRequestDTO dto) {
        Livre livre = new Livre();
        BeanUtils.copyProperties(dto, livre, "imgCouverture", "auteurIds");
        livre.setImgCouverture(extractBytes(dto));
        return livre;
    }

    /**
     * RequestDTO → Entité existante (pour la mise à jour)
     * On ne touche à l'image que si une nouvelle est fournie.
     */
    public void updateEntityFromDTO(LivreRequestDTO dto, Livre livre) {
        BeanUtils.copyProperties(dto, livre, "imgCouverture", "auteurIds");
        if (dto.getImgCouverture() != null && !dto.getImgCouverture().isEmpty()) {
            livre.setImgCouverture(extractBytes(dto));
        }
    }

    /**
     * Entité → ResponseDTO
     * BeanUtils copie : id, titre, resume, seuilMinimal,
     *                   totalExemplaires, exemplairesDisponibles, category
     * Exclus manuellement : imgCouverture (byte[] → Base64), auteurs (évite récursion)
     */
    public LivreResponseDTO toResponseDTO(Livre livre) {
        LivreResponseDTO dto = LivreResponseDTO.builder().build();
        BeanUtils.copyProperties(livre, dto, "imgCouverture", "auteurs");

        // byte[] → Base64 String
        if (livre.getImgCouverture() != null && livre.getImgCouverture().length > 0) {
            dto.setImgCouvertureBase64(Base64.getEncoder().encodeToString(livre.getImgCouverture()));
        }

        // Auteurs sans leurs livres (évite la récursion infinie Livre → Auteur → Livre)
        if (livre.getAuteurs() != null) {
            List<AuteurResponseDTO> auteurDTOs = livre.getAuteurs().stream()
                    .map(auteur -> {
                        AuteurResponseDTO auteurDTO = AuteurResponseDTO.builder().build();
                        BeanUtils.copyProperties(auteur, auteurDTO, "livres");
                        return auteurDTO;
                    })
                    .toList();
            dto.setAuteurs(auteurDTOs);
        } else {
            dto.setAuteurs(Collections.emptyList());
        }

        return dto;
    }

    // -------------------------------------------------------
    // PRIVÉ
    // -------------------------------------------------------
    private byte[] extractBytes(LivreRequestDTO dto) {
        if (dto.getImgCouverture() == null || dto.getImgCouverture().isEmpty()) {
            return null;
        }
        try {
            return dto.getImgCouverture().getBytes();
        } catch (IOException e) {
            throw new BadRequestException("Erreur lors de la lecture de l'image : " + e.getMessage());
        }
    }
}