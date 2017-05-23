package com.studycandy.api;

import com.studycandy.core.BaseController;
import com.studycandy.model.CommentPost;
import com.studycandy.model.Post;
import com.studycandy.model.User;
import com.studycandy.service.CommentPostService;
import com.studycandy.service.PostService;
import com.studycandy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Yxm on 2017/5/23.
 */
@RequestMapping("/app/square")
@Controller
public class SquareAPI extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentPostService commentPostService;

    @RequestMapping(value = "/postDayAllList", method = GET)
    public String squareDay(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Post> l = postService.getAllDayPost();
        Map<Integer,String> m = new HashMap<Integer, String>();
        String nickname="";
        for(Post p : l){
            if(m.containsKey(p.getUserId())) {
                if (userService.getUserById(p.getUserId()) != null)
                    nickname = userService.getUserById(p.getUserId()).getUserNickname();
                else
                    nickname = "null";
                m.put(p.getUserId(), nickname);
            }
        }
        Map map = new HashMap();
        map.put("AllDayPostList", l);
        map.put("NicknameList", m);
        return ajaxReturn(response, map, "广场白天所有帖子", 0 );
    }
    //进入黑夜
    @RequestMapping(value = "/postNightAllList", method = GET)
    public String squareNight(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Post> l = postService.getAllNightPost();
        return ajaxReturn(response, l, "广场夜晚所有帖子", 0);
    }
    //白天发帖
    @RequestMapping(value = "/addDayPost", method = POST)
    public String addDayPost(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam String title,
                             @RequestParam String content,
                             @RequestParam String username,
                             @RequestParam String password) {
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Post entity = new Post();
            entity.setPostTitle(title);
            entity.setPostContent(content);
            long date = new Date().getTime();
            entity.setGmtCreate(new Timestamp(date));
            entity.setGmtModified(new Timestamp(date));
            entity.setIsNight(0);
            entity.setUserId(user.getId());
            postService.save(entity);
        } catch (Exception e) {
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
        return ajaxReturn(response, null, "发帖成功！", 0);
    }
    //黑夜发帖
    @RequestMapping(value = "/addNightPost", method = POST)
    public String addNightPost(HttpServletRequest request, HttpServletResponse response, Model model,
                               @RequestParam String title,
                               @RequestParam String content,
                               @RequestParam String username,
                               @RequestParam String password) {
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Post entity = new Post();
            entity.setPostTitle(title);
            entity.setPostContent(content);
            long date = new Date().getTime();
            entity.setGmtCreate(new Timestamp(date));
            entity.setGmtModified(new Timestamp(date));
            entity.setIsNight(1);
            entity.setUserId(user.getId());
            postService.save(entity);
        } catch (Exception e) {
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
        return ajaxReturn(response, null, "发帖成功！", 0);
    }
    //取出白天帖子最新
    @RequestMapping(value = "/postDayLatestList", method = GET)
    public String postDayLatest(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Post> l= postService.getLatestDayPost(10);
        Map<Integer,String> m = new HashMap<Integer, String>();
        String nickname="";
        for(Post p : l){
            if(m.containsKey(p.getUserId())) {
                if (userService.getUserById(p.getUserId()) != null)
                    nickname = userService.getUserById(p.getUserId()).getUserNickname();
                else
                    nickname = "null";
                m.put(p.getUserId(), nickname);
            }
        }
        Map map = new HashMap();
        map.put("LatestDayPostList", l);
        map.put("NicknameList", m);
        return ajaxReturn(response, map, "", 0);
    }
    //取出晚上帖子最新
    @RequestMapping(value = "/postNightLatestList", method = GET)
    public String postNightLatest(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Post> l= postService.getLatestNightPost(10);
        return ajaxReturn(response, l, "", 0);
    }
    //TODO 继续完善API
    //获取用户发布的所有帖子
    @RequestMapping(value = "/alluserpost",  method = POST)
    public String postList(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            List<Post> l = postService.getUserPostList(this.getCurrentUser(request).getId());
            System.out.println(l);
            return ajaxReturn(response, l, "成功", 0);
        } catch (Exception e) {
            return ajaxReturn(response, null, "失败", 0);
        }
    }
    //删除帖子
    @RequestMapping(value = "/deletePost", method = POST)
    public String deletePost(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam Integer id) {
        //判断是否是帖子主人删除
        if(this.getCurrentUser(request).getId()==postService.getPostById(id).getUserId())
            postService.deleteById(id);
        return "square/campusSquare";
    }
    //获取单个帖子详细界面
    @RequestMapping(value = "/postview/{id}")
    public String getPost(HttpServletRequest request, HttpServletResponse response, Model model,
                          @PathVariable("id") Integer id) {
        List<CommentPost> l = commentPostService.getCommentPostListByPostId(id);
        Map<Integer, String> m = new HashMap<Integer, String>();
        for(CommentPost p : l) {
            m.put(p.getUserId(),userService.getUserById(p.getUserId()).getUserNickname());
        }
        model.addAttribute("postComments",l);
        model.addAttribute("postCommentsUserName",m);
        model.addAttribute("post",postService.getPostById(id));
        model.addAttribute("user",userService.getUserById(postService.getPostById(id).getUserId()));
        return "square/post";
    }
    //进入修改帖子界面
    @RequestMapping(value = "/modify/{id}", method = POST)
    public String modify(HttpServletRequest request, HttpServletResponse response, Model model,
                         @PathVariable("id") Integer id) {
        Post t = postService.getPostById(id);
        //判断是否是帖子主人要进行修改
        if(this.getCurrentUser(request).getId()==t.getUserId()){
            t.setGmtModified(new Timestamp(new Date().getTime()));
            model.addAttribute("post",t);
            return "modifypostview";
        }else{
            return "campusSquare";
        }
    }
    //提交帖子修改
    @RequestMapping(value = "/postmodify", method = POST)
    public String modifyPost(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam Post post) {
        //判断是否是帖子主人修改
        if(this.getCurrentUser(request).getId()==post.getUserId())
            postService.modifyPost(post);
        model.addAttribute("post",post);
        //修改完之后转到帖子详细界面
        return "redirect:square/postview/"+post.getId();
    }
    /*评论功能 Start*/
    //获取帖子的所有回复
    @RequestMapping(value = "/postcomments/{postId}")
    public String postComments(HttpServletRequest request, HttpServletResponse response, Model model,
                               @PathVariable("postId") Integer id){
        List<CommentPost> l = commentPostService.getCommentPostListByPostId(id);
        Map<Integer, String> m = new HashMap<Integer, String>();
        for(CommentPost p :l) {
            m.put(p.getId(),userService.getUserById(p.getUserId()).getUserNickname());
        }
        model.addAttribute("postComments",l);
        model.addAttribute("postCommentsUserName",m);
        return "postcomments";
    }
    //添加回复
    @RequestMapping(value = "/addcomment",method = POST)
    public String addcomment(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam Integer postId,
                             @RequestParam String commentContent){
        CommentPost entity = new CommentPost();
        entity.setPostId(postId);
        entity.setFollowId(-1);
        entity.setGmtCreate(new Timestamp(new Date().getTime()));
        entity.setGmtModified(new Timestamp(new Date().getTime()));
        entity.setCommentContent(commentContent);
        try {
            entity.setUserId(this.getCurrentUser(request).getId());
            commentPostService.saveCommentPost(entity);
        }catch (Exception e){
            return ajaxReturn(response,null,"失败",-1);
        }
        return ajaxReturn(response,null,"成功",0);
    }
    //添加回复的回复
    @RequestMapping(value = "/addCommentToComment",method = POST)
    public String addCommentToComment(HttpServletRequest request, HttpServletResponse response, Model model,
                                      @RequestParam Integer postId,
                                      @RequestParam String commentContent,
                                      @RequestParam Integer followId){
        CommentPost entity = new CommentPost();
        entity.setPostId(postId);
        entity.setFollowId(followId);
        try {
            entity.setUserId(this.getCurrentUser(request).getId());
        }catch (Exception e){

        }
        entity.setGmtCreate(new Timestamp(new Date().getTime()));
        entity.setGmtModified(new Timestamp(new Date().getTime()));
        entity.setCommentContent(commentContent);
        commentPostService.saveCommentPost(entity);
        return "postcomments";
    }
    //删除回复
    @RequestMapping(value = "deletecomment",method = POST)
    public String deleteComment(HttpServletRequest request, HttpServletResponse response, Model model,
                                @RequestParam Integer commentId){
        CommentPost t = commentPostService.getCommentPost(commentId);
        //判断是否是评论主人要进行修改
        if(this.getCurrentUser(request).getId()==t.getUserId())
            commentPostService.deleteCommentPost(commentId);
        return "postcomments";
    }
    /*评论功能 End*/
}

