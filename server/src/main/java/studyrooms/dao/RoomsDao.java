package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studyrooms.entity.Rooms;

@Repository("roomsDao")
public interface RoomsDao extends BaseMapper<Rooms> {
}
