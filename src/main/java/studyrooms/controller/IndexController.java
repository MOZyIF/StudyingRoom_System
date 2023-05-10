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
import studyrooms.handle.CacheHandle;
import studyrooms.msg.R;
import studyrooms.service.UserService;

import java.util.UUID;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CacheHandle cacheHandle;

    @GetMapping("/info")
    @ResponseBody
    public R info(String token){

        Log.info("依据登陆标识获取用户信息");

        Users user = userService.getOne(cacheHandle.getUserInfoCache(token));

        return R.successData(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(String userName, String passWord){

        Log.info("用户登录，用户名：{}， 用户密码：{}", userName, passWord);

        Users user = userService.getUserByUserName(userName);

        if(user == null) {

            return R.warn("输入的用户名不存在");
        }else {

            if(passWord.equals(user.getPassWord().trim())) {

                if(user.getStatus() == SysCode.USER_STATUS_Y){

                    String token = UUID.randomUUID().toString().replaceAll("-","");

                    cacheHandle.addUserCache(token, user.getId());

                    return R.success("登录成功", token);
                }else{

                    return R.warn("账号异常，限制操作");
                }
            }else {

                return R.warn("输入的密码错误");
            }
        }
    }

    @PostMapping("/exit")
    @ResponseBody
    public R exit(String token) {

        Log.info("用户退出系统并移除登录信息");

        cacheHandle.removeUserCache(token);

        return R.success();
    }

    @PostMapping("/info")
    @ResponseBody
    public R updSessionInfo(Users user){

        Log.info("修改用户信息，{}", user);

        Users oldUser = userService.getOne(user.getId());

        if(oldUser.getUserName().equals(user.getUserName())){

            userService.update(user);

            return R.success();
        }else{

            if(userService.checkUserName(user.getUserName())){

                return R.warn("用户账号已存在，请重新输入");
            }else{

                userService.update(user);

                return R.success();
            }
        }
    }

    @PostMapping("/pwd")
    @ResponseBody
    public R updSessionPwd(String oldPwd, String newPwd, String rePwd, String token){

        Log.info("修改登陆用户密码");

        Users user = userService.getOne(cacheHandle.getUserInfoCache(token));

        if(oldPwd.equals(user.getPassWord())){

            if(newPwd.equals(rePwd)){

                user.setPassWord(newPwd);
                userService.update(user);

                return R.success();
            }else{

                return R.warn("两次输入密码不一致，请重新输入");
            }
        }else {

            return R.warn("原始密码输入错误，请重新输入");
        }
    }
}
