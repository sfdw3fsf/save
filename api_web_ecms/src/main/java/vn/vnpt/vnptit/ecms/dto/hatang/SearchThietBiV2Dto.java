package vn.vnpt.vnptit.ecms.dto.hatang;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for device search criteria V2")
public class SearchThietBiV2Dto {
    private String khongGian;
    private Number rackId;
    private Number soHuuId;
    private Number loaiThietBi;
    private String tenThietBi;
    private Number hangSxId;
    private String serial;
    private String taisanId;
    private String soTheTs;
    private Number donViQlId;
    private String caNhanQl;
    private Integer pageIndex;
    private Integer pageSize;
    private Number cumhtId;
    private Number idcId;

    public String getKhongGian() {
        return khongGian;
    }

    public void setKhongGian(String khongGian) {
        this.khongGian = khongGian;
    }

    public Number getRackId() {
        return rackId;
    }

    public void setRackId(Number rackId) {
        this.rackId = rackId;
    }

    public Number getSoHuuId() {
        return soHuuId;
    }

    public void setSoHuuId(Number soHuuId) {
        this.soHuuId = soHuuId;
    }

    public Number getLoaiThietBi() {
        return loaiThietBi;
    }

    public void setLoaiThietBi(Number loaiThietBi) {
        this.loaiThietBi = loaiThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public Number getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Number hangSxId) {
        this.hangSxId = hangSxId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTaisanId() {
        return taisanId;
    }

    public void setTaisanId(String taisanId) {
        this.taisanId = taisanId;
    }

    public String getSoTheTs() {
        return soTheTs;
    }

    public void setSoTheTs(String soTheTs) {
        this.soTheTs = soTheTs;
    }

    public Number getDonViQlId() {
        return donViQlId;
    }

    public void setDonViQlId(Number donViQlId) {
        this.donViQlId = donViQlId;
    }

    public String getCaNhanQl() {
        return caNhanQl;
    }

    public void setCaNhanQl(String caNhanQl) {
        this.caNhanQl = caNhanQl;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Number getCumhtId() {
        return cumhtId;
    }

    public void setCumhtId(Number cumhtId) {
        this.cumhtId = cumhtId;
    }

    public Number getIdcId() {
        return idcId;
    }

    public void setIdcId(Number idcId) {
        this.idcId = idcId;
    }
}
