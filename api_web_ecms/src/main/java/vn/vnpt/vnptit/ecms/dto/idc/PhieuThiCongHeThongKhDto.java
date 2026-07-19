package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhieuThiCongHeThongKhDto {
    @JsonProperty(value = "phieuThiCongId", required = false)
    private Long phieuThiCongId;
    @JsonProperty(value = "trangThaiId", required = false)
    private Long trangThaiId;
    @JsonProperty(value = "nhanVienId", required = false)
    private Long nhanVienId;
    @JsonProperty(value = "thietBiId", required = false)
    private Long thietBiId;
    @JsonProperty(value = "rackId", required = false)
    private Long rackId;
    @JsonProperty(value = "upUnitPos", required = false)
    private Long upUnitPos;
    @JsonProperty(value = "downUnitPos", required = false)
    private Long downUnitPos;
    @JsonProperty(value = "thoiDiemLapDat", required = false)
    private Date thoiDiemLapDat;
    @JsonProperty(value = "maTheTaiSan", required = false)
    private String maTheTaiSan;
    @JsonProperty(value = "lyDoTra", required = false)
    private String lyDoTra;

    public PhieuThiCongHeThongKhDto() {
    }

    public PhieuThiCongHeThongKhDto(Long phieuThiCongId, Long trangThaiId, Long nhanVienId, Long thietBiId,
            Long rackId, Long upUnitPos, Long downUnitPos, Date thoiDiemLapDat, String maTheTaiSan, String lyDoTra) {
        this.phieuThiCongId = phieuThiCongId;
        this.trangThaiId = trangThaiId;
        this.nhanVienId = nhanVienId;
        this.thietBiId = thietBiId;
        this.rackId = rackId;
        this.upUnitPos = upUnitPos;
        this.downUnitPos = downUnitPos;
        this.thoiDiemLapDat = thoiDiemLapDat;
        this.maTheTaiSan = maTheTaiSan;
        this.lyDoTra = lyDoTra;
    }

    public Long getPhieuThiCongId() {
        return phieuThiCongId;
    }

    public void setPhieuThiCongId(Long phieuThiCongId) {
        this.phieuThiCongId = phieuThiCongId;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getUpUnitPos() {
        return upUnitPos;
    }

    public void setUpUnitPos(Long upUnitPos) {
        this.upUnitPos = upUnitPos;
    }

    public Long getDownUnitPos() {
        return downUnitPos;
    }

    public void setDownUnitPos(Long downUnitPos) {
        this.downUnitPos = downUnitPos;
    }

    public Date getThoiDiemLapDat() {
        return thoiDiemLapDat;
    }

    public void setThoiDiemLapDat(Date thoiDiemLapDat) {
        this.thoiDiemLapDat = thoiDiemLapDat;
    }

    public String getMaTheTaiSan() {
        return maTheTaiSan;
    }

    public void setMaTheTaiSan(String maTheTaiSan) {
        this.maTheTaiSan = maTheTaiSan;
    }

    public String getLyDoTra() {
        return lyDoTra;
    }

    public void setLyDoTra(String lyDoTra) {
        this.lyDoTra = lyDoTra;
    }
}
