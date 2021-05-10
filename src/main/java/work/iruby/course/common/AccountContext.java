package work.iruby.course.common;

import work.iruby.course.entity.Account;

public class AccountContext {
    private static final ThreadLocal<Account> currentAccount = new ThreadLocal<>();

    public static Account getCurrentAccount() {
        return currentAccount.get();
    }

    public static void setCurrentAccount(Account account) {
        currentAccount.set(account);
    }
}