package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyrooms.comm.SysCode;
import studyrooms.entity.Users;
import studyrooms.msg.PageData;
import studyrooms.msg.R;
import studyrooms.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/page/student")
    @ResponseBody
    public R getPageStudentInfos(Long pageIndex, Long pageSize, String stuName, Integer collegeId, Integer gradeId) {

        Log.info("分页查找学生信息，当前页码：{}，"
                + "每页数据量：{}, 模糊查询，学生姓名：{}，学院编号：{}，班级编号：{}",
                            pageIndex, pageSize, stuName, collegeId, gradeId);

        PageData page = userService.getPageStudentInfos(pageIndex, pageSize, stuName, collegeId, gradeId);

        return R.successData(page);
    }

    @GetMapping("/page/manage")
    @ResponseBody
    public R getPageManageInfos(Long pageIndex, Long pageSize, String manName){

        Log.info("分页查找管理员信息，当前页码：{}，"
                        + "每页数据量：{}, 模糊查询，管理员姓名：{}", pageIndex, pageSize, manName);

        PageData page = userService.getPageManageInfos(pageIndex, pageSize, manName);

        return R.successData(page);
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(Users user){

        Log.info("添加管理员信息，传入参数：{}", user);

        if(userService.checkUserId(user.getId())){

            return R.warn("工号已存在，请重新输入");
        }

        if(userService.checkUserName(user.getUserName())){

            return R.warn("账号已存在，请重新输入");
        }

        user.setPassWord(user.getUserName());
        user.setStatus(SysCode.USER_STATUS_Y);
        user.setType(SysCode.USER_TYPE_MAN);
        userService.add(user);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(Users user){

        Log.info("删除管理员信息，传入参数：{}", user);

        userService.delete(user);
        return R.success();
    }
}
