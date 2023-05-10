package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studyrooms.entity.Grades;


@Repository("gradesDao")
public interface GradesDao extends BaseMapper<Grades> {
}
