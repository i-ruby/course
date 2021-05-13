package work.iruby.course.service;

import work.iruby.course.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoleListByRoleNameList(List<String> roleName);
}
