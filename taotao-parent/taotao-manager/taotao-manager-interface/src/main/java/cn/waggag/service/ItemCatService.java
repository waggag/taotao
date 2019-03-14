package cn.waggag.service;

import cn.waggag.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/13 0:08
 * @Description: cn.waggag.service
 * @version: 1.0
 * 商品分类管理
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(long parentId);

}
