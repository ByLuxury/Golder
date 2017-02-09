package com.zhoushuai.golder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhoushuai on 2016/10/17.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //4个tab标签布局
    private RelativeLayout mLayoutHome, mLayoutOrde, mLayoutShopping, mLayoutMe;

    //底部tab标签切换的Fragment
    private Fragment mFragmentHome, mFragmentOrder, mFragmentShopping, mFragmentMe;

    //定义一个当前的Fragment
    private Fragment mCurrentFragment;

    //底部标签图片
    private ImageView mImageViewHome, mImageViewOrde, mImageViewShopping, mImageViewMe;

    //文字
    private TextView mTextViewHome, mTextViewOrder, mTextViewShopping, mTextViewMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initBottomTab();

    }

    //初始化视图
    private void initView() {
        mLayoutHome = (RelativeLayout) findViewById(R.id.relative_bottom_tab_home);
        mLayoutOrde = (RelativeLayout) findViewById(R.id.relative_bottom_tab_order);
        mLayoutShopping = (RelativeLayout) findViewById(R.id.relative_bottom_tab_shopping);
        mLayoutMe = (RelativeLayout) findViewById(R.id.relative_bottom_tab_me);
        //注册监听
        mLayoutHome.setOnClickListener(this);
        mLayoutOrde.setOnClickListener(this);
        mLayoutShopping.setOnClickListener(this);
        mLayoutMe.setOnClickListener(this);


        //找到底部标签图片和文字id

        mImageViewHome = (ImageView) findViewById(R.id.image_bottom_tab_home);
        mImageViewOrde = (ImageView) findViewById(R.id.image_bottom_tab_order);
        mImageViewShopping = (ImageView) findViewById(R.id.image_bottom_tab_shopping);
        mImageViewMe = (ImageView) findViewById(R.id.image_bottom_tab_me);

        mTextViewHome = (TextView) findViewById(R.id.text_bottom_tab_home);
        mTextViewOrder = (TextView) findViewById(R.id.text_bottom_tab_order);
        mTextViewShopping = (TextView) findViewById(R.id.text_bottom_tab_shopping);
        mTextViewMe = (TextView) findViewById(R.id.text_bottom_tab_me);


    }

    /**
     * 初始化底部标签
     */


    private void initBottomTab() {
        if (mFragmentHome == null) {
            mFragmentHome = new GolderBottomHome();
        }
        if (!mFragmentHome.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.linear_middle_content, mFragmentHome).commit();

            // 记录当前的Fragment
            mCurrentFragment = mFragmentHome;
            mImageViewHome.setImageResource(R.drawable.bottom_tab_home_pressed);
            mTextViewHome.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_pressed));

            mImageViewOrde.setImageResource(R.drawable.bottom_tab_order_normal);
            mTextViewOrder.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

            mImageViewShopping.setImageResource(R.drawable.bottom_tab_shopping_normal);
            mTextViewOrder.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

            mImageViewMe.setImageResource(R.drawable.bottom_tab_me_normal);
            mTextViewMe.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_bottom_tab_home:
                clickHomeTabLayout();
                break;
            case R.id.relative_bottom_tab_order:
                clickOrdeTabLayout();
                break;
            case R.id.relative_bottom_tab_shopping:
                clickShoppingTabLayout();
                break;
            case R.id.relative_bottom_tab_me:
                clickMeTabLayout();
                break;

        }
    }

    //点击首页tab标签
    private void clickHomeTabLayout() {
        if (mFragmentHome == null) {
            mFragmentHome = new GolderBottomHome();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mFragmentHome);

        mImageViewHome.setImageResource(R.drawable.bottom_tab_home_pressed);
        mTextViewHome.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_pressed));

        mImageViewOrde.setImageResource(R.drawable.bottom_tab_order_normal);
        mTextViewOrder.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewShopping.setImageResource(R.drawable.bottom_tab_shopping_normal);
        mTextViewShopping.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewMe.setImageResource(R.drawable.bottom_tab_me_normal);
        mTextViewMe.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

    }

    private void clickOrdeTabLayout() {
        if (mFragmentOrder == null) {
            mFragmentOrder = new GolderBottomOrder(); //判断当前Fragment是否为空,为空则实例化
        }
        //获取订单页面传递过来的数据对象
        PostOrderItem post = (PostOrderItem) getIntent().getSerializableExtra("item");
        if (post != null) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("postitem", post);
            mFragmentOrder.setArguments(bundle); //传递对象给Fragment
        }
        //添加或者显示出当前的Fragment
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mFragmentOrder);
        mImageViewHome.setImageResource(R.drawable.bottom_tab_home_normal);
        mTextViewHome.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewOrde.setImageResource(R.drawable.bottom_tab_order_pressed);
        mTextViewOrder.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_pressed));

        mImageViewShopping.setImageResource(R.drawable.bottom_tab_shopping_normal);
        mTextViewShopping.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewMe.setImageResource(R.drawable.bottom_tab_me_normal);
        mTextViewMe.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

    }

    private void clickShoppingTabLayout() {
        if (mFragmentShopping == null) {
            mFragmentShopping = new GolderBottomShopping();
        }

        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mFragmentShopping);
        mImageViewHome.setImageResource(R.drawable.bottom_tab_home_normal);
        mTextViewHome.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewOrde.setImageResource(R.drawable.bottom_tab_order_normal);
        mTextViewOrder.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewShopping.setImageResource(R.drawable.bottom_tab_shopping_pressed);
        mTextViewShopping.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_pressed));

        mImageViewMe.setImageResource(R.drawable.bottom_tab_me_normal);
        mTextViewMe.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

    }

    private void clickMeTabLayout() {
        if (mFragmentMe == null) {
            mFragmentMe = new GolderBottomMe();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String name = bundle.getString("username");
            Bundle postBundle = new Bundle();
            postBundle.putString("name", name);
            mFragmentMe.setArguments(postBundle);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mFragmentMe);

        mImageViewHome.setImageResource(R.drawable.bottom_tab_home_normal);
        mTextViewHome.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewOrde.setImageResource(R.drawable.bottom_tab_order_normal);
        mTextViewOrder.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewShopping.setImageResource(R.drawable.bottom_tab_shopping_normal);
        mTextViewShopping.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_normal));

        mImageViewMe.setImageResource(R.drawable.bottom_tab_me_pressed);
        mTextViewMe.setTextColor(ContextCompat.getColor(this, R.color.text_bottom_tab_pressed));

    }


    /**
     * 添加或者显示Fragment
     */
    private void addOrShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (mCurrentFragment == fragment) {

            return;
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded()) {
            fragmentTransaction.hide(mCurrentFragment).add(R.id.linear_middle_content, fragment).commit();

        } else {
            fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;

    }

}



