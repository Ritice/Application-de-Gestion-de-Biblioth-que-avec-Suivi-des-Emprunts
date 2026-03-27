package cm.netconsulting.emprunt_service.dto.responseDTO;


import cm.netconsulting.emprunt_service.enumeration.TypeAction;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SuiviResponse {
    private Long id;
    private Long livreId;
    private String livreTitre;
    private Long utilisateurId;
    private TypeAction typeAction;
    private LocalDateTime dateAction;
}
