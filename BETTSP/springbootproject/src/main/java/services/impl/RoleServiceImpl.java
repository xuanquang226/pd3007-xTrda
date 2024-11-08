package services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.RoleDao;
import data.dto.RoleDTO;
import services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<String> getManyRoleNameByIdIn(List<Long> idRoleList) {
        List<RoleDTO> roleList = roleDao.getManyRoleByIdIn(idRoleList);
        List<String> roleNameList = roleList.stream().map(RoleDTO::getName).collect(Collectors.toList());
        return roleNameList;
    }
}
