package com.earthchen.dto;

import com.earthchen.validator.MyValidConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 使用@JsonView()注解来限定视图
 */
public class User {

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    private String id;

    @MyValidConstraint(message = "自定义校验注解测试")
    private String username;

    /**
     * @NotBlank 设置字段不为空
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * @Past 设置为过去的时间
     * message 设置自定义提示信息
     */
    @Past(message = "生日必须为过去的时间")
    private Date birthday;


    @JsonView(User.UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @JsonView(User.UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
