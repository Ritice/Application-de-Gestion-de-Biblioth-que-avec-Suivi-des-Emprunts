package cm.netconsulting.emprunt_service.dto.requestDTO;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
    public class EmpruntRequest {

        @NotNull
        private Long utilisateurId;

        @NotNull
        private Long livreId;

        private Integer dureeJours = 14;
    }

