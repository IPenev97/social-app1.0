package com.devproject.usersystem.services.implementations;

import com.devproject.usersystem.data.models.User;
import com.devproject.usersystem.data.repositories.UserRepository;
import com.devproject.usersystem.services.interfaces.AuthService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.models.auth.UserRegisterServiceModel;
import com.devproject.usersystem.web.security.WebSecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private  UserRepository userRepository;

    private  ModelMapper modelMapper;
    private  PasswordEncoder passwordEncoder;
    private ProfileService profileService;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void registerUser(UserRegisterServiceModel model)  {
        if (userRepository.findByEmail(model.getEmail()).isPresent()||
            userRepository.findByUsername(model.getUsername()).isPresent()){
            return;
            //TODO HandleException
        }
        User user = modelMapper.map(model,User.class);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        System.out.println();
        //Hardcoded
        user.setRole("USER");
        user.setProfile(profileService.initProfile(user.getUsername()));
        userRepository.save(user);
    }



}
