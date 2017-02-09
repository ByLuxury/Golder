package adapter.zhoushuai.golder;

/**
 * Created by zhoushuai on 2016/11/2.
 */

//首页显示商家列表 商家属性

public class MarketItem {
    public int marketPictureID;
    public String marketTitle;
    public String marketDelMsg;
    public String marketDistace;

    public MarketItem(int marketPicture,  String marketTitle, String marketDelMsg, String marketDistace) {
        this.marketPictureID = marketPicture;
        this.marketTitle = marketTitle;
        this.marketDelMsg = marketDelMsg;
        this.marketDistace = marketDistace;
    }
}
