package cm.netconsulting.utilisateur_service.service;

import cm.netconsulting.utilisateur_service.dto.responseDTO.UtililisateurResponseDTO;
import cm.netconsulting.utilisateur_service.entity.Utilisateur;
import cm.netconsulting.utilisateur_service.response.Response;

import java.util.List;

public interface UtilisateurService {


    Response<List<UtililisateurResponseDTO>> getAllUsers();

}
