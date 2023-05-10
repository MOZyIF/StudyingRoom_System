package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyrooms.msg.R;
import studyrooms.service.MakeLogsService;
import studyrooms.service.StudentService;
import studyrooms.utils.DateUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private MakeLogsService makeLogsService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/seat/day/usevfree")
    @ResponseBody
    public R getUseVSFreeInDay(){

        Log.info("查询当日使用和空闲的座位比, 当日日期：{}", DateUtils.getNowDate());

        Map<String, Object> resl = makeLogsService.getUseVSNoUseInDay();

        return R.successData(resl);
    }

    @GetMapping("/seat/month/use")
    @ResponseBody
    public R getUseInMonth(){

        Log.info("查询本月每日使用的座位数，当日日期：{}", DateUtils.getNowDate());

        List<Map<String, Object>> resl = makeLogsService.getUseTotalInMonth();

        return R.successData(resl);
    }

    @GetMapping("/seat/month/make")
    @ResponseBody
    public R getMakeInMonth(){

        Log.info("查询本月每日预约的座位数，当日日期：{}", DateUtils.getNowDate());

        List<Map<String, Object>> resl = makeLogsService.getMakeTotalInMonth();

        return R.successData(resl);
    }

    @GetMapping("/student/normalvsblack")
    @ResponseBody
    public R getNormalVSBlack(){

        Log.info("查询目前正常和拉黑的学生人数比, 当日日期: {}", DateUtils.getNowDate());

        Map<String, Object> resl = studentService.getNormalVSBlack();

        return R.successData(resl);
    }

}
