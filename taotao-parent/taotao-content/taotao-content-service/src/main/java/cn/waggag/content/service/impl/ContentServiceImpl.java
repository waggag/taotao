package cn.waggag.content.service.impl;

import cn.waggag.common.pojo.EasyUIDataGridResult;
import cn.waggag.content.service.ContentService;
import cn.waggag.jedis.JedisClient;
import cn.waggag.mapper.TbContentMapper;
import cn.waggag.pojo.TbContent;
import cn.waggag.pojo.TbContentExample;
import cn.waggag.pojo.TbContentExample.Criteria;
import cn.waggag.utils.JsonUtils;
import cn.waggag.utils.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/16 0:37
 * @Description: cn.waggag.content.service.impl
 * @version: 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public TaotaoResult addContent(TbContent content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //同步缓冲，删除对应的缓冲信息,
        jedisClient.hdel(INDEX_CONTENT,content.getCategoryId().toString());

        //插入数据库
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //获取查询结果
        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKey(content);
        //返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(String ids) {
        String[] list = ids.split(",");
        for(String id:list){
            //查询对应的content并删除缓冲信息
            TbContent content = contentMapper.selectByPrimaryKey(Long.valueOf(id));
            //同步缓冲，删除对应的缓冲信息,
            jedisClient.hdel(INDEX_CONTENT,content.getCategoryId().toString());

            //删除内容
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        return TaotaoResult.ok();
    }

    //从数据库中查询缓冲展示到首页
    @Override
    public List<TbContent> getContentByCid(long cid) {
        //先查询缓冲
        //添加缓冲不能影响正常的业务逻辑
        //缓冲中没有再查询数据库
        //设置查询条件
        try {
            //查询缓冲
            String json = jedisClient.hget(INDEX_CONTENT, cid + "");
            //查询到结果，把json转换为List返回
            if(StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExample(example);
        //把结果添加到缓冲
        try {
            jedisClient.hset(INDEX_CONTENT,cid+"", JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
