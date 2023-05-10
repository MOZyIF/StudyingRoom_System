package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyrooms.dao.GradesDao;
import studyrooms.dao.StudentsDao;
import studyrooms.entity.Grades;
import studyrooms.entity.Students;
import studyrooms.service.GradesService;
import studyrooms.utils.StringUtils;
import studyrooms.msg.PageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("gradesService")
public  class GradesServiceImpl implements GradesService {

    @Autowired
    private GradesDao gradesDao;

    @Autowired
    private StudentsDao studentsDao;

    @Override
    @Transactional
    public void add(Grades grades) {

        gradesDao.insert(grades);
    }

    @Override
    @Transactional
    public void update(Grades grades) {

        gradesDao.updateById(grades);
    }

    @Override
    @Transactional
    public void delete(Grades grades) {

        gradesDao.deleteById(grades);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Grades getOne(Integer id) {

        Grades grade = gradesDao.selectById(id);

        return grade;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean isReferStudent(Integer id) {

        QueryWrapper<Students> qw = new QueryWrapper<Students>();
        qw.eq("grade_id", id);

        return studentsDao.selectCount(qw) > 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Grades> getAll() {

        QueryWrapper<Grades> qw = new QueryWrapper<Grades>();
        qw.orderByDesc("create_time");

        List<Grades> grades = gradesDao.selectList(qw);

        return grades;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getPageInfos(Long pageIndex, Long pageSize, String name) {

        QueryWrapper<Grades> qw = new QueryWrapper<Grades>();
        qw.orderByDesc("create_time");

        if(StringUtils.isNotNullOrEmpty(name)){

            qw.like("name", name);
        }

        Page<Grades> page = gradesDao.selectPage(new Page<Grades>(pageIndex, pageSize), qw);

        return parsePage(page);
    }

    public PageData parsePage(Page<Grades> p) {

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for (Grades grade : p.getRecords()) {

            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("id", grade.getId());
            temp.put("name", grade.getName());
            temp.put("createTime", grade.getCreateTime());
            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }
}
