package com.social.socialmediappli.controller;

import com.social.socialmediappli.dto.InfluencerDto;
import com.social.socialmediappli.entity.User;
import com.social.socialmediappli.service.InfluencerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/socialmedia/influencer")
@RequiredArgsConstructor
public class InfluencerController {

    private final InfluencerService influencerService;

    @PostMapping("/register")
    public String registerUser(@RequestBody InfluencerDto influencerDto){
        return influencerService.registerInfluencer(influencerDto);
    }

    @GetMapping("/getOneInfluencer/{influencerId}")
    public ResponseEntity<InfluencerDto> getOneInfluencer(@PathVariable Long influencerId){
        InfluencerDto influencerDto = influencerService.getOneInfluencer(influencerId);
        return ResponseEntity.ok(influencerDto);
    }

    @GetMapping("/getAllInfluencer")
    public List<InfluencerDto> getAllInfluencerRepo(){
        return influencerService.getAllInfluencer();
    }

    @DeleteMapping("/deleteOneInfluencer/{influencerId}/{platformName}")
    public String deleteOneInfluencer(@PathVariable Long influencerId ,@PathVariable String platformName){
        return influencerService.deleteOneInfluencer(influencerId, platformName);
    }

    @GetMapping("/getAllFollower/{influencerId}/{platformName}")
    public Set<User> getAllFollowerOfInfluencerOnSpecificPlatform(@PathVariable Long influencerId, @PathVariable String platformName){
        return influencerService.getAllFollowerOfInfluencerOnSpecificPlatform(influencerId, platformName);
    }
}
