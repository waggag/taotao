package cn.waggag.order.controller;

import cn.waggag.order.pojo.OrderInfo;
import cn.waggag.order.service.OrderService;
import cn.waggag.pojo.TbItem;
import cn.waggag.pojo.TbUser;
import cn.waggag.utils.CookieUtils;
import cn.waggag.utils.JsonUtils;
import cn.waggag.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/5/7 0:30
 * @version: 1.0
 */
@Controller
public class OrderCartController {

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;

    @Autowired
    private OrderService orderService;


    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        //用户必须是登录状态
        //取用户ID
        TbUser user = (TbUser)request.getAttribute("user");

        //根据用户信息取收货列表，使用静态数据

        //把收货地址信息取出传递到展示页面

        //从Cookie中取购物车商品列表展示到页面
        List<TbItem> cartItemList = getCartItemList(request);
        request.setAttribute("cartList", cartItemList);
        //返回逻辑视图
        return "order-cart";
    }

    /**
     * 从购物车中获取商品列表
     *
     * @param request 请求
     * @return 商品列表
     */
    private List<TbItem> getCartItemList(HttpServletRequest request) {
        // 从cookie中取购物车商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            // 如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    /**
     * 生产订单的处理方法
     * @param orderInfo
     */
    @RequestMapping(value="/order/create",method= RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model) {
        //生产订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        //返回逻辑视图
        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        //预计送达时间，预计三天后送达
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));

        return "success";
    }

}
