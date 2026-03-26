package cm.netconsulting.utilisateur_service.mapper;

import cm.netconsulting.utilisateur_service.dto.responseDTO.RoleResponseDTO;
import cm.netconsulting.utilisateur_service.entity.Role;
import cm.netconsulting.utilisateur_service.Enumeration.ROLE;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public ROLE toEnum(String roleName) {
        try {
            return ROLE.valueOf(roleName.toUpperCase().replace("ROLE_", ""));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid role: " + roleName);
        }
    }

    public RoleResponseDTO toDto(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setNom(role.getNom());
        return dto;
    }
}