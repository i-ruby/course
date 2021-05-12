package work.iruby.course.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.iruby.course.entity.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;

    public static User of(Account account) {
        return new User(account.getId(), account.getUsername());
    }
}
