package com.social.socialmediappli.exceptions;

public class PlatformNotFoundException extends RuntimeException{
    public PlatformNotFoundException(String msg){
        super(msg);
    }
}
