package cm.netconsulting.emprunt_service.service.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
