package cn.waggag.sso.controller;

import cn.waggag.pojo.TbUser;
import cn.waggag.sso.service.UserService;
import cn.waggag.utils.CookieUtils;
import cn.waggag.utils.JsonUtils;
import cn.waggag.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 王港
 * @Date: 2019/3/31 22:22
 * @version: 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param, @PathVariable int type) {
        TaotaoResult result = userService.checkData(param, type);
        return result;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        TaotaoResult result = userService.register(user);
        return result;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        TaotaoResult result = userService.login(username, password);
        //登陆成功后写cookie
        if (result.getStatus() == 200) {
            //把Token写入Cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
        }
        return result;
    }

    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET,
            //指定返回响应的数据的Content-type
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if (StringUtils.isNoneBlank(callback)) {
            return callback + "(" + JsonUtils.objectToJson(result) + ");";
        }
        return JsonUtils.objectToJson(result);
    }

    @RequestMapping(value = "/logout/{token}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String logout(@PathVariable String token, String callback) {
        TaotaoResult result = userService.logout(token);
        //判断是否为jsonp请求
        if(StringUtils.isNotBlank(callback)){
            return callback+"("+JsonUtils.objectToJson(result)+");";
        }
       return JsonUtils.objectToJson(result);
    }
}
