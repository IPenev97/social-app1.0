package com.devproject.usersystem.web.view.controllers;

import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.interfaces.SongService;
import com.devproject.usersystem.web.view.models.profiles.ProfileDisplayModel;
import com.devproject.usersystem.web.view.models.songs.*;
import com.devproject.usersystem.web.view.models.profiles.ProfileSongsDisplayModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class SongController {

    private final SongService songService;
    private final ProfileService profileService;


    public SongController(SongService songService, ProfileService profileService) {
        this.songService = songService;
        this.profileService = profileService;

    }

    @GetMapping("/music/list")
    public String getSongList(Principal principal, Model model, @ModelAttribute SongUploadModel uploadModel) {
        ProfileSongsDisplayModel profileModel = profileService.getProfileSongs(principal.getName());
        model.addAttribute("profile", profileModel);

        return "/profile/currentProfile/songList";
    }
    @GetMapping("/music/edit")
    public String getEditSongList(Principal principal, Model model, @ModelAttribute SongUploadModel uploadModel) {
        ProfileSongsDisplayModel profileModel = profileService.getProfileSongs(principal.getName());
        model.addAttribute("profile", profileModel);

        return "/profile/currentProfile/editSongList";
    }

    @PostMapping("/music/upload")
    public String uploadSong(@ModelAttribute SongUploadModel songModel, @RequestParam("file") MultipartFile file, Principal principal) {


        try {
            profileService.uploadSongToProfile(principal.getName(), songModel, file);
        } catch (IOException e) {
            //TODO Handle Exception
        }

        return "redirect:/music/edit";

    }

    @GetMapping("/music/getSong/{id}")
    @ResponseBody
    public SongDisplayModel getSong(@PathVariable(value = "id") Long id) {

        try {
            return songService.findById(id);
        } catch (Exception e) {
            //TODO Handle Exception
            return null;
        }

    }

    @PostMapping("/music/update")
    public String updateSong(@ModelAttribute SongUpdateModel model, @RequestParam("file") MultipartFile file) {

        try {
            songService.updateSong(model, file);
        } catch (Exception e) {
            //TODO Handle Exception
        }
        return "redirect:/music/edit";
    }

    @GetMapping("/music/delete/{id}")
    public String deleteSong(@PathVariable(value = "id") Long id, Principal principal) {

        try {

            profileService.deleteSongFromProfile(principal.getName(), id);
        } catch (Exception e) {
            //TODO Handle Exception
            return null;
        }
        System.out.println();
        return "redirect:/music/edit";
    }

    @GetMapping("/music/profile/{username}")
    public String getAllSongsForProfile(@PathVariable(value = "username") String username, Model model, Principal principal) {

        ProfileSongsDisplayModel profileModel = profileService.getProfileSongs(username);
        ProfileSongsDisplayModel currentProfileModel = profileService.getProfileSongs(principal.getName());
        model.addAttribute("displayProfile", profileModel);
        model.addAttribute("currentProfile", currentProfileModel);
        return "profile/otherProfile/songList";
    }

    @PostMapping("/music/updateTop1Song")
    public String uploadTop1SongToCurrentProfile(@ModelAttribute SongUploadModel song, @RequestParam("file")MultipartFile picture, Principal principal) {

        try {
            profileService.uploadTop1Song(song, principal.getName(), picture);
        }
        catch (IOException e){
            //TODO Handle exception
        }
        return "redirect:/music/edit";
    }
    @PostMapping("/music/updateTop2Song")
    public String uploadTop2SongToCurrentProfile(@ModelAttribute SongUploadModel song, @RequestParam("file")MultipartFile picture, Principal principal) {
        System.out.println();
        try {
            profileService.uploadTop2Song(song, principal.getName(), picture);
        }
        catch (IOException e){
            //TODO Handle exception
        }
        return "redirect:/music/edit";
    }
    @PostMapping("/music/updateTop3Song")
    public String uploadTop3SongToCurrentProfile(@ModelAttribute SongUploadModel song, @RequestParam("file")MultipartFile picture, Principal principal) {
        System.out.println();
        try {
            profileService.uploadTop3Song(song, principal.getName(), picture);
        }
        catch (IOException e){
            //TODO Handle exception
        }
        return "redirect:/music/edit";
    }
    @PostMapping("/music/comment/add")
    public String addCommentToSong(@ModelAttribute AddCommentToSongModel comment, Principal principal){

        songService.addCommentToSong(principal.getName(), comment);

        return "redirect:/music/profile/"+comment.getUsername();
    }
    @GetMapping("/music/profile/{username}/suggestions")
    public String getSuggestions(@PathVariable String username, Principal principal, Model model){
        ProfileSongsDisplayModel profileModel = profileService.getProfileSongs(username);
        ProfileSongsDisplayModel currentProfileModel = profileService.getProfileSongs(principal.getName());
        model.addAttribute("displayProfile", profileModel);
        model.addAttribute("currentProfile", currentProfileModel);
        return "profile/otherProfile/songSuggestions";
    }
    @PostMapping("/music/profile/{username}/suggestions")
    public String addSuggestion(@PathVariable String username,@RequestParam("file") MultipartFile file, @ModelAttribute SongUploadModel suggestionModel){
        try {
            profileService.addSongSuggestionToProfile(username, suggestionModel, file);
        }
        catch (IOException e){
            //TODO : HANDLE exception
        }
        return "redirect:/music/profile/"+username+"/suggestions";
    }
}


