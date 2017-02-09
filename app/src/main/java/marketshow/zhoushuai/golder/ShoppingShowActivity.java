package marketshow.zhoushuai.golder;

/**
 * Created by zhoushuai on 2016/11/4.
 */

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.golder.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务展示列表
 */
public class ShoppingShowActivity extends Activity implements View.OnClickListener, onShoppingCartBackListener, ShopToDetailListener {

    private boolean isScroll = true;  //判断滚动的标志
    private ListView mShopCategoryListView;//左边商品分类listview
    private PinnedHeaderListView mShopDetailListView;  //右边展示详细商品的listview
    private SubShopShowDetailAdapter sectionedAdapter;
    private List<ShopProductItem> productList;//定义存放商品的集合
    ShopProductItem product;
    private TextView shoppingPrise; //购物金额
    private TextView shoppingNum;//购物数目
    private TextView settlement;  //立即结算
    private FrameLayout cardLayout; //单击购物车时弹出的视图
    private LinearLayout cardShopLayout;
    private View bg_layout;   //上部分背景
    private ImageView shopping_cart; //购物车图片
    private int number = 0;  // 正在执行的动画数量，默认为0
    private boolean isClean = false; //定义一个是否完成清理的标志
    private FrameLayout animation_viewGroup;
    private TextView defaultText;  //默认显示文本
    private List<String> mNameList;  //商品分类名字集合
    private String[] mDrinksName;
    private double[] mDrinksPrices;
    private int[] mDrinksPic;
    private ImageView imageMarketShow;
    /**
     * 分类列表
     */
    private List<GoodsTypeItem> productCategorizes;

    private List<ShopProductItem> shopProductsAllItem;

    private ListView shoppingListView;

    private ShopAdapter shopAdapter;

    private TextView MarketName;
    private double sum; //添加到购物车钱的数目
    private int shopNum;
    private int imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_show_item_content);
        MarketName = (TextView) findViewById(R.id.text_market_show_name);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        MarketName.setText(bundle.getString("MarketName"));
        imageMarketShow = (ImageView) findViewById(R.id.image_market_show_name);
        imageMarketShow.setImageResource(R.drawable.home_ykl_market_show);
        initView();//加载视图


    }


    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // 用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {

                    }
                    isClean = false;

                    break;
                default:
                    break;
            }
        }
    };


    public List<GoodsTypeItem> getData() {
        productCategorizes = new ArrayList<>();
        String[] GoodsNames = new String[]{"生活用品", "饮料饮品", "生鲜蔬菜", "牛奶乳品",
                "服饰鞋靴", "休闲零食", "美容化妆", "母婴用品", "厨房用品",
                "家居生活", "图书杂志", "电脑办公", "生鲜蔬菜", "牛奶乳品"};
        for (int i = 1; i < GoodsNames.length; i++) {
            GoodsTypeItem productCategorize = new GoodsTypeItem();
            productCategorize.setType(GoodsNames[i]);

            //分类详细商品
            mDrinksName = new String[]{"农夫山泉", "怡宝", "红牛功能饮料", "汇源橙汁", "茉莉蜜茶", "可口可乐", "雪碧", "冰红茶"};
            mDrinksPrices = new double[]{2.00, 2.00, 6.00, 3.49, 4.00, 3.00, 3.00, 2.85};
            mDrinksPic = new int[]{R.drawable.drink_nfsq, R.drawable.drink_yb, R.drawable.drink_hn,
                    R.drawable.drink_hycz, R.drawable.dingk_mlmc, R.drawable.drink_kl,
                    R.drawable.drink_xb, R.drawable.drink_bhc};
            HashMap<String, Object> mapDetailGoods = new HashMap<>();
            for (int n = 0; n < mDrinksName.length; n++)
                mapDetailGoods.put(mDrinksName[n], mDrinksPrices[n]);
            ArrayList<Map<String, Object>> priceAndGoodsList = new ArrayList<>();
            priceAndGoodsList.add(mapDetailGoods);
            //添加详细商品
            shopProductsAllItem = new ArrayList<>();

            //添加饮料饮品属性
            for (int j = 0; j < mDrinksName.length; j++) {
                product = new ShopProductItem();
                // product.setId(14345 + i + j);
                product.setHeadImage(mDrinksPic[j]);
                product.setGoods(mDrinksName[j]);
                //new DecimalFormat("0.00").format(mapDetailGoods.get(DetailGoods[j]));
                DecimalFormat df = new DecimalFormat("0.00");//格式化单价,保留2位小数
                //  df.format(mapDetailGoods.get(DetailGoods[j]));
                product.setPrice(df.format(mapDetailGoods.get(mDrinksName[j])) + "");

                shopProductsAllItem.add(product);
            }
            productCategorize.setProduct(shopProductsAllItem);
            productCategorizes.add(productCategorize);
        }
        return productCategorizes;
    }

    //初始化加载视图

    private void initView() {
        getData(); //调用获取数据方法
        animation_viewGroup = createAnimLayout();
        //TextView noData = (TextView) findViewById(R.id.noData);
        //RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        //击加号时 将相应的价格添到下面到购物测车里
        shoppingPrise = (TextView) findViewById(R.id.shoppingPrise);

        //购物车旁边显示添加的数量


        shoppingNum = (TextView) findViewById(R.id.shoppingNum);

        //立即结算
        settlement = (TextView) findViewById(R.id.settlement);
        //左边分类信息的ListView
        mShopCategoryListView = (ListView) findViewById(R.id.lv_left_goods_category);

        //右边更多商品展示
        mShopDetailListView = (PinnedHeaderListView) findViewById(R.id.lv_right_goods_show);

        //底部购物车图标
        shopping_cart = (ImageView) findViewById(R.id.image_shopping_cart);
        defaultText = (TextView) findViewById(R.id.defaultText);
        // shoppingList = (LinearLayout) getView().findViewById(R.id.shoppingList);

        //底下购物车到ListView
        shoppingListView = (ListView) findViewById(R.id.shopproductListView);
        cardLayout = (FrameLayout) findViewById(R.id.cardLayout);
        cardShopLayout = (LinearLayout) findViewById(R.id.cardShopLayout);
        //点击购物车图标时，上半部分的背景颜色
        bg_layout = findViewById(R.id.bg_layout);

        initData(); //调用加载初始化数据方法
    }

    //初始化数据
    public void initData() {
        productList = new ArrayList<>();
        mNameList = new ArrayList<>();
        sectionedAdapter = new SubShopShowDetailAdapter(this, productCategorizes);

        sectionedAdapter.SetOnSetHolderClickListener(new SubShopShowDetailAdapter.HolderClickListener() {

            @Override
            public void onHolderClick(Drawable drawable, int[] startLocation) {
                doAnim(drawable, startLocation);
            }

        });

        for (GoodsTypeItem type : productCategorizes) {
            mNameList.add(type.getType());   // 便历分类条目，添加到集合中
        }
        mShopDetailListView.setAdapter(sectionedAdapter);
        sectionedAdapter.setCallBackListener(this);
        mShopCategoryListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.categorize_item, mNameList));

        shopAdapter = new ShopAdapter(this, productList);
        shoppingListView.setAdapter(shopAdapter);
        shopAdapter.setShopToDetailListener(this);

        //商品分类ListView点击事件
        mShopCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                isScroll = false;

                for (int i = 0; i < mShopCategoryListView.getChildCount(); i++) {
                    if (i == position) {
                        mShopCategoryListView.getChildAt(i).setBackgroundColor(
                                Color.rgb(255, 255, 255));//设置当前选中项为白色
                    } else {

                        mShopCategoryListView.getChildAt(i).setBackgroundColor(
                                Color.TRANSPARENT);  //否则为透明色
                    }
                }

                int rightSection = 0;  //右边详细商品列表，默认第一项被选中
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;  //得到当前选中的
                }
                mShopDetailListView.setSelection(rightSection);

            }

        });


        //右边商品视图监听  实现滚动监听器
        mShopDetailListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < mShopCategoryListView.getChildCount(); i++) {

                        if (i == sectionedAdapter
                                .getSectionForPosition(firstVisibleItem)) {
                            mShopCategoryListView.getChildAt(i).setBackgroundColor(
                                    Color.rgb(255, 255, 255));
                        } else {
                            mShopCategoryListView.getChildAt(i).setBackgroundColor(
                                    Color.TRANSPARENT);

                        }
                    }

                } else {
                    isScroll = true;
                }
            }
        });

        mShopDetailListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                // Toast.makeText(ShoppingShowActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });

        bg_layout.setOnClickListener(this);
        settlement.setOnClickListener(new SettlementListener(sectionedAdapter.getPosition()));  //立即结算注册监听
        shopping_cart.setOnClickListener(this); //购物车图标监听
    }

    /**
     * 回调函数更新购物车和价格显示状态
     *
     * @param product 传递产品类型类，以及当前类型
     * @param type    当前产品
     */

    @Override
    public void updateProduct(ShopProductItem product, String type) {
        if (type.equals("1")) {
            if (!productList.contains(product)) {
                productList.add(product);
            } else {
                for (ShopProductItem shopProductItem : productList) {
                    if (product.getId() == shopProductItem.getId()) {
                        shopProductItem.setNumber(shopProductItem.getNumber());
                    }
                }
            }
        } else if (type.equals("2")) {
            if (productList.contains(product)) {
                if (product.getNumber() == 0) {
                    productList.remove(product);
                } else {
                    for (ShopProductItem shopProductItem : productList) {
                        if (product.getId() == shopProductItem.getId()) {
                            shopProductItem.setNumber(shopProductItem.getNumber());
                        }
                    }
                }

            }
        }
        shopAdapter.notifyDataSetChanged();
        setPrise();
    }

    @Override
    public void onUpdateDetailList(ShopProductItem product, String type) {
        if (type.equals("1")) {
            for (int i = 0; i < productCategorizes.size(); i++) {
                shopProductsAllItem = productCategorizes.get(i).getProduct();
                for (ShopProductItem shopProductItem : shopProductsAllItem) {
                    if (product.getId() == shopProductItem.getId()) {
                        shopProductItem.setNumber(product.getNumber());
                    }
                }
            }
        } else if (type.equals("2")) {
            for (int i = 0; i < productCategorizes.size(); i++) {
                shopProductsAllItem = productCategorizes.get(i).getProduct();
                for (ShopProductItem shopProductItem : shopProductsAllItem) {
                    if (product.getId() == shopProductItem.getId()) {
                        shopProductItem.setNumber(product.getNumber());
                    }
                }
            }
        }
        sectionedAdapter.notifyDataSetChanged();
        setPrise();
    }


    //移除产品
    @Override
    public void onRemovePriduct(ShopProductItem product) {
        for (int i = 0; i < productCategorizes.size(); i++) {
            shopProductsAllItem = productCategorizes.get(i).getProduct();
            for (ShopProductItem shopProductItem : shopProductsAllItem) {
                if (product.getId() == shopProductItem.getId()) {
                    productList.remove(product);
                    shopAdapter.notifyDataSetChanged();
                    shopProductItem.setNumber(shopProductItem.getNumber());
                }
            }
        }
        sectionedAdapter.notifyDataSetChanged();
        shopAdapter.notifyDataSetChanged();
        setPrise();
    }


    /**
     * 更新购物车价格
     */
    public void setPrise() {
        sum = 0;
        shopNum = 0;
        for (ShopProductItem pro : productList) {
            // sum = sum + (pro.getNumber() * Double.parseDouble(pro.getPrice()));
            sum = DoubleUtil.sum(sum, DoubleUtil.mul((double) pro.getNumber(), Double.parseDouble(pro.getPrice())));
            shopNum = shopNum + pro.getNumber();

        }
        //如果添加的总数大于0 则显示具体数目，否则隐藏
        if (shopNum > 0) {
            shoppingNum.setVisibility(View.VISIBLE);
        } else {
            shoppingNum.setVisibility(View.GONE);
        }

        if (sum >= 10) {  //判断是否达到配送金额
            settlement.setText(R.string.text_settlement);
            settlement.setBackgroundColor(Color.rgb(220, 89, 20));
        } else {
            settlement.setText(R.string.text_selected_shopping_show);
            settlement.setBackgroundColor(Color.rgb(105, 105, 105));
        }

        //添加到购物车中商品的价格，如果被添加了，设置价格并可见
        if (sum > 0) {
            shoppingPrise.setVisibility(View.VISIBLE);
//            settlement.setText(R.string.text_settlement);
//            settlement.setBackgroundColor(Color.rgb(220,89,20));

        } else {
            //当购物车中无东西时，设置购物车图标为默认颜色
            ImageView imageView = (ImageView) findViewById(R.id.image_shopping_cart);
            imageView.setImageResource(R.drawable.shopping_cart_noselect);
//            settlement.setText(R.string.text_selected_shopping_show);
//           settlement.setBackgroundColor(Color.rgb(105,105,105));

            // Toast.makeText(this,"ddd",Toast.LENGTH_SHORT).show();
        }
        shoppingPrise.setText("¥" + " " + (new DecimalFormat("0.00")).format(sum));
        shoppingNum.setText(String.valueOf(shopNum));
        //return sum;
    }

    // 结算单独写个监听类 因为需要构造有参数的类
    class SettlementListener implements View.OnClickListener {
        int position;

        public SettlementListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (sum < 10) {
                Toast.makeText(ShoppingShowActivity.this, "10起送哦", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ShoppingShowActivity.this, Settlement.class);
            //传递数据
            Bundle bundle = new Bundle();
            bundle.putCharSequence("marketName", MarketName.getText().toString());
            //  bundle.putInt("marketIcon",imageId);
            // bundle.putInt("marketPic",);
            bundle.putCharSequence("shoppingName", productList.get(position).getGoods()); //名称
            String GoodsPrices = sum + ""; //购物价格
            bundle.putCharSequence("GoodsPrices", GoodsPrices);
            bundle.putCharSequence("shoppingNum", shoppingNum.getText());  //购物数量
            bundle.putCharSequence("GoodsRealPrices", GoodsPrices);
            bundle.putInt("GoodsPic", productList.get(position).getHeadImage());
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_shopping_cart:  //底部购物车事件
                if (productList.isEmpty() || productList == null) {
                    defaultText.setVisibility(View.VISIBLE);
                } else {
                    defaultText.setVisibility(View.GONE);
                }

                if (cardLayout.getVisibility() == View.GONE) {
                    cardLayout.setVisibility(View.VISIBLE);

                    // 加载动画
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
                    // 动画开始
                    cardShopLayout.setVisibility(View.VISIBLE);
                    cardShopLayout.startAnimation(animation);
                    bg_layout.setVisibility(View.VISIBLE);

                } else {
                    cardLayout.setVisibility(View.GONE);
                    bg_layout.setVisibility(View.GONE);
                    cardShopLayout.setVisibility(View.GONE);
                }
                break;

//            case R.id.settlement: //底部立即结算事件
//                if(sum<10){
//
//                    Toast.makeText(this,"金额不足",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Intent intent = new Intent(ShoppingShowActivity.this, Settlement.class);
//                //传递数据
//                Bundle bundle=new Bundle();
//                String GoodsPrices=sum+""; //购物价格
//                bundle.putCharSequence("GoodsPrices",GoodsPrices);
//                bundle.putCharSequence("shoppingNum",shoppingNum.getText().toString());  //购物数量
//                //bundle.putCharSequence("shopingName",productList.get());
//
//                intent.putExtras(bundle);
////                IntentObjectPool.putObjectExtra(intent, CommonParameter.SETTLEMENT_DETAILS, productList);
////                IntentObjectPool.putStringExtra(intent,"shopId",shopId);
//                startActivity(intent);
//                break;

            case R.id.bg_layout: //上面一层的背景
                cardLayout.setVisibility(View.GONE);
                bg_layout.setVisibility(View.GONE);
                cardShopLayout.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 创建动画层
     *
     * @return
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    private void doAnim(Drawable drawable, int[] start_location) {
        if (!isClean) {
            setAnim(drawable, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    /**
     * 动画效果设置
     *
     * @param drawable       将要加入购物车的商品
     * @param start_location 起始位置
     */
    @SuppressLint("NewApi")
    private void setAnim(Drawable drawable, int[] start_location) {
        Animation mScaleAnimation = new ScaleAnimation(1.2f, 0.6f, 1.2f, 0.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);  //改变缩放的中心点，相对于自己的中心坐标
        mScaleAnimation.setFillAfter(true);

        final ImageView iview = new ImageView(this);  //创建移动的图片
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview,
                start_location);  //加载动画图片


        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        settlement.getLocationInWindow(end_location);

        // 计算位移
        int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);


        Animation mRotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF,
                0.3f);
        mRotateAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(mRotateAnimation);
        set.addAnimation(mScaleAnimation);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(500);// 动画的执行时间
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

                ObjectAnimator.ofFloat(shopping_cart, "translationY", 0, 4, -2, 0).setDuration(400).start();
                ObjectAnimator.ofFloat(shoppingNum, "translationY", 0, 4, -2, 0).setDuration(400).start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

    /**
     * @param vg       动画运行的层 这里是frameLayout
     * @param view     要运行动画的View
     * @param location 动画的起始位置
     * @return
     * @deprecated 将要执行动画的view 添加到动画层
     */

    private View addViewToAnimLayout(FrameLayout vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    /**
     * 内存过低时及时处理动画产生的未处理冗余
     */
    @Override
    public void onLowMemory() {
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
