package marketshow.zhoushuai.golder;

/**
 * Created by zhoushuai on 2016/11/4.
 */

/**
 *
 * 购物车添加接口回调
 */
public interface onShoppingCartBackListener {
    /**
     * Type表示添加或减少
     * @param product
     * @param type
     */
    void updateProduct(ShopProductItem product, String type);
}
