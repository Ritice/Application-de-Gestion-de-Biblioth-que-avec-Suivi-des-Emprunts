package cm.netconsulting.utilisateur_service.dto.requestDTO;

import cm.netconsulting.utilisateur_service.Enumeration.ROLE;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
public class RoleRequestDTO {
    private Long id;
    private ROLE nom;

}
