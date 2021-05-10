package work.iruby.course.service;

import org.springframework.stereotype.Service;
import work.iruby.course.entity.Account;

@Service
public interface AccountService {

    Account register(String username, String password);

    Account login(String username, String password);
}
