package com.innowave_technologies.githubapi;

/**
 * Created by João MF on 11/05/2018.
 */

public class Profile {

    private String mUserName;
    private String mEmail;
    private String mAvatar;
    private String mFollowers;

    public Profile(String username, String email, String avatar, String followers){
        mUserName = username;
        mEmail = email;
        mAvatar = avatar;
        mFollowers = followers;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getFollowers() {
        return mFollowers;
    }

    public void setFollowers(String mFollowers) {
        this.mFollowers = mFollowers;
    }
}
