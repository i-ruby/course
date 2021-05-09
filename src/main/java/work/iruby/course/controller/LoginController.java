package work.iruby.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import work.iruby.course.dao.AccountDao;

@Controller
public class LoginController {

    private final AccountDao accountDao;

    public LoginController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping("/index")
    @ResponseBody
    public Object index() {
        System.out.println(accountDao.findAll());
        return "index";
    }
}
