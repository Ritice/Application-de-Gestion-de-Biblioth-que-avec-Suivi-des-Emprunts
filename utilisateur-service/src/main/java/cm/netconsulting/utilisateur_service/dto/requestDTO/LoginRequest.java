package cm.netconsulting.utilisateur_service.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "email est obligatoire")
    @Email(message = "format email incorrect")
    private String email;
    @NotBlank(message = "le mot de passe est obbligatoire")
    private String password;
}