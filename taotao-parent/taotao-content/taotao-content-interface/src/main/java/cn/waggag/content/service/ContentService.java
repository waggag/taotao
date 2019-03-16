package cn.waggag.content.service;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.pojo.TbContent;
import cn.waggag.utils.TaotaoResult;

import java.util.List;

/**
 * @Auther: 王港
 * @Date: 2019/3/16 0:36
 * @Description: cn.waggag.content.service
 * @version: 1.0
 */
public interface ContentService {
    /**
     * 添加Content
     * @param content
     * @return 结果
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 获取内容列表
     */
    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

    /**
     *  更新内容
     */
    TaotaoResult updateContent(TbContent content);

    /**
     *  删除商品
     */
    TaotaoResult deleteContent(String ids);
    /**
     * 前台展示大广告
     */
    List<TbContent> getContentByCid(long cid);
}
