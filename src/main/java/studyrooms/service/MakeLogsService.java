package studyrooms.service;

import studyrooms.entity.MakeLogs;
import studyrooms.msg.PageData;

import java.util.List;
import java.util.Map;

public interface MakeLogsService extends BaseService<MakeLogs, Integer>{

    /**
     * 统计本日使用和空闲的位置数目
     * @return
     */
    public Map<String, Object> getUseVSNoUseInDay();

    /**
     * 统计本月每日使用
     * @return
     */
    public List<Map<String, Object>> getUseTotalInMonth();

    /**
     * 统计本月每日预约
     * @return
     */
    public List<Map<String, Object>> getMakeTotalInMonth();

    /**
     * 检查指定日期学生能否预约
     * @param stuId 学生学号
     * @param makeTime 预约时间
     * @return
     */
    public Boolean isMake(String stuId, String makeTime);

    /**
     * 检查指定自习室是否可以预约
     * @param roomId 自习室ID
     * @param makeTime 预约时间
     * @return
     */
    public Boolean isMake(Integer roomId, String makeTime);

    /**
     * 分页查询预约记录信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param stuId 学生Id
     * @param stuName 学生姓名
     * @param roomName 自习室名称
     * @return
     */
    public PageData getPageInfos(Long pageIndex, Long pageSize, String stuId, String stuName, String roomName);
}
