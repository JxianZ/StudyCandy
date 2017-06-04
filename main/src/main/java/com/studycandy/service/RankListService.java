package com.studycandy.service;

import com.studycandy.mapper.UserInfoMapper;
import com.studycandy.model.UserInfo;
import com.studycandy.service.Tools.ComparatorRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Yxm on 2017/4/9.
 */
@Service
public class RankListService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public List<UserInfo> getRankList(){
        List<UserInfo> l = userInfoMapper.selectByIntegral();
        Collections.sort(l, new ComparatorRank());
        for(UserInfo u : l){
            System.out.println(u.getId());
        }
        return l;
    }
}
