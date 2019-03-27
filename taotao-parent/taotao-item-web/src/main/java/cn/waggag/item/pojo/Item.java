package cn.waggag.item.pojo;

import cn.waggag.pojo.TbItem;

/**
 * @author 王港
 * @Date: 2019/3/28 0:20
 * @version: 1.0
 */
public class Item extends TbItem {
    //初始化属性
    public Item(TbItem item) {
        this.setId(item.getId());
        this.setTitle(item.getTitle());
        this.setSellPoint(item.getSellPoint());
        this.setPrice(item.getPrice());
        this.setNum(item.getNum());
        this.setBarcode(item.getBarcode());
        this.setImage(item.getImage());
        this.setCid(item.getCid());
        this.setStatus(item.getStatus());
        this.setCreated(item.getCreated());
        this.setUpdated(item.getUpdated());
    }

    public String[] getImages() {
        if (this.getImage() != null && !"".equals(this.getImage())) {
            String image2 = this.getImage();
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }
}
