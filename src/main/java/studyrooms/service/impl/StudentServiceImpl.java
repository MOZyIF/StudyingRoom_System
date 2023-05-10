package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyrooms.comm.SysCode;
import studyrooms.dao.BlackLogsDao;
import studyrooms.dao.StudentsDao;
import studyrooms.dao.UsersDao;
import studyrooms.entity.BlackLogs;
import studyrooms.entity.Students;
import studyrooms.entity.Users;
import studyrooms.service.StudentService;

import java.util.HashMap;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private StudentsDao studentsDao;

    @Autowired
    private BlackLogsDao blackLogsDao;

    @Override
    @Transactional
    public void add(Students students) {

        studentsDao.insert(students);
    }

    @Override
    @Transactional
    public void update(Students students) {

        studentsDao.updateById(students);
    }

    @Override
    @Transactional
    public void delete(Students students) {

        QueryWrapper<BlackLogs> qw = new QueryWrapper<BlackLogs>();
        qw.eq("student_id", students.getId());
        blackLogsDao.delete(qw);

        studentsDao.deleteById(students);

        Users user = usersDao.selectById(students.getId());
        usersDao.deleteById(user);
    }

    @Override
    @Transactional
    public void addStudent(Users user, Students student) {

        usersDao.insert(user);
        studentsDao.insert(student);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String, Object> getNormalVSBlack(){

        Map<String, Object> resl = new HashMap<String, Object>();

        QueryWrapper<Users> qw_1 = new QueryWrapper<Users>();
        qw_1.eq("type", SysCode.USER_TYPE_STU);
        qw_1.eq("status", SysCode.USER_STATUS_Y);
        Integer normalTotal = usersDao.selectCount(qw_1);
        resl.put("normal", normalTotal);

        QueryWrapper<Users> qw_2 = new QueryWrapper<Users>();
        qw_2.eq("type", SysCode.USER_TYPE_STU);
        qw_2.eq("status", SysCode.USER_STATUS_N);
        Integer blackTotal = usersDao.selectCount(qw_2);
        resl.put("black", blackTotal);

        return resl;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Students getOne(String id) {

        Students student = studentsDao.selectById(id);

        return student;
    }

}
