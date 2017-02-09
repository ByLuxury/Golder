package com.zhoushuai.golder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import sqlite.zhoushuai.golder.Login;


/**
 * Created by zhoushuai on 2016/10/17.
 */

public class GolderBottomMe extends Fragment {
    private ImageView mImageHead;
    private TextView mTextUsername;
    private View meView;
    private String username;
    onTestListener mCallback;
    public interface onTestListener

    {

        public void onTest(String str);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        meView = inflater.inflate(R.layout.golder_bottom_me, container, false);
        mImageHead = (ImageView) meView.findViewById(R.id.image_top_me_headpic);
        mImageHead.setOnClickListener(new MyClikcLIstener());
        mTextUsername= (TextView) meView.findViewById(R.id.text_top_me_headname);

        Bundle bundle=getArguments();
        if(bundle!=null)
            mTextUsername.setText(bundle.getString("name"));

        return meView;
    }

    class MyClikcLIstener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            Intent intentRegister = new Intent(getActivity(), Login.class);
            startActivity(intentRegister);
        }
    }
}
