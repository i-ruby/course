package work.iruby.course.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.iruby.course.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleOnlyName {
    private Integer id;
    private String name;

    public static RoleOnlyName of(Role role) {
        return new RoleOnlyName(role.getId(), role.getName());
    }
}
