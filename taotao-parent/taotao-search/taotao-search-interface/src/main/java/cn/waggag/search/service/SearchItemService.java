package cn.waggag.search.service;

import cn.waggag.utils.TaotaoResult;

/**
 * @auther: 王港
 * @Date: 2019/3/19 22:31
 * @version: 1.0
 */
public interface SearchItemService {
    /**
     * 导入商品信息到数据库
     * @return
     */
    TaotaoResult importItemsToIndex();

}
