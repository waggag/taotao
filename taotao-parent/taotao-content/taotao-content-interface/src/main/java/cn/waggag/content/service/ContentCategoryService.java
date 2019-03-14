package cn.waggag.content.service;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.common.pojo.EasyUITreeNode;
import cn.waggag.utils.TaotaoResult;

import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/15 0:59
 * @Description: cn.waggag.content.service
 * @version: 1.0
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryList(long parentId);

    TaotaoResult addContentCategory(long parentId, String name);

    TaotaoResult updataContentCategory(long id, String name);

}
