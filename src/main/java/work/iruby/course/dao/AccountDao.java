package work.iruby.course.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import work.iruby.course.entity.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
    Account findOneByUsername(String username);

    Page<Account> findAllByUsernameStartingWith(String username, Pageable pageable);
}
