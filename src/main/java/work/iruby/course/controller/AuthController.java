package work.iruby.course.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import work.iruby.course.common.Account2User;
import work.iruby.course.common.AccountContext;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.entity.Account;
import work.iruby.course.service.AccountService;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    private AccountService accountService;

    /**
     * @api {get} /api/v1/session 检查登录状态
     * @apiName 检查登录状态
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParamExample Request-Example:
     *            GET /api/v1/auth
     *
     * @apiSuccess {User} user 用户信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "user": {
     *           "id": 123,
     *           "username": "Alice"
     *       }
     *     }
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * @return 已登录的用户
     */
    @GetMapping("/session")
    public Object session() {
        Account currentAccount = AccountContext.getCurrentAccount();
        if (currentAccount == null) {
            throw  HttpCodeException.unAuthorized("Unauthorized");
        }
        return Account2User.of(currentAccount);
    }

    /**
     * @api {post} /api/v1/account 注册
     * @apiName 注册
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/x-www-form-urlencoded
     *
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParamExample Request-Example:
     *          username: Alice
     *          password: MySecretPassword
     *
     * @apiSuccess {Integer} id 用户id
     * @apiSuccess {String} username 用户名
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *         "id": 123,
     *         "username": "Alice"
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     *
     * @apiError 409 Conflict 若用户名已经被注册
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 409 Conflict
     *     {
     *       "message": "用户名已经被注册"
     *     }
     */
    /**
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/user")
    public Object register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        if (username.length() < 2 || username.length() > 20) {
            throw HttpCodeException.badRequest("用户名必须在2到20之间");
        }
        if (password.isBlank()) {
            throw HttpCodeException.badRequest("密码为空或只包含空格");
        }
        return Account2User.of(accountService.register(username, password));
    }

    /**
     * @api {post} /api/v1/session 登录
     * @apiName 登录
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/x-www-form-urlencoded
     *
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParamExample Request-Example:
     *          username: Alice
     *          password: MySecretPassword
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "user": {
     *           "id": 123,
     *           "username": "Alice"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/session")
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        return Account2User.of(accountService.login(username, password));
    }

    /**
     * @api {delete} /api/v1/session 登出
     * @apiName 登出
     * @apiGroup 登录与鉴权
     * @apiHeader {String} Accept application/json
     * @apiParamExample Request-Example:
     * DELETE /api/v1/session
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 204 No Content
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    @DeleteMapping("/session")
    public void logout() {
        AccountContext.setCurrentAccount(null);
    }
}
