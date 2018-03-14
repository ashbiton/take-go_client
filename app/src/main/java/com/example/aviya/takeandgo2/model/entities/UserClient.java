package com.example.aviya.takeandgo2.model.entities;

/**
 * Created by aviya on 31/12/2017.
 */

public class UserClient {
    private int _id;
    private String password;
    private String userName;

    public UserClient() {
    }

    public UserClient(int _id, String password, String userName) {
        this._id = _id;
        this.password = password;
        this.userName = userName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
