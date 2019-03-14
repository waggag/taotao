package cn.waggag.service.impl;

import cn.waggag.common.pojo.EasyUITreeNode;
import cn.waggag.mapper.TbItemCatMapper;
import cn.waggag.pojo.TbItemCat;
import cn.waggag.pojo.TbItemCatExample;
import cn.waggag.pojo.TbItemExample;
import cn.waggag.service.ItemCatService;
import cn.waggag.pojo.TbItemCatExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/13 0:10
 * @Description: cn.waggag.service.impl
 * @version: 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据父节点ID查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        Criteria criteria = example.createCriteria();
        //设置parentid
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat itemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            //如果节点下有子节点“closed”,没有子节点“open”
            node.setState(itemCat.getIsParent() ? "closed" : "open");
            //添加到节点列表
            resultList.add(node);
        }
        return resultList;
    }
}
