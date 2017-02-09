package marketshow.zhoushuai.golder;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.golder.R;
import java.util.List;
/**
 * Created by zhoushuai on 2016/11/6.
 */

/**
 * 右边商品详细展示适配器
 *
 */

public class SubShopShowDetailAdapter extends ShopDetailShowAdapter {

	List<GoodsTypeItem>  mPruductCagestsList;
    private HolderClickListener mHolderClickListener;
    private Context context;
    private LayoutInflater mInflater;
    private static int position;
    /**
     * 购物车商品价格
     */
    public TextView commodityPrise;

    private onShoppingCartBackListener callBackListener;

    public void setCallBackListener(onShoppingCartBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }


    public SubShopShowDetailAdapter(Context context, List<GoodsTypeItem> mPruductCagestsList){
		this.context = context;
		this.mPruductCagestsList = mPruductCagestsList;
		mInflater = LayoutInflater.from(context);



	}

    @Override
    public Object getItem(int section, int position) {
        return mPruductCagestsList.get(section).getProduct().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return mPruductCagestsList.size();
    }

    @Override
    public int getCountForSection(int section) {
        return mPruductCagestsList.get(section).getProduct().size();
    }

     public void setPosition(int position){
          SubShopShowDetailAdapter.position=position;
     }

     public int getPosition(){

         return position;
     }
    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_item, null);
            viewHolder = new ViewHolder();
            viewHolder.head = (ImageView) convertView.findViewById(R.id.head);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.prise = (TextView) convertView.findViewById(R.id.prise);
            viewHolder.increase = (TextView) convertView.findViewById(R.id.increase);
            viewHolder.reduce = (TextView) convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView) convertView.findViewById(R.id.shoppingNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShopProductItem product = mPruductCagestsList.get(section).getProduct().get(position);
        viewHolder.name.setText(product.getGoods());
        viewHolder.prise.setText(String.valueOf(product.getPrice()));
        viewHolder.shoppingNum.setText(String.valueOf(product.getNumber()));
        viewHolder.head.setImageResource(product.getHeadImage());

         //添加监听
        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = product.getNumber();
                num++;
                product.setNumber(num);
                viewHolder.shoppingNum.setText(product.getNumber()+"");
               // new SettlementListener(this);
               // Toast.makeText(context,product.getGoods(),Toast.LENGTH_SHORT).show();
               setPosition(position);
                if (callBackListener != null) {
                    callBackListener.updateProduct(product, "1");
                } else {
                }
                if(mHolderClickListener!=null){
                    int[] startLocation = new int[2];//定义 x，y坐标
                     viewHolder.shoppingNum.getLocationInWindow(startLocation);//获取点击商品图片的位置
                    Drawable drawable = context.getDrawable(R.drawable.bottom_red_add);//复制一个新的商品图标
                    //TODO:解决方案，先监听到左边ListView的Item中，然后在开始动画添加
                 ImageView imageView= (ImageView) ((Activity)context).findViewById(R.id.image_shopping_cart);
                    imageView.setImageResource(R.drawable.shopping_cart_selected);

                    mHolderClickListener.onHolderClick(drawable, startLocation);

                }
            }
        });


        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = product.getNumber();
                if (num > 0) {
                    num--;
                    product.setNumber(num);
                    viewHolder.shoppingNum.setText(product.getNumber()+"");
                    if (callBackListener != null) {
                        callBackListener.updateProduct(product, "2");
                    } else {
                       /// ImageView imageView= (ImageView) ((Activity)context).findViewById(R.id.image_shopping_cart);
                       // imageView.setImageResource(R.drawable.shopping_cart_noselect);
                    }
                }
            }
        });

        viewHolder.shoppingNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    int shoppingNum = Integer.parseInt(viewHolder.shoppingNum.getText().toString());
                }
            }
        });

        return convertView;
    }

    //为了防止多次findViewById定一个ViewHolder来缓存
    class ViewHolder {
        //商品顶部图片
        public ImageView head;
        //商品名称
        public TextView name;
        //商品价格
          TextView prise;
        //商品价格
        public TextView increase;
        //商品数目
        public TextView shoppingNum;
        //减少
        public TextView reduce;
    }

    public void SetOnSetHolderClickListener(HolderClickListener holderClickListener){
        this.mHolderClickListener = holderClickListener;
    }
    public interface HolderClickListener{
        void onHolderClick(Drawable drawable, int[] start_location);
    }
    

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(mPruductCagestsList.get(section).getType());
        return layout;
    }

}
