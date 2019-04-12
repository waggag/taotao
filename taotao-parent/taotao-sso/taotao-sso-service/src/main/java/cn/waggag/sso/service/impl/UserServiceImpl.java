package cn.waggag.sso.service.impl;

import cn.waggag.jedis.JedisClient;
import cn.waggag.mapper.TbUserMapper;
import cn.waggag.pojo.TbUser;
import cn.waggag.pojo.TbUserExample;
import cn.waggag.sso.service.UserService;
import cn.waggag.utils.JsonUtils;
import cn.waggag.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 王港
 * @Date: 2019/3/31 22:08
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String data, int type) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        //1 判断用户名是否可用
        //2 判断手机号是否可用
        //3 判断邮箱是否可用
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(data);
        } else if (type == 3) {
            criteria.andEmailEqualTo(data);
        } else {
            return TaotaoResult.build(400, "非法数据，请求参数非法");
        }
        List<TbUser> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            //数据库中查询到数据，使用的数据不可用
            return TaotaoResult.ok(false);
        }
        //使用的数据数据库中不存在，可以使用
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        //检查数据的有效性
        if (StringUtils.isBlank(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空！");
        }
        // 判断用户名是否重复
        TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
        if (!(boolean) taotaoResult.getData()) {
            return TaotaoResult.build(400, "用户名已存在，请换一个重新输入！");
        }
        // 判断密码是否为空
        if (StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空！");
        }
        // 判断手机号是否重复
        if (StringUtils.isNotBlank(user.getPhone())) {
            TaotaoResult data = checkData(user.getPhone(), 2);
            if (!(boolean) data.getData()) {
                return TaotaoResult.build(400, "手机号码不能重复！");
            }
        }
        // 判断邮箱是否重复
        if (StringUtils.isNotBlank(user.getEmail())) {
            TaotaoResult data = checkData(user.getEmail(), 2);
            if (!(boolean) data.getData()) {
                return TaotaoResult.build(400, "邮箱不能重复！");
            }
        }
        // 补全用户属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // Spring自带的密码进行MD5加密处理
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        // 先进行数据的校验
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            // 没有查到用户名，返回登陆失败
            return TaotaoResult.build(400, "登录失败，该用户可能暂时没有注册！");
        }
        // 密码要进行md5的加密校验
        TbUser user = list.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "登录失败，密码可能不正确！");
        }
        // 生产Token，使用uuid
        String token = UUID.randomUUID().toString();
        //把用户的信息保存到redis，key就是token，value是用户信息
        //清空密码
        user.setPassword(null);
        jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        // 设置key的过期时间
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        // 返回登陆成功，其中的token返回用于订单
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400, "用户登录已经过期");
        }
        //重置Session在Redis中的过期时间
        jedisClient.expire(USER_SESSION+":"+token,SESSION_EXPIRE);
        //把json转换为用户对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult logout(String token) {
        //从redis查询是否存在用户
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户登录已过期，已自动退出！");
        }
        jedisClient.expire(USER_SESSION+":"+token, 0);
        return TaotaoResult.ok();
    }
}
