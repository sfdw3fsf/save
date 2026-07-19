package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for device search criteria")
public class SearchThietBiDto {
    private String maThietBi;
    private String tenThietBi;
    private Number trangThaiVhId;
    private Number loaiThietBi;
    private Number soHuuId;
    private String serial;
    private String kyHieu;
    private Number idcId;
    private Number rackId;
    private Number hieuLuc;
    private String soTheTs;
    private Long idChiTietTbi;
    
    @JsonProperty("taisanId")
    private String taiSanId;
    private String khongGian;
    private Integer pageIndex;
    private Integer pageSize;

    // Getters and Setters
    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public Number getTrangThaiVhId() {
        return trangThaiVhId;
    }

    public void setTrangThaiVhId(Number trangThaiVhId) {
        this.trangThaiVhId = trangThaiVhId;
    }

    public Number getLoaiThietBi() {
        return loaiThietBi;
    }

    public void setLoaiThietBi(Number loaiThietBi) {
        this.loaiThietBi = loaiThietBi;
    }

    public Number getSoHuuId() {
        return soHuuId;
    }

    public void setSoHuuId(Number soHuuId) {
        this.soHuuId = soHuuId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getKyHieu() {
        return kyHieu;
    }

    public void setKyHieu(String kyHieu) {
        this.kyHieu = kyHieu;
    }

    public Number getIdcId() {
        return idcId;
    }

    public void setIdcId(Number idcId) {
        this.idcId = idcId;
    }

    public Number getRackId() {
        return rackId;
    }

    public void setRackId(Number rackId) {
        this.rackId = rackId;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Number hieuLuc) {
        this.hieuLuc = hieuLuc;
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

    public void setSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSoTheTs() {
        return soTheTs;
    }

    public void setSoTheTs(String soTheTs) {
        this.soTheTs = soTheTs;
    }

    public Long getIdChiTietTbi() {
        return idChiTietTbi;
    }

    public void setIdChiTietTbi(Long idChiTietTbi) {
        this.idChiTietTbi = idChiTietTbi;
    }

    public String getTaiSanId() {
        return taiSanId;
    }

    public void setTaiSanId(String taiSanId) {
        this.taiSanId = taiSanId;
    }

    public String getKhongGian() {
        return khongGian;
    }

    public void setKhongGian(String khongGian) {
        this.khongGian = khongGian;
    }
}
