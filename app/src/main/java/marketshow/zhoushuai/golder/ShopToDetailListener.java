package marketshow.zhoushuai.golder;



/**
 *
 * 单击购物车显示详细商品列表添加接口回调
 */
public interface ShopToDetailListener {
    /**
     * Type表示添加或减少
     * @param product
     * @param type
     */
    void onUpdateDetailList(ShopProductItem product, String type);

    void onRemovePriduct(ShopProductItem product);
}
