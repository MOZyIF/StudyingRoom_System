package studyrooms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "fater_rooms")
public class Rooms implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "intro")
    private String intro;

    @TableField(value = "rnums")
    private int rNums;

    @TableField(value = "cnums")
    private int cNums;

    @TableField(value = "total")
    private int total;

    @TableField(value = "max_total")
    private int maxTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getrNums() {
        return rNums;
    }

    public void setrNums(int rNums) {
        this.rNums = rNums;
    }

    public int getcNums() {
        return cNums;
    }

    public void setcNums(int cNums) {
        this.cNums = cNums;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", rNums=" + rNums +
                ", cNums=" + cNums +
                ", total=" + total +
                ", maxTotal=" + maxTotal +
                '}';
    }
}
