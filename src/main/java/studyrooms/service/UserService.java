package studyrooms.service;

import studyrooms.entity.Users;
import studyrooms.msg.PageData;

import java.util.Map;

public interface UserService extends BaseService<Users, String>{

    /**
     * 检查管理员工号是否重复
     * @param id 指定工号
     * @return
     */
    public Boolean checkUserId(String id);

    /**
     * 检查管理员账号是否重复
     * @param userName 指定账号
     * @return
     */
    public Boolean checkUserName(String userName);

    /**
     * 依据用户账号查找用户信息
     * @param userName 用户账号
     * @return
     */
    public Users getUserByUserName(String userName);

    /**
     * 分页查找学生信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param stuName 学生姓名
     * @param collegeId 学院ID
     * @param gradeId 班级ID
     * @return
     */
    public PageData getPageStudentInfos(Long pageIndex, Long pageSize,
                                        String stuName, Integer collegeId, Integer gradeId);

    /**
     * 分页查询管理员信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param manName 管理员姓名
     * @return
     */
    public PageData getPageManageInfos(Long pageIndex, Long pageSize, String manName);
}
