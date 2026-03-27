package cm.netconsulting.emprunt_service.service.impl;

import cm.netconsulting.emprunt_service.dto.requestDTO.EmpruntRequest;
import cm.netconsulting.emprunt_service.dto.responseDTO.EmpruntResponse;
import cm.netconsulting.emprunt_service.dto.responseDTO.SuiviResponse;
import cm.netconsulting.emprunt_service.entity.Emprunt;
import cm.netconsulting.emprunt_service.entity.SuiviEmprunt;
import cm.netconsulting.emprunt_service.enumeration.StatutEmprunt;
import cm.netconsulting.emprunt_service.enumeration.TypeAction;
import cm.netconsulting.emprunt_service.mapper.EmpruntMapper;
import cm.netconsulting.emprunt_service.repository.EmpruntRepository;
import cm.netconsulting.emprunt_service.repository.SuiviEmpruntRepository;
import cm.netconsulting.emprunt_service.service.EmpruntService;
import cm.netconsulting.emprunt_service.service.client.BibliothequeClient;
import cm.netconsulting.emprunt_service.service.client.UtilisateurClient;
import cm.netconsulting.emprunt_service.service.client.dto.LivreResponseDTO;
import cm.netconsulting.emprunt_service.service.client.dto.UtililisateurResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpruntServiceImpl implements EmpruntService {

    private final EmpruntRepository      empruntRepository;
    private final SuiviEmpruntRepository suiviEmpruntRepository;
    private final BibliothequeClient bibliothequeClient;
    private final UtilisateurClient utilisateurClient;
    private final EmpruntMapper          empruntMapper;


    @Override
    @Transactional
    public EmpruntResponse emprunter(EmpruntRequest request) {
        UtililisateurResponseDTO utilisateur = utilisateurClient
                .getUtilisateur(request.getUtilisateurId())
                .getData();

        if (utilisateur == null) {
            throw new RuntimeException(
                    "Utilisateur introuvable avec l'id : " + request.getUtilisateurId()
            );
        }

        log.info("Utilisateur trouvé : {} {}", utilisateur.getNom(), utilisateur.getPrenom());

        LivreResponseDTO livre = bibliothequeClient
                .getLivre(request.getLivreId())
                .getData();

        if (livre == null) {
            throw new RuntimeException(
                    "Livre introuvable avec l'id : " + request.getLivreId()
            );
        }

        log.info("Livre trouvé : {} — exemplaires : {} — seuil : {}",
                livre.getTitre(),
                livre.getExemplairesDisponibles(),
                livre.getSeuilMinimal()
        );


        if (livre.getTotalExemplaires() <= livre.getSeuilMinimal()) {
            throw new RuntimeException(
                    "Emprunt impossible : le nombre d'exemplaires disponibles (" +
                            livre.getTotalExemplaires() +
                            ") a atteint le seuil minimal (" +
                            livre.getSeuilMinimal() + ")"
            );
        }


        Emprunt emprunt = Emprunt.builder()
                .utilisateurId(request.getUtilisateurId())
                .livreId(request.getLivreId())
                .livreTitre(livre.getTitre())
                .dateRetourPrevue(LocalDateTime.now().plusDays(request.getDureeJours()))
                .statut(StatutEmprunt.EN_COURS)
                .build();

        emprunt = empruntRepository.save(emprunt);
        log.info("Emprunt créé avec l'id : {}", emprunt.getId());

        // 5. Décrémente les exemplaires dans ms-bibliotheque
        bibliothequeClient.decrementerExemplaires(request.getLivreId());
        log.info("Exemplaires décrémentés pour le livre id : {}", request.getLivreId());

        // 6. Enregistre dans le suivi (historique)
        enregistrerSuivi(emprunt, TypeAction.EMPRUNT);

        return empruntMapper.toEmpruntResponse(emprunt);
    }


    @Override
    @Transactional
    public EmpruntResponse retourner(Long empruntId) {


        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException(
                        "Emprunt introuvable avec l'id : " + empruntId
                ));

        if (emprunt.getStatut() == StatutEmprunt.RETOURNE) {
            throw new RuntimeException("Ce livre a déjà été retourné");
        }


        emprunt.setStatut(StatutEmprunt.RETOURNE);
        emprunt.setDateRetourReelle(LocalDateTime.now());
        empruntRepository.save(emprunt);
        log.info("Emprunt {} marqué comme retourné", empruntId);

        // 4. Incrémente les exemplaires dans ms-bibliotheque
        bibliothequeClient.incrementerExemplaires(emprunt.getLivreId());
        log.info("Exemplaires incrémentés pour le livre id : {}", emprunt.getLivreId());


        enregistrerSuivi(emprunt, TypeAction.RETOUR);

        return empruntMapper.toEmpruntResponse(emprunt);
    }


    @Override
    public List<EmpruntResponse> getEmpruntsParUtilisateur(Long userId) {
        return empruntRepository.findByUtilisateurId(userId)
                .stream()
                .map(empruntMapper::toEmpruntResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<SuiviResponse> getHistoriqueParLivre(Long livreId) {
        return suiviEmpruntRepository
                .findByLivreIdOrderByDateActionDesc(livreId)
                .stream()
                .map(empruntMapper::toSuiviResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<SuiviResponse> getHistoriqueComplet() {
        return suiviEmpruntRepository
                .findAllByOrderByDateActionDesc()
                .stream()
                .map(empruntMapper::toSuiviResponse)
                .collect(Collectors.toList());
    }


    private void enregistrerSuivi(Emprunt emprunt, TypeAction action) {
        SuiviEmprunt suivi = SuiviEmprunt.builder()
                .empruntId(emprunt.getId())
                .livreId(emprunt.getLivreId())
                .livreTitre(emprunt.getLivreTitre())
                .utilisateurId(emprunt.getUtilisateurId())
                .typeAction(action)
                .dateAction(LocalDateTime.now())
                .build();

        suiviEmpruntRepository.save(suivi);
        log.info("Suivi enregistré : action={} livre={}", action, emprunt.getLivreId());
    }
}


