package work.iruby.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import work.iruby.course.entity.Session;

import java.util.Optional;

public interface SessionDao extends JpaRepository<Session, Integer> {

    Optional<Session> findOneByCookie(String cookie);

    Optional<Session> findOneByAccountId(Integer accountId);

    void deleteByCookie(String cookie);
}
