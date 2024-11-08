package data.dao;

import java.util.List;

import data.dto.RoleDTO;

public interface RoleDao {
    List<RoleDTO> getManyRole();

    RoleDTO getOneRole(Long id);

    List<RoleDTO> getManyRoleByIdIn(List<Long> idRoleList);
}
