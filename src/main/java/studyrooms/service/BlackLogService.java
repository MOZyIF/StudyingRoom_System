package studyrooms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import studyrooms.entity.BlackLogs;
import studyrooms.msg.PageData;

import java.util.List;

public interface BlackLogService extends BaseService<BlackLogs, Integer>{

    /**
     * 分页查询指定学生的黑名单记录
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param stuId 学生学号
     * @return
     */
    public PageData getBlackLogsByStuId(Long pageIndex, Long pageSize, String stuId);
}
