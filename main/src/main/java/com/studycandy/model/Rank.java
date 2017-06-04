package com.studycandy.model;

/**
 * Created by Yxm on 2017/6/4.
 */
public class Rank {
    private Integer userId;
    private String nickname;
    private String motto;
    private Integer points;
    private String schoolName;

    public Rank(){

    }
    public Rank(Integer userId, String nickname, String motto, Integer points, String schoolName) {
        this.userId = userId;
        this.nickname = nickname;
        this.motto = motto;
        this.points = points;
        this.schoolName = schoolName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
