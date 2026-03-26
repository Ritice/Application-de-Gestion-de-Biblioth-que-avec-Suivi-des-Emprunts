package cm.netconsulting.utilisateur_service.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    @NotBlank(message = "le nom est obligatoire")
    private String nom;

    @NotBlank(message = "le prenom est obligatoire")
    private String prenom;

    @NotBlank(message = "email obligatoire")
    @Email(message = "email non valide")
    private String email;

    @NotBlank(message = "mot de passe obligatoire")
    @Size(min = 3, message = " le mot de passe doit avoir au moins 3 caractères")
    private String password;

    @NotBlank(message = "adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "le numero de telephone est obligatoire")
    private String telephone;

    private List<String> roles;
}
