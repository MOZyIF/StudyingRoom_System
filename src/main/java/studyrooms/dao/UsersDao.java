package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studyrooms.entity.Users;

import java.util.Map;

@Repository("usersDao")
public interface UsersDao extends BaseMapper<Users> {

    /**
     * 分页查询学生用户信息
     * @param page 分页信息
     * @param stuName 学生姓名
     * @param collegeId 学院ID
     * @param gradeId 班级ID
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            "u.id, u.user_name userName, u.pass_word passWord, u.name, " +
            "u.gender, u.age, u.type, u.status, s.total, s.college_id collegeId, s.grade_id gradeId," +
            "(SELECT name FROM fater_colleges WHERE id = s.college_id) collegeName," +
            "(SELECT name FROM fater_grades WHERE id = s.grade_id) gradeName " +
            "FROM fater_users u, fater_students s " +
            "WHERE type=2 AND u.id = s.id " +
            "<if test='stuName != null and stuName.trim() != &quot;&quot; '>" +
            "AND name LIKE CONCAT('%', #{stuName}, '%') " +
            "</if>" +
            "<if test='collegeId != null '>" +
            "AND s.college_id = #{collegeId} " +
            "</if>" +
            "<if test='gradeId != null '>" +
            "AND s.grade_id = #{gradeId} " +
            "</if>" +
            "</script>")
    public Page<Map<String, Object>> qryPageStudents(Page<Map<String, Object>> page,
                                                     @Param("stuName") String stuName,
                                                     @Param("collegeId") Integer collegeId,
                                                     @Param("gradeId") Integer gradeId);

    /**
     * 分页查询管理员信息
     * @param page 分页信息
     * @param manName 管理员姓名
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            "id, user_name userName, pass_word passWord, name, " +
            "gender, age, type, status " +
            "FROM fater_users " +
            "WHERE type=1 " +
            "<if test='manName != null and manName.trim() != &quot;&quot; '>" +
            "AND name LIKE CONCAT('%', #{manName}, '%') " +
            "</if>" +
            "</script>")
    public Page<Map<String, Object>> qryPageManagers(Page<Map<String, Object>> page,
                                                     @Param("manName") String manName);
}
