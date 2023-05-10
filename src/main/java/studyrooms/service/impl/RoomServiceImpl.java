package studyrooms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyrooms.dao.MakeLogsDao;
import studyrooms.dao.RoomsDao;
import studyrooms.entity.MakeLogs;
import studyrooms.entity.Rooms;
import studyrooms.msg.PageData;
import studyrooms.service.RoomService;
import studyrooms.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    public RoomsDao roomsDao;

    @Autowired
    public MakeLogsDao makeLogsDao;

    @Override
    @Transactional
    public void add(Rooms rooms) {

        roomsDao.insert(rooms);
    }

    @Override
    @Transactional
    public void update(Rooms rooms) {

        roomsDao.updateById(rooms);
    }

    @Override
    @Transactional
    public void delete(Rooms rooms) {

        roomsDao.deleteById(rooms);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Rooms getOne(Integer id) {

        Rooms room = roomsDao.selectById(id);

        return room;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Boolean isRefer(Integer roomId){

        QueryWrapper<MakeLogs> qw = new QueryWrapper<MakeLogs>();
        qw.eq("room_id", roomId);

        return makeLogsDao.selectCount(qw) > 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Map<String, Object>> getRooms(String roomId, String makeTime){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        Rooms room = roomsDao.selectById(roomId);

        for(int i = 0; i < room.getrNums(); i++){

            for(int j = 0; j < room.getrNums(); j++){

                Map<String, Object> temp = new HashMap<String, Object>();

                QueryWrapper<MakeLogs> qw = new QueryWrapper<MakeLogs>();
                qw.eq("room_id", roomId);
                qw.eq("make_time", makeTime);
                qw.eq("place", i+","+j);

                temp.put("rIndex", i);
                temp.put("cIndex", j);
                temp.put("flag", makeLogsDao.selectCount(qw)>0);
                resl.add(temp);
            }
        }

        return resl;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageData getPageInfos(Long pageIndex, Long pageSize, String name) {

        QueryWrapper<Rooms> qw = new QueryWrapper<Rooms>();

        if(StringUtils.isNotNull(name)){

            qw.like("name", name);
        }

        Page<Rooms> page = roomsDao.selectPage(new Page<Rooms>(pageIndex, pageSize), qw);

        return parsePage(page);
    }

    public PageData parsePage(Page<Rooms> p){

        List<Map<String, Object>> resl = new ArrayList<Map<String, Object>>();

        for(Rooms room : p.getRecords()){

            Map<String, Object> temp = new HashMap<String, Object>();

            temp.put("id", room.getId());
            temp.put("name", room.getName());
            temp.put("intro", room.getIntro());
            temp.put("rNums", room.getrNums());
            temp.put("cNums", room.getcNums());
            temp.put("total", room.getTotal());
            temp.put("maxTotal", room.getMaxTotal());

            resl.add(temp);
        }

        PageData pageData = new PageData(p.getCurrent(), p.getSize(), p.getTotal(), resl);

        return pageData;
    }
}
