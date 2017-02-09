package com.zhoushuai.golder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import adapter.zhoushuai.golder.GolderHomeAdapter;
import adapter.zhoushuai.golder.MarketItem;
import marketshow.zhoushuai.golder.ShoppingShowActivity;

/**
 * Created by zhoushuai on 2016/10/17.
 */
/*首页*/
public class GolderBottomHome extends Fragment implements AdapterView.OnItemClickListener{

   private ListView mListViewMarket;
    private List<MarketItem> mListMarketData;
    private ImageView mImageView;
   private int[] mImageMarketId=new int[]{
           R.drawable.home_ykl_market, R.drawable.home_yd_market,R.drawable.drf,R.drawable.cb,R.drawable.yh,
           R.drawable.home_ykl_market, R.drawable.home_yd_market,R.drawable.drf,R.drawable.cb,R.drawable.yh};
   private String[] mNameMarket=new String[]{"宜客乐","悦动超市","大润发","重百商场","永辉超市","宜客乐","悦动超市","大润发","重百商场","永辉超市"};
    private ArrayList<String> mDelMsg=new ArrayList();
    private ArrayList<String>  mDistance=new ArrayList<>();
// private String[] mDistance=new String[]{"距您1km","距您1km","距您1km","距您1km","距您1km"};
    private View mHomeView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       mHomeView=inflater.inflate(R.layout.golder_bottom_home,container,false);
        //mImageView= (ImageView) mHomeView.findViewById(R.id.image_home_top_message);
       mListViewMarket= (ListView) mHomeView.findViewById(R.id.lv_market);
      // mListViewMarket.addHeaderView(listHeadLine());
        mListMarketData=new ArrayList<>();
        mDelMsg=new ArrayList<>();
        mDistance=new ArrayList<>();
        String distacne[]=new String[]{"1.1km","350m","3.2km","2.5km","2.8km",
                "1.1km","450m","3.2km","2.5km","2.8km"};
        for(int i=0;i<10;i++) {
            mDelMsg.add("10元起送   免费配送");
            mDistance.add("距您"+distacne[i]);
         }
        for(int i=0;i<10;i++){
        mListMarketData.add(new MarketItem(mImageMarketId[i],mNameMarket[i],mDelMsg.get(i),mDistance.get(i)));
        }
        GolderHomeAdapter golderHomeAdapter=new GolderHomeAdapter(getActivity(),mListMarketData);
      mListViewMarket.setAdapter(golderHomeAdapter);
     mListViewMarket.setOnItemClickListener(this);

        return mHomeView;

    }

    private View listHeadLine(){ //ListView头部线条
        ImageView imageView=new ImageView(getActivity());
        imageView.setImageResource(R.drawable.listheadline);

        return imageView;
    }
 //ListView单击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         String MarketName=mListMarketData.get(position-1).marketTitle;
        Intent intent=new Intent(getActivity(),ShoppingShowActivity.class);//Intent跳转
        Bundle bundle=new Bundle();//实例化一个Bundle对象
        bundle.putCharSequence("MarketName",MarketName); //把超市名字传递过去
        intent.putExtras(bundle);
        startActivity(intent);

    }




}
