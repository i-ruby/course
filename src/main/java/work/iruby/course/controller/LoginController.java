package work.iruby.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import work.iruby.course.entity.Account;
import work.iruby.course.enums.StatusType;
import work.iruby.course.repository.AccountResitory;

@Controller
public class LoginController {

    private final AccountResitory accountResitory;

    public LoginController(AccountResitory accountResitory) {
        this.accountResitory = accountResitory;
    }

    @GetMapping("/index")
    @ResponseBody
    public Object index() {
        System.out.println(accountResitory.findAll());
        Account account = new Account();
        account.setUsername("iruby");
        account.setEncryptedPassword("");
        account.setStatus(StatusType.OK);
        accountResitory.save(account);
        System.out.println(accountResitory.findAll());
        return "index";
    }
}
