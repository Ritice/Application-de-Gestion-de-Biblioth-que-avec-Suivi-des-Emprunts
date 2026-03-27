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
public class UtililisateurResponseDTO {
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private boolean isActive;

    private String password;

    private String adresse;

    private String telephone;

    private List<String> roles;
}

