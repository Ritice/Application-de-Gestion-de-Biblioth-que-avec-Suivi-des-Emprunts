package cm.netconsulting.bibliotheque_service.service.impl;

import cm.netconsulting.bibliotheque_service.dto.resquestDTO.AuteurRequestDTO;
import cm.netconsulting.bibliotheque_service.dto.responseDTO.AuteurResponseDTO;
import cm.netconsulting.bibliotheque_service.entity.Auteur;
import cm.netconsulting.bibliotheque_service.entity.Livre;
import cm.netconsulting.bibliotheque_service.exception.BadRequestException;
import cm.netconsulting.bibliotheque_service.exception.NotFoundException;
import cm.netconsulting.bibliotheque_service.mapper.AuteurMapper;
import cm.netconsulting.bibliotheque_service.repository.AuteurRepository;
import cm.netconsulting.bibliotheque_service.repository.LivreRepository;
import cm.netconsulting.bibliotheque_service.service.AuteurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuteurServiceImpl implements AuteurService {

    private final AuteurRepository auteurRepository;
    private final LivreRepository livreRepository;
    private final AuteurMapper auteurMapper;

    @Override
    public AuteurResponseDTO creer(AuteurRequestDTO dto) {
        Auteur auteur = auteurMapper.toEntity(dto);

        List<Livre> livres = resolveLivres(dto.getLivreIds());
        auteur.setLivres(livres);

        //  Synchroniser le côté inverse (livre.auteurs) pour la cohérence mémoire
        Auteur saved = auteurRepository.save(auteur);
        for (Livre livre : livres) {
            if (livre.getAuteurs() == null) {
                livre.setAuteurs(new ArrayList<>());
            }
            if (!livre.getAuteurs().contains(saved)) {
                livre.getAuteurs().add(saved);
            }
        }

        return auteurMapper.toResponseDTO(saved);
    }

    @Override
    public AuteurResponseDTO modifier(Long id, AuteurRequestDTO dto) {
        log.info("Modification de l'auteur id : {}", id);

        Auteur auteur = auteurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auteur introuvable avec l'id : " + id));

        // Mise à jour des champs simples via mapper
        auteurMapper.updateEntityFromDTO(dto, auteur);

        // Mise à jour des associations si une nouvelle liste est fournie
        if (dto.getLivreIds() != null) {
            // Nettoyer les anciens livres du côté inverse
            if (auteur.getLivres() != null) {
                for (Livre ancienLivre : auteur.getLivres()) {
                    if (ancienLivre.getAuteurs() != null) {
                        ancienLivre.getAuteurs().remove(auteur);
                    }
                }
            }

            // Associer les nouveaux livres
            List<Livre> nouveauxLivres = resolveLivres(dto.getLivreIds());
            auteur.setLivres(nouveauxLivres);

            // Synchroniser le côté inverse
            for (Livre livre : nouveauxLivres) {
                if (livre.getAuteurs() == null) {
                    livre.setAuteurs(new ArrayList<>());
                }
                if (!livre.getAuteurs().contains(auteur)) {
                    livre.getAuteurs().add(auteur);
                }
            }
        }

        Auteur updated = auteurRepository.save(auteur);
        log.info("Auteur id {} modifié avec succès", updated.getId());

        return auteurMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public AuteurResponseDTO trouverParId(Long id) {
        log.info("Recherche de l'auteur id : {}", id);
        Auteur auteur = auteurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auteur introuvable avec l'id : " + id));
        return auteurMapper.toResponseDTO(auteur);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuteurResponseDTO> listerTous(Pageable pageable) {
        log.info("Listing de tous les auteurs - page {}", pageable.getPageNumber());
        return auteurRepository.findAll(pageable).map(auteurMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuteurResponseDTO> rechercherParNom(String nom, Pageable pageable) {
        if (nom == null || nom.isBlank()) {
            throw new BadRequestException("Le paramètre 'nom' est obligatoire pour la recherche");
        }
        return auteurRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(nom, nom, pageable)
                .map(auteurMapper::toResponseDTO);
    }

    @Override
    public void supprimer(Long id) {
        Auteur auteur = auteurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auteur introuvable avec l'id : " + id));

        // Nettoyer la table auteur_livre avant suppression
        if (auteur.getLivres() != null) {
            for (Livre livre : auteur.getLivres()) {
                if (livre.getAuteurs() != null) {
                    livre.getAuteurs().remove(auteur);
                }
            }
            auteur.getLivres().clear();
        }

        auteurRepository.delete(auteur);
        log.info("Auteur id {} supprimé avec succès", id);
    }


    private List<Livre> resolveLivres(List<Long> livreIds) {
        if (livreIds == null || livreIds.isEmpty()) {
            return new ArrayList<>();
        }
        return livreIds.stream()
                .map(livreId -> livreRepository.findById(livreId)
                        .orElseThrow(() -> new NotFoundException(
                                "Livre introuvable avec l'id : " + livreId)))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }
}