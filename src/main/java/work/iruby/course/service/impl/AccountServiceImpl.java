package work.iruby.course.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.dao.AccountDao;
import work.iruby.course.entity.Account;
import work.iruby.course.service.AccountService;

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
        account = accountDao.save(account);
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
