package cn.waggag.service.impl;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.mapper.TbItemDescMapper;
import cn.waggag.mapper.TbItemMapper;
import cn.waggag.pojo.TbItem;
import cn.waggag.pojo.TbItemDesc;
import cn.waggag.pojo.TbItemExample;
import cn.waggag.service.ItemService;
import cn.waggag.utils.IDUtils;
import cn.waggag.utils.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;


/**
 * @author 王港
 * @Date: 2019/3/12 18:58
 * @Description: cn.waggag.service.impl
 * @version: 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "itemAddTopic")
    private Destination destination;

    @Override
    public TbItem getItemById(long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample itemExample = new TbItemExample();
        List<TbItem> list =  itemMapper.selectByExample(itemExample);
        //取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        //生成商品的IDn
        long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //商品状态 1正常 2下架 3删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //补全pojo属性
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        // 向Activemq发送商品添加消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });
        //返回结果
        return TaotaoResult.ok();
    }


}
