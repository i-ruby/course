package work.iruby.course.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.common.PageData;
import work.iruby.course.dao.AccountDao;
import work.iruby.course.entity.Account;
import work.iruby.course.entity.Role;
import work.iruby.course.enums.StatusType;
import work.iruby.course.service.AccountService;
import work.iruby.course.service.RoleService;
import work.iruby.course.vo.RoleOnlyName;
import work.iruby.course.vo.UserWithRole;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final RoleService roleService;

    public AccountServiceImpl(AccountDao accountDao, RoleService roleService) {
        this.accountDao = accountDao;
        this.roleService = roleService;
    }

    @Override
    public Account register(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setEncryptedPassword(password2EncryptedPassword(password));
        account.setStatus(StatusType.OK);
        return saveAndCatchSqlException(account);
    }

    @Override
    public Account login(String username, String password) {
        Account account = accountDao.findOneByUsername(username);
        if (account == null) {
            throw HttpCodeException.badRequest("Bad Request 用户的请求包含错误");
        }
        if (BCrypt.verifyer().verify(password.toCharArray(), account.getEncryptedPassword()).verified) {
            return account;
        } else {
            throw HttpCodeException.badRequest("Bad Request 用户的请求包含错误");
        }
    }

    @Override
    public Account getAccountById(int id) {
        return accountDao.findById(id).orElseThrow(() -> HttpCodeException.notFound("用户不存在!"));
    }

    @Override
    public Account updateAccount(UserWithRole userWithRole) {
        Account account = getAccountById(userWithRole.getId());
        List<RoleOnlyName> roles = userWithRole.getRoles();
        List<Role> newRoles = roleService.getRoleListByRoleNameList(roles.stream().map(RoleOnlyName::getName).collect(Collectors.toList()));
        if (userWithRole.getUsername() != null){
            account.setUsername(userWithRole.getUsername());
        }
        account.setRoles(newRoles);
        return saveAndCatchSqlException(account);
    }


    @Override
    public PageData<List<UserWithRole>> getAccountPage(String search, Integer pageNum, Integer pageSize, String orderBy, String orderType) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (orderType != null) {
            direction = Sort.Direction.fromString(orderType);
        }
        PageRequest pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(direction, orderBy));
        Page<Account> accountPage;
        if (search == null) {
            accountPage = accountDao.findAll(pageable);
        } else {
            accountPage = accountDao.findAllByUsernameStartingWith(search, pageable);
        }
        PageData<List<UserWithRole>> withRolePageData = new PageData<>();
        withRolePageData.setData(accountPage.getContent().stream().map(UserWithRole::of).collect(Collectors.toList()));
        withRolePageData.setPageNum(pageNum);
        withRolePageData.setPageSize(pageSize);
        withRolePageData.setTotalPage(accountPage.getTotalPages());
        return withRolePageData;
    }

    //将密码明文加密
    private String password2EncryptedPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private Account saveAndCatchSqlException(Account account) {
        try {
            account = accountDao.save(account);
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException") && ((SQLException) e.getMostSpecificCause()).getSQLState().equals("23505")) {
                throw HttpCodeException.conflict("用户名已注册");
            }
            throw e;
        }
        return account;
    }
}
