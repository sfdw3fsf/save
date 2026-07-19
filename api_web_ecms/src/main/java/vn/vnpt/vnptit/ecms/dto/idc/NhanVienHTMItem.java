package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NhanVienHTMItem {
    private Long nhanvienId;
    private Long nhiemvuId;
    public NhanVienHTMItem() {
    }

    public NhanVienHTMItem(Long nhanvienId, Long nhiemvuId) {
        this.nhanvienId = nhanvienId;
        this.nhiemvuId = nhiemvuId;
    }

    public Long getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(Long nhanvienId) {
        this.nhanvienId = nhanvienId;
    }

    public Long getNhiemvuId() {
        return nhiemvuId;
    }

    public void setNhiemvuId(Long nhiemvuId) {
        this.nhiemvuId = nhiemvuId;
    }

    @Override
    public String toString() {
        return "NhanVienHTMItem{" +
                "nhanvienId=" + nhanvienId +
                ", nhiemvuId=" + nhiemvuId +
                '}';
    }
}