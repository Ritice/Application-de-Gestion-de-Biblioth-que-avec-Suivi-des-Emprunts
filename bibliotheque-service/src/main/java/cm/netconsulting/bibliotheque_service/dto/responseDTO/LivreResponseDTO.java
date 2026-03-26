package cm.netconsulting.bibliotheque_service.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LivreResponseDTO {
    private Long id;
    private String titre;
    private String resume;
    private int seuilMinimal;
    private int totalExemplaires;
    private int exemplairesDisponibles;
    private String category;


    private String imgCouvertureBase64;

    // Liste des auteurs simplifiés
    private List<AuteurResponseDTO> auteurs;
}
