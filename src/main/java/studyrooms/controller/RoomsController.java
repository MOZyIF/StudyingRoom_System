package studyrooms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyrooms.entity.Rooms;
import studyrooms.msg.PageData;
import studyrooms.msg.R;
import studyrooms.service.RoomService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rooms")
public class RoomsController extends BaseController{

    protected static final Logger Log = LoggerFactory.getLogger(RoomsController.class);

    @Autowired
    private RoomService roomService;

    @GetMapping("/makelist")
    @ResponseBody
    public R getRooms(String roomId, String makeTime){

        Log.info("获取预定列表，自习室ID：{}, 预约时间：{}", roomId, makeTime);

        List<Map<String, Object>> rooms = roomService.getRooms(roomId, makeTime);

        return R.successData(rooms);
    }

    @GetMapping("/page")
    @ResponseBody
    public R getPageInfos(Long pageIndex, Long pageSize, String name) {

        Log.info("分页查找学院信息，当前页码：{}，"
                + "每页数据量：{}, 模糊查询，自习室名称：{}", pageIndex, pageSize, name);

        PageData page = roomService.getPageInfos(pageIndex, pageSize, name);

        return R.successData(page);
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(Rooms room){

        Log.info("添加自习室信息，传入参数：{}", room);

        Integer total = room.getrNums() * room.getcNums();

        if(total < room.getMaxTotal()){

            return R.warn("允许预约数目超出上限");
        }

        room.setTotal(total);
        roomService.add(room);

        return R.success();
    }

    @PostMapping("/upd")
    @ResponseBody
    public R updInfo(Rooms room){

        Log.info("修改自习室信息，传入参数：{}", room);

        Integer total = room.getrNums() * room.getcNums();

        if(total < room.getMaxTotal()){

            return R.warn("允许预约数目超出上限");
        }

        room.setTotal(total);
        roomService.update(room);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(Rooms room){

        Log.info("删除自习室信息，传入参数：{}", room);

        if(roomService.isRefer(room.getId())){

            return R.warn("存在关联预约记录无法移除");
        }

        roomService.delete(room);

        return R.success();
    }
}
