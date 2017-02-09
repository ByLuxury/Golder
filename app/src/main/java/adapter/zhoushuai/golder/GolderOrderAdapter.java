package adapter.zhoushuai.golder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoushuai.golder.R;

import java.util.List;

/**
 * Created by zhoushuai on 2016/11/17.
 */

public class GolderOrderAdapter extends BaseAdapter {

    private List<OrderItem> orderList;
    private LayoutInflater inflater;

    public GolderOrderAdapter(Context context,List<OrderItem> orderList){
        inflater=LayoutInflater.from(context);
        this.orderList=orderList;
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderViewHolder orderViewHolder;
        if (convertView==null){
            orderViewHolder=new OrderViewHolder();
            convertView=inflater.inflate(R.layout.order_item,null);
            orderViewHolder.imageMarket= (ImageView) convertView.findViewById(R.id.image_order_icon);
            orderViewHolder.textMarketName= (TextView) convertView.findViewById(R.id.text_order_market_name);
            orderViewHolder.textShopName= (TextView) convertView.findViewById(R.id.text_order_shopname);
            orderViewHolder.textShopPrice= (TextView) convertView.findViewById(R.id.text_order_price);
            convertView.setTag(orderViewHolder);
        }
        else{
            orderViewHolder= (OrderViewHolder) convertView.getTag();
        }
        OrderItem orderItem=orderList.get(position);
        orderViewHolder.imageMarket.setImageResource(orderItem.getImageId());
        orderViewHolder.textMarketName.setText(orderItem.getOrderMarket());
        orderViewHolder.textShopName.setText(orderItem.getShopName());
        orderViewHolder.textShopPrice.setText(orderItem.getShopPrice());
        return convertView;

    }

    class OrderViewHolder {
        private ImageView imageMarket;
        private TextView textMarketName;
        private TextView textShopName;
        private TextView textShopPrice;

    }
}
