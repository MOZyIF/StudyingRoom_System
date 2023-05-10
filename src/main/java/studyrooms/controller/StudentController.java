package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyrooms.comm.SysCode;
import studyrooms.entity.Students;
import studyrooms.entity.Users;
import studyrooms.msg.R;
import studyrooms.service.StudentService;
import studyrooms.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(Users user, Students student){

        Log.info("添加学生信息，传入学生账号：{}, 学生信息：{}", user, student);

        if(userService.checkUserId(user.getId())){

            return R.warn("学号已存在，请重新输入");
        }

        if(userService.checkUserName(user.getUserName())){

            return R.warn("账号已存在，请重新输入");
        }

        student.setId(user.getId());
        user.setPassWord(user.getUserName());
        user.setStatus(SysCode.USER_STATUS_Y);
        user.setType(SysCode.USER_TYPE_STU);

        studentService.addStudent(user, student);

        return R.success();
    }

    @PostMapping("/upd")
    @ResponseBody
    public R updInfo(Students student){

        Log.info("修改学生信息，传入参数：{}", student);

        studentService.update(student);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(Students student){

        Log.info("删除学生信息，传入参数：{}", student);

        studentService.delete(student);

        return R.success();
    }
}
