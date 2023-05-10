package studyrooms.service;

import studyrooms.entity.Rooms;
import studyrooms.msg.PageData;

import java.util.List;
import java.util.Map;

public interface RoomService extends BaseService<Rooms, Integer>{

    /**
     * 检查指定自习室是否存在关联信息
     * @param roomId 自习室ID
     * @return
     */
    public Boolean isRefer(Integer roomId);

    /**
     * 获取自习室预定立碑
     * @param roomId 自习室编号
     * @param makeTime 预约时间
     * @return
     */
    public List<Map<String, Object>> getRooms(String roomId, String makeTime);

    /**
     * 分页查询自习室信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param name 自习室名称
     * @return
     */
    public PageData getPageInfos(Long pageIndex, Long pageSize, String name);
}
