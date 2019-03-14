package cn.waggag.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @Auther: 王港
 * @Date: 2019/3/13 1:43
 * @Description: cn.waggag.utils
 * @version: 1.0
 * 测试FastDFS服务器得使用
 */
public class TestFastDfs {

    @Test
    public void uploadFiles() throws IOException, MyException {
        //1.向工程中添加jar包
        //2.创建一个配置文件，配置Tracker服务器得地址
        //3.加载配置文件
        ClientGlobal.init("D:/WorkSpace/IntelliJ IDEA/商城项目/taotao-parent/taotao-manager-web/src/main/resources/resource/client.conf");
        //4.创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //5.使用TrackerClient对象获得teackerserver对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //6.创建一个StorageServer,用null就可以
        StorageServer storageServer = null;
        //7.创建一个StorageClient对象。trackerserver,storageserver两个参数
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //8.使用StorageClient对象上传文件
        String[] strings = storageClient.upload_file("C:/Users/王港/OneDrive/图片/壁纸/Aqua.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("D:/WorkSpace/IntelliJ IDEA/商城项目/taotao-parent/taotao-manager-web/src/main/resources/resource/client.conf");
        String uploadFile = fastDFSClient.uploadFile("C:/Users/王港/OneDrive/图片/壁纸/Aqua.jpg");
        System.out.println(uploadFile);
    }

}
