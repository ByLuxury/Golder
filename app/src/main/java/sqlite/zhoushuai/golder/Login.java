package sqlite.zhoushuai.golder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.golder.GolderBottomMe;
import com.zhoushuai.golder.GolderBottomOrder;
import com.zhoushuai.golder.MainActivity;
import com.zhoushuai.golder.R;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhoushuai on 2016/11/12.
 */

public class Login extends Activity  {

    private TextView mLogin;   //登录
    private EditText mAccount;//账号
    private EditText mPassword;//密码
    private TextView otherLogin;//第三方登录
    private TextView mRegister;
    private TextView mForgetPwd;
    private static final String BMOBKEY = "8dbe99a8b8785fe01bf95b0ffff355d3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        Bmob.initialize(Login.this, "8dbe99a8b8785fe01bf95b0ffff355d3");
      initData();


        }

    private void initData(){

        mLogin= (TextView) findViewById(R.id.text_login_green);//登录
        mRegister= (TextView) findViewById(R.id.free_register);//注册
        mForgetPwd= (TextView) findViewById(R.id.forget_pwd);//忘记密码
        mAccount= (EditText) findViewById(R.id.edit_account);
        mPassword= (EditText) findViewById(R.id.edit_password);
        otherLogin= (TextView) findViewById(R.id.text_other_login);
       // mLogin.setOnClickListener(new LoginListener());
    }

     //免费注册事件
    public void freeResiger(View view){
        Intent intentLogin=new Intent(Login.this,Register.class);
        startActivity(intentLogin);
    }

     //返回
    public void loginBack(View view){
        finish();
    }

    //用户登录
   public void userLogin(View view){

             final String userId=mAccount.getText().toString();
            final String userPwd=mPassword.getText().toString();

            if("".equals(userId)||"".equals(userPwd)){
                Toast.makeText(Login.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            BmobQuery<User> query=new BmobQuery<>();

         query.addWhereEqualTo("userId",mAccount.getText().toString());

            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {

                   if(e==null) {
                       String id=list.get(0).getUserId();
                       String pwd=list.get(0).getUserPassword();
                       String name=list.get(0).getUserName();
                       if(userId.equals(id)&&userPwd.equals(pwd)){
                           Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                           Bundle bundle=new Bundle();
                           bundle.putCharSequence("username",name);
//                           golderBottomOrder.setArguments(bundle);
//                           FragmentManager fragmentManager=getSupportFragmentManager();
//                           FragmentTransaction transaction=fragmentManager.beginTransaction();
                           //transaction.add(R.id.linear_middle_content,golderBottomOrder).commit();
                          Intent intent=new Intent(Login.this, MainActivity.class);
                        // finish();
                           intent.putExtras(bundle);
                           startActivity(intent);

                       }
                       else{
                           Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
                       }
                   }
                    else{
                       Toast.makeText(Login.this, "账号未注册", Toast.LENGTH_SHORT).show();
                   }
                }
            });

        }



}
