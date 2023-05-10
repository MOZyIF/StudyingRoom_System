package studyrooms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "fater_students")
public class Students implements Serializable {

    @TableId(value = "id")
    private String id;

    @TableField(value = "total")
    private long total;

    @TableField(value = "grade_id")
    private int gradeId;

    @TableField(value = "college_id")
    private int collegeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "Students{" +
                "id='" + id + '\'' +
                ", total=" + total +
                ", gradeId=" + gradeId +
                ", collegeId=" + collegeId +
                '}';
    }
}
