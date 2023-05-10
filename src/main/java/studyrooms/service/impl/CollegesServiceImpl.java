package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyrooms.dao.CollegesDao;
import studyrooms.dao.StudentsDao;
import studyrooms.entity.Colleges;
import studyrooms.entity.Students;
import studyrooms.msg.PageData;
import studyrooms.service.CollegesService;
import studyrooms.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("collegesService")
public class CollegesServiceImpl implements CollegesService {

    @Autowired
    private CollegesDao collegesDao;

    @Autowired
    private StudentsDao studentsDao;

    @Override
    @Transactional
    public void add(Colleges colleges) {

        collegesDao.insert(colleges);
    }

    @Override
    @Transactional
    public void update(Colleges colleges) {

        collegesDao.updateById(colleges);
    }

    @Override
    @Transactional
    public void delete(Colleges colleges) {

        collegesDao.deleteById(colleges);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Colleges getOne(Integer id) {

        Colleges college = collegesDao.selectById(id);

        return college;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean isReferStudent(Integer id){

        QueryWrapper<Students> qw = new QueryWrapper<Students>();
        qw.eq("college_id", id);

        return studentsDao.selectCount(qw) > 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Colleges> getAll() {

        QueryWrapper<Colleges> qw = new QueryWrapper<Colleges>();
        qw.orderByDesc("create_time");
        List<Colleges> colleges = collegesDao.selectList(qw);

        return colleges;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getPageInfos(Long pageIndex, Long pageSize, String name) {

        QueryWrapper<Colleges> qw = new QueryWrapper<Colleges>();

        if(StringUtils.isNotNullOrEmpty(name)){

            qw.like("name", name);
        }

        qw.orderByDesc("create_time");

        Page<Colleges> page =
                collegesDao.selectPage(new Page<Colleges>(pageIndex, pageSize), qw);

        return parsePage(page);
    }

    public PageData parsePage(Page<Colleges> p) {

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for (Colleges college : p.getRecords()) {

            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("id", college.getId());
            temp.put("name", college.getName());
            temp.put("createTime", college.getCreateTime());
            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }
}
