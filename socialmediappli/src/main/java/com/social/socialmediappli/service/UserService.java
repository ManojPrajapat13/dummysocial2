package com.social.socialmediappli.service;
import com.social.socialmediappli.dto.UserDto;
import java.util.*;

public interface UserService {

    String registerUser(UserDto userDto);

    UserDto getOneUser(Long userId);

    List<UserDto> getAllUser();

    String deleteOneUser(Long userId, String platformName);

    String userFollowInfluencer(Long userId, Long influencerId , String platformName);

    String userUnfollowInfluencer(Long userId, Long influencerId , String platformName);











    // update user
//    public void updateUser()




}