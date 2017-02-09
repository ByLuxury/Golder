package sqlite.zhoushuai.golder;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhoushuai on 2016/12/12.
 */

public class Feedback extends BmobObject {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserame(String username) {
        this.username = username;
    }
}
