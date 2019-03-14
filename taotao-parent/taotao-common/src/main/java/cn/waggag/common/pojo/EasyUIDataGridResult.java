package cn.waggag.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王港
 * @Date: 2019/3/12 22:01
 * @Description: cn.waggag.common
 * @version: 1.0
 * 返回分页得结果，使用这个java类来分页
 */
public class EasyUIDataGridResult implements Serializable {

    private long total;
    private List<?> rows;

    public EasyUIDataGridResult() {
    }

    public EasyUIDataGridResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
