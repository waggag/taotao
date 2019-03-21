package cn.waggag.search.service.impl;

import cn.waggag.common.pojo.SearchItem;
import cn.waggag.utils.TaotaoResult;
import cn.waggag.search.mapper.SearchItemMapper;
import cn.waggag.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/19 22:33
 * @version: 1.0
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemsToIndex() {
        try {
            //1.先查询所有的商品数据
            List<SearchItem> itemList = searchItemMapper.getItemList();
            //遍历商品添加到索引库
            for (SearchItem searchItem : itemList) {
                /// 创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                // 向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                document.addField("item_desc", searchItem.getItem_desc());
                //将商品写入数据库
                solrServer.add(document);
            }
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "数据导入失败");
        } catch (IOException e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "数据导入失败");
        }
        //提交
        return TaotaoResult.ok();
    }
}
