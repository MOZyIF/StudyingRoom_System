package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studyrooms.entity.MakeLogs;

import java.util.List;
import java.util.Map;

@Repository("makeLogsDao")
public interface MakeLogsDao extends BaseMapper<MakeLogs> {

    /**
     * 分页查询自习室预约记录
     * @param page 分页信息
     * @param stuName 学生姓名
     * @param roomName 自习室名字
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            "u.name stuName, u.gender, " +
            "(SELECT name FROM fater_colleges WHERE id = (SELECT college_id FROM fater_students WHERE id = u.id)) collegeName, " +
            "(SELECT name FROM fater_grades WHERE id = (SELECT grade_id FROM fater_students WHERE id = u.id)) gradeName, " +
            "r.name roomName, r.total, r.rnums, r.cnums, r.max_total maxTotal, " +
            "m.id, m.student_id studentId, m.place, m.create_time createTime, m.make_time makeTime,  " +
            "m.start_time startTime, m.end_time endTime, m.status, m.room_id roomId " +
            "FROM fater_make_logs m, fater_users u, fater_rooms r " +
            "WHERE m.student_id = u.id AND m.room_id = r.id " +
            "<if test='stuId != null and stuId.trim() != &quot;&quot; '>" +
            "AND m.student_id = #{stuId} " +
            "</if>" +
            "<if test='stuName != null and stuName.trim() != &quot;&quot; '>" +
            "AND u.name LIKE CONCAT('%', #{stuName}, '%') " +
            "</if>" +
            "<if test='roomName != null and roomName.trim() != &quot;&quot; '>" +
            "AND r.name LIKE CONCAT('%', #{roomName}, '%') " +
            "</if>" +
            "ORDER BY m.create_time DESC " +
            "</script>")
    public Page<Map<String, Object>> qryPageInfos(Page<Map<String, Object>> page,
                                                  @Param("stuId") String stuId,
                                                  @Param("stuName") String stuName,
                                                  @Param("roomName") String roomName);

    /**
     * 查询全部的位置总数
     * @return
     */
    @Select("<script>" +
            "SELECT sum(total) FROM fater_rooms " +
            "</script>")
    public Long qryTotal();

    /**
     * 获取当日已经使用的位置
     * @return
     */
    @Select("<script>" +
            "SELECT count(*) FROM fater_make_logs " +
            "WHERE status = 1 AND YEAR(make_time) = YEAR(NOW()) AND " +
            "MONTH(make_time) = MONTH(NOW()) AND DAY(make_time) = DAY(NOW()) " +
            "</script>")
    public Long qryUseTotalInDay();

    /**
     * 获取当日各区域预约数目
     * @return
     */
    @Select("<script>" +
            "SELECT count(*) FROM fater_make_logs " +
            "WHERE YEAR(make_time) = YEAR(NOW()) AND " +
            "MONTH(make_time) = MONTH(NOW()) AND DAY(make_time) = DAY(NOW()) " +
            "</script>")
    public Long qryMakeTotalInDay();

    /**
     * 查询本月每日使用的数目变化
     * @return
     */
    @Select("<script>" +
            "SELECT DAY(make_time) d, count(*) total FROM fater_make_logs " +
            "WHERE (status = 1 OR status = 2) AND YEAR(make_time) = YEAR(NOW()) AND  " +
            "MONTH(make_time) = MONTH(NOW()) GROUP BY DAY(make_time) " +
            "</script>")
    public List<Map<String, Object>> qryUseTotalInMonth();

    /**
     * 查询本月每日预约的数目变化
     * @return
     */
    @Select("<script>" +
            "SELECT DAY(make_time) d, count(*) total FROM fater_make_logs " +
            "WHERE YEAR(make_time) = YEAR(NOW()) AND  " +
            "MONTH(make_time) = MONTH(NOW()) GROUP BY DAY(make_time) " +
            "</script>")
    public List<Map<String, Object>> qryMakeTotalInMonth();
}
