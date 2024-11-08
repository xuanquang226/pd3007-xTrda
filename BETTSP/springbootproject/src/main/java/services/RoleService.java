package services;

import java.util.List;

public interface RoleService {
    List<String> getManyRoleNameByIdIn(List<Long> idRoleList);
}
