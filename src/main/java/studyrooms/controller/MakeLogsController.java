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
import studyrooms.entity.MakeLogs;
import studyrooms.msg.PageData;
import studyrooms.msg.R;
import studyrooms.service.MakeLogsService;
import studyrooms.utils.DateUtils;

@Controller
@RequestMapping("/makelogs")
public class MakeLogsController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(MakeLogsController.class);

    @Autowired
    private MakeLogsService makeLogsService;

    @GetMapping("/page")
    @ResponseBody
    public R getPageInfos(Long pageIndex, Long pageSize, String stuId, String stuName, String roomName) {

        Log.info("分页查找预约信息，当前页码：{}，"
                + "每页数据量：{}, 模糊查询，学生ID：{}，学生姓名：{}，自习室名称：{}", pageIndex, pageSize, stuId, stuName, roomName);

        PageData page = makeLogsService.getPageInfos(pageIndex, pageSize, stuId, stuName, roomName);

        return R.successData(page);
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(MakeLogs makeLog){

        Log.info("添加预约记录， 传入参数：{}", makeLog);

        if(makeLogsService.isMake(makeLog.getRoomId(), makeLog.getMakeTime())){

            if(makeLogsService.isMake(makeLog.getStudentId(), makeLog.getMakeTime())){

                makeLog.setCreateTime(DateUtils.getNowDate());
                makeLog.setStatus(SysCode.MAKE_STATUS_SUB);
                makeLogsService.add(makeLog);

                return R.success();
            }else{

                return R.warn("当日已有预约，无法提交");
            }
        }else{

            return R.warn("当日预约已达最大上限");
        }
    }

    @PostMapping("/start")
    @ResponseBody
    public R start(MakeLogs makeLog){

        Log.info("学生开始使用， 传入参数：{}", makeLog);

        makeLog.setStartTime(DateUtils.getNowDate());
        makeLog.setStatus(SysCode.MAKE_STATUS_USE);
        makeLogsService.update(makeLog);

        return R.success();
    }

    @PostMapping("/end")
    @ResponseBody
    public R end(MakeLogs makeLog){

        Log.info("学生结束使用， 传入参数：{}", makeLog);

        makeLog.setEndTime(DateUtils.getNowDate());
        makeLog.setStatus(SysCode.MAKE_STATUS_END);
        makeLogsService.update(makeLog);

        return R.success();
    }


    @PostMapping("/cancel")
    @ResponseBody
    public R cancel(MakeLogs makeLog){

        Log.info("学生爽约， 传入参数：{}", makeLog);

        makeLog.setStatus(SysCode.MAKE_STATUS_CANCLE);
        makeLogsService.update(makeLog);

        return R.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public R delete(MakeLogs makeLog){

        makeLogsService.delete(makeLog);

        return R.success();
    }
}
