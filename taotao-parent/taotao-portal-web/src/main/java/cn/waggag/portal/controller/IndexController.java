package cn.waggag.portal.controller;

import cn.waggag.content.service.ContentService;
import cn.waggag.pojo.TbContent;
import cn.waggag.portal.pojo.Ad1Node;
import cn.waggag.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/15 0:06
 * @Description: cn.waggag.portal.controller
 * @version: 1.0
 * 首页展示Controller
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;
    //从配置文件中读取属性

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @RequestMapping("/index")
    public  String showIndex(Model model){
        //通过cid查询content列表
        List<TbContent> contentList = contentService.getContentByCid(89);
        //把列表转为Ad1Node
        List<Ad1Node> nodes = new ArrayList<>();
        for(TbContent content:contentList){
            Ad1Node node = new Ad1Node();
            node.setAlt(content.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setSrc(content.getPic());
            node.setSrcB(content.getPic2());
            node.setHref(content.getUrl());
            //添加到节点列表
            nodes.add(node);
        }
        //后太查询到广告数据传递给页面，否则不发送数据
        if(nodes.size()>0){
            //把列表转为json数据
            String ad1Json = JsonUtils.objectToJson(nodes);
            //把json数据传递给页面
            model.addAttribute("ad1",ad1Json);
        }
        return "index";
    }
}
