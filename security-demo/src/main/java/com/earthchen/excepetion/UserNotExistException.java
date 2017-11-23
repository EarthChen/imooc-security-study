package com.earthchen.excepetion;


public class UserNotExistException extends RuntimeException {


    private static final long serialVersionUID=-1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public UserNotExistException(String id){
        super("user not exist");
        this.id=id;
    }
}
