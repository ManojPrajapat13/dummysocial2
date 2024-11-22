package com.social.socialmediappli.controller;

import com.social.socialmediappli.dto.UserDto;
import com.social.socialmediappli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socialmedia/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @GetMapping("/getOneUser/{userId}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable Long userId){
        UserDto userDto = userService.getOneUser(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getAllUser")
    public List<UserDto> getAllUser(){
        return userService.getAllUser();
    }

    @DeleteMapping("/deleteOneUser/{userId}/{platformName}")
    public String deleteOneUser(@PathVariable Long userId, @PathVariable String platformName){
        return userService.deleteOneUser(userId , platformName );
    }

    @PostMapping("/followInfluencer/{userId}/{influencerId}/{platformName}")
    public String userFollowInfluencer(@PathVariable Long userId, @PathVariable Long influencerId, @PathVariable String platformName){
        return userService.userFollowInfluencer(userId,influencerId,platformName);
    }

    @Transactional
    @PostMapping("/unfollowInfluencer/{userId}/{influencerId}/{platformName}")
    public String userUnfollowInfluencer(@PathVariable Long userId,@PathVariable Long influencerId ,@PathVariable String platformName){
        return userService.userUnfollowInfluencer(userId,influencerId,platformName);
    }
}
