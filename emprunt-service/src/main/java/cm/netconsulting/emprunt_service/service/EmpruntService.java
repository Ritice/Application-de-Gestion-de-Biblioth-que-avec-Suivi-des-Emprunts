package cm.netconsulting.emprunt_service.service;

import cm.netconsulting.emprunt_service.dto.requestDTO.EmpruntRequest;
import cm.netconsulting.emprunt_service.dto.responseDTO.EmpruntResponse;
import cm.netconsulting.emprunt_service.dto.responseDTO.SuiviResponse;

import java.util.List;

public interface EmpruntService {

    EmpruntResponse emprunter(EmpruntRequest request);

    EmpruntResponse retourner(Long empruntId);

    List<EmpruntResponse> getEmpruntsParUtilisateur(Long userId);

    List<SuiviResponse> getHistoriqueParLivre(Long livreId);

    List<SuiviResponse> getHistoriqueComplet();
}
