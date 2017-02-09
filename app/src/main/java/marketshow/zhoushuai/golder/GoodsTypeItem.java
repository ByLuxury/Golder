package marketshow.zhoushuai.golder;

import java.util.List;

/**
 *
 * 商品类型属性
 */
public class GoodsTypeItem {

    private int id;
    private String type;
    private String createtime;
    private List<ShopProductItem> product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

     //创建时间
//    public String getCreatetime() {
//        return createtime;
//    }
  //设置创建事件
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<ShopProductItem> getProduct() {
        return product;
    }

    public void setProduct(List<ShopProductItem> product) {
        this.product = product;
    }
}
