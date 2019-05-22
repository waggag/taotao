package cn.waggag.order.pojo;

import cn.waggag.pojo.TbOrder;
import cn.waggag.pojo.TbOrderItem;
import cn.waggag.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/5/22 10:45
 * @version: 1.0
 */
public class OrderInfo  extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
