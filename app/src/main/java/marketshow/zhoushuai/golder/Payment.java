package marketshow.zhoushuai.golder;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.zhoushuai.golder.GolderBottomOrder;
import com.zhoushuai.golder.MainActivity;
import com.zhoushuai.golder.PostOrderItem;
import com.zhoushuai.golder.R;

/**
 * Created by zhoushuai on 2016/11/7.
 */

public class Payment extends FragmentActivity {

    private LinearLayout mLayoutPro, mLayoutInput;
    private String mMarketName;
    private String mShopName;
    private String BillMoney;
    private int mImageId;
    private Intent intent;
    private Bundle bundle;
    private TextView tvPro;
    private GolderBottomOrder golderBottomOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        bundle = intent.getExtras();
        mShopName = bundle.getString("mShopName");
        mImageId=bundle.getInt("mImageId");
        mMarketName = bundle.getString("mMarketName", mMarketName);
        //mShopName = bundle.getString("mShopName");
        BillMoney = bundle.getString("BillMoney");
        final PasswordView pwdView = new PasswordView(this);

        setContentView(pwdView);
         golderBottomOrder=new GolderBottomOrder();
        tvPro = (TextView) findViewById(R.id.text_pro);
        mLayoutInput = (LinearLayout) findViewById(R.id.layout_input);
        mLayoutPro = (LinearLayout) pwdView.findViewById(R.id.layout_progress_inputpwd);
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {

                // mLayoutInput.setVisibility(View.INVISIBLE);
                mLayoutPro.setVisibility(View.VISIBLE);


                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Toast.makeText(Payment.this,"下单成功!",Toast.LENGTH_LONG).show();
                        //mLayoutPro.setVisibility(View.INVISIBLE);
                        if (msg.what == 1) {
                            PostOrderItem postOrderItem=new PostOrderItem();//把要传的数据封装成一个类
                            postOrderItem.setmImageId(mImageId);
                            postOrderItem.setmMarketName(mMarketName);
                            postOrderItem.setmShopName(mShopName);
                            postOrderItem.setBillMoney(BillMoney);
                            Intent billIntent = new Intent(Payment.this, MainActivity.class);
                            Bundle billbundle = new Bundle();
                            billbundle.putSerializable("item",postOrderItem);//传递对象
                            billIntent.putExtras(billbundle);
//                            billbundle.putInt("mImageId", mImageId);
//                            billbundle.putCharSequence("mMarketName", mMarketName);
//                            billbundle.putCharSequence("mShopName", mShopName);
//                            billbundle.putCharSequence("BillMoney", BillMoney);
//                            billIntent.putExtras(billbundle);
                            startActivity(billIntent);
                        }

                    }
                };

                new Thread() {  //开启一个线程 模拟提交订单网络超时显示 延迟2秒发送消息

                    @Override
                    public void run() {
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        handler.sendMessageDelayed(msg, 1800);
                    }
                }.start();


            }
        });


    }

}
