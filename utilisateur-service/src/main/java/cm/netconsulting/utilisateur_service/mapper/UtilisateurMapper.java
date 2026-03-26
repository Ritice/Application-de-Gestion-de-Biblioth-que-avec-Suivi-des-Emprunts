package cm.netconsulting.utilisateur_service.mapper;


import cm.netconsulting.utilisateur_service.dto.requestDTO.RegisterRequest;
import cm.netconsulting.utilisateur_service.dto.responseDTO.UtililisateurResponseDTO;
import cm.netconsulting.utilisateur_service.entity.Role;
import cm.netconsulting.utilisateur_service.entity.Utilisateur;
import cm.netconsulting.utilisateur_service.Enumeration.ROLE;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UtilisateurMapper {

    public UtililisateurResponseDTO toDto(Utilisateur user) {
        UtililisateurResponseDTO dto = new UtililisateurResponseDTO();

        BeanUtils.copyProperties(user, dto);
        dto.setPassword(null);
        if (user.getRoles() != null) {
            dto.setRoles(
                    user.getRoles().stream()
                            .map(role -> role.getNom().name())
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }


    public Utilisateur toEntity(RegisterRequest request) {
        Utilisateur user = new Utilisateur();
        BeanUtils.copyProperties(request, user);
        return user;
    }


    public List<Role> mapToRoles(List<String> roleNames, List<Role> rolesFromDb) {
        return rolesFromDb;
    }
}
