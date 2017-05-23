package com.studycandy.api;

import com.studycandy.core.BaseController;
import com.studycandy.model.Classroom;
import com.studycandy.service.ClassRoomService;
import com.studycandy.service.CourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yxm on 2017/5/23.
 */
@Controller
@RequestMapping("/app/classroom")
public class ClassRoomAPI extends BaseController {
    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private CourseService courseService;

    //大教室临时入口,返回界面
    @RequestMapping(value = {"", "/", "allclassroom"})
    public String classList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //推荐课表 TODO 获取推荐课程列表
        List<Classroom> l = new ArrayList<Classroom>();
        l = classRoomService.getListAllClassroom();
        return ajaxReturn(response, l, "所有教室信息", 0);
    }

    //校区所有的教室列表
    @RequestMapping(value = {"/{schoolId}"})
    public String classList(HttpServletRequest request, HttpServletResponse response, Model model,
                            @PathVariable Integer schoolId) {
        //TODO 按校区获取教室
        return ajaxReturn(response, classRoomService.getClassRoomsBySchoolId(schoolId), "校区教室信息", 0);
    }
    //
    @RequestMapping(value = "/class")
    public String classView(HttpServletRequest request, HttpServletResponse response, Model model,
                            @RequestParam Integer classId){
        return ajaxReturn(response, courseService.getCourseByClassId(classId), "课程信息", 0);
    }
    @RequestMapping(value = {"/class/course"})
    public String toCourse(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam Integer courseId) {
        return ajaxReturn(response, courseService.getCourseById(courseId));
    }
}
