package com.devproject.usersystem.web.view.controllers;

import com.devproject.usersystem.services.interfaces.ImageService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.interfaces.SongService;
import com.devproject.usersystem.web.view.models.profiles.ProfileSongsDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileUpdateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ProfileController {
    private final ProfileService profileService;
    private final SongService songService;
    private final ImageService imageService;

    @Autowired
    public ProfileController(ProfileService profileService, SongService songService, ImageService imageService) {
        this.profileService = profileService;
        this.songService = songService;
        this.imageService = imageService;
    }

    @ModelAttribute("model")
    public ProfileUpdateModel model() {
        return new ProfileUpdateModel();
    }

    @GetMapping("/profile/edit")
    public String getEditProfile(Principal principal, Model model) {

        ProfileDisplayModel profileModel = profileService.getProfileByUsername(principal.getName());
        model.addAttribute("profileModel", profileModel);
        return "/profile/currentProfile/editProfile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute ProfileUpdateModel profileModel, @RequestParam("file") MultipartFile image, Model model, Principal principal) {
        try {


            profileService.editProfile(principal.getName(), profileModel, image);
        } catch (IOException e) {
            //TODO:Handle Exception
        }
        return "redirect:/";
    }


    @GetMapping("/profile/social/global")
    public String getFriendsSearchGlobal(Model model, Principal principal) {
        model.addAttribute("profiles", profileService.getAllProfilesExceptCurrentAndFriends(principal.getName()));

        return "profile/social/global";
    }
    @GetMapping("/profile/social/acceptRequest/{id}")
    public String addFriend(@PathVariable(value="id") Long id, Principal principal){
        profileService.acceptFriendRequestToProfile(id, principal.getName());
        return "redirect:/profile/social/requests";
    }
    @GetMapping("/profile/social/declineRequest/{id}")
    public String declineRequest(@PathVariable(value="id") Long id, Principal principal){
        profileService.declineFriendRequestToProfile(id, principal.getName());
        return "redirect:/profile/social/requests";
    }
    @GetMapping("/profile/social/sendFriendRequest/{id}")
    public String sendRequest(@PathVariable(value="id") Long id, Principal principal){
        profileService.addFriendRequestToProfile(id, principal.getName());
        return "redirect:/profile/social/global";
    }
    @GetMapping("/profile/social/removeFriend/{id}")
    public String removeFriend(@PathVariable(value="id") Long id, Principal principal){
        profileService.removeFriendFromProfile(id, principal.getName());
        return "redirect:/profile/social/friends";
    }
    @GetMapping("/profile/social/requests")
    public String getRequests(Principal principal, Model model){
        model.addAttribute("profiles", profileService.getFriendRequestsReceivedForProfile(principal.getName()));
        return "profile/social/friendRequests";
    }
    @GetMapping("/profile/social/friends")
    public String getFriends(Principal principal, Model model){
        model.addAttribute("profiles", profileService.getFriendsForProfile(principal.getName()));
        return "profile/social/friends";
    }
    @GetMapping("/profile/{username}")
    public String getProfile(@PathVariable(value="username") String username, Model model){
        model.addAttribute("profile", profileService.getProfileByUsername(username));
        return "profile/otherProfile/profilePage";
    }
}

