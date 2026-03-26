package cm.netconsulting.bibliotheque_service.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
