package work.iruby.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import work.iruby.course.entity.Session;

public interface SessionDao extends JpaRepository<Session, Integer> {

    Session findOneByCookie(String cookie);

    Session findOneByAccountId(Integer accountId);

    void deleteByCookie(String cookie);
}
