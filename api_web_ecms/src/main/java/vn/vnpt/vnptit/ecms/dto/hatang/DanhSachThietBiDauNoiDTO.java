package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;

public class DanhSachThietBiDauNoiDTO implements Serializable {
    private Long thietBiNguonId;
    private Long idcId;
    private Long loaiThietBiId;

    public DanhSachThietBiDauNoiDTO(
            Long thietBiNguonId,
            Long idcId,
            Long loaiThietBiId) {
        this.thietBiNguonId = thietBiNguonId;
        this.idcId = idcId;
        this.loaiThietBiId = loaiThietBiId;
    }

    public DanhSachThietBiDauNoiDTO() {
    }

    public Long getThietBiNguonId() {
        return thietBiNguonId;
    }

    public void setThietBiNguonId(Long thietBiNguonId) {
        this.thietBiNguonId = thietBiNguonId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(Long loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }

}
