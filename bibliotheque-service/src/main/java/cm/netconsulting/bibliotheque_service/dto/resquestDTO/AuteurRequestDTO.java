package cm.netconsulting.bibliotheque_service.dto.resquestDTO;

import lombok.Data;

import java.util.List;

@Data
public class AuteurRequestDTO {
    private String nom;
    private String prenom;
    private String adresse;
    private String nationalite;

    private List<Long> livreIds;
}
