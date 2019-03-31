package cn.waggag.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author 王港
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfiger;

    @RequestMapping("/genhtml")
    public String genHtml() throws Exception{
        //生成静态页面
        Configuration configuration = freeMarkerConfiger.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map<String, String> data = new HashMap<>();
        data.put("hello", "Spring freemarker test");
        Writer out = new FileWriter(new File("D:/html/test.html"));
        template.process(data, out);
        out.close();
        //返回结果
        return "OK";
    }
}
