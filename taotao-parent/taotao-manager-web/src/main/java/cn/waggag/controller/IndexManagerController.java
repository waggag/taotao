package cn.waggag.controller;

import cn.waggag.search.service.SearchItemService;
import cn.waggag.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther: 王港
 * @Date: 2019/3/20 0:15
 * @version: 1.0
 * 索引库维护
 */
@Controller
public class IndexManagerController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importIndex(){
        return searchItemService.importItemsToIndex();
    }

}
