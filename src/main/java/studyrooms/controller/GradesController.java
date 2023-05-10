package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import studyrooms.entity.Grades;
import studyrooms.msg.PageData;
import studyrooms.msg.R;
import studyrooms.service.GradesService;
import studyrooms.utils.DateUtils;

import java.util.List;

@Controller
@RequestMapping("/grades")
public class GradesController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(GradesController.class);

    @Autowired
    private GradesService gradesService;

    @GetMapping("/all")
    @ResponseBody
    public R getAll() {

        Log.info("获取全部班级信息");

        List<Grades> grades = gradesService.getAll();

        return R.successData(grades);
    }

    @GetMapping("/page")
    @ResponseBody
    public R getPageInfos(Long pageIndex, Long pageSize, String name) {

        Log.info("分页查找班级信息，当前页码：{}，"
                + "每页数据量：{}, 模糊查询，班级名称名称：{}", pageIndex, pageSize, name);

        PageData page = gradesService.getPageInfos(pageIndex, pageSize, name);

        return R.successData(page);
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(Grades grade) {

        grade.setCreateTime(DateUtils.getNowDate());

        Log.info("添加班级信息，传入参数：{}", grade);

        gradesService.add(grade);

        return R.success();
    }

    @PostMapping("/upd")
    @ResponseBody
    public R updInfo(Grades grade) {

        Log.info("修改班级信息，传入参数：{}", grade);

        gradesService.update(grade);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(Grades grade) {

        Log.info("删除班级信息，传入参数：{}", grade);

        if(gradesService.isReferStudent(grade.getId())){

            return R.warn("存在关联信息无法移除");
        }else{

            gradesService.delete(grade);

            return R.success();
        }
    }
}
