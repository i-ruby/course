package work.iruby.course.common;

import work.iruby.course.entity.Account;
import work.iruby.course.vo.User;

public class Account2User {
    public static User of(Account account) {
        User user = new User();
        user.setId(account.getId());
        user.setUsername(account.getUsername());
        return user;
    }
}
