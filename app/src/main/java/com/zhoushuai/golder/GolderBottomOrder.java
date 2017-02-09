package com.zhoushuai.golder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import adapter.zhoushuai.golder.GolderOrderAdapter;
import adapter.zhoushuai.golder.OrderItem;

/**
 * Created by zhoushuai on 2016/10/7.
 */

public class GolderBottomOrder extends Fragment {
    private ListView lvOrder;
    private List<OrderItem> listOrder;
    private View view;
    //    private String mMarketName;
//    private String mShopName;
//    private  String BillMoney;
    //private int mImageId;
    private Intent intent;
    private Bundle bundle;
    private ImageView mImageId;
    private TextView mMarketName;
    private TextView mShopName;
    private TextView BillMoney;
    private LinearLayout tipLayout;
    private ImageView image_arraw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.golder_bottom_order, container, false);
        //Bundle data = getArguments();
        initData();

        return view;

    }

    private void initData() {
        mImageId= (ImageView) view.findViewById(R.id.image_order_icon);
        mMarketName= (TextView) view.findViewById(R.id.text_order_market_name);
       mShopName= (TextView) view.findViewById(R.id.text_order_shopname);
        BillMoney= (TextView) view.findViewById(R.id.text_order_shopprice);
        tipLayout= (LinearLayout) view.findViewById(R.id.tiplayout);
        image_arraw= (ImageView) view.findViewById(R.id.order_right_raw);
        Bundle bundle=getArguments();

        if(bundle!=null) {
            tipLayout.setVisibility(View.INVISIBLE);
            PostOrderItem post= (PostOrderItem) bundle.getSerializable("postitem");
          // Toast.makeText(getActivity(), post.getmMarketName()+ ":"+post.getmShopName()+":"+post.getBillMoney(), Toast.LENGTH_SHORT).show();
            mImageId.setImageResource(post.getmImageId());
            mMarketName.setText(post.getmMarketName());
            mShopName.setText(post.getmShopName());
            BillMoney.setText("总付款¥"+post.getBillMoney());
            image_arraw.setVisibility(View.VISIBLE);

        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        rereshData.reresh();
//
//    }
//
//    private OnRereshData rereshData;
//
//    public void setOnRereshData(OnRereshData rereshData){
//        this.rereshData = rereshData;
//    }
//
//
//    public interface OnRereshData{
//        void reresh();
//    }
}
