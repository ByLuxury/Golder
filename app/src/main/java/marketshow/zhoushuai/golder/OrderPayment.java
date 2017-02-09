package marketshow.zhoushuai.golder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zhoushuai.golder.R;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by zhoushuai on 2016/11/6.
 */

public class OrderPayment extends Activity {
 private TextView mTextBillNumber;
    private TextView mTextBillTime;
    private TextView mTextBillMoney;
    private TextView mTextPayment;
    private Intent intent;
    private  Bundle bundle;
    private Random random;
    private int ShopId;
    private  String time;
    private String mMarketName;
    private String mShopName;
    private  String BillMoney;
    private int mImageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_payment);
        mTextBillNumber= (TextView) findViewById(R.id.text_bill_number);
        mTextBillTime= (TextView) findViewById(R.id.text_bill_time);
        mTextBillMoney= (TextView) findViewById(R.id.text_bill_money);
        mTextPayment= (TextView) findViewById(R.id.text_final_payment);
        intent=getIntent();
        bundle=intent.getExtras();
        mImageId=bundle.getInt("mImageId");
        mMarketName=bundle.getString("mMarketName");
        mShopName=bundle.getString("shoppingName");
        DecimalFormat df=new DecimalFormat("0.00");
        BillMoney=df.format(Double.parseDouble(bundle.getString("BillMoney")));
        mTextBillMoney.setText(BillMoney);
        random=new Random();
        ShopId=random.nextInt(214748312)+104048312;//产生随机数来表示商品编号
       mTextBillNumber.setText(ShopId+"");
        mTextBillTime.setText(getTime());

        mTextPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderPayment.this,Payment.class);
                 bundle=new Bundle();
                bundle.putInt("mImageId",mImageId);
                bundle.putCharSequence("mMarketName",mMarketName);
                bundle.putCharSequence("mShopName",mShopName);
                bundle.putCharSequence("BillMoney",BillMoney);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public String getTime(){
        Date date=new Date();
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time=df.format(date);

        return time;
    }

}
