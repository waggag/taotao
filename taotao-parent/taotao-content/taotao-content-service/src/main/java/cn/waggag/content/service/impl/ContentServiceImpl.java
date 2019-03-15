package cn.waggag.content.service.impl;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.content.service.ContentService;
import cn.waggag.mapper.TbContentMapper;
import cn.waggag.pojo.TbContent;
import cn.waggag.pojo.TbContentExample;
import cn.waggag.pojo.TbContentExample.Criteria;
import cn.waggag.utils.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/16 0:37
 * @Description: cn.waggag.content.service.impl
 * @version: 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public TaotaoResult addContent(TbContent content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据库
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //获取查询结果
        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKey(content);
        //返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(String ids) {
        String[] list = ids.split(",");
        for(String id:list){
            //删除内容
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        return TaotaoResult.ok();
    }


}
