package cm.netconsulting.utilisateur_service.service.impl;

import cm.netconsulting.utilisateur_service.dto.requestDTO.RoleRequestDTO;
import cm.netconsulting.utilisateur_service.dto.responseDTO.RoleResponseDTO;
import cm.netconsulting.utilisateur_service.entity.Role;
import cm.netconsulting.utilisateur_service.Enumeration.ROLE;
import cm.netconsulting.utilisateur_service.exception.BadRequestException;
import cm.netconsulting.utilisateur_service.exception.NotFoundException;
import cm.netconsulting.utilisateur_service.mapper.RoleMapper;
import cm.netconsulting.utilisateur_service.repository.RoleRepository;
import cm.netconsulting.utilisateur_service.response.Response;
import cm.netconsulting.utilisateur_service.service.RoleService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Response<RoleResponseDTO> createRole(RoleRequestDTO roleRequest) {

        ROLE roleEnum = roleMapper.toEnum(roleRequest.getNom().name());

        if (roleRepository.findByNom(roleEnum).isPresent()) {
            throw new BadRequestException("ce role existe deja");
        }

        Role role = Role.builder()
                .nom(roleEnum)
                .build();

        Role savedRole = roleRepository.save(role);

        return Response.<RoleResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Role creer avec success")
                .data(roleMapper.toDto(savedRole))
                .build();
    }

    @Override
    public Response<RoleResponseDTO> updateRole(RoleRequestDTO roleRequest) {

        Role existingRole = roleRepository.findById(roleRequest.getId())
                .orElseThrow(() -> new NotFoundException("role introuvable"));

        ROLE roleEnum = roleMapper.toEnum(roleRequest.getNom().name());

        roleRepository.findByNom(roleEnum).ifPresent(role -> {
            if (!role.getId().equals(existingRole.getId())) {
                throw new BadRequestException("role deja existant");
            }
        });

        existingRole.setNom(roleEnum);

        Role updatedRole = roleRepository.save(existingRole);

        return Response.<RoleResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role mise a jour avec success")
                .data(roleMapper.toDto(updatedRole))
                .build();
    }

    @Override
    public Response<List<RoleResponseDTO>> getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        List<RoleResponseDTO> roleDTOS = roles.stream()
                .map(roleMapper::toDto)
                .toList();

        return Response.<List<RoleResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("roles charger avec success")
                .data(roleDTOS)
                .build();
    }

    @Override
    public Response<?> deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        roleRepository.delete(role);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role supprimer avec success")
                .build();
    }
}