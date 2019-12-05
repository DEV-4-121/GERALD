package com.example.gerald_app.data;

import com.example.gerald_app.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            authenticate(username, password);
            return new Result.Success<>(username);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private boolean authenticate(String username, String password){
        if(username == "user_gerald"){
            if(password == "1234"){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
