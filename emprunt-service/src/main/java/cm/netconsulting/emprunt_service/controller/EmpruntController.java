package cm.netconsulting.emprunt_service.controller;

import cm.netconsulting.emprunt_service.dto.requestDTO.EmpruntRequest;
import cm.netconsulting.emprunt_service.dto.responseDTO.EmpruntResponse;
import cm.netconsulting.emprunt_service.dto.responseDTO.SuiviResponse;
import cm.netconsulting.emprunt_service.response.Response;
import cm.netconsulting.emprunt_service.service.EmpruntService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emprunts")
@RequiredArgsConstructor
public class EmpruntController {

    private final EmpruntService empruntService;

    //endpoint pour emprunter un livre
    @PostMapping
    public ResponseEntity<Response<EmpruntResponse>> emprunter(
            @Valid @RequestBody EmpruntRequest request) {

        EmpruntResponse data = empruntService.emprunter(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.<EmpruntResponse>builder()
                        .statusCode(201)
                        .message("Emprunt créé avec succès")
                        .data(data)
                        .build());
    }

    //endpoint pour retourner un livre
    @PutMapping("/{id}/retour")
    public ResponseEntity<Response<EmpruntResponse>> retourner(
            @PathVariable Long id) {

        EmpruntResponse data = empruntService.retourner(id);

        return ResponseEntity.ok(
                Response.<EmpruntResponse>builder()
                        .statusCode(200)
                        .message("Retour enregistré avec succès")
                        .data(data)
                        .build());
    }

    //endpoint pour obtenir les emprunts d'un utilisateur
    @GetMapping("/utilisateur/{userId}")
    public ResponseEntity<Response<List<EmpruntResponse>>> getEmpruntsParUtilisateur(
            @PathVariable Long userId) {

        List<EmpruntResponse> data = empruntService.getEmpruntsParUtilisateur(userId);

        return ResponseEntity.ok(
                Response.<List<EmpruntResponse>>builder()
                        .statusCode(200)
                        .message("Emprunts de l'utilisateur " + userId)
                        .data(data)
                        .build());
    }

    // endpoint pour obtenir l'historique complet
    @GetMapping("/suivi")
    public ResponseEntity<Response<List<SuiviResponse>>> getHistoriqueComplet() {

        List<SuiviResponse> data = empruntService.getHistoriqueComplet();

        return ResponseEntity.ok(
                Response.<List<SuiviResponse>>builder()
                        .statusCode(200)
                        .message("Historique complet des emprunts")
                        .data(data)
                        .build());
    }

    // endpoint pour obtenir le historique d'un livre
    @GetMapping("/suivi/livre/{livreId}")
    public ResponseEntity<Response<List<SuiviResponse>>> getHistoriqueParLivre(
            @PathVariable Long livreId) {

        List<SuiviResponse> data = empruntService.getHistoriqueParLivre(livreId);

        return ResponseEntity.ok(
                Response.<List<SuiviResponse>>builder()
                        .statusCode(200)
                        .message("Historique du livre " + livreId)
                        .data(data)
                        .build());
    }
}
