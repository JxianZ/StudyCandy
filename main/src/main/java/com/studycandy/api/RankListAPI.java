package com.studycandy.api;

import com.studycandy.core.BaseController;
import com.studycandy.model.Rank;
import com.studycandy.model.User;
import com.studycandy.model.UserInfo;
import com.studycandy.service.RankListService;
import com.studycandy.service.SchoolService;
import com.studycandy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/6/4.
 */
@Controller
@RequestMapping(value = "/app/rankList")
public class RankListAPI extends BaseController {
    @Autowired
    RankListService rankListService;
    @Autowired
    UserService userService;
    @Autowired
    SchoolService schoolService;

    @RequestMapping(value = {"","/"})
    public String rankList(HttpServletRequest request, HttpServletResponse response, Model model){
        List<UserInfo> l = rankListService.getRankList();
        Map<Integer,User> userList = userService.getUserByUserInfo(l);
        Map<Integer,String> schoolMap = schoolService.getSchoolMap();
        ArrayList list= new ArrayList<Rank>();
        for(UserInfo u : l){
            Rank r = new Rank(u.getId(), userList.get(u.getId()).getUserNickname(),
                    u.getUserMotto(), u.getUserMoney()*10+u.getUserIntegral(), schoolMap.get(u.getUserSchoolId()));
            list.add(r);
        }
        return ajaxReturn(response, list, "排名信息", 0);
    }

}