package cn.waggag.order.service.impl;

import cn.waggag.jedis.JedisClient;
import cn.waggag.mapper.TbOrderItemMapper;
import cn.waggag.mapper.TbOrderMapper;
import cn.waggag.mapper.TbOrderShippingMapper;
import cn.waggag.order.pojo.OrderInfo;
import cn.waggag.order.service.OrderService;
import cn.waggag.pojo.TbOrderItem;
import cn.waggag.pojo.TbOrderShipping;
import cn.waggag.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单处理service
 * @author 王港
 * @Date: 2019/5/22 10:58
 * @version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;
    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        // 生成订单号,可以使用redis的incr生成
        if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
            // 设置初始值
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //向订单表中插入数据，需要补全pojo属性
        orderInfo.setOrderId(orderId);
        // 免邮费
        orderInfo.setPostFee("0");
        // 1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        // 订单创建时间
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        // 向订单表插入数据
        orderMapper.insert(orderInfo);

        // 向订单明细表插入数据。
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            // 获得明细主键
            String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            tbOrderItem.setId(oid);
            tbOrderItem.setOrderId(orderId);
            // 插入明细数据
            orderItemMapper.insert(tbOrderItem);
        }
        // 向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        if(orderShipping == null) {
            orderShipping = new TbOrderShipping();
        }
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        //返回订单号
        return TaotaoResult.ok(orderId);
    }
}
