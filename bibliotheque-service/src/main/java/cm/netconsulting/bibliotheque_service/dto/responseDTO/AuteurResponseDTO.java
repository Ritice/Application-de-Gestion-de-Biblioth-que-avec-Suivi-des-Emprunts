package cm.netconsulting.bibliotheque_service.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuteurResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String nationalite;

    private List<LivreResponseDTO> livres;
}
