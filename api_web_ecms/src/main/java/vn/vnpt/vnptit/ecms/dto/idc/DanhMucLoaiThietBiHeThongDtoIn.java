package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanhMucLoaiThietBiHeThongDtoIn {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("ten")
    private String ten;

    @JsonProperty("mang_thiet_bi_id")
    private Long mangThietBiId;

    @JsonProperty("hieu_luc")
    private Integer hieuLuc;

    @JsonProperty("ghi_chu")
    private String ghiChu;

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

    public Long getMangThietBiId() {
        return mangThietBiId;
    }
    public void setMangThietBiId(Long mangThietBiId) {
        this.mangThietBiId = mangThietBiId;
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
}