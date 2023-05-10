package studyrooms.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import studyrooms.entity.Colleges;
import studyrooms.msg.PageData;

import java.util.List;

/**
 * 业务层处理
 * 学院信息处理
 */
public interface CollegesService extends BaseService<Colleges, Integer>{

    /**
     * 检查是否存在关联学生
     * @param id 指定学院ID
     * @return true-存在关联学生 false-无关联学生
     */
    public Boolean isReferStudent(Integer id);

    /**
     * 获取全部的学院信息
     * @return
     */
    public List<Colleges> getAll();

    /**
     * 分页获取学院信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param name 学院名称
     * @return
     */
    public PageData getPageInfos(Long pageIndex, Long pageSize, String name);
}
