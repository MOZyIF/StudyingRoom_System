package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studyrooms.entity.Colleges;

@Repository("collegesDao")
public interface CollegesDao extends BaseMapper<Colleges> {
}
