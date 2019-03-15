package cn.waggag.controller;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.content.service.ContentService;
import cn.waggag.pojo.TbContent;
import cn.waggag.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 王港
 * @Date: 2019/3/16 0:45
 * @Description: cn.waggag.controller
 * @version: 1.0
 * 内容管理
 */
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content){
        return contentService.addContent(content);
    }

    /**
     * 查询所有的列表
     * @return
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }

    /**
     * 更新content内容
     * @param content
     * @return
     */
    @RequestMapping("/content/edit")
    @ResponseBody
    public TaotaoResult updateContent(TbContent content){
        return contentService.updateContent(content);
    }

    /**
     * 删除content
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public  TaotaoResult deleteContent(String ids){
        return contentService.deleteContent(ids);
    }

}
