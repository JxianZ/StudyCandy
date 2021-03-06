package com.studycandy.mapper;

import com.studycandy.model.Coach;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coach record);

    Coach selectByPrimaryKey(Integer id);

    List<Coach> selectAll();

    int updateByPrimaryKey(Coach record);

    List<Coach> selectByCoachId(Integer coachId);

    List<Coach> selectByUserId(Integer userId);

    int acceptCoach(Integer coachId);

    int solveCoach(Integer coachId);

    public int acCoach(@Param("id")Integer id, @Param("coachId")Integer coachId);
}