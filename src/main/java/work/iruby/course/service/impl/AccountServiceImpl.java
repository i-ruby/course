package work.iruby.course.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.dao.AccountDao;
import work.iruby.course.entity.Account;
import work.iruby.course.enums.StatusType;
import work.iruby.course.service.AccountService;

import java.sql.SQLException;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account register(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setEncryptedPassword(password2EncryptedPassword(password));
        account.setStatus(StatusType.OK);
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

    //将密码明文加密
    private String password2EncryptedPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
