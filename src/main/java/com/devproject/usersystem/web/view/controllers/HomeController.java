package com.devproject.usersystem.web.view.controllers;

import com.devproject.usersystem.services.interfaces.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller()
public class HomeController {

    private final ProfileService profileService;
    @Autowired
    public HomeController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/")
    public String getHome(Principal principal, Model model){
        if(principal==null){
            return "home/homePage";
        }

        model.addAttribute("profile", profileService.getProfileByUsername(principal.getName()));
        return "profile/currentProfile/profilePage";
    }


}
