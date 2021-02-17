package com.devproject.usersystem.web.view.controllers;

import com.devproject.usersystem.services.interfaces.AuthService;
import com.devproject.usersystem.services.models.auth.UserRegisterServiceModel;
import com.devproject.usersystem.web.view.models.auth.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class AuthController implements WebMvcConfigurer {

    private final AuthService authService;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("model")
public UserRegisterModel model(){
    return new UserRegisterModel();
}
@GetMapping("/register")
    public String getRegister(@ModelAttribute("model") UserRegisterModel model){
    return "auth/register";
}
@PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("model") UserRegisterModel model, BindingResult bindingResult){

    if(bindingResult.hasErrors()){
        return "redirect:/";
    }
    try {
        authService.registerUser(modelMapper.map(model, UserRegisterServiceModel.class));
    }
    catch(Exception e){
        return "redirect:/register";
    }

    return "redirect:/";
}
    @GetMapping(value="/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @GetMapping("/login")
    public String getLogin(){
        return "auth/login";
}







}
