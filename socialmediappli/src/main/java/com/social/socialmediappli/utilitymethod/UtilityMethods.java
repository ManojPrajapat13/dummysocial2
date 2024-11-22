package com.social.socialmediappli.utilitymethod;
import com.social.socialmediappli.entity.Influencer;
import com.social.socialmediappli.entity.User;
import com.social.socialmediappli.repository.InfluencerRepo;
import com.social.socialmediappli.repository.PlatformRepo;
import com.social.socialmediappli.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UtilityMethods {

    private final UserRepo userRepo;
    private final PlatformRepo platformRepo;
    private final InfluencerRepo influencerRepo;

    // no. of follower increase by 1
    public int increaseCountFollower(int count){
        count++;
        return count;
    }

    // no. of follower decrease by 1
    public int decreaseCountFollower(int count){
        count--;
        return count;
    }

    //  check user present on specific platform or not
    public boolean checkUserPresent(Long userId ,String platformName) {
        return userRepo.findById(userId)
                .map(user -> platformRepo.getAllPlatFormByUserId(user.getUserId())
                        .stream()
                        .anyMatch(platform -> platform.getPlatformName().equals(platformName)))
                .orElse(false);
    }
    //  check influencer present on specific platform or not
    public boolean checkInfluencerPresent(Long influencerId, String platformName){
        return influencerRepo.findById(influencerId)
                .map(influencer -> platformRepo.getAllPlatFormByInfluencerId(influencer.getInfluencerId())
                        .stream()
                        .anyMatch(platform -> platform.getPlatformName().equals(platformName)))
                .orElse(false);
    }

    // check user already follow influencer on same platform
    public boolean checkUserAlreadyFollowInfluencerOnSpecificPlatform(Long userId, Long influencerId, String platformName){
        if (checkInfluencerPresent(influencerId, platformName) && checkUserPresent(userId , platformName)){
            // Retrieve User and Influencer entities
            Optional<User> userOptional = userRepo.findById(userId);
            Optional<Influencer> influencerOptional = influencerRepo.findById(influencerId);

            if (userOptional.isPresent() && influencerOptional.isPresent()) {
                User user = userOptional.get();
                Influencer influencer = influencerOptional.get();

                switch (platformName) {
                    case ("Facebook"):
                        return user.getInfluencerListFb().contains(influencer);
                    case ("Instagram"):
                        return user.getInfluencerListIg().contains(influencer);
                    case ("Twitter"):
                        return user.getInfluencerListTw().contains(influencer);
                    case ("Yahoo"):
                        return user.getInfluencerListYh().contains(influencer);
                    default:
                        return false;
                }
            }
        }
        return false;
    }
}
