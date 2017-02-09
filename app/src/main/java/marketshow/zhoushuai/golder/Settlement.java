package marketshow.zhoushuai.golder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhoushuai.golder.R;

import java.text.DecimalFormat;

/**
 * Created by zhoushuai on 2016/11/10.
 */

public class Settlement extends Activity {

  private TextView mCommitSettlement;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;
    private ImageView mImageShopIcon;
    private TextView mTextShopName;
    private TextView mTextshopCount;
    private TextView mTextShopPrice;
    private TextView mTextRealPrice;
    private Intent intent;
    private Bundle bundle;
    private  String ShopRealPrice;
    private  String mMarketName;
    private int mImageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settlement_product);
        initData();
        mCommitSettlement.setOnClickListener(new OrderListener());

    }

    private void initData(){
        mCommitSettlement= (TextView) findViewById(R.id.commit_settlement);
        mProgressBar= (ProgressBar) findViewById(R.id.loading_progressBar);
        mLinearLayout= (LinearLayout) findViewById(R.id.layout_progress);
        mImageShopIcon= (ImageView) findViewById(R.id.image_order_shop);
        mTextShopName= (TextView) findViewById(R.id.text_order_shop_name);
        mTextshopCount= (TextView) findViewById(R.id.text_shop_sum);
        mTextShopPrice= (TextView) findViewById(R.id.text_order_price);
        mTextRealPrice= (TextView) findViewById(R.id.text_real_menoy);
        intent=getIntent();
        bundle=intent.getExtras();
        mImageId=bundle.getInt("GoodsPic");
        mMarketName=bundle.getString("marketName");
        mTextShopName.setText(bundle.getString("shoppingName"));
        int sum=Integer.parseInt(bundle.getString("shoppingNum")); //获取的数目转换为整型
        DecimalFormat df=new DecimalFormat("0.00");//格式化数字,保留2位小数
        String ShopPrice=df.format(Double.parseDouble(bundle.getString("GoodsPrices")));
        mTextShopPrice.setText("¥"+ShopPrice);
        mTextshopCount.setText("数量 x"+sum);
        ShopRealPrice=df.format(Double.parseDouble(bundle.getString("GoodsRealPrices")));
        mTextRealPrice.setText(ShopRealPrice);
        mImageShopIcon.setImageResource(bundle.getInt("GoodsPic"));
    }

    class OrderListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
           mLinearLayout.setVisibility(View.VISIBLE);


             final Handler handler=new Handler(){
                 @Override
                 public void handleMessage(Message msg) {
                     super.handleMessage(msg);
                     if(msg.what==1) {
                         mLinearLayout.setVisibility(View.INVISIBLE);
                         Intent billIntent = new Intent(Settlement.this, OrderPayment.class);
                         Bundle billBundle=new Bundle();
                         billBundle.putInt("mImageId",mImageId);
                         billBundle.putCharSequence("BillMoney",ShopRealPrice);
                         billBundle.putCharSequence("shoppingName",bundle.getString("shoppingName"));
                         billBundle.putCharSequence("mMarketName",mMarketName);
                         billIntent.putExtras(billBundle);
                         finish();
                         startActivity(billIntent);
                     }

                 }
             };
                  new Thread(){  //开启一个线程 模拟提交订单网络超时显示 延迟2秒发送消息

                      @Override
                      public void run() {
                          Message msg=handler.obtainMessage();
                          msg.what=1;
                          handler.sendMessageDelayed(msg,1800);
                      }
                  }.start();



       }
    }
}
