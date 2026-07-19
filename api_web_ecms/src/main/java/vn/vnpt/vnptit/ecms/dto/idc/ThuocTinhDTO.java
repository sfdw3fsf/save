package vn.vnpt.vnptit.ecms.dto.idc;

public class ThuocTinhDTO {
    private String kieuTT;     // p_kieu_tt
    private Long duLieuId;   // p_dulieu_id
    private String ten;        // p_ten
    private String giaTri;     // p_giatri
    private Integer thuTu;     // p_thutu
    private String nguoiCN;    // p_nguoi_cn

    public ThuocTinhDTO() {
    }

    public ThuocTinhDTO(String kieuTT, Long duLieuId, String ten, String giaTri, Integer thuTu, String nguoiCN) {
        this.kieuTT = kieuTT;
        this.duLieuId = duLieuId;
        this.ten = ten;
        this.giaTri = giaTri;
        this.thuTu = thuTu;
        this.nguoiCN = nguoiCN;
    }

    public String getKieuTT() {
        return kieuTT;
    }

    public void setKieuTT(String kieuTT) {
        this.kieuTT = kieuTT;
    }

    public Long getDuLieuId() {
        return duLieuId;
    }

    public void setDuLieuId(Long duLieuId) {
        this.duLieuId = duLieuId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(String giaTri) {
        this.giaTri = giaTri;
    }

    public Integer getThuTu() {
        return thuTu;
    }

    public void setThuTu(Integer thuTu) {
        this.thuTu = thuTu;
    }

    public String getNguoiCN() {
        return nguoiCN;
    }

    public void setNguoiCN(String nguoiCN) {
        this.nguoiCN = nguoiCN;
    }
}