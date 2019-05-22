package cn.waggag.order.service;

import cn.waggag.order.pojo.OrderInfo;
import cn.waggag.utils.TaotaoResult;

/**
 * @author 王港
 * @Date: 2019/5/22 10:56
 * @version: 1.0
 */
public interface OrderService {

    TaotaoResult createOrder(OrderInfo orderInfo);


}
