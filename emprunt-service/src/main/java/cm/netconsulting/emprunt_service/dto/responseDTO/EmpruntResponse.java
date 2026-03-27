package cm.netconsulting.emprunt_service.dto.responseDTO;

import cm.netconsulting.emprunt_service.enumeration.StatutEmprunt;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmpruntResponse {
    private Long id;
    private Long utilisateurId;
    private Long livreId;
    private String livreTitre;
    private LocalDateTime dateEmprunt;
    private LocalDateTime dateRetourPrevue;
    private LocalDateTime dateRetourReelle;
    private StatutEmprunt statut;
}
