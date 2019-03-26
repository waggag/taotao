package cn.waggag.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author 王港
 * @Date: 2019/3/26 1:27
 * @version: 1.0
 */
public class TestActiveMq {

    //queue
    //Producer
    @Test
    public void testQueueProducer() throws Exception {
        //1.创建一个连接工厂对象，制定mq的IP和端口
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //2.创建一个连接Connection
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.使用Connection创建一个Session对象
        //第一个参数是否开启事务，一般不开启事务，保证数据的最终一致。
        //如果第一个参数为true，如果为false，第二个参数代表应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用Session对象创建一个Destination对象，两种形式queue,topic
        Queue queue = session.createQueue("test-queue");
        //6.使用Session对象创建一个Product对象
        MessageProducer producer = session.createProducer(queue);
        //7.创建一个TextMessage对象
		/*ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("Hello ActiveMQ!");*/
        TextMessage textMessage = session.createTextMessage("Hello ActiveMQ!");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        connection.close();
        session.close();
        producer.close();

    }

    @Test
    public void testQueueConsumer() throws Exception {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用Session创建一个Destination，Destination应该和消息的发送端一致。
        Queue queue = session.createQueue("test-queue");
        //使用Session创建一个Consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //向Consumer对象中设置一个MessageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                //取消息的内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        //系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    //topic
    //Producer
    /**
     * Topic默认发送了不持久化，发送了就丢失
     * @throws Exception
     */
    @Test
    public void testTopicProducer() throws Exception {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建Destination，应该使用topic
        Topic topic = session.createTopic("test-topic");
        //创建一个Producer对象
        MessageProducer producer = session.createProducer(topic);
        //创建一个TextMessage对象
        TextMessage textMessage = session.createTextMessage("hello activemq topic");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumser() throws Exception {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用Session创建一个Destination，Destination应该和消息的发送端一致。
        Topic topic = session.createTopic("test-topic");
        //使用Session创建一个Consumer对象
        MessageConsumer consumer = session.createConsumer(topic);
        //向Consumer对象中设置一个MessageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                //取消息的内容
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        //系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
        System.out.println("topic消费者2.。。。");
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
