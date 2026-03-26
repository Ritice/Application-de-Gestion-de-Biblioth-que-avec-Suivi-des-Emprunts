package cm.netconsulting.utilisateur_service.dto.responseDTO;

import cm.netconsulting.utilisateur_service.Enumeration.ROLE;
import cm.netconsulting.utilisateur_service.entity.Utilisateur;
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
public class RoleResponseDTO {
    private Long id;
    private ROLE nom;
    private List<Utilisateur> utilisateurs;
}
