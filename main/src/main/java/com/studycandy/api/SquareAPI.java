package com.studycandy.api;

import com.studycandy.core.BaseController;
import com.studycandy.model.CommentPost;
import com.studycandy.model.Post;
import com.studycandy.model.PostComment_t;
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
    //获取用户发布的所有帖子
    @RequestMapping(value = "/postUserAll",  method = POST)
    public String postList(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam String username,
                           @RequestParam String password) {
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            List<Post> l = postService.getUserPostList(user.getId());
            return ajaxReturn(response, l, "用户"+user.getId()+"所有帖子", 0);
        } catch (Exception e) {
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
    }
    //删除帖子
    @RequestMapping(value = "/deletePost", method = POST)
    public String deletePost(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam Integer id) {
        //判断是否是帖子主人删除
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            if(user.getId() == postService.getPostById(id).getUserId())
                postService.deleteById(id);
            else
                throw new Exception("删除异常");
            return ajaxReturn(response, "success", "帖子删除成功", 0);
        } catch (Exception e) {
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
    }
    //获取单个帖子详细界面
    @RequestMapping(value = "/postView/{id}")
    public String getPost(HttpServletRequest request, HttpServletResponse response, Model model,
                          @PathVariable("id") Integer id) {
        try {
            List<CommentPost> l = commentPostService.getCommentPostListByPostId(id);
            Post post = postService.getPostById(id);
            if(post == null) throw new Exception("帖子不存在");
            Map<Integer, String> m = new HashMap<Integer, String>();
            for (CommentPost p : l) {
                m.put(p.getUserId(), userService.getUserById(p.getUserId()).getUserNickname());
            }
            Map map = new HashMap();
            map.put("Post", post);
            map.put("User", userService.getNicknameById(postService.getPostById(id).getUserId()));
            map.put("Comments", l);
            map.put("Commenters", m);
            return ajaxReturn(response, map, id + "帖子信息", 0);
        }catch(Exception e){
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
    }
    //进入修改帖子界面
    @RequestMapping(value = "/modifyPost/{id}", method = POST)
    public String modify(HttpServletRequest request, HttpServletResponse response, Model model,
                         @PathVariable("id") Integer id,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam String username,
                         @RequestParam String password) {
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Post entity = postService.getPostById(id);
            if(entity == null) throw new Exception("帖子不存在");
            entity.setPostTitle(title);
            entity.setPostContent(content);
            long date = new Date().getTime();
            entity.setGmtModified(new Timestamp(date));
            entity.setUserId(user.getId());
            postService.modifyPost(entity);
        } catch (Exception e) {
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
        return ajaxReturn(response, null, "修改成功！", 0);
    }
    /*评论功能 Start*/
    //获取帖子的所有回复
    @RequestMapping(value = "/postCommentAllList/{postId}")
    public String postComments(HttpServletRequest request, HttpServletResponse response, Model model,
                               @PathVariable("postId") Integer id){
        try {
            List<CommentPost> l = commentPostService.getCommentPostListByPostId(id);
            if(l == null) throw new Exception("帖子不存在或没有评论");
            List<PostComment_t> list = new ArrayList<>();
            for (CommentPost p : l) {
                PostComment_t c = new PostComment_t(p.getId(), p.getPostId(),userService.getNicknameById(p.getUserId()), p.getCommentContent(), p.getGmtCreate(), p.getGmtModified(),p.getLikeNum(), p.getTodayNum(), p.getUnlikeNum(), p.getFollowId());
                list.add(c);
            }
            return ajaxReturn(response, list, id+"帖子回帖", 0);
        }catch (Exception e){
            return ajaxReturn(response, null, e.getMessage(), -1);
        }
    }
    //添加回复
    @RequestMapping(value = "/addPostComment",method = POST)
    public String addcomment(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam Integer postId,
                             @RequestParam String commentContent,
                             @RequestParam String username,
                             @RequestParam String password){
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Post post = postService.getPostById(postId);
            if(post == null) throw new Exception("帖子不存在");
            CommentPost entity = new CommentPost();
            entity.setPostId(postId);
            entity.setFollowId(-1);
            long date = new Date().getTime();
            entity.setGmtCreate(new Timestamp(date));
            entity.setGmtModified(new Timestamp(date));
            entity.setCommentContent(commentContent);
            entity.setUserId(user.getId());
            commentPostService.saveCommentPost(entity);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
        return ajaxReturn(response,null,"回复成功",0);
    }
    //添加回复的回复
    @RequestMapping(value = "/addCommentToComment",method = POST)
    public String addCommentToComment(HttpServletRequest request, HttpServletResponse response, Model model,
                                      @RequestParam Integer postId,
                                      @RequestParam String commentContent,
                                      @RequestParam Integer followId,
                                      @RequestParam String username,
                                      @RequestParam String password){
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Post post = postService.getPostById(postId);
            if(post == null) throw new Exception("帖子不存在");
            CommentPost entity = new CommentPost();
            entity.setPostId(postId);
            entity.setFollowId(followId);
            long date = new Date().getTime();
            entity.setGmtCreate(new Timestamp(date));
            entity.setGmtModified(new Timestamp(date));
            entity.setCommentContent(commentContent);
            entity.setUserId(user.getId());
            commentPostService.saveCommentPost(entity);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
        return ajaxReturn(response,null,"回复成功",0);
    }
    //删除回复
    @RequestMapping(value = "deletecomment",method = POST)
    public String deleteComment(HttpServletRequest request, HttpServletResponse response, Model model,
                                @RequestParam Integer commentId,
                                @RequestParam String username,
                                @RequestParam String password){
        try {
            User user = userService.loginGetObj(username, password);
            if (user == null) throw new Exception("用户状态异常");
            CommentPost t = commentPostService.getCommentPost(commentId);
            if (t == null) throw new Exception("评论不存在");
            //判断是否是评论主人要进行修改
            if (user.getId() == t.getUserId())
                commentPostService.deleteCommentPost(commentId);
            else
                throw new Exception("删除失败");
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
        return ajaxReturn(response,null,"回复成功",0);
    }
    /*评论功能 End*/
}

