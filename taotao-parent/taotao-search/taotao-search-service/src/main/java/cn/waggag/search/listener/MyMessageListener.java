package cn.waggag.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author 王港
 * @Date: 2019/3/27 0:25
 * @version: 1.0
 */
public class MyMessageListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        try {
            //接受到消息
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
