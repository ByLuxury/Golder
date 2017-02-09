package marketshow.zhoushuai.golder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhoushuai.golder.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * 底部购物车适配器
 */
public class ShopAdapter extends BaseAdapter {

    private ShopToDetailListener shopToDetailListener;

    public void setShopToDetailListener(ShopToDetailListener callBackListener) {
        this.shopToDetailListener = callBackListener;
    }
    private List<ShopProductItem> shopProductItems;
    private LayoutInflater mInflater;
    public ShopAdapter(Context context, List<ShopProductItem> shopProductItems) {
        this.shopProductItems = shopProductItems;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shopProductItems.size();
    }

    @Override
    public Object getItem(int position) {
        return shopProductItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pop_shop_cart, null);
            viewHolder = new ViewHolder();
            viewHolder.commodityName = (TextView) convertView.findViewById(R.id.commodityName);
            viewHolder.commodityPrise = (TextView) convertView.findViewById(R.id.commodityPrise);
         // viewHolder.commodityNum = (TextView) convertView.findViewById(R.id.commodityNum);
            viewHolder.increase = (TextView)  convertView.findViewById(R.id.increase);
            viewHolder.reduce = (TextView)  convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView)  convertView.findViewById(R.id.shoppingNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commodityName.setText(shopProductItems.get(position).getGoods());
        viewHolder.commodityPrise.setText(shopProductItems.get(position).getPrice());
        //viewHolder.commodityNum.setText(1+"");
        viewHolder.shoppingNum.setText(shopProductItems.get(position).getNumber()+"");

        //点击购物车底部弹起来的添加按钮事件
        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = shopProductItems.get(position).getNumber();
                num++;
                shopProductItems.get(position).setNumber(num);
                viewHolder.shoppingNum.setText(shopProductItems.get(position).getNumber()+"");

                double price=Double.parseDouble(shopProductItems.get(position).getPrice());
                viewHolder.commodityPrise.setText(String.valueOf(new DecimalFormat("#.00").format(num*price)));

                if (shopToDetailListener != null) {
                    shopToDetailListener.onUpdateDetailList(shopProductItems.get(position), "1");
                } else {
                }
            }
        });


         //购物车减操作
        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = shopProductItems.get(position).getNumber();
                if (num > 0) {
                    num--;
                    viewHolder.shoppingNum.setText(shopProductItems.get(position).getNumber()+"");
                    double price=Double.parseDouble(shopProductItems.get(position).getPrice());
                    viewHolder.commodityPrise.setText(String.valueOf(new DecimalFormat("#.00").format(num*price)));
                    if(num==0){
                        shopProductItems.get(position).setNumber(num);
                        shopToDetailListener.onRemovePriduct(shopProductItems.get(position));
                        viewHolder.commodityPrise.setText("0");
                    }
                    else {
                        shopProductItems.get(position).setNumber(num);
                        viewHolder.shoppingNum.setText(shopProductItems.get(position).getNumber()+"");
                        if (shopToDetailListener != null) {
                            shopToDetailListener.onUpdateDetailList(shopProductItems.get(position), "2");
                        } else {
                        }
                    }

                }
            }
        });

        return convertView;
    }

    //单击底部购物车显示条目
    class ViewHolder {
        /**
         * 购物车商品名称
         */
        public TextView commodityName;
        /**
         * 购物车商品价格
         */
        public TextView commodityPrise;
        /**
         * 单击底部购物车商品数量
         */
        //public TextView commodityNum;
        /**
         * 增加
         */
        public TextView increase;
        /**
         * 减少
         */
        public TextView reduce;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
    }
}
