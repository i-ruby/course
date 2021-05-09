package work.iruby.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import work.iruby.course.entity.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
}
