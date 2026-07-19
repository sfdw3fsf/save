package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class tainguyenTANGGIAMtheoYCDTO {

    @JsonProperty("p_ma_VPC")
    private String maVpc;

    @JsonProperty("p_ma_MayAo")
    private String maMayAo;

    @JsonProperty("p_id_VPC")
    private Long idXemChiTiet;

    @JsonProperty("p_SPDV_ID")
    private Long spdvId;

    @JsonProperty("p_LOAIHT_ID")
    private Long loaiHtId;

    @JsonProperty("p_CUMSPDV_ID")
    private Long cumSpdvId;

    @JsonProperty("p_NOIDUNG")
    private String noiDung;

    @JsonProperty("p_LOAIYC_ID")
    private Long loaiYcId;

    @JsonProperty("p_TRANGTHAIPH_ID")
    private Long trangThaiPhId;

    @JsonProperty("p_NGAY_YC")
    private String ngayYc;

    @JsonProperty("p_NGAY_HT")
    private String ngayHt;

    @JsonProperty("p_FILE_DINHKEM")
    private String fileDinhKem;

    @JsonProperty("p_MOTA")
    private String moTa;

    @JsonProperty("p_SUDUNG")
    private Long suDung;

    @JsonProperty("p_GHICHU")
    private String ghiChu;

    @JsonProperty("p_NGUOI_CN")
    private String nguoiCn;

    @JsonProperty("p_NGAY_CN")
    private String ngayCn;

    @JsonProperty("p_PHIEUYC_ID")
    private Long phieuYcId;

    @JsonProperty("p_MA_DICHVU")
    private String maDichVu;

    @JsonProperty("p_TRANGTHAICP_ID")
    private Long trangThaiCpId;

    @JsonProperty("p_SO_LANGUI")
    private Long soLanGui;

    @JsonProperty("p_CUMHT_ID")
    private Long cumHtId;

    @JsonProperty("p_NGAY_HH")
    private String ngayHh;

    @JsonProperty("p_NGAY_GH")
    private String ngayGh;

    @JsonAlias({ "p_NHANVIEN_ID", "p_NHANVIEN_YC_ID", "p_NHANVIEN_QL_ID" })
    private Long nhanVienId;

    @JsonProperty("p_TINHPHI")
    private Long tinhPhi;

    @JsonAlias({ "p_DONVI_ID", "p_DONVI_CQ_ID", "p_DONVI_YC_ID", "p_DONVI_QL_ID" })
    private Long donViId;

    @JsonProperty("p_PHIEUYCDV_ID")
    private Long phieuYcDvId;

    @JsonProperty("p_TEN_DANGNHAP")
    private String tenDangNhap;

    @JsonProperty("p_SLHT_CORECPU")
    private Long slhtCoreCpu;

    @JsonProperty("p_DLHT_RAM")
    private Long dlhtRam;

    @JsonProperty("p_DLHT_GPU")
    private Long dlhtGpu;

    @JsonProperty("p_DLHT_SSD")
    private Long dlhtSsd;

    @JsonProperty("p_DLHT_HDD")
    private Long dlhtHdd;

    @JsonProperty("p_DLHT_NVME")
    private Long dlhtNvme;

    @JsonProperty("p_SLYC_CORECPU")
    private Long slycCoreCpu;

    @JsonProperty("p_DLYC_RAM")
    private Long dlycRam;

    @JsonProperty("p_DLYC_GPU")
    private Long dlycGpu;

    @JsonProperty("p_DLYC_SSD")
    private Long dlycSsd;

    @JsonProperty("p_DLYC_HDD")
    private Long dlycHdd;

    @JsonProperty("p_DLYC_NVME")
    private Long dlycNvme;

    @JsonProperty("p_SLCP_CORECPU")
    private Long slcpCoreCpu;

    @JsonProperty("p_DLCP_RAM")
    private Long dlcpRam;

    @JsonProperty("p_DLCP_GPU")
    private Long dlcpGpu;

    @JsonProperty("p_DLCP_SSD")
    private Long dlcpSsd;

    @JsonProperty("p_DLCP_HDD")
    private Long dlcpHdd;

    @JsonProperty("p_DLCP_NVME")
    private Long dlcpNvme;

    @JsonProperty("p_HEDIEUHANH_ID")
    private Long heDieuHanhId;

    @JsonProperty("p_KIENTRUC")
    private String kienTruc;

    @JsonProperty("p_VPC")
    private String vpc;

    @JsonProperty("p_MAYAO_ID")
    private Long mayAoId;

    public tainguyenTANGGIAMtheoYCDTO() {
    }

    public tainguyenTANGGIAMtheoYCDTO(String maVpc) {
        this.maVpc = maVpc;
    }

    public String getMaVpc() {
        return maVpc;
    }

    public void setMaVpc(String maVpc) {
        this.maVpc = maVpc;
    }

    public String getMaMayAo() {
        return maMayAo;
    }

    public void setMaMayAo(String maMayAo) {
        this.maMayAo = maMayAo;
    }

    public Long getIdXemChiTiet() {
        return idXemChiTiet;
    }

    public void setIdXemChiTiet(Long idXemChiTiet) {
        this.idXemChiTiet = idXemChiTiet;
    }

    public Long getSpdvId() {
        return spdvId;
    }

    public void setSpdvId(Long spdvId) {
        this.spdvId = spdvId;
    }

    public Long getLoaiHtId() {
        return loaiHtId;
    }

    public void setLoaiHtId(Long loaiHtId) {
        this.loaiHtId = loaiHtId;
    }

    public Long getCumSpdvId() {
        return cumSpdvId;
    }

    public void setCumSpdvId(Long cumSpdvId) {
        this.cumSpdvId = cumSpdvId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getLoaiYcId() {
        return loaiYcId;
    }

    public void setLoaiYcId(Long loaiYcId) {
        this.loaiYcId = loaiYcId;
    }

    public Long getTrangThaiPhId() {
        return trangThaiPhId;
    }

    public void setTrangThaiPhId(Long trangThaiPhId) {
        this.trangThaiPhId = trangThaiPhId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getNgayYc() {
        return ngayYc;
    }

    public void setNgayYc(String ngayYc) {
        this.ngayYc = ngayYc;
    }

    public String getNgayHt() {
        return ngayHt;
    }

    public void setNgayHt(String ngayHt) {
        this.ngayHt = ngayHt;
    }

    public String getFileDinhKem() {
        return fileDinhKem;
    }

    public void setFileDinhKem(String fileDinhKem) {
        this.fileDinhKem = fileDinhKem;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getSuDung() {
        return suDung;
    }

    public void setSuDung(Long suDung) {
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

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public Long getPhieuYcId() {
        return phieuYcId;
    }

    public void setPhieuYcId(Long phieuYcId) {
        this.phieuYcId = phieuYcId;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public Long getTrangThaiCpId() {
        return trangThaiCpId;
    }

    public void setTrangThaiCpId(Long trangThaiCpId) {
        this.trangThaiCpId = trangThaiCpId;
    }

    public Long getSoLanGui() {
        return soLanGui;
    }

    public void setSoLanGui(Long soLanGui) {
        this.soLanGui = soLanGui;
    }

    public Long getCumHtId() {
        return cumHtId;
    }

    public void setCumHtId(Long cumHtId) {
        this.cumHtId = cumHtId;
    }

    public String getNgayHh() {
        return ngayHh;
    }

    public void setNgayHh(String ngayHh) {
        this.ngayHh = ngayHh;
    }

    public String getNgayGh() {
        return ngayGh;
    }

    public void setNgayGh(String ngayGh) {
        this.ngayGh = ngayGh;
    }

    public Long getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Long tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getPhieuYcDvId() {
        return phieuYcDvId;
    }

    public void setPhieuYcDvId(Long phieuYcDvId) {
        this.phieuYcDvId = phieuYcDvId;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public Long getSlhtCoreCpu() {
        return slhtCoreCpu;
    }

    public void setSlhtCoreCpu(Long slhtCoreCpu) {
        this.slhtCoreCpu = slhtCoreCpu;
    }

    public Long getDlhtRam() {
        return dlhtRam;
    }

    public void setDlhtRam(Long dlhtRam) {
        this.dlhtRam = dlhtRam;
    }

    public Long getDlhtGpu() {
        return dlhtGpu;
    }

    public void setDlhtGpu(Long dlhtGpu) {
        this.dlhtGpu = dlhtGpu;
    }

    public Long getDlhtSsd() {
        return dlhtSsd;
    }

    public void setDlhtSsd(Long dlhtSsd) {
        this.dlhtSsd = dlhtSsd;
    }

    public Long getDlhtHdd() {
        return dlhtHdd;
    }

    public void setDlhtHdd(Long dlhtHdd) {
        this.dlhtHdd = dlhtHdd;
    }

    public Long getDlhtNvme() {
        return dlhtNvme;
    }

    public void setDlhtNvme(Long dlhtNvme) {
        this.dlhtNvme = dlhtNvme;
    }

    public Long getSlycCoreCpu() {
        return slycCoreCpu;
    }

    public void setSlycCoreCpu(Long slycCoreCpu) {
        this.slycCoreCpu = slycCoreCpu;
    }

    public Long getDlycRam() {
        return dlycRam;
    }

    public void setDlycRam(Long dlycRam) {
        this.dlycRam = dlycRam;
    }

    public Long getDlycGpu() {
        return dlycGpu;
    }

    public void setDlycGpu(Long dlycGpu) {
        this.dlycGpu = dlycGpu;
    }

    public Long getDlycSsd() {
        return dlycSsd;
    }

    public void setDlycSsd(Long dlycSsd) {
        this.dlycSsd = dlycSsd;
    }

    public Long getDlycHdd() {
        return dlycHdd;
    }

    public void setDlycHdd(Long dlycHdd) {
        this.dlycHdd = dlycHdd;
    }

    public Long getDlycNvme() {
        return dlycNvme;
    }

    public void setDlycNvme(Long dlycNvme) {
        this.dlycNvme = dlycNvme;
    }

    public Long getSlcpCoreCpu() {
        return slcpCoreCpu;
    }

    public void setSlcpCoreCpu(Long slcpCoreCpu) {
        this.slcpCoreCpu = slcpCoreCpu;
    }

    public Long getDlcpRam() {
        return dlcpRam;
    }

    public void setDlcpRam(Long dlcpRam) {
        this.dlcpRam = dlcpRam;
    }

    public Long getDlcpGpu() {
        return dlcpGpu;
    }

    public void setDlcpGpu(Long dlcpGpu) {
        this.dlcpGpu = dlcpGpu;
    }

    public Long getDlcpSsd() {
        return dlcpSsd;
    }

    public void setDlcpSsd(Long dlcpSsd) {
        this.dlcpSsd = dlcpSsd;
    }

    public Long getDlcpHdd() {
        return dlcpHdd;
    }

    public void setDlcpHdd(Long dlcpHdd) {
        this.dlcpHdd = dlcpHdd;
    }

    public Long getDlcpNvme() {
        return dlcpNvme;
    }

    public void setDlcpNvme(Long dlcpNvme) {
        this.dlcpNvme = dlcpNvme;
    }

    public Long getHeDieuHanhId() {
        return heDieuHanhId;
    }

    public void setHeDieuHanhId(Long heDieuHanhId) {
        this.heDieuHanhId = heDieuHanhId;
    }

    public String getKienTruc() {
        return kienTruc;
    }

    public void setKienTruc(String kienTruc) {
        this.kienTruc = kienTruc;
    }

    public String getVpc() {
        return vpc;
    }

    public void setVpc(String vpc) {
        this.vpc = vpc;
    }

    public Long getMayAoId() {
        return mayAoId;
    }

    public void setMayAoId(Long mayAoId) {
        this.mayAoId = mayAoId;
    }
}
