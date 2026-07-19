package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanhMucLoaiThietBiHeThongDtoOut {

    @JsonProperty("id")
    private Long id; // map từ LOAITBI_ID

    @JsonProperty("ten")
    private String ten; // map từ LOAI_TBI

    @JsonProperty("mang_thiet_bi_id")
    private Long mangThietBiId; // map từ NHOMTBI_ID trong DB

    @JsonProperty("hieu_luc")
    private Integer hieuLuc; // map từ SUDUNG

    @JsonProperty("ghi_chu")
    private String ghiChu; // map từ GHICHU


    // getters & setters
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

    public Integer getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getMangThietBiId() {
        return mangThietBiId;
    }

    public void setMangThietBiId(Long mangThietBiId) {
        this.mangThietBiId = mangThietBiId;
    }
}