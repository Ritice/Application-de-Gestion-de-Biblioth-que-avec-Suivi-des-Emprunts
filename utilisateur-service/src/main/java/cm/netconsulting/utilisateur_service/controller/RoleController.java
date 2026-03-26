package cm.netconsulting.utilisateur_service.controller;

import cm.netconsulting.utilisateur_service.dto.requestDTO.RoleRequestDTO;
import cm.netconsulting.utilisateur_service.dto.responseDTO.RoleResponseDTO;
import cm.netconsulting.utilisateur_service.response.Response;
import cm.netconsulting.utilisateur_service.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('ADMIN')") // Décommenter si sécurisation
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Response<RoleResponseDTO>> createRole(@RequestBody @Valid RoleRequestDTO roleRequest) {
        return ResponseEntity.ok(roleService.createRole(roleRequest));
    }

    @PutMapping
    public ResponseEntity<Response<RoleResponseDTO>> updateRole(@RequestBody @Valid RoleRequestDTO roleRequest) {
        return ResponseEntity.ok(roleService.updateRole(roleRequest));
    }

    @GetMapping
    public ResponseEntity<Response<List<RoleResponseDTO>>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }
}