package cn.waggag.controller;

import cn.waggag.common.pojo.EasyUITreeNode;
import cn.waggag.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/13 0:31
 * @Description: cn.waggag.controller
 * @version: 1.0
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> itemCatList = itemCatService.getItemCatList(parentId);
        return  itemCatList;
    }

}
