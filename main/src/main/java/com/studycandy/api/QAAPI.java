package com.studycandy.api;

import com.studycandy.core.BaseController;
import com.studycandy.model.*;
import com.studycandy.service.QAService;
import com.studycandy.service.UserInfoService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Yxm on 2017/5/19.
 */
@Controller
@RequestMapping("/app/QAroom")
public class QAAPI extends BaseController {
    @Autowired
    private QAService qaService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    /*悬赏制*/
    /*
    * 问题 0:未解决 1:解决
    */
    //获取所有问题
    @RequestMapping(value = {"/questionAllList"}, method = GET)
    public String getAllQuestion(HttpServletRequest request, HttpServletResponse response){
        List<Question> l = qaService.getAllQuestion();
        Map m = new HashMap();
        m.put("Questionlist", l);
        m.put("UserList", userService.getUserByQuestion(l));
        return ajaxReturn(response, m, "所有问题", 0);
    }
    //获取用户提出的所有问题
    @RequestMapping(value = "/questionUserList/{userId}")
    public String getuserQuestion(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable("userId") Integer userId){
        try {
            List<Question> l = qaService.getQustionsByUserId(userId);
            String nickname = userService.getNicknameById(userId);
            Map m = new HashMap();
            m.put("QuestionUserList", l);
            m.put(userId, nickname);
            return ajaxReturn(response, m, nickname + "的所有问题", 0);
        }catch (Exception e){
            return ajaxReturn(response, null, "异常", -1);
        }
    }
    //获取问题详细界面
    @RequestMapping(value = "/questionView/{id}")
    public String questionView(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable("id") Integer id){
        Question question = qaService.getQuestion(id);
        if(question==null)
            return ajaxReturn(response, null, "问题不存在", 0);
        List<Answer> answerList = qaService.getAnswersByQuestionId(id);
        Map m = new HashMap();
        m.put("Question", question);
        m.put("Quester", userService.getNicknameById(question.getUserId()));
        m.put("AnswerList", answerList);
        m.put("AnswererList",userService.getNicknameByAnswer(answerList));
        return ajaxReturn(response, m, "问题界面", 0);
    }
    //添加问题
    @RequestMapping(value = "/questionAdd", method = POST)
    public String addQuestion(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam String title,
                              @RequestParam String content,
                              @RequestParam Integer reward,
                              @RequestParam String username,
                              @RequestParam String password){
        try{
        User user = userService.loginGetObj(username,password);
        if(user == null)throw new Exception("用户状态异常");
        Question entity = new Question();
        entity.setQuestionTitle(title);
        entity.setQuestionStatus(0);
        entity.setQuestionContent(content);
        entity.setGmtCreate(new Timestamp(new Date().getTime()));
        entity.setGmtModified(new Timestamp(new Date().getTime()));
        entity.setQuestionReward(reward);
            Integer u = user.getId();
            entity.setUserId(u);
            UserInfo userInfo = userInfoService.getByUserId(u);
            int integral = userInfo.getUserIntegral()-reward;
            if(integral<0) throw new Exception("糖豆不足");
            userInfoService.changeUserIntegral(u, integral);
            qaService.saveQuestion(entity);
            return ajaxReturn(response,null,"成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //删除问题
    @RequestMapping(value = "questionDelete", method = POST)
    public String deleteQuestion(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam Integer id){
        //判断是否是问题主人要进行删除
        try{
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            if(user.getId()==qaService.getQuestion(id).getUserId())
                qaService.deleteQuestion(id);
            return ajaxReturn(response,null,"成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //提交问题修改
    @RequestMapping(value = "/questionModify", method = POST)
    public String modifyPostQuestion(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String title,
                                     @RequestParam String content,
                                     @RequestParam Integer reward,
                                     @RequestParam Integer id) {
        //判断是否是问题主人修改
        try{
            User user = userService.loginGetObj(username,password);
            Question question = qaService.getQuestion(id);
            if(user == null||question.getUserId()!=user.getId())throw new Exception("用户状态异常");
            question.setQuestionContent(content);
            question.setQuestionTitle(title);
            question.setGmtModified(new Timestamp(new Date().getTime()));
            question.setQuestionReward(reward);
            UserInfo userInfo = userInfoService.getByUserId(user.getId());
            int integral = userInfo.getUserIntegral()-reward;
            if(integral<0) throw new Exception("糖豆不足");
            qaService.modifyQuestion(question);
            //修改完之后返回问题的id
            return ajaxReturn(response,question.getId(),"修改成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //回答问题
    @RequestMapping(value = "/addAnswer", method = POST)
    public String addAnswer(HttpServletRequest request, HttpServletResponse response, Model model,
                            @RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String answerContent,
                            @RequestParam Integer questionId){
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Answer answer = new Answer();
            answer.setAnswerContent(answerContent);
            answer.setGmtCreate(new Timestamp(new Date().getTime()));
            answer.setGmtModified(new Timestamp(new Date().getTime()));
            answer.setQuestionId(questionId);
            answer.setUserId(user.getId());
            qaService.saveAnswer(answer);
            return ajaxReturn(response,null,"回答成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //选取最佳答案
    @RequestMapping(value = "questionSetBestAnswer/{questionId}", method = POST)
    public String setBestAnswer(HttpServletRequest request, HttpServletResponse response, Model model,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam Integer answerId,
                                @PathVariable("questionId") Integer questionId){
        try {
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Question question = qaService.getQuestion(questionId);
            if(question == null) throw new Exception("问题不存在");
            if(question.getUserId()==user.getId()) {
                qaService.setBestAnswer(questionId, answerId);
                UserInfo u = userInfoService.getByUserId(qaService.getAnswer(answerId).getUserId());
                userInfoService.changeUserIntegral(u.getId(),u.getUserIntegral()+question.getQuestionReward());
            }
            else throw new Exception("用户状态异常");
            return ajaxReturn(response,null,"成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }

    /*预约制*/
    /*
    * 预约状态 CoachStatus -1:未接受 0:已接受 1:已解决
    * 预约模式 CoachMode 0:线上 1:线下
    */
    //获取所有问题
    @RequestMapping(value = "/orderAllList", method = GET)
    public String getAllOrder(HttpServletRequest request, HttpServletResponse response, Model model){
        return ajaxReturn(response, qaService.getAllCoach(), "所有预约", 0);
    }
    //获取用户提出的所有预约
    @RequestMapping(value = "/orderUserList/{userId}")
    public String getUserOrder(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable("userId") Integer userId){
        try {
            List<Coach> l = qaService.getCoachesByUserId(userId);
            String nickname = userService.getNicknameById(userId);
            Map m = new HashMap();
            m.put("OrderUserList", l);
            m.put(userId, nickname);
            return ajaxReturn(response, m, nickname + "的所有预约", 0);
        }catch (Exception e){
            return ajaxReturn(response, null, "异常", -1);
        }
    }
    //获取预约详细界面
    @RequestMapping(value = "/orderView/{id}")
    public String coachView(HttpServletRequest request, HttpServletResponse response, Model model,
                            @PathVariable("id") Integer id){
        Coach coach = qaService.getCoach(id);
        if(coach==null)
            return ajaxReturn(response, null, "预约不存在", 0);
        List<Answer> answerList = qaService.getAnswersByQuestionId(id);
        Map m = new HashMap();
        m.put("Coach",coach);
        m.put("User", userService.getNicknameById(coach.getUserId()));
        m.put("Coacher", userService.getNicknameById(coach.getCoachId()));
        return ajaxReturn(response, m, id+"信息", 0);
    }
    //添加预约
    @RequestMapping(value = "/orderAdd", method = POST)
    public String addCoach(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String content,
                           @RequestParam Integer reward,
                           @RequestParam Integer mode){
        try{
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Coach entity = new Coach();
            entity.setCoachStatus(-1);
            entity.setCoachMode(mode);
            entity.setCoachContent(content);
            entity.setCoachReward(reward);
            entity.setUserId(user.getId());
            UserInfo userInfo = userInfoService.getByUserId(user.getId());
            int integral = userInfo.getUserIntegral()-reward;
            if(integral<0) throw new Exception("糖豆不足");
            qaService.saveCoach(entity);
            return ajaxReturn(response,null,"添加预约成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //删除预约
    @RequestMapping(value = "orderDelete", method = POST)
    public String deleteCoach(HttpServletRequest request, HttpServletResponse response, Model model,
                              @RequestParam String username,
                              @RequestParam String password,
                              @RequestParam Integer id){
        //判断是否是预约主人要进行删除
        try{
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            if(user.getId()==qaService.getCoach(id).getUserId())
                qaService.deleteCoach(id);
            return ajaxReturn(response,null,"删除预约成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //提交预约修改
    @RequestMapping(value = "/orderModify", method = POST)
    public String modifyPostCoach(HttpServletRequest request, HttpServletResponse response, Model model,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String content,
                                  @RequestParam Integer reward,
                                  @RequestParam Integer mode,
                                  @RequestParam Integer id) {
        //判断是否是问题主人修改
        try{
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            Coach entity = qaService.getCoach(id);
            entity.setCoachMode(mode);
            entity.setCoachContent(content);
            entity.setCoachReward(reward);
            UserInfo userInfo = userInfoService.getByUserId(user.getId());
            int integral = userInfo.getUserIntegral()-reward;
            if(integral<0) throw new Exception("糖豆不足");
            qaService.saveCoach(entity);
            //修改完之后返回问题的id
            return ajaxReturn(response,entity.getId(),"修改成功",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }
    //接受预约
    @RequestMapping(value = "/orderAccept/{id}", method = POST)
    public String acceptCoach(HttpServletRequest request, HttpServletResponse response, Model model,
                              @RequestParam String username,
                              @RequestParam String password,
                              @PathVariable("id")Integer id) {
        try {
            Coach coach = qaService.getCoach(id);
            if(coach == null) throw new Exception("预约不存在");
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            if(coach.getUserId() == user.getId())throw new Exception("不能接受自己的预约");
            qaService.acCoach(id, coach.getUserId());
            return ajaxReturn(response, null, coach.getUserId()+"接受了"+id, 0);
        }
        catch (Exception e){
            return ajaxReturn(response, null,e.getMessage(),-1);
        }
    }
    //预约解决
    @RequestMapping(value = "/orderSolve", method = POST)
    public String solveCoach(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam Integer id){
        try{
            Coach coach = qaService.getCoach(id);
            if(coach == null) throw new Exception("预约不存在");
            User user = userService.loginGetObj(username,password);
            if(user == null)throw new Exception("用户状态异常");
            if(coach.getUserId() != user.getId())throw new Exception("用户状态异常");
            qaService.solveCoach(id);
            return ajaxReturn(response,id,"预约结束",0);
        }catch (Exception e){
            return ajaxReturn(response,null,e.getMessage(),-1);
        }
    }

}
