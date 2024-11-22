package com.social.socialmediappli.service.serviceimpl;

import com.social.socialmediappli.dto.UserDto;
import com.social.socialmediappli.entity.Influencer;
import com.social.socialmediappli.entity.PlatForm;
import com.social.socialmediappli.entity.User;
import com.social.socialmediappli.exceptions.PlatformNotFoundException;
import com.social.socialmediappli.exceptions.UserNotFoundException;
import com.social.socialmediappli.repository.InfluencerRepo;
import com.social.socialmediappli.repository.PlatformRepo;
import com.social.socialmediappli.repository.UserRepo;
import com.social.socialmediappli.service.UserService;
import com.social.socialmediappli.utilitymethod.UtilityMethods;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PlatformRepo platformRepo;
    private final InfluencerRepo influencerRepo;
    private final ModelMapper modelMapper;
    private final UtilityMethods utilityMethods;

    // for user registration
    @Override
    public String registerUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);

        Set<PlatForm> platForms = new HashSet<>();
        for(PlatForm p : user.getPlatFormNames()){
            PlatForm existPlatform = platformRepo.findByPlatformName(p.getPlatformName());
            if(existPlatform != null){
                platForms.add(existPlatform);
            }else{
                platForms.add(p);
            }
        }
        user.setPlatFormNames(platForms);
        userRepo.save(user);
        return "user register successfully..";
    }

    // get one user
    @Override
    public UserDto getOneUser(Long userId){
        return userRepo.findById(userId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

    }

    // get all user
    @Override
    public List<UserDto> getAllUser(){
        List<User> userList = (List<User>) userRepo.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for(User user : userList ){
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    // delete one user
    @Override
    public String deleteOneUser(Long userId, String platformName){
        User user = userRepo.findByUserId(userId);
        PlatForm platForm = platformRepo.findByPlatformName(platformName);
        if(utilityMethods.checkUserPresent(userId, platformName)){
            user.getPlatFormNames().remove(platForm);
            platForm.getUsers().remove(user);
            userRepo.save(user);
            return "User successfully remove from the platform " + platformName;
        }else{
            throw new UserNotFoundException("user is not present on this platform....");
        }
    }

    // user follow influencer
    @Override
    public String userFollowInfluencer(Long userId, Long influencerId , String platformName){
        if (utilityMethods.checkUserAlreadyFollowInfluencerOnSpecificPlatform(userId, influencerId, platformName)){
            return "User Already Follow Influencer On Specific Platform...";
        }
        if(utilityMethods.checkUserPresent(userId , platformName) && utilityMethods.checkInfluencerPresent(influencerId , platformName)) {
            Influencer influencer = influencerRepo.findByInfluencerId(influencerId);
            User user = userRepo.findByUserId(userId);
            // Increment the follower count based on the platform name
            switch (platformName) {
                case ("Facebook"):
                    influencer.setFacebookFollower(utilityMethods.increaseCountFollower(influencer.getFacebookFollower()));
                    influencer.getUserListFb().add(user);
                    user.getInfluencerListFb().add(influencer);
                    break;
                case ("Instagram"):
                    influencer.setInstagramFollower(utilityMethods.increaseCountFollower(influencer.getInstagramFollower()));
                    influencer.getUserListIg().add(user);
                    user.getInfluencerListIg().add(influencer);
                    break;
                case ("Twitter"):
                    influencer.setTwitterFollower(utilityMethods.increaseCountFollower(influencer.getTwitterFollower()));
                    influencer.getUserListTw().add(user);
                    user.getInfluencerListTw().add(influencer);
                    break;
                case ("Yahoo"):
                    influencer.setYahooFollower(utilityMethods.increaseCountFollower(influencer.getYahooFollower()));
                    influencer.getUserListYh().add(user);
                    user.getInfluencerListYh().add(influencer);
                    break;
                default:
                    throw new PlatformNotFoundException("platform is not found...");
            }
            userRepo.save(user);
        } else {
            return  "user or influencer is not present on specific platform...";
        }
        return "follow successfully...";
    }

    // unfollow
    @Override
    public String userUnfollowInfluencer(Long userId, Long influencerId, String platformName){
        Influencer influencer = influencerRepo.findByInfluencerId(influencerId);
        User user = userRepo.findByUserId(userId);

        if (utilityMethods.checkUserAlreadyFollowInfluencerOnSpecificPlatform(userId, influencerId, platformName)){

            switch (platformName) {
                case ("Facebook"):
                    influencer.setFacebookFollower(utilityMethods.decreaseCountFollower(influencer.getFacebookFollower()));
                    user.getInfluencerListFb().remove(influencer);
                    influencer.getUserListFb().remove(user);
                    break;
                case ("Instagram"):
                    influencer.setInstagramFollower(utilityMethods.decreaseCountFollower(influencer.getInstagramFollower()));
                    user.getInfluencerListIg().remove(influencer);
                    influencer.getUserListIg().remove(user);
                    break;
                case ("Twitter"):
                    influencer.setTwitterFollower(utilityMethods.decreaseCountFollower(influencer.getTwitterFollower()));
                    user.getInfluencerListTw().remove(influencer);
                    influencer.getUserListTw().remove(user);
                    break;
                case ("Yahoo"):
                    influencer.setYahooFollower(utilityMethods.decreaseCountFollower(influencer.getYahooFollower()));
                    user.getInfluencerListYh().remove(influencer);
                    influencer.getUserListYh().remove(user);
                    break;
                default:
                    throw new PlatformNotFoundException("platform is not found...");
            }
            userRepo.save(user);
            influencerRepo.save(influencer);
            return  "unfollow successfully...";
        }else{
            return "User not follow Influencer On Specific Platform...";
        }
    }
}
