package work.iruby.course.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import work.iruby.course.annotation.Permission;
import work.iruby.course.annotation.Role;
import work.iruby.course.common.AccountContext;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.entity.Account;
import work.iruby.course.service.RoleService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class PermissionAspect {
    private RoleService roleService;

    public PermissionAspect(RoleService roleService) {
        this.roleService = roleService;
    }

    @Pointcut("@annotation(work.iruby.course.annotation.Role)")
    private void roleRequired() {
    }

    @Pointcut("@annotation(work.iruby.course.annotation.Permission)")
    private void permissionRequired() {
    }

    @Around("roleRequired() || permissionRequired()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Account account = AccountContext.getCurrentAccount();
        if (account == null) {
            throw HttpCodeException.unAuthorized("没有登录!");
        }
        List<work.iruby.course.entity.Role> accountRoles = account.getRoles();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Role role = method.getAnnotation(Role.class);
        if (role != null) {
            if (!accountRoles.containsAll(roleService.getRoleListByRoleNameList(Arrays.asList(role.name())))) {
                throw HttpCodeException.forbidden("角色权限不符!");
            }
        }

        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            String[] permissionRequired = permission.value();
            Set<String> permissions = new HashSet<>();
            for (work.iruby.course.entity.Role accountRole : accountRoles) {
                accountRole.getPermissions().forEach(p -> permissions.add(p.getName()));
                if (!permissions.containsAll(Arrays.stream(permissionRequired).collect(Collectors.toSet()))) {
                    throw HttpCodeException.forbidden("权限不足!");
                }
            }
        }

        return pjp.proceed();
    }
}
