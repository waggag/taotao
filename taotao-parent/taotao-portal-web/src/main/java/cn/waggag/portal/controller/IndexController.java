package cn.waggag.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: 王港
 * @Date: 2019/3/15 0:06
 * @Description: cn.waggag.portal.controller
 * @version: 1.0
 * 首页展示Controller
 */
public class IndexController {

    @RequestMapping("/index")
    public  String showIndex(){
        return "index";
    }

}
