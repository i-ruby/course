package work.iruby.course.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.iruby.course.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRole {
    private Integer id;
    private String username;
    private List<RoleOnlyName> roles;

    public static UserWithRole of(Account account) {
        return new UserWithRole(account.getId(), account.getUsername(), account.getRoles().stream().map(RoleOnlyName::of).collect(Collectors.toList()));
    }
}
