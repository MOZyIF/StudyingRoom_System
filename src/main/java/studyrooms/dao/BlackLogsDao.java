package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studyrooms.entity.BlackLogs;

@Repository("blackLogsDao")
public interface BlackLogsDao extends BaseMapper<BlackLogs> {
}
