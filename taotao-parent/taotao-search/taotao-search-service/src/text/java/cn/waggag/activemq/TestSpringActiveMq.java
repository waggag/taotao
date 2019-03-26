package cn.waggag.activemq;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author 王港
 * @Date: 2019/3/27 0:36
 * @version: 1.0
 */
public class TestSpringActiveMq {

    @Test
    public void testSpringActiveMq() throws IOException {
        //初始化Spring容器
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext-ActiveMq.xml");
        //等待
        System.in.read();
    }
}
