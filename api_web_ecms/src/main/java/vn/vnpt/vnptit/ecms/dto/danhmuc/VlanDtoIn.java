package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VlanDtoIn implements Serializable {
    private Long id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String ten;
    private Long idc_id;
    private Long thietbi_id;
    private Long vrf_id;
    private Long zone_id;
    private String ghi_chu;
    private Long hieu_luc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(Long idc_id) {
        this.idc_id = idc_id;
    }

    public Long getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Long thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public Long getVrf_id() {
        return vrf_id;
    }

    public void setVrf_id(Long vrf_id) {
        this.vrf_id = vrf_id;
    }

    public Long getZone_id() {
        return zone_id;
    }

    public void setZone_id(Long zone_id) {
        this.zone_id = zone_id;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public Long getHieu_luc() {
        return hieu_luc;
    }

    public void setHieu_luc(Long hieu_luc) {
        this.hieu_luc = hieu_luc;
    }
}
