package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HaTangCnttThongTinChungDTO {

    // --- KHOI KHAI BAO BIEN (FIELDS) ---
    private Long id;
    private Long chungLoaiThietBiId;
    private Long loaiThietBiId;
    private String ten;
    private String kyHieu;
    private Integer loaiKhachHang;
    private String ghiChu;
    private Integer hieuLuc;
    private String serialNumber;
    private String model;
    private Long hangSxId;
    private Long nhaCungCapId;
    private Long heDieuHanhId;
    private Integer csDinhDanh;
    private Integer csThucTe;
    private Long idcId;
    private Long upUnitId;
    private Long downUnitId;
    private Long donViQLyId;
    private Long phongBanQLyId;
    private Long nguoiQLyId;
    private Long toanhaId;
    private Long matSanId;
    private Long phongId;
    private Long idcRackId;
    private Long heThongTbiId;
    private Long mangTbiId;
    private String iconPath;
    private String canCuLd;
    private Long trangThaiTsId;
    private String maTs;
    private Integer tsNoiBo;
    private String moTa;
    private String nguoiCn;
    private java.util.Date ngayCn;
    private Integer tinhId;
    private Long hdtbId;
    private Integer thietBiIdc;
    private Integer toaDoX;
    private Integer toaDoY;
    private Long longQuayId;
    private Integer matRackUnit;
    private Long loaiTbiId;
    private Long chungLoaiTbiId;
    private String ipQuanTri;
    private Integer doiTuongKt;
    private Long trangThaiVhId;
    private Integer fullDepth;
    private Long loaiVtId;
    private Long nhaccId;
    private Integer slRackUnit;
    private Integer canhRackUnit;
    private Integer dai;
    private Integer rong;
    private Integer cao;
    private Integer daDinhDanh;
    private Integer loaiNdien;
    private Integer ddDinhDanh;
    private Integer loaiMay;
    private Integer ddThucTe;
    private String maDa;
    private String hanBaoHanh;
    private String ngayLapDat;
    private String ngaySuDung;
    private String tenDa;
    private String ngayKh;
    private Long taiSanId;
    private Long chuQuanId;
    private String maTbi;
    private String maVtu;
    private Long phanLoaiTbiId;
    @JsonProperty("heSoCosPhi")
    private Long cosPhi;
    private Long tyLeSuDung;
    private String cauHinhHeThong;
    private Long kieuLapDat;

    // --- MANG DANH SACH O CAM MOI THEM VAO ---
    private List<ThongTinOCamDTO> danhSachOCam;

    // --- INNER CLASS CHO O CAM (Hoac tach ra file rieng) ---
    public static class ThongTinOCamDTO {
        private Long loaiOCamId;
        private String ten;
        private Integer trangThaiOCam;
        private Integer trangThaiKetNoi;
        private Long thietBiKetNoiId;

        // Getters and Setters cho O Cam
        public Long getLoaiOCamId() {
            return loaiOCamId;
        }

        public void setLoaiOCamId(Long loaiOCamId) {
            this.loaiOCamId = loaiOCamId;
        }

        public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public Integer getTrangThaiOCam() {
            return trangThaiOCam;
        }

        public void setTrangThaiOCam(Integer trangThaiOCam) {
            this.trangThaiOCam = trangThaiOCam;
        }

        public Integer getTrangThaiKetNoi() {
            return trangThaiKetNoi;
        }

        public void setTrangThaiKetNoi(Integer trangThaiKetNoi) {
            this.trangThaiKetNoi = trangThaiKetNoi;
        }

        public Long getThietBiKetNoiId() {
            return thietBiKetNoiId;
        }

        public void setThietBiKetNoiId(Long thietBiKetNoiId) {
            this.thietBiKetNoiId = thietBiKetNoiId;
        }
    }

    // --- GETTERS AND SETTERS ---

    public List<ThongTinOCamDTO> getDanhSachOCam() {
        return danhSachOCam;
    }

    public void setDanhSachOCam(List<ThongTinOCamDTO> danhSachOCam) {
        this.danhSachOCam = danhSachOCam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChungLoaiThietBiId() {
        return chungLoaiThietBiId;
    }

    public void setChungLoaiThietBiId(Long chungLoaiThietBiId) {
        this.chungLoaiThietBiId = chungLoaiThietBiId;
    }

    public Long getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(Long loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getKyHieu() {
        return kyHieu;
    }

    public void setKyHieu(String kyHieu) {
        this.kyHieu = kyHieu;
    }

    public Integer getLoaiKhachHang() {
        return loaiKhachHang;
    }

    public void setLoaiKhachHang(Integer loaiKhachHang) {
        this.loaiKhachHang = loaiKhachHang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Long getNhaCungCapId() {
        return nhaCungCapId;
    }

    public void setNhaCungCapId(Long nhaCungCapId) {
        this.nhaCungCapId = nhaCungCapId;
    }

    public Long getHeDieuHanhId() {
        return heDieuHanhId;
    }

    public void setHeDieuHanhId(Long heDieuHanhId) {
        this.heDieuHanhId = heDieuHanhId;
    }

    public Integer getCsDinhDanh() {
        return csDinhDanh;
    }

    public void setCsDinhDanh(Integer csDinhDanh) {
        this.csDinhDanh = csDinhDanh;
    }

    public Integer getCsThucTe() {
        return csThucTe;
    }

    public void setCsThucTe(Integer csThucTe) {
        this.csThucTe = csThucTe;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getUpUnitId() {
        return upUnitId;
    }

    public void setUpUnitId(Long upUnitId) {
        this.upUnitId = upUnitId;
    }

    public Long getDownUnitId() {
        return downUnitId;
    }

    public void setDownUnitId(Long downUnitId) {
        this.downUnitId = downUnitId;
    }

    public Long getDonViQLyId() {
        return donViQLyId;
    }

    public void setDonViQLyId(Long donViQLyId) {
        this.donViQLyId = donViQLyId;
    }

    public Long getPhongBanQLyId() {
        return phongBanQLyId;
    }

    public void setPhongBanQLyId(Long phongBanQLyId) {
        this.phongBanQLyId = phongBanQLyId;
    }

    public Long getNguoiQLyId() {
        return nguoiQLyId;
    }

    public void setNguoiQLyId(Long nguoiQLyId) {
        this.nguoiQLyId = nguoiQLyId;
    }

    public Long getToanhaId() {
        return toanhaId;
    }

    public void setToanhaId(Long toanhaId) {
        this.toanhaId = toanhaId;
    }

    public Long getMatSanId() {
        return matSanId;
    }

    public void setMatSanId(Long matSanId) {
        this.matSanId = matSanId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public Long getIdcRackId() {
        return idcRackId;
    }

    public void setIdcRackId(Long idcRackId) {
        this.idcRackId = idcRackId;
    }

    public Long getHeThongTbiId() {
        return heThongTbiId;
    }

    public void setHeThongTbiId(Long heThongTbiId) {
        this.heThongTbiId = heThongTbiId;
    }

    public Long getMangTbiId() {
        return mangTbiId;
    }

    public void setMangTbiId(Long mangTbiId) {
        this.mangTbiId = mangTbiId;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getCanCuLd() {
        return canCuLd;
    }

    public void setCanCuLd(String canCuLd) {
        this.canCuLd = canCuLd;
    }

    public Long getTrangThaiTsId() {
        return trangThaiTsId;
    }

    public void setTrangThaiTsId(Long trangThaiTsId) {
        this.trangThaiTsId = trangThaiTsId;
    }

    public String getMaTs() {
        return maTs;
    }

    public void setMaTs(String maTs) {
        this.maTs = maTs;
    }

    public Integer getTsNoiBo() {
        return tsNoiBo;
    }

    public void setTsNoiBo(Integer tsNoiBo) {
        this.tsNoiBo = tsNoiBo;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Date getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(Date ngayCn) {
        this.ngayCn = ngayCn;
    }

    public Integer getTinhId() {
        return tinhId;
    }

    public void setTinhId(Integer tinhId) {
        this.tinhId = tinhId;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Integer getThietBiIdc() {
        return thietBiIdc;
    }

    public void setThietBiIdc(Integer thietBiIdc) {
        this.thietBiIdc = thietBiIdc;
    }

    public Integer getToaDoX() {
        return toaDoX;
    }

    public void setToaDoX(Integer toaDoX) {
        this.toaDoX = toaDoX;
    }

    public Integer getToaDoY() {
        return toaDoY;
    }

    public void setToaDoY(Integer toaDoY) {
        this.toaDoY = toaDoY;
    }

    public Long getLongQuayId() {
        return longQuayId;
    }

    public void setLongQuayId(Long longQuayId) {
        this.longQuayId = longQuayId;
    }

    public Integer getMatRackUnit() {
        return matRackUnit;
    }

    public void setMatRackUnit(Integer matRackUnit) {
        this.matRackUnit = matRackUnit;
    }

    public Long getLoaiTbiId() {
        return loaiTbiId;
    }

    public void setLoaiTbiId(Long loaiTbiId) {
        this.loaiTbiId = loaiTbiId;
    }

    public Long getChungLoaiTbiId() {
        return chungLoaiTbiId;
    }

    public void setChungLoaiTbiId(Long chungLoaiTbiId) {
        this.chungLoaiTbiId = chungLoaiTbiId;
    }

    public String getIpQuanTri() {
        return ipQuanTri;
    }

    public void setIpQuanTri(String ipQuanTri) {
        this.ipQuanTri = ipQuanTri;
    }

    public Integer getDoiTuongKt() {
        return doiTuongKt;
    }

    public void setDoiTuongKt(Integer doiTuongKt) {
        this.doiTuongKt = doiTuongKt;
    }

    public Long getTrangThaiVhId() {
        return trangThaiVhId;
    }

    public void setTrangThaiVhId(Long trangThaiVhId) {
        this.trangThaiVhId = trangThaiVhId;
    }

    public Integer getFullDepth() {
        return fullDepth;
    }

    public void setFullDepth(Integer fullDepth) {
        this.fullDepth = fullDepth;
    }

    public Long getLoaiVtId() {
        return loaiVtId;
    }

    public void setLoaiVtId(Long loaiVtId) {
        this.loaiVtId = loaiVtId;
    }

    public Long getNhaccId() {
        return nhaccId;
    }

    public void setNhaccId(Long nhaccId) {
        this.nhaccId = nhaccId;
    }

    public Integer getSlRackUnit() {
        return slRackUnit;
    }

    public void setSlRackUnit(Integer slRackUnit) {
        this.slRackUnit = slRackUnit;
    }

    public Integer getCanhRackUnit() {
        return canhRackUnit;
    }

    public void setCanhRackUnit(Integer canhRackUnit) {
        this.canhRackUnit = canhRackUnit;
    }

    public Integer getDai() {
        return dai;
    }

    public void setDai(Integer dai) {
        this.dai = dai;
    }

    public Integer getRong() {
        return rong;
    }

    public void setRong(Integer rong) {
        this.rong = rong;
    }

    public Integer getCao() {
        return cao;
    }

    public void setCao(Integer cao) {
        this.cao = cao;
    }

    public Integer getDaDinhDanh() {
        return daDinhDanh;
    }

    public void setDaDinhDanh(Integer daDinhDanh) {
        this.daDinhDanh = daDinhDanh;
    }

    public Integer getLoaiNdien() {
        return loaiNdien;
    }

    public void setLoaiNdien(Integer loaiNdien) {
        this.loaiNdien = loaiNdien;
    }

    public Integer getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(Integer loaiMay) {
        this.loaiMay = loaiMay;
    }

    public Integer getDdDinhDanh() {
        return ddDinhDanh;
    }

    public void setDdDinhDanh(Integer ddDinhDanh) {
        this.ddDinhDanh = ddDinhDanh;
    }

    public Integer getDdThucTe() {
        return ddThucTe;
    }

    public void setDdThucTe(Integer ddThucTe) {
        this.ddThucTe = ddThucTe;
    }

    public String getMaDa() {
        return maDa;
    }

    public void setMaDa(String maDa) {
        this.maDa = maDa;
    }

    public String getHanBaoHanh() {
        return hanBaoHanh;
    }

    public void setHanBaoHanh(String hanBaoHanh) {
        this.hanBaoHanh = hanBaoHanh;
    }

    public String getNgayLapDat() {
        return ngayLapDat;
    }

    public void setNgayLapDat(String ngayLapDat) {
        this.ngayLapDat = ngayLapDat;
    }

    public String getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public String getTenDa() {
        return tenDa;
    }

    public void setTenDa(String tenDa) {
        this.tenDa = tenDa;
    }

    public String getNgayKh() {
        return ngayKh;
    }

    public void setNgayKh(String ngayKh) {
        this.ngayKh = ngayKh;
    }

    public Long getTaiSanId() {
        return taiSanId;
    }

    public void setTaiSanId(Long taiSanId) {
        this.taiSanId = taiSanId;
    }

    public Long getChuQuanId() {
        return chuQuanId;
    }

    public void setChuQuanId(Long chuQuanId) {
        this.chuQuanId = chuQuanId;
    }

    public String getMaTbi() {
        return maTbi;
    }

    public void setMaTbi(String maTbi) {
        this.maTbi = maTbi;
    }

    public String getMaVtu() {
        return maVtu;
    }

    public void setMaVtu(String maVtu) {
        this.maVtu = maVtu;
    }

    public Long getPhanLoaiTbiId() {
        return phanLoaiTbiId;
    }

    public void setPhanLoaiTbiId(Long phanLoaiTbiId) {
        this.phanLoaiTbiId = phanLoaiTbiId;
    }

    public Long getCosPhi() {
        return cosPhi;
    }

    public void setCosPhi(Long cosPhi) {
        this.cosPhi = cosPhi;
    }

    public Long getTyLeSuDung() {
        return tyLeSuDung;
    }

    public void setTyLeSuDung(Long tyLeSuDung) {
        this.tyLeSuDung = tyLeSuDung;
    }

    public String getCauHinhHeThong() {
        return cauHinhHeThong;
    }

    public void setCauHinhHeThong(String cauHinhHeThong) {
        this.cauHinhHeThong = cauHinhHeThong;
    }

    public Long getKieuLapDat() {
        return kieuLapDat;
    }

    public void setKieuLapDat(Long kieuLapDat) {
        this.kieuLapDat = kieuLapDat;
    }
}