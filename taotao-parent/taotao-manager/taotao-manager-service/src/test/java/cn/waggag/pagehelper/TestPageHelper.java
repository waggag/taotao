package cn.waggag.pagehelper;

import cn.waggag.mapper.TbItemMapper;
import cn.waggag.pojo.TbItem;
import cn.waggag.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @Auther: 王港
 * @Date: 2019/3/12 22:31
 * @Description: cn.waggag.pagehelper
 * @version: 1.0
 * PageHelper得基本使用
 */

public class TestPageHelper {

    @Test
    public void testPageHelper() {
        //1.在mybatis的配置文件中配置分页插件
        //2.在执行查询之前配置分页条件。使用PageHelper的静态方法
        PageHelper.startPage(1, 10);
        //3.执行查询
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext
                ("classpath:spring/applicationContext-dao.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        //创建Example对象
        TbItemExample example = new TbItemExample();
        //	Criteria criteria = example.createCriteria();
        List<TbItem> list = itemMapper.selectByExample(example);
        //4.取分页信息。使用PageInfo对象取。
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总记页数：" + pageInfo.getPages());
        System.out.println("返回的记录数：" + list.size());
    }

}
