package com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel;


public class UserData {
    public static final int USER_STATE_VERIFIED = 1;
    public static final int USER_STATE_UNVERIFIED = 0;

    public String id;
    public String name;
    public String email;
    public String p_number;
    public int verified; // 0 = unverified, 1 = verified
    public String token;
    public String created_at;
    public String updated_at;
    public String profile_pic;

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public static int getUserStateVerified() {
        return USER_STATE_VERIFIED;
    }

    public static int getUserStateUnverified() {
        return USER_STATE_UNVERIFIED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getP_number() {
        return p_number;
    }

    public void setP_number(String p_number) {
        this.p_number = p_number;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

