package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyrooms.comm.SysCode;
import studyrooms.dao.BlackLogsDao;
import studyrooms.dao.UsersDao;
import studyrooms.entity.BlackLogs;
import studyrooms.entity.Users;
import studyrooms.msg.PageData;
import studyrooms.service.BlackLogService;
import studyrooms.utils.DateUtils;
import studyrooms.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("blackLogService")
public class BlackLogServiceImpl implements BlackLogService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private BlackLogsDao blackLogsDao;

    @Override
    @Transactional
    public void add(BlackLogs blackLogs) {

        Users user = usersDao.selectById(blackLogs.getStudentId());
        user.setStatus(SysCode.USER_STATUS_N);
        usersDao.updateById(user);

        blackLogsDao.insert(blackLogs);
    }

    @Override
    @Transactional
    public void update(BlackLogs blackLogs) {

        Users user = usersDao.selectById(blackLogs.getStudentId());
        user.setStatus(SysCode.USER_STATUS_Y);
        usersDao.updateById(user);

        blackLogs.setOutTime(DateUtils.getNowDate("yyyy-MM-dd"));
        blackLogsDao.updateById(blackLogs);
    }

    @Override
    @Transactional
    public void delete(BlackLogs blackLogs) {

        blackLogsDao.deleteById(blackLogs);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public BlackLogs getOne(Integer id) {

        BlackLogs blackLog = blackLogsDao.selectById(id);

        return blackLog;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getBlackLogsByStuId(Long pageIndex, Long pageSize, String stuId) {

        QueryWrapper<BlackLogs> qw = new QueryWrapper<BlackLogs>();
        qw.eq("student_id", stuId);
        qw.orderByDesc("into_time");

        Page<BlackLogs> page = blackLogsDao.selectPage(new Page<BlackLogs>(pageIndex, pageSize), qw);

        return parsePage(page);
    }

    public PageData parsePage(Page<BlackLogs> p){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for(BlackLogs blackLogs : p.getRecords()){

            Map<String, Object> temp = new HashMap<String, Object>();

            temp.put("id", blackLogs.getId());
            temp.put("intoTime", blackLogs.getIntoTime());
            temp.put("outTime", StringUtils.isNotNullOrEmpty(blackLogs.getOutTime()) ? blackLogs.getOutTime() : "-----");
            temp.put("reason", blackLogs.getReason());

            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }
}
