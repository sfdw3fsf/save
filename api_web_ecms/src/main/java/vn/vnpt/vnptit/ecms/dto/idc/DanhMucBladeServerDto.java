package vn.vnpt.vnptit.ecms.dto.idc;

import java.time.LocalDateTime;

public class DanhMucBladeServerDto {

    private Long id;                   // CHUNGLOAIBS_ID

    private String maVt;               // MA_VT
    private String modelThietBi;       // MODEL_THIETBI
    private Long hangSxId;             // HANGSX_ID
    private Double congSuat;           // CONGSUAT
    private Integer fullDepth;         // FULL_DEPTH

    // Blade
    private Integer bladeSoKhe;        // BLADE_SOKHE
    private String bladeModelGpu;      // BLADE_MODEL_GPU
    private Integer bladeSoLuongGpu;   // BLADE_SL_GPU
    private Integer bladeSoSlotFan;    // BLADE_SOSLOT_FAN
    private Integer bladeSoSlotPci;    // BLADE_SOSLOT_PCI

    // Data Port (DP_)
    private Integer dpSoCong;          // DP_SOCONG
    private String dpTenCong;          // DP_TENCONG
    private String dpLoaiCong;         // DP_LOAICONG
    private Double dpBangThong;        // DP_BANGTHONG
    private Long dpTtModuleId;         // DP_TTMODULE_ID
    private Long dpMucDichSdId;        // DP_MUCDICHSD_ID

    // Power Port (PP_)
    private Integer ppSoCong;          // PP_SOCONG
    private String ppTenCong;          // PP_TENCONG
    private Double ppDongDien;         // PP_DONGDIEN
    private Double ppDienAp;           // PP_DIENAP
    private Integer ppLoaiNd;          // PP_LOAIND  (1 pha / 3 pha)
    private Long ppChuanDnId;          // PP_CHUANDN_ID

    // Control Port (CP_)
    private Long cpMucDichSdId;        // CP_MUCDICHSD_ID
    private String cpLoaiCong;         // CP_LOAICONG
    private Integer cpSoLuong;              // CP_SL
    private String cpTenCong;          // CP_TENCONG

    // Khác
    private Integer soLuongULD;        // SOLUONG_U_LD
    private String moTa;               // MOTA
    private Integer suDung;            // SUDUNG
    private String ghiChu;             // GHICHU

    private String nguoiCn;            // NGUOI_CN (ghi nhận người cập nhật)
    private LocalDateTime ngayCn;      // NGAY_CN (ghi nhận thời điểm cập nhật)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelThietBi() {
        return modelThietBi;
    }

    public void setModelThietBi(String modelThietBi) {
        this.modelThietBi = modelThietBi;
    }

    public String getMaVt() {
        return maVt;
    }

    public void setMaVt(String maVt) {
        this.maVt = maVt;
    }

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Double getCongSuat() {
        return congSuat;
    }

    public void setCongSuat(Double congSuat) {
        this.congSuat = congSuat;
    }

    public Integer getFullDepth() {
        return fullDepth;
    }

    public void setFullDepth(Integer fullDepth) {
        this.fullDepth = fullDepth;
    }

    public Integer getBladeSoKhe() {
        return bladeSoKhe;
    }

    public void setBladeSoKhe(Integer bladeSoKhe) {
        this.bladeSoKhe = bladeSoKhe;
    }

    public String getBladeModelGpu() {
        return bladeModelGpu;
    }

    public void setBladeModelGpu(String bladeModelGpu) {
        this.bladeModelGpu = bladeModelGpu;
    }

    public Integer getBladeSoLuongGpu() {
        return bladeSoLuongGpu;
    }

    public void setBladeSoLuongGpu(Integer bladeSoLuongGpu) {
        this.bladeSoLuongGpu = bladeSoLuongGpu;
    }

    public Integer getBladeSoSlotFan() {
        return bladeSoSlotFan;
    }

    public void setBladeSoSlotFan(Integer bladeSoSlotFan) {
        this.bladeSoSlotFan = bladeSoSlotFan;
    }

    public Integer getBladeSoSlotPci() {
        return bladeSoSlotPci;
    }

    public void setBladeSoSlotPci(Integer bladeSoSlotPci) {
        this.bladeSoSlotPci = bladeSoSlotPci;
    }

    public Integer getDpSoCong() {
        return dpSoCong;
    }

    public void setDpSoCong(Integer dpSoCong) {
        this.dpSoCong = dpSoCong;
    }

    public String getDpTenCong() {
        return dpTenCong;
    }

    public void setDpTenCong(String dpTenCong) {
        this.dpTenCong = dpTenCong;
    }

    public String getDpLoaiCong() {
        return dpLoaiCong;
    }

    public void setDpLoaiCong(String dpLoaiCong) {
        this.dpLoaiCong = dpLoaiCong;
    }

    public Double getDpBangThong() {
        return dpBangThong;
    }

    public void setDpBangThong(Double dpBangThong) {
        this.dpBangThong = dpBangThong;
    }

    public Long getDpTtModuleId() {
        return dpTtModuleId;
    }

    public void setDpTtModuleId(Long dpTtModuleId) {
        this.dpTtModuleId = dpTtModuleId;
    }

    public Long getDpMucDichSdId() {
        return dpMucDichSdId;
    }

    public void setDpMucDichSdId(Long dpMucDichSdId) {
        this.dpMucDichSdId = dpMucDichSdId;
    }

    public Integer getPpSoCong() {
        return ppSoCong;
    }

    public void setPpSoCong(Integer ppSoCong) {
        this.ppSoCong = ppSoCong;
    }

    public String getPpTenCong() {
        return ppTenCong;
    }

    public void setPpTenCong(String ppTenCong) {
        this.ppTenCong = ppTenCong;
    }

    public Double getPpDongDien() {
        return ppDongDien;
    }

    public void setPpDongDien(Double ppDongDien) {
        this.ppDongDien = ppDongDien;
    }

    public Double getPpDienAp() {
        return ppDienAp;
    }

    public void setPpDienAp(Double ppDienAp) {
        this.ppDienAp = ppDienAp;
    }

    public Integer getPpLoaiNd() {
        return ppLoaiNd;
    }

    public void setPpLoaiNd(Integer ppLoaiNd) {
        this.ppLoaiNd = ppLoaiNd;
    }

    public Long getPpChuanDnId() {
        return ppChuanDnId;
    }

    public void setPpChuanDnId(Long ppChuanDnId) {
        this.ppChuanDnId = ppChuanDnId;
    }

    public Long getCpMucDichSdId() {
        return cpMucDichSdId;
    }

    public void setCpMucDichSdId(Long cpMucDichSdId) {
        this.cpMucDichSdId = cpMucDichSdId;
    }

    public String getCpLoaiCong() {
        return cpLoaiCong;
    }

    public void setCpLoaiCong(String cpLoaiCong) {
        this.cpLoaiCong = cpLoaiCong;
    }

    public Integer getCpSoLuong() {
        return cpSoLuong;
    }

    public String getCpTenCong() {
        return cpTenCong;
    }

    public void setCpTenCong(String cpTenCong) {
        this.cpTenCong = cpTenCong;
    }

    public Integer getSoLuongULD() {
        return soLuongULD;
    }

    public void setSoLuongULD(Integer soLuongULD) {
        this.soLuongULD = soLuongULD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getSuDung() {
        return suDung;
    }

    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public LocalDateTime getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(LocalDateTime ngayCn) {
        this.ngayCn = ngayCn;
    }
}