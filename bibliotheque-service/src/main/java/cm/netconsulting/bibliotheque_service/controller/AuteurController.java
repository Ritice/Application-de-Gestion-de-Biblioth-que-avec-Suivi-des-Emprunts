package cm.netconsulting.bibliotheque_service.controller;

import cm.netconsulting.bibliotheque_service.dto.resquestDTO.AuteurRequestDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.AuteurResponseDTO;
import cm.netconsulting.bibliotheque_service.service.AuteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auteurs")
@RequiredArgsConstructor
public class AuteurController {

    private final AuteurService auteurService;


    @PostMapping
    public ResponseEntity<AuteurResponseDTO> creer(@RequestBody AuteurRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auteurService.creer(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuteurResponseDTO> modifier(
            @PathVariable Long id,
            @RequestBody AuteurRequestDTO dto) {
        return ResponseEntity.ok(auteurService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuteurResponseDTO> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(auteurService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<Page<AuteurResponseDTO>> listerTous(
            @RequestParam(defaultValue = "0")   int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(auteurService.listerTous(pageable));
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<AuteurResponseDTO>> rechercherParNom(
            @RequestParam String nom,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("nom").ascending());
        return ResponseEntity.ok(auteurService.rechercherParNom(nom, pageable));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        auteurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}