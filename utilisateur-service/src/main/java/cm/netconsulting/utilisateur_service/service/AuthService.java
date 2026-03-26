package cm.netconsulting.utilisateur_service.service;

import cm.netconsulting.utilisateur_service.dto.requestDTO.LoginRequest;
import cm.netconsulting.utilisateur_service.dto.requestDTO.RegisterRequest;
import cm.netconsulting.utilisateur_service.dto.responseDTO.LoginResponseDTO;
import cm.netconsulting.utilisateur_service.response.Response;

public interface AuthService {
    Response<?> register(RegisterRequest registerRequest);
    Response<LoginResponseDTO> login(LoginRequest loginRequest);
}
