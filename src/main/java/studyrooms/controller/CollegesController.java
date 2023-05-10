package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyrooms.entity.Colleges;
import studyrooms.msg.PageData;
import studyrooms.msg.R;
import studyrooms.service.CollegesService;
import studyrooms.utils.DateUtils;

import java.util.List;

@Controller
@RequestMapping("/colleges")
public class CollegesController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(CollegesController.class);

    @Autowired
    private CollegesService collegesService;

    @GetMapping("/all")
    @ResponseBody
    public R getAll() {

        Log.info("获取全部学院信息列表");

        List<Colleges> colleges = collegesService.getAll();

        return R.successData(colleges);
    }

    @GetMapping("/page")
    @ResponseBody
    public R getPageInfos(Long pageIndex, Long pageSize, String name) {

        Log.info("分页查找学院信息，当前页码：{}，"
                        + "每页数据量：{}, 模糊查询，学院名称名称：{}", pageIndex, pageSize, name);

        PageData page = collegesService.getPageInfos(pageIndex, pageSize, name);

        return R.successData(page);
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(Colleges college) {

        college.setCreateTime(DateUtils.getNowDate());

        Log.info("添加学院信息，传入参数：{}", college);

        collegesService.add(college);

        return R.success();
    }

    @PostMapping("/upd")
    @ResponseBody
    public R updInfo(Colleges college) {

        Log.info("修改学院信息，传入参数：{}", college);

        collegesService.update(college);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(Colleges college) {

        Log.info("删除学院信息，传入参数：{}", college);

        if(collegesService.isReferStudent(college.getId())){

            return R.warn("存在关联信息无法移除");
        }else{

            collegesService.delete(college);

            return R.success();
        }
    }
}
