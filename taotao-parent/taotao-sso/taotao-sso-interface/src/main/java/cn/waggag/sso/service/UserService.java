package cn.waggag.sso.service;

import cn.waggag.pojo.TbUser;
import cn.waggag.utils.TaotaoResult;

/**
 * @author 王港
 * @Date: 2019/3/31 21:56
 * @version: 1.0
 */
public interface UserService {

    /**
     *  校验数据
     * @param data 校验的数据
     * @param type  参数的类型 1用户名 2手机号 3Email
     * @return  Json数据
     */
    TaotaoResult checkData(String data,int type);

    /**
     * 用户注册
     * @param user 存放数据
     * @return  结果
     */
    TaotaoResult register(TbUser user);

    /**
     * 用户登陆
     * @param username 用户名
     * @param password  密码
     * @return  登陆结果
     */
    TaotaoResult login(String username,String password);

    /**
     * 根据Token查询用户的信息
     * @param token 用户信息保存的key
     * @return 用户信息
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 用户安全退出
     * @param token 用户信息的key
     * @return  退出是否成功
     */
    TaotaoResult logout(String token);

}
