package com.devproject.usersystem.services.interfaces;

import com.devproject.usersystem.services.models.auth.UserLoginServiceModel;
import com.devproject.usersystem.services.models.auth.UserRegisterServiceModel;
import org.springframework.stereotype.Service;


public interface AuthService {
    void registerUser(UserRegisterServiceModel model) throws Exception;


}
