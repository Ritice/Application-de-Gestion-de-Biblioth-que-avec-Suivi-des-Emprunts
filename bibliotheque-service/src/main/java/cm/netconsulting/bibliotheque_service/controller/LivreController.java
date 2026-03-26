package cm.netconsulting.bibliotheque_service.controller;

import cm.netconsulting.bibliotheque_service.dto.resquestDTO.LivreRequestDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.LivreResponseDTO;
import cm.netconsulting.bibliotheque_service.service.LivreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/livres")
@RequiredArgsConstructor
public class LivreController {

    private final LivreService livreService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LivreResponseDTO> creer(@ModelAttribute LivreRequestDTO dto) {
        LivreResponseDTO response = livreService.creer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LivreResponseDTO> modifier(
            @PathVariable Long id,
            @ModelAttribute LivreRequestDTO dto) {
        return ResponseEntity.ok(livreService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreResponseDTO> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(livreService.trouverParId(id));
    }


    @GetMapping
    public ResponseEntity<Page<LivreResponseDTO>> listerTous(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(livreService.listerTous(pageable));
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<LivreResponseDTO>> rechercherParTitre(
            @RequestParam String titre,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("titre").ascending());
        return ResponseEntity.ok(livreService.rechercherParTitre(titre, pageable));
    }

    @GetMapping("/categorie")
    public ResponseEntity<Page<LivreResponseDTO>> rechercherParCategorie(
            @RequestParam String category,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("titre").ascending());
        return ResponseEntity.ok(livreService.rechercherParCategorie(category, pageable));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        livreService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}