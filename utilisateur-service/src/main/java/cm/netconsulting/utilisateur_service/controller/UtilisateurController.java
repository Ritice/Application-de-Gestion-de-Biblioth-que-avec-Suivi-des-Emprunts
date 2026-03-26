package cm.netconsulting.utilisateur_service.controller;

import cm.netconsulting.utilisateur_service.dto.responseDTO.UtililisateurResponseDTO;
import cm.netconsulting.utilisateur_service.response.Response;

import cm.netconsulting.utilisateur_service.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UtilisateurController {


    private final UtilisateurService utilisateurService;


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<UtililisateurResponseDTO>>> getAllUsers(){
        return ResponseEntity.ok(utilisateurService.getAllUsers());
    }





}
