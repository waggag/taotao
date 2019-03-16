package cn.waggag.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;

/**
 * @Auther: 王港
 * @Date: 2019/3/16 13:21
 * @Description: cn.waggag.jedis
 * @version: 1.0
 */
public class TestJedis {

    @Test
    public void testJedis(){
        //创建jedis
        Jedis jedis = new Jedis("192.168.25.128", 6379);
        jedis.set("s1","waggag");
        String s1 = jedis.get("s1");
        System.out.println(s1);
        //关闭jedis
        jedis.close();

    }

    @Test
    public void testJedisPool() {
        //创建一个jedis连接池
        JedisPool jedispool = new JedisPool("192.168.25.128",6379);
        //从连接池中获取连接
        Jedis jedis = jedispool.getResource();
        //使用jedis操作数据库((方法内使用)
        jedis.set("jedis-key","123");
        String string = jedis.get("jedis-key");
        System.out.println(string);
        //关闭jedis
        jedis.close();
        //系统关闭前关闭连接池
        jedispool.close();
    }

    @Test
    public void testJedisCluster(){
        //创建一个jedisCluster对象，构造参数是set,集合中每个元素是HostAndPort
        HashSet<HostAndPort> nodes = new HashSet<>();
        //向集合中添加节点
        nodes.add(new HostAndPort("192.168.25.128",7001));
        nodes.add(new HostAndPort("192.168.25.128",7002));
        nodes.add(new HostAndPort("192.168.25.128",7003));
        nodes.add(new HostAndPort("192.168.25.128",7004));
        nodes.add(new HostAndPort("192.168.25.128",7005));
        nodes.add(new HostAndPort("192.168.25.128",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("cluster-test","hello jedis cluster");
        //直接使用JedisCluseter操作redis
        String s = jedisCluster.get("cluster-test");
        System.out.println(s);
        //系统关闭前关闭
        try {
            jedisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
