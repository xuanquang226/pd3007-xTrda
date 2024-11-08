package data.dao;

import java.util.List;

import data.dto.RoleAccountDTO;

public interface RoleAccountDao {
    void createRoleAccount(Long idRole, Long idAccount);

    List<RoleAccountDTO> getManyRoleAccountByIdRole(Long idRole);

    List<RoleAccountDTO> getManyRoleAccountByIdAccount(Long idAccount);
}
