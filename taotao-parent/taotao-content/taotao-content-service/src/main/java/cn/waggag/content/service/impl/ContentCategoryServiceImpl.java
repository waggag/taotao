package cn.waggag.content.service.impl;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.common.pojo.EasyUITreeNode;
import cn.waggag.content.service.ContentCategoryService;
import cn.waggag.mapper.TbContentCategoryMapper;
import cn.waggag.pojo.TbContentCategory;
import cn.waggag.pojo.TbContentCategoryExample;
import cn.waggag.pojo.TbContentCategoryExample.Criteria;
import cn.waggag.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/15 1:01
 * @Description: cn.waggag.content.service.impl
 * @version: 1.0
 * 内容分类管理
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {

        //根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        //设置查询条件
        Criteria criteria = example.createCriteria();
        Criteria criteria1 = criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory: list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }

    /**
     *    添加商品分类
     */
    @Override
    public TaotaoResult addContentCategory(long parentId, String name) {
        //创建一个POJO对象
        TbContentCategory contentCategory = new TbContentCategory();
        //补全属性
        contentCategory.setParentId(parentId);;
        contentCategory.setName(name);
        //状态。可选值1(正常)，0(删除)
        contentCategory.setStatus(1);
        //设置排序，默认为1
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        contentCategoryMapper.insert(contentCategory);
        //判断父节点的状态,如果是子节点将其设置为父节点
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()) {
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        //包装数据
        return TaotaoResult.ok(contentCategory);
    }
    /**
     * 更新商品分类
     */
    @Override
    public TaotaoResult updataContentCategory(long id, String name) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        contentCategoryMapper.updateByPrimaryKey(category);
        return TaotaoResult.ok(category);
    }

    @Override
    public TaotaoResult deleteContentCategory(long id) {

        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        //判断删除的节点是否为父类
        if(contentCategory.getIsParent()){
            List<TbContentCategory> list=getContentCategoryListByParentId(id);
            //递归删除
            for(TbContentCategory childContentCategory : list){
                deleteContentCategory(childContentCategory.getId());
            }
        }
        //判断父类中是否还有子类节点，没有的话，把父类改成子类
        if(getContentCategoryListByParentId(contentCategory.getParentId()).size()==1)
        {
            TbContentCategory parentCategory=contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
            parentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
        return  TaotaoResult.ok();
    }

    /**
     * 获取该节点下的孩子节点
     * @param id
     * @return 父节点下的所有孩子节点
     */
    private List<TbContentCategory> getContentCategoryListByParentId(long id){
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        return list;
    }
}
