package sqlite.zhoushuai.golder;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhoushuai on 2016/11/12.
 */

public class User extends BmobObject{
    private String userId;  //用户id
    private String userName; //用户名
    private String userPhonenum;//电话号码
    private String userPassword;//密码

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhonenum() {
        return userPhonenum;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhonenum(String userPhonenum) {
        this.userPhonenum = userPhonenum;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
