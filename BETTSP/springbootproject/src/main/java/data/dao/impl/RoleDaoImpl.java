package data.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.RoleDao;
import data.dto.RoleDTO;
import data.mapper.RoleMapper;
import data.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private RoleRepository repository;

    @Override
    public List<RoleDTO> getManyRole() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public RoleDTO getOneRole(Long id) {
        return mapper
                .toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity")));
    }

    @Override
    public List<RoleDTO> getManyRoleByIdIn(List<Long> idRoleList) {
        return mapper.toDto(repository.findManyRoleByIdIn(idRoleList));
    }

}
