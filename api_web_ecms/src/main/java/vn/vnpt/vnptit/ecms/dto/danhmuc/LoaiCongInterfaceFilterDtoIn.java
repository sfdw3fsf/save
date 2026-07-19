package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoaiCongInterfaceFilterDtoIn implements Serializable {
    private Long id;

    private Long phanloai;
    private Long chungloaiThietBiId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(Long phanloai) {
        this.phanloai = phanloai;
    }

    public Long getChungloaiThietBiId() {
        return chungloaiThietBiId;
    }

    public void setChungloaiThietBiId(Long chungloaiThietBiId) {
        this.chungloaiThietBiId = chungloaiThietBiId;
    }


}
