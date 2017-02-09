package com.zhoushuai.golder;

import java.io.Serializable;

/**
 * Created by zhoushuai on 2016/12/14.
 */

public class PostOrderItem implements Serializable {
    private int mImageId;
    private String mMarketName;
    private String mShopName;
    private String BillMoney;

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }

    public void setmMarketName(String mMarketName) {
        this.mMarketName = mMarketName;
    }

    public void setmShopName(String mShopName) {
        this.mShopName = mShopName;
    }

    public void setBillMoney(String billMoney) {
        BillMoney = billMoney;
    }

    public int getmImageId() {
        return mImageId;
    }

    public String getmMarketName() {
        return mMarketName;
    }

    public String getmShopName() {
        return mShopName;
    }

    public String getBillMoney() {
        return BillMoney;
    }
}
