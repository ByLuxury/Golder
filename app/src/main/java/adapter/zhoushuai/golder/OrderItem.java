package adapter.zhoushuai.golder;

/**
 * Created by zhoushuai on 2016/11/17.
 */

public class OrderItem {

    private  int imageId;
    private String orderMarketName;
    private String shopName;
    private String shopPrice;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setOrderMarket(String orderMarketName) {
        this.orderMarketName = orderMarketName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public int getImageId() {
        return imageId;
    }

    public String getOrderMarket() {
        return orderMarketName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopPrice() {
        return shopPrice;
    }
}
