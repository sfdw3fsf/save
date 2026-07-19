package vn.vnpt.vnptit.ecms.dto.hatang;

public class CumDichVuDto {
    private Long id;
    private String ten;
    private String spdvId;
    private String ghiChu;
    private Integer hieuLuc;

    public CumDichVuDto() {
    }

    public CumDichVuDto(Long id, String ten, String spdvId, String ghiChu, Integer hieuLuc) {
        this.id = id;
        this.ten = ten;
        this.spdvId = spdvId;
        this.ghiChu = ghiChu;
        this.hieuLuc = hieuLuc;
    }

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

    public String getSpdvId() {
        return spdvId;
    }

    public void setSpdvId(String spdvId) {
        this.spdvId = spdvId;
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
}
