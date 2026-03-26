package cm.netconsulting.utilisateur_service.service;

import cm.netconsulting.utilisateur_service.dto.requestDTO.RoleRequestDTO;
import cm.netconsulting.utilisateur_service.dto.responseDTO.RoleResponseDTO;
import cm.netconsulting.utilisateur_service.response.Response;

import java.util.List;

public interface RoleService {

    Response<RoleResponseDTO> createRole(RoleRequestDTO roleRequest);

    Response<RoleResponseDTO> updateRole(RoleRequestDTO roleRequest);

    Response<List<RoleResponseDTO>> getAllRoles();

    Response<?> deleteRole(Long id);
}
