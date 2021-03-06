package com.example.ben.rainy_night.http.bmob.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Bmob 创建用户表
 *
 * @author Ben
 * @date 2018/1/15
 */

public class UserEntity extends BmobUser implements Serializable{

    private static final long serialVersionUID = 4490757709998655360L;
    /**
     * 设置昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 头像
     */
    private BmobFile headimg;

    public UserEntity() {

    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public BmobFile getHeadimg() {
        return headimg;
    }

    public void setHeadimg(BmobFile headimg) {
        this.headimg = headimg;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", headimg=" + headimg+
                '}';
    }
}
