package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyrooms.entity.BlackLogs;
import studyrooms.msg.PageData;
import studyrooms.msg.R;
import studyrooms.service.BlackLogService;
import studyrooms.utils.DateUtils;

import java.util.List;

@Controller
@RequestMapping("/blacklogs")
public class BlackLogsController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(BlackLogsController.class);

    @Autowired
    private BlackLogService blackLogService;

    @GetMapping("/page")
    @ResponseBody
    public R getBlackLogsByStuId(Long pageIndex, Long pageSize, String stuId){

        Log.info("分页获取指定学生相关的拉黑记录， 当前页码：{}，每页数据量：{}, 学生ID：{}", pageIndex, pageSize, stuId);

        PageData page = blackLogService.getBlackLogsByStuId(pageIndex, pageSize, stuId);

        return R.successData(page);
    }

    @PostMapping("/into")
    @ResponseBody
    public R intoBlack(BlackLogs blackLogs){

        Log.info("添加黑名单：传入参数：{}", blackLogs);

        blackLogs.setIntoTime(DateUtils.getNowDate("yyyy-MM-dd"));
        blackLogService.add(blackLogs);

        return R.success();
    }

    @PostMapping("/out")
    @ResponseBody
    public R outBlack(BlackLogs blackLogs){

        Log.info("移出黑名单：传入参数：{}", blackLogs);

        blackLogs.setOutTime(DateUtils.getNowDate("yyyy-MM-dd"));
        blackLogService.update(blackLogs);

        return R.success();
    }
}
