package studyrooms.service;

import studyrooms.entity.Colleges;
import studyrooms.entity.Grades;
import studyrooms.msg.PageData;

import java.util.List;

/**
 * 业务层处理
 * 班级信息处理
 */
public interface GradesService extends BaseService<Grades, Integer>{

    /**
     * 检查是否存在关联学生
     * @param id 指定班级ID
     * @return true-存在关联学生 false-无关联学生
     */
    public Boolean isReferStudent(Integer id);

    /**
     * 获取全部的班级信息
     * @return
     */
    public List<Grades> getAll();

    /**
     * 分页获取学院信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param name 班级名称
     * @return
     */
    public PageData getPageInfos(Long pageIndex, Long pageSize, String name);
}
