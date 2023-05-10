package studyrooms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "fater_black_logs")
public class BlackLogs implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "student_id")
    private String studentId;

    @TableField(value = "into_time")
    private String intoTime;

    @TableField(value = "out_time")
    private String outTime;

    @TableField(value = "reason")
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getIntoTime() {
        return intoTime;
    }

    public void setIntoTime(String intoTime) {
        this.intoTime = intoTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BlackLogs{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", intoTime='" + intoTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
