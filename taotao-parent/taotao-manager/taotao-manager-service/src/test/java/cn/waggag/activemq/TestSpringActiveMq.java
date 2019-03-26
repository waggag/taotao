package cn.waggag.activemq;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @author 王港
 * @Date: 2019/3/27 0:15
 * @version: 1.0
 */
public class TestSpringActiveMq {
    /**
     * 使用jmsTemplate发送消息
     */
    @Test
    public void testJmsTemplate() {
        //初始化Spring容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-ActiveMq.xml");
        //从容器中获取JmsTemplate对象
        JmsTemplate template = applicationContext.getBean(JmsTemplate.class);
        //从容器中获取Destination对象
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        //发送消息
        template.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("Spring Active sends queue!");
                return textMessage;
            }
        });
    }
}
