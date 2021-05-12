package work.iruby.course.service;

import org.springframework.stereotype.Service;
import work.iruby.course.common.PageData;
import work.iruby.course.entity.Account;
import work.iruby.course.vo.UserWithRole;

import java.util.List;

@Service
public interface AccountService {

    Account register(String username, String password);

    Account login(String username, String password);

    Account getAccountById(int id);

    Account updateAccount(UserWithRole userWithRole);

    PageData<List<UserWithRole>> getAccountPage(String search, Integer pageNum, Integer pageSize, String orderBy, String orderType);
}
