package studyrooms.service;

import studyrooms.entity.Students;
import studyrooms.entity.Users;

import java.util.Map;

public interface StudentService extends BaseService<Students, String>{

    /**
     * 获取学生正常和黑名单人数对比
     * @return
     */
    public Map<String, Object> getNormalVSBlack();

    /**
     * 添加学生信息
     * @param user 学生账号
     * @param student 学生信息
     */
    public void addStudent(Users user, Students student);
}
