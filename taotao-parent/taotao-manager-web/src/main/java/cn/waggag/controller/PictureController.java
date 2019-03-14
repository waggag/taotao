package cn.waggag.controller;

import cn.waggag.utils.FastDFSClient;
import cn.waggag.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 王港
 * @Date: 2019/3/14 1:55
 * @Description: cn.waggag.controller
 * @version: 1.0
 */
@Controller
public class PictureController {

    /**
     * 使用注解取配置文件中给出的值
     */
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile){
        try {
            //接受上传的文件
            //取扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            //上传到图片服务器,路径有问题，以后需要解决
            FastDFSClient fastDFSClient = new FastDFSClient("D:/WorkSpace/IntelliJ IDEA/商城项目/taotao-parent/taotao-manager-web/src/main/resources/resource/client.conf");
//            FastDFSClient fastDFSClient = new FastDFSClient("classpath:/resources/resource/client.conf");
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url = IMAGE_SERVER_URL+url;
            //响应上传的图片URL
            Map result = new HashMap();
            result.put("error",0);
            result.put("url",url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error",1);
            result.put("messsage","图片上传失败");
            return JsonUtils.objectToJson(result);
        }
    }
}
