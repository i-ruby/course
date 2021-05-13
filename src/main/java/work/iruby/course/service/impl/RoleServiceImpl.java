package work.iruby.course.service.impl;

import org.springframework.stereotype.Service;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.dao.RoleDao;
import work.iruby.course.entity.Role;
import work.iruby.course.service.RoleService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getRoleListByRoleNameList(List<String> roleName) {
        Map<String, Role> allRoleMap = roleDao.findAll().stream().collect(Collectors.toMap(Role::getName, role -> role));
        List<Role> roleList = roleName.stream()
                .map(allRoleMap::get)
                .filter(role -> {
                    if (role == null) {
                        throw HttpCodeException.badRequest("获取角色失败,包含不正确的角色名");
                    }
                    return true;
                })
                .collect(Collectors.toList());
        return roleList;
    }
}
