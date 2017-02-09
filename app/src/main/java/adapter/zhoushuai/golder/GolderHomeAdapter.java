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
 * Created by zhoushuai on 2016/10/21.
 */

public class GolderHomeAdapter extends BaseAdapter {

 private List<MarketItem> mLstdata;
  private LayoutInflater  mLayoutInflater;

    public GolderHomeAdapter(Context context ,List<MarketItem> mLstdata) {
        this.mLstdata = mLstdata;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mLstdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mLstdata.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder(); //如果converView为空，实例化viewHolder
            convertView=mLayoutInflater.inflate(R.layout.golder_home_item_view,null);//找到每一项的布局

            //找到相应的布局ID
            viewHolder.marketPicture= (ImageView) convertView.findViewById(R.id.image_home_market);
            //viewHolder.marketSplitLine= (TextView) convertView.findViewById(R.id.view_home_splitline);
            viewHolder.marketTitle= (TextView)convertView.findViewById(R.id.text_home_markettitle);
            viewHolder.marketDelMsg= (TextView)convertView.findViewById(R.id.text_home_delivery_meaasge);
            viewHolder.marketDistace= (TextView)convertView.findViewById(R.id.text_home_marketdistance);
                //设置Tag，将viewHolder与convertView关联
            convertView.setTag(viewHolder);
        }
   else {
             //如果为空，获取Tag
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //获取当前选项
        MarketItem marketItem=mLstdata.get(position);
        //为每一个选项设值
         viewHolder.marketPicture.setImageResource(marketItem.marketPictureID);
         // viewHolder.marketSplitLine.setText(marketItem.marketSplitLine);
        viewHolder.marketTitle.setText(marketItem.marketTitle);
        viewHolder.marketDelMsg.setText(marketItem.marketDelMsg);
        viewHolder.marketDistace.setText(marketItem.marketDistace);

        return convertView;
    }

    class ViewHolder{   //创建内部类与要显示的试图对应
        public ImageView marketPicture;
       // public TextView marketSplitLine;
        public TextView marketTitle;
        public TextView marketDelMsg;
        public TextView marketDistace;

    }
}
