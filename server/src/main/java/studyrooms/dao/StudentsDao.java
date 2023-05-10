package studyrooms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studyrooms.entity.Grades;
import studyrooms.entity.Students;

@Repository("studentsDao")
public interface StudentsDao extends BaseMapper<Students> {
}
