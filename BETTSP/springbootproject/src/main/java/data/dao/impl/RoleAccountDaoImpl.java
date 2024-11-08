package data.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.RoleAccountDao;
import data.dto.RoleAccountDTO;
import data.mapper.RoleAccountMapper;
import data.repositories.RoleAccountRepository;

@Service
public class RoleAccountDaoImpl implements RoleAccountDao {

    @Autowired
    private RoleAccountRepository repository;

    @Autowired
    private RoleAccountMapper mapper;

    @Override
    public void createRoleAccount(Long idRole, Long idAccount) {
        RoleAccountDTO roleAccountDto = new RoleAccountDTO();
        roleAccountDto.setIdRole(idRole);
        roleAccountDto.setIdAccount(idAccount);
        repository.save(mapper.toEntity(roleAccountDto));
    }

    @Override
    public List<RoleAccountDTO> getManyRoleAccountByIdAccount(Long idAccount) {
        return mapper.toDto(repository.findAllByIdAccount(idAccount));
    }

    @Override
    public List<RoleAccountDTO> getManyRoleAccountByIdRole(Long idRole) {
        // TODO Auto-generated method stub
        return null;
    }

}
