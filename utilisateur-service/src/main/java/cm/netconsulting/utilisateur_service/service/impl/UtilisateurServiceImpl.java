package cm.netconsulting.utilisateur_service.service.impl;

import cm.netconsulting.utilisateur_service.dto.responseDTO.UtililisateurResponseDTO;
import cm.netconsulting.utilisateur_service.entity.Utilisateur;
import cm.netconsulting.utilisateur_service.mapper.UtilisateurMapper;
import cm.netconsulting.utilisateur_service.repository.UserRepository;
import cm.netconsulting.utilisateur_service.response.Response;
import cm.netconsulting.utilisateur_service.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper utilisateurMapper;

    @Override
    public Response<List<UtililisateurResponseDTO>> getAllUsers() {


        List<Utilisateur> userList = userRepository.findAll(
                Sort.by(Sort.Direction.DESC, "id")
        );

        List<UtililisateurResponseDTO> userDTOS = userList.stream()
                .map(utilisateurMapper::toDto)
                .toList();

        return Response.<List<UtililisateurResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("utilisateur charger avec success")
                .data(userDTOS)
                .build();
    }


}




