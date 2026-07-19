package vn.vnpt.vnptit.ecms.dto.hatang;

import java.util.Date;

/**
 * DTO tìm kiếm danh sách phiếu yêu cầu tài nguyên.
 * Tương ứng với tham số của SP_GET_DS_PHIEU_YEU_CAU_TN.
 */
public class SearchPhieuYeuCauTnDto {

    /** Mã phiếu (LIKE, bỏ qua nếu null) */
    private String maPhieu;

    /** Người yêu cầu (LIKE, bỏ qua nếu null) */
    private String nguoiYc;

    /** Đơn vị chủ quản ID (bỏ qua nếu null) */
    private Long donViCqId;

    /** SPDV hạ tầng ID (bỏ qua nếu null) */
    private Long spdvId;

    /** Cụm SPDV ID (bỏ qua nếu null) */
    private Long cumSpdvId;

    /** Loại hạ tầng ID (bỏ qua nếu null) */
    private Long loaiHtId;

    /** Nội dung phiếu (LIKE, bỏ qua nếu null) */
    private String noiDung;

    /** Đơn vị yêu cầu ID (bỏ qua nếu null) */
    private Long donViYcId;

    /**
     * Loại yêu cầu: 0=Tất cả, 1=Tăng, 2=Giảm, 3=Gia hạn, 4=Hỗ trợ.
     * Mặc định: 0
     */
    private Long loaiYcId = 0L;

    /**
     * Trạng thái: 0=Tất cả, 1..6.
     * Mặc định: 0
     */
    private Long trangThai = 0L;

    /**
     * Hiệu lực: 0=Tất cả, 1=Hoạt động (SUDUNG=1), 2=Không hoạt động.
     * Mặc định: 1
     */
    private Long hieuLuc = 1L;

    /**
     * Tính phí: 0=Tất cả, 1=Tính phí, 2=Không tính phí.
     * Mặc định: 0
     */
    private Long tinhPhi = 0L;

    /** Từ ngày yêu cầu (bỏ qua nếu null) */
    private Date tuNgayYc;

    /** Đến ngày yêu cầu (bỏ qua nếu null) */
    private Date denNgayYc;

    /** Từ ngày hoàn thành (bỏ qua nếu null) */
    private Date tuNgayHt;

    /** Đến ngày hoàn thành (bỏ qua nếu null) */
    private Date denNgayHt;

    // ===== Getters & Setters =====

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNguoiYc() {
        return nguoiYc;
    }

    public void setNguoiYc(String nguoiYc) {
        this.nguoiYc = nguoiYc;
    }

    public Long getDonViCqId() {
        return donViCqId;
    }

    public void setDonViCqId(Long donViCqId) {
        this.donViCqId = donViCqId;
    }

    public Long getSpdvId() {
        return spdvId;
    }

    public void setSpdvId(Long spdvId) {
        this.spdvId = spdvId;
    }

    public Long getCumSpdvId() {
        return cumSpdvId;
    }

    public void setCumSpdvId(Long cumSpdvId) {
        this.cumSpdvId = cumSpdvId;
    }

    public Long getLoaiHtId() {
        return loaiHtId;
    }

    public void setLoaiHtId(Long loaiHtId) {
        this.loaiHtId = loaiHtId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getDonViYcId() {
        return donViYcId;
    }

    public void setDonViYcId(Long donViYcId) {
        this.donViYcId = donViYcId;
    }

    public Long getLoaiYcId() {
        return loaiYcId;
    }

    public void setLoaiYcId(Long loaiYcId) {
        this.loaiYcId = loaiYcId;
    }

    public Long getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Long trangThai) {
        this.trangThai = trangThai;
    }

    public Long getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Long hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Long tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Date getTuNgayYc() {
        return tuNgayYc;
    }

    public void setTuNgayYc(Date tuNgayYc) {
        this.tuNgayYc = tuNgayYc;
    }

    public Date getDenNgayYc() {
        return denNgayYc;
    }

    public void setDenNgayYc(Date denNgayYc) {
        this.denNgayYc = denNgayYc;
    }

    public Date getTuNgayHt() {
        return tuNgayHt;
    }

    public void setTuNgayHt(Date tuNgayHt) {
        this.tuNgayHt = tuNgayHt;
    }

    public Date getDenNgayHt() {
        return denNgayHt;
    }

    public void setDenNgayHt(Date denNgayHt) {
        this.denNgayHt = denNgayHt;
    }
}
