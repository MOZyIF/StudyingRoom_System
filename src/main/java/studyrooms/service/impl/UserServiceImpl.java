package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import studyrooms.dao.BlackLogsDao;
import studyrooms.dao.UsersDao;
import studyrooms.entity.BlackLogs;
import studyrooms.entity.Users;
import studyrooms.msg.PageData;
import studyrooms.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private BlackLogsDao blackLogsDao;

    @Override
    @Transactional
    public void add(Users users) {

        usersDao.insert(users);
    }

    @Override
    @Transactional
    public void update(Users users) {

        usersDao.updateById(users);
    }

    @Override
    @Transactional
    public void delete(Users users) {

        usersDao.deleteById(users);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Users getOne(String id) {

        Users user = usersDao.selectById(id);

        return user;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean checkUserId(String id) {

        QueryWrapper<Users> qw = new QueryWrapper<Users>();
        qw.eq("id", id);

        return usersDao.selectCount(qw) > 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean checkUserName(String userName) {

        QueryWrapper<Users> qw = new QueryWrapper<Users>();
        qw.eq("user_name", userName);

        return usersDao.selectCount(qw) > 0;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Users getUserByUserName(String userName){

        QueryWrapper<Users> qw = new QueryWrapper<Users>();
        qw.eq("user_name", userName);

        return usersDao.selectOne(qw);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getPageStudentInfos(Long pageIndex, Long pageSize, String stuName, Integer collegeId, Integer gradeId) {

        Page<Map<String, Object>> page =
                usersDao.qryPageStudents(new Page<Map<String, Object>>(pageIndex, pageSize), stuName, collegeId, gradeId);

        return parseStuPage(page);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getPageManageInfos(Long pageIndex, Long pageSize, String manName) {

        Page<Map<String, Object>> page =
                usersDao.qryPageManagers(new Page<Map<String, Object>>(pageIndex, pageSize), manName);

        return parseManPage(page);
    }

    public PageData parseStuPage(Page<Map<String, Object>> p){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for (Map<String, Object> item : p.getRecords()){

            Map<String, Object> temp = new HashMap<String, Object>();

            temp.put("id", item.get("id"));
            temp.put("userName", item.get("userName"));
            temp.put("passWord", item.get("passWord"));
            temp.put("name", item.get("name"));
            temp.put("gender", item.get("gender"));
            temp.put("age", item.get("age"));
            temp.put("status", item.get("status"));
            temp.put("type", item.get("type"));
            temp.put("total", item.get("total"));
            temp.put("collegeId", item.get("collegeId"));
            temp.put("collegeName", item.get("collegeName"));
            temp.put("gradeId", item.get("gradeId"));
            temp.put("gradeName", item.get("gradeName"));

            QueryWrapper<BlackLogs> qw = new QueryWrapper<BlackLogs>();
            qw.eq("student_id", item.get("id"));
            temp.put("blackTotal", blackLogsDao.selectCount(qw));

            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }

    public PageData parseManPage(Page<Map<String, Object>> p){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for (Map<String, Object> item : p.getRecords()){

            Map<String, Object> temp = new HashMap<String, Object>();

            temp.put("id", item.get("id"));
            temp.put("userName", item.get("userName"));
            temp.put("passWord", item.get("passWord"));
            temp.put("name", item.get("name"));
            temp.put("gender", item.get("gender"));
            temp.put("age", item.get("age"));
            temp.put("status", item.get("status"));
            temp.put("type", item.get("type"));

            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }
}
