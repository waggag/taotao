package cn.waggag.controller;

import cn.waggag.common.pojo.EasyUITreeNode;
import cn.waggag.content.service.ContentCategoryService;
import cn.waggag.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/15 1:16
 * @Description: cn.waggag.controller
 * @version: 1.0
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult addContentCategory(Long parentId, String name) {
        return contentCategoryService.addContentCategory(parentId, name);
    }

    //读取的数据必须对应前台传输的数据
    @RequestMapping("/content/category/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,String name){
        return contentCategoryService.updataContentCategory(id, name);
    }

    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        return contentCategoryService.deleteContentCategory(id);
    }



}
