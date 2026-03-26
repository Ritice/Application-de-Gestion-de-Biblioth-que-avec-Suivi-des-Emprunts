package cm.netconsulting.utilisateur_service.controller;

import cm.netconsulting.utilisateur_service.dto.requestDTO.LoginRequest;
import cm.netconsulting.utilisateur_service.dto.requestDTO.RegisterRequest;
import cm.netconsulting.utilisateur_service.dto.responseDTO.LoginResponseDTO;
import cm.netconsulting.utilisateur_service.response.Response;
import cm.netconsulting.utilisateur_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Response<?>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponseDTO>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
