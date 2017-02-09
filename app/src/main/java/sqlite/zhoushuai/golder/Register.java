package sqlite.zhoushuai.golder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zhoushuai.golder.MainActivity;
import com.zhoushuai.golder.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zhoushuai on 2016/11/15.
 */

public class Register extends Activity {

    private EditText mUserId;
    private EditText mUsername;
    private EditText mPhonenumber;
    private EditText mPassword;
    private EditText mMakeSurePassword;
    private TextView mTextRegister;
    private ImageView mImageRegister;
    private static final String BMOBKEY = "8dbe99a8b8785fe01bf95b0ffff355d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resiger_layout);
        Bmob.initialize(Register.this, BMOBKEY);
        initdata();
    }

    private void initdata() {
        mUserId = (EditText) findViewById(R.id.edit_account_register);
        mUsername = (EditText) findViewById(R.id.edit_accountname_register);
        mPhonenumber = (EditText) findViewById(R.id.edit_phonenum_register);
        mPassword = (EditText) findViewById(R.id.edit_password_register);
        mMakeSurePassword = (EditText) findViewById(R.id.edit_makesuepwd_register);
        mTextRegister = (TextView) findViewById(R.id.text_register_green);
        mImageRegister = (ImageView) findViewById(R.id.image_user_register_back);
        mImageRegister.setOnClickListener(new ReturnListener());

    }

    //注册事件
    public void register(View view) {
        String userId = mUserId.getText().toString();
        String userName = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String phoneNumber = mPhonenumber.getText().toString();
        String makesurePwd = mMakeSurePassword.getText().toString();
        if ("".equals(userId)) {
            Toast.makeText(Register.this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(userName) || "".equals(password)) {
            Toast.makeText(Register.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(makesurePwd)) {
            Toast.makeText(Register.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();//实例化用户类
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setUserPhonenum(phoneNumber);
   // 保存到Bmob
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Register.this, "注册失败或账号已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class ReturnListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Register.this,Login.class);
            startActivity(intent);
        }
    }

}
