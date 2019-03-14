package cn.waggag.service;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.pojo.TbItem;
import cn.waggag.utils.TaotaoResult;

import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/12 18:54
 * @Description: cn.waggag.service
 * @version: 1.0
 */
public interface ItemService {
    /**
     * 通过商品Id获取商品
     */
    TbItem getItemById(long id);
    /**
     * 使用页面传递得参数来分页
     */
    EasyUIDataGridResult getItemList(int  page,int rows);

    /**
     *新增商品
     */
    TaotaoResult addItem(TbItem item,String desc);

}
