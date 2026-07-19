package vn.vnpt.vnptit.ecms.dto.hatang;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchHistorySyncMayAoDto {
    private Long cumId;
    private String loaiDoiTuong;
    private String maMayAo;
    private String tenMayAo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayCpFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayCpTo;

    public Long getCumId() {
        return cumId;
    }

    public void setCumId(Long cumId) {
        this.cumId = cumId;
    }

    public String getLoaiDoiTuong() {
        return loaiDoiTuong;
    }

    public void setLoaiDoiTuong(String loaiDoiTuong) {
        this.loaiDoiTuong = loaiDoiTuong;
    }

    public String getMaMayAo() {
        return maMayAo;
    }

    public void setMaMayAo(String maMayAo) {
        this.maMayAo = maMayAo;
    }

    public String getTenMayAo() {
        return tenMayAo;
    }

    public void setTenMayAo(String tenMayAo) {
        this.tenMayAo = tenMayAo;
    }

    public Date getNgayCpFrom() {
        return ngayCpFrom;
    }

    public void setNgayCpFrom(Date ngayCpFrom) {
        this.ngayCpFrom = ngayCpFrom;
    }

    public Date getNgayCpTo() {
        return ngayCpTo;
    }

    public void setNgayCpTo(Date ngayCpTo) {
        this.ngayCpTo = ngayCpTo;
    }
}
