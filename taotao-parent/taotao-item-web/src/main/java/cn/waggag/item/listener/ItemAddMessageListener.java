package cn.waggag.item.listener;

import cn.waggag.item.pojo.Item;
import cn.waggag.pojo.TbItem;
import cn.waggag.pojo.TbItemDesc;
import cn.waggag.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王港
 * @Date: 2019/3/30 22:46
 * @version: 1.0
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            //接受消息，从消息中取商品ID
            TextMessage textMessage = (TextMessage) message;
            String strId = textMessage.getText();
            long itemId = Long.parseLong(strId);
            //等待事务提交
            Thread.sleep(100);
            //根据商品ID查询商品信息及商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //使用freemarker生成静态页面
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //准备需要的数据
            Map data = new HashMap<>();
            data.put("item",item);
            data.put("itemDesc",itemDesc);
            //指定输出的目录
            Writer out = new FileWriter(new File(HTML_OUT_PATH)+strId+".html");
            template.process(data,out);
            //关闭资源
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
