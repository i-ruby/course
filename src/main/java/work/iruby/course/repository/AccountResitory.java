package work.iruby.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.iruby.course.entity.Account;

public interface AccountResitory extends JpaRepository<Account, Integer> {
}
