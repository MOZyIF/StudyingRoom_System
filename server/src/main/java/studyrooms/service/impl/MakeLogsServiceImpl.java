package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyrooms.comm.SysCode;
import studyrooms.dao.*;
import studyrooms.entity.*;
import studyrooms.msg.PageData;
import studyrooms.service.MakeLogsService;
import studyrooms.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("makeLogsService")
public class MakeLogsServiceImpl implements MakeLogsService {

    @Autowired
    private MakeLogsDao makeLogsDao;

    @Autowired
    private StudentsDao studentsDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private BlackLogsDao blackLogsDao;

    @Autowired
    private RoomsDao roomsDao;

    @Override
    @Transactional
    public void add(MakeLogs makeLogs) {

        makeLogsDao.insert(makeLogs);
    }

    @Override
    @Transactional
    public void update(MakeLogs makeLogs) {

        if(makeLogs.getStatus() == SysCode.MAKE_STATUS_END){

            Long diffH = DateUtils.getDiffHour(makeLogs.getStartTime(), makeLogs.getEndTime());

            Students student = studentsDao.selectById(makeLogs.getStudentId());
            student.setTotal(student.getTotal() + diffH);
            studentsDao.updateById(student);
        }else if(makeLogs.getStatus() == SysCode.MAKE_STATUS_CANCLE){

            Users user = usersDao.selectById(makeLogs.getStudentId());
            user.setStatus(SysCode.USER_STATUS_N);
            usersDao.updateById(user);

            BlackLogs blackLogs = new BlackLogs();
            blackLogs.setStudentId(makeLogs.getStudentId());
            blackLogs.setIntoTime(DateUtils.getNowDate("yyyy-MM-dd"));
            blackLogs.setReason(SysCode.BLACK_CANCLE_RESON);
            blackLogsDao.insert(blackLogs);
        }

        makeLogsDao.updateById(makeLogs);
    }

    @Override
    @Transactional
    public void delete(MakeLogs makeLogs) {

        makeLogsDao.deleteById(makeLogs.getId());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MakeLogs getOne(Integer id) {

        MakeLogs makeLog = makeLogsDao.selectById(id);

        return makeLog;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String, Object> getUseVSNoUseInDay(){

        Map<String, Object> resl = new HashMap<String, Object>();

        Long useTotal = makeLogsDao.qryUseTotalInDay();
        resl.put("useTotal", useTotal);

        Long total = makeLogsDao.qryTotal();
        resl.put("freeTotal", total == null ? 0 : total - useTotal);

        return resl;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Map<String, Object>> getUseTotalInMonth(){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        List<Map<String, Object>> temps = makeLogsDao.qryUseTotalInMonth();

        for(int i = 1; i <= DateUtils.getTotalInMonth(); i++){

            Map<String, Object> item = new HashMap<String, Object>();

            Boolean flag = true;
            for(Map<String, Object> temp : temps){

                if(Integer.valueOf(temp.get("d").toString()) == i){

                    item.put("day", i);
                    item.put("total", temp.get("total"));
                    flag = false;
                    break;
                }
            }
            if(flag){
                item.put("day", i);
                item.put("total", 0);
            }
            resl.add(item);
        }

        return resl;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Map<String, Object>> getMakeTotalInMonth(){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        List<Map<String, Object>> temps = makeLogsDao.qryMakeTotalInMonth();

        for(int i = 1; i <= DateUtils.getTotalInMonth(); i++){

            Map<String, Object> item = new HashMap<String, Object>();

            Boolean flag = true;
            for(Map<String, Object> temp : temps){

                if(Integer.valueOf(temp.get("d").toString()) == i){

                    item.put("day", i);
                    item.put("total", temp.get("total"));
                    flag = false;
                    break;
                }
            }
            if(flag){
                item.put("day", i);
                item.put("total", 0);
            }
            resl.add(item);
        }

        return resl;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean isMake(String stuId, String makeTime){

        QueryWrapper<MakeLogs> qw = new QueryWrapper<MakeLogs>();
        qw.eq("student_id", stuId);
        qw.eq("make_time", makeTime);

        return makeLogsDao.selectCount(qw) <= 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean isMake(Integer roomId, String makeTime){

        Rooms room = roomsDao.selectById(roomId);

        QueryWrapper<MakeLogs> qw = new QueryWrapper<MakeLogs>();
        qw.eq("room_id", roomId);
        qw.eq("make_time", makeTime);

        return makeLogsDao.selectCount(qw) <= room.getMaxTotal();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getPageInfos(Long pageIndex, Long pageSize, String stuId, String stuName, String roomName) {

        Page<Map<String, Object>> page = makeLogsDao.qryPageInfos(new Page<>( pageIndex, pageSize), stuId, stuName, roomName);

        return parsePage(page);
    }

    public PageData parsePage(Page<Map<String, Object>> p){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for(Map<String, Object> item : p.getRecords()){

            Map<String, Object> temp = new HashMap<String, Object>();

            temp.put("id", item.get("id"));
            temp.put("place", item.get("place"));
            temp.put("createTime", item.get("createTime"));
            temp.put("makeTime", item.get("makeTime"));
            temp.put("startTime", item.get("startTime"));
            temp.put("endTime", item.get("endTime"));
            temp.put("status", item.get("status"));
            temp.put("roomId", item.get("roomId"));
            temp.put("roomName", item.get("roomName"));
            temp.put("total", item.get("total"));
            temp.put("rnums", item.get("rnums"));
            temp.put("cnums", item.get("cnums"));
            temp.put("maxTotal", item.get("maxTotal"));
            temp.put("studentId", item.get("studentId"));
            temp.put("stuName", item.get("stuName"));
            temp.put("gender", item.get("gender"));

            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }
}
