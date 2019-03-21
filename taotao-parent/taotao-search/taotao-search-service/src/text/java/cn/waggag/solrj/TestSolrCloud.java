package cn.waggag.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

    /**
     * Solr与SolrCloud大致一样，只有服务的创建不同
     * @throws Exception
     */
    @Test
    public void testSolrCloudAdDocument() throws Exception{

        //创建一个CloudSolrServer对象，需要集群的zkHost
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183");
        //设置默认的Collection
        cloudSolrServer.setDefaultCollection("collection2");
        //常见一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.setField("id", "test001");
        document.setField("item_title", "测试商品名称");
        document.setField("item_price", 1000);
        //把文档写入索引库
        cloudSolrServer.add(document);
        //提交
        cloudSolrServer.commit();
    }

}
