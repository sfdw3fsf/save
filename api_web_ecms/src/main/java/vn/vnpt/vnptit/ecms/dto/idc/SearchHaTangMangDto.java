package vn.vnpt.vnptit.ecms.dto.idc;

public class SearchHaTangMangDto {
    private String ten;
    private Long idcId;
    private Long trangThaiCCDVId;
    private Long donViQuanLyId;
    private Long phongQuanLyId;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }


    public Long getPhongQuanLyId() {
        return phongQuanLyId;
    }

    public void setPhongQuanLyId(Long phongQuanLyId) {
        this.phongQuanLyId = phongQuanLyId;
    }

    public Long getDonViQuanLyId() {
        return donViQuanLyId;
    }

    public void setDonViQuanLyId(Long donViQuanLyId) {
        this.donViQuanLyId = donViQuanLyId;
    }

    public Long getTrangThaiCCDVId() {
        return trangThaiCCDVId;
    }

    public void setTrangThaiCCDVId(Long trangThaiCCDVId) {
        this.trangThaiCCDVId = trangThaiCCDVId;
    }
}