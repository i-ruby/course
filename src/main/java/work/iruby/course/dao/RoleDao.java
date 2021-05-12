package work.iruby.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import work.iruby.course.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
}
