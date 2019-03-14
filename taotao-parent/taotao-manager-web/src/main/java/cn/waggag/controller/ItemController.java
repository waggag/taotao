package cn.waggag.controller;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.pojo.TbItem;
import cn.waggag.service.ItemService;
import cn.waggag.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 王港
 * @Date: 2019/3/12 19:26
 * @Description: cn.waggag.controller
 * @version: 1.0
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }

    //限定提交的方法只能为POST
    @RequestMapping(value="/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addITem(TbItem item,String desc){
        TaotaoResult result = itemService.addItem(item, desc);
        return  result;
    }
}
