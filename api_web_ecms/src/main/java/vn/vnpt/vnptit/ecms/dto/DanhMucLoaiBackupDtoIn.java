package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class DanhMucLoaiBackupDtoIn {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("ten")
    private String ten;
    @JsonProperty("ghichu")
    private String ghichu;
    @JsonProperty("hieuluc")
    @Min(value = 0, message = "Hiệu lực chỉ được phép là 0 hoặc 1")
    @Max(value = 1, message = "Hiệu lực chỉ được phép là 0 hoặc 1")
    private int hieuluc;

    public String getTen() {
        return ten; 
    }

    public String getGhichu() {
        return ghichu;
    }

    public int getHieuluc() {
        return hieuluc;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public void setHieuluc(int hieuluc) {
        this.hieuluc = hieuluc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
