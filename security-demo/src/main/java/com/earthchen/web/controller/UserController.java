package com.earthchen.web.controller;

import com.earthchen.dto.User;
import com.earthchen.excepetion.UserNotExistException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.IfNode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * RequestParam(required = false)用来设置参数不是必填，默认为true必填
     * 用name可以修改url参数的名字
     * defaultValue 可以设置参数的默认值
     *
     * @param username
     * @return
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User> getUser(@ApiParam(value = "用户名") @RequestParam(required = false, defaultValue = "1111") String username) {
        List<User> users = new ArrayList<>();
        users.add(new User("111", "1111"));
        users.add(new User("111", "1111"));
        users.add(new User("111", "1111"));
        return users;
    }


//    /**
//     * 使用条件查询类封装多个参数
//     * @param userQueryCondition
//     * @return
//     */
//    @GetMapping("/user")
//    public List<User> getUser(UserQueryCondition userQueryCondition) {
//        List<User> users = new ArrayList<>();
//        users.add(new User("111", "1111"));
//        users.add(new User("111", "1111"));
//        users.add(new User("111", "1111"));
//        return users;
//    }


//    /**
//     * 传递分页参数
//     * /user?username=111&size=15&page=2&sort=age,desc
//     * <p>
//     * 如果需要默认参数使用PageableDefault()注解
//     *
//     * @param username
//     * @param pageable
//     * @return
//     */
//    @GetMapping("/user")
//    public List<User> getUser(@RequestParam String username,
//                              @PageableDefault(page = 2,
//                                      size = 12,
//                                      sort = "age,desc") Pageable pageable) {
//
//        System.out.println(pageable.getPageSize());
//        System.out.println(pageable.getPageNumber());
//        System.out.println(pageable.getSort());
//
//        List<User> users = new ArrayList<>();
//        users.add(new User("111", "1111"));
//        users.add(new User("111", "1111"));
//        users.add(new User("111", "1111"));
//        return users;
//    }

    /**
     * /user/{id:\d+}正则表达式对url参数进行限制
     *
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam(value = "用户id") @PathVariable String id) {

//        throw new UserNotExistException(id);

        User user = new User("tom", "11111");
        return user;
    }

    /**
     * @param user
     * @return
     * @Valid 校验参数约束
     */
    @PostMapping
    public User createUser(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setId("1");
        return user;

    }

    /**
     * 更新user
     *
     * @param user
     * @param errors
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public User updateUser(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField() + " " + error.getDefaultMessage();
                System.out.println(message);
            });
        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getBirthday());
        System.out.println(user.getPassword());


        user.setId("1");
        return user;
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }

}
