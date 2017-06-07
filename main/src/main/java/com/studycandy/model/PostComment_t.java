package com.studycandy.model;

import java.util.Date;

/**
 * Created by Yxm on 2017/6/7.
 */
public class PostComment_t {
    private Integer id;

    private Integer postId;

    private String nickname;

    private String commentContent;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer likeNum;

    private Integer todayNum;

    private Integer unlikeNum;

    private Integer followId;

    public PostComment_t(Integer id, Integer postId, String nickname, String commentContent, Date gmtCreate, Date gmtModified, Integer likeNum, Integer todayNum, Integer unlikeNum, Integer followId) {
        this.id = id;
        this.postId = postId;
        this.nickname = nickname;
        this.commentContent = commentContent;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.likeNum = likeNum;
        this.todayNum = todayNum;
        this.unlikeNum = unlikeNum;
        this.followId = followId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(Integer todayNum) {
        this.todayNum = todayNum;
    }

    public Integer getUnlikeNum() {
        return unlikeNum;
    }

    public void setUnlikeNum(Integer unlikeNum) {
        this.unlikeNum = unlikeNum;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }
}
