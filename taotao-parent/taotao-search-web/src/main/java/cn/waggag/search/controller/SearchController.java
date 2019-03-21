package cn.waggag.search.controller;

import cn.waggag.common.pojo.SearchResult;
import cn.waggag.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther 王港
 * @Date: 2019/3/20 3:07
 * @version: 1.0
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchSearch;

    @Value("${SEARCH_RESULT_ROWS}")
    private int SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String query, @RequestParam(defaultValue = "1") Integer page, Model model) {
        //调服务执行查询
        try {
            //对查询条件进行转码,以UTF-8向页面显示数据
            query = new String(query.getBytes("iso8859-1"), "utf-8");
            SearchResult result = searchSearch.search(query, page, SEARCH_RESULT_ROWS);
            //把结果传递给页面
            model.addAttribute("query", query);
            model.addAttribute("totalPages", result.getTotalPage());
            model.addAttribute("itemList", result.getItemList());
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }
}
