package work.iruby.course.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.course.entity.Account;

@RestController
@RequestMapping("api/v1")
public class AccountController {


    /**
     * @api {patch} /api/v1/user 更新用户
     * @apiName 更新用户信息（权限）
     * @apiGroup 用户管理
     * @apiDescription
     *  管理员才能访问，获取分页的用户信息，支持搜索*
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     *   {
     *       "id": 12345,
     *       "roles": [
     *          {
     *              "name": "管理员"
     *          }
     *       ]
     *   }
     *
     * @apiSuccess {User} user 更新后的用户信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *              "id": 12345
     *              "username": "zhangsan"
     *              "roles": [
     *                  {
     *                      "name": "管理员"
     *                  }
     *              ]
     *          }
     * @apiError 400 Bad request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若没有权限
     * @apiError 404 Not Found 若用户未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *       "message": "Forbidden"
     *     }
     */
    /**
     * @param id      要修改的用户 id
     * @param account 新的用户数据
     * @return
     */
    @PatchMapping("/user")
    public Object updateAccount(@RequestParam("id") Integer id,
                                @RequestParam("id") Account account) {
        return null;
    }

    /**
     * @api {get} /api/v1/user/{id} 获取指定id的用户
     * @apiName 获取指定id的用户
     * @apiGroup 用户管理
     * @apiDescription
     *  管理员才能访问此接口
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     *    GET /api/v1/user/1
     * @apiSuccess {User} user 用户信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *              "id": 12345
     *              "username": "zhangsan"
     *              "roles": [
     *                  {
     *                      "name": "管理员"
     *                  }
     *              ]
     *          }
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若没有权限
     * @apiError 404 Not Found 若用户未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *       "message": "Forbidden"
     *     }
     */
    /**
     * @param id 用户ID
     * @return 获得的用户
     */
    @GetMapping("/user/{id}")
    public Object getAccountById(@PathVariable("id") Integer id) {
        return null;
    }

    /**
     * @api {get} /api/v1/user 获取用户列表
     * @apiName 获取用户列表
     * @apiGroup 用户管理
     * @apiDescription
     *  管理员才能访问，获取分页的用户信息，支持搜索
     *
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} [pageSize] 每页包含多少个用户
     * @apiParam {Number} [pageNum] 页码，从1开始
     * @apiParam {String} [orderBy] 排序字段，如username/createdAt
     * @apiParam {String} [orderType] 排序方法，Asc/Desc
     *
     * @apiParamExample Request-Example:
     *            GET /api/v1/user?pageSize=10&pageNum=1&orderBy=price&orderType=Desc&search=zhang
     *
     * @apiSuccess {Number} totalPage 总页数
     * @apiSuccess {Number} pageNum 当前页码，从1开始
     * @apiSuccess {Number} pageSize 每页包含多少个用户
     * @apiSuccess {List[User]} data 用户列表
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "totalPage": 100,
     *       "pageSize": 10,
     *       "pageNum": 1,
     *       "data": [
     *          {
     *              "id": 12345
     *              "username": "zhangsan"
     *              "roles": [
     *                  {
     *                      "name": "管理员"
     *                  }
     *              ]
     *          }
     *       ]
     *     }
     * @apiError 400 Bad request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若没有权限
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * @param search    search
     * @param pageSize
     * @param pageNum
     * @param orderBy
     * @param orderType
     * @return
     */
    @GetMapping("/user")
    public Object getAccountList(@RequestParam(value = "search", required = false) String search,
                                 @RequestParam(name = "pageNum", required = false) Integer pageNum,
                                 @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                 @RequestParam(name = "orderBy", required = false) String orderBy,
                                 @RequestParam(name = "orderType", required = false) String orderType) {
        return null;
    }
}
