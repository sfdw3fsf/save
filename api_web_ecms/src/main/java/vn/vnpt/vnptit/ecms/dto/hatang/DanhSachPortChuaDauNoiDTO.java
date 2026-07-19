package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;

public class DanhSachPortChuaDauNoiDTO implements Serializable {
    private Long thietBiId;

    public DanhSachPortChuaDauNoiDTO(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public DanhSachPortChuaDauNoiDTO() {
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }
}
