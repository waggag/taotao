package cn.waggag.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/20 1:43
 * @version: 1.0
 */
public class SearchResult implements Serializable {

    private long totalPage;
    private long recordCount;
    private List<SearchItem> itemList;

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
