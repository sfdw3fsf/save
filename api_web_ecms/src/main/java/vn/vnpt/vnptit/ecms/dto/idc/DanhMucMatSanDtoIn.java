package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


public class DanhMucMatSanDtoIn {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("toanha_id")
    private Long idToaNha;

    @JsonProperty("toa_nha")
    private String toaNha;

    @JsonProperty("dien_tich")
    private Double dienTich;

    @JsonProperty("chieu_dai")
    private Double chieuDai;

    @JsonProperty("chieu_rong")
    private Double chieuRong;

    @JsonProperty("ghi_chu")
    private String ghiChu;

    @JsonProperty("ten")
    private String ten;

    @JsonProperty("ten_tat")
    private String tenTat;

    @JsonProperty("mo_ta")
    private String moTa;

    @JsonProperty("su_dung")
    private Integer suDung;

    @JsonProperty("hinh_anh")
    private String hinhAnh;

    @JsonProperty("trangthai_vh")
    private Integer trangThaiVH;


    @JsonProperty("ngay_sd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date ngaySd;

    @JsonProperty("vi_tri")
    private String viTri;

    @JsonProperty("tam_san_rong")
    private Double tamSanRong;

    @JsonProperty("tam_san_dai")
    private Double tamSanDai;

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public Double getTamSanDai() {
        return tamSanDai;
    }
    public void setTamSanDai(Double tamSanDai) {
        this.tamSanDai = tamSanDai;
    }
    public Double getTamSanRong() {
        return tamSanRong;
    }
    public void setTamSanRong(Double tamSanRong) {
        this.tamSanRong = tamSanRong;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Date getNgaySd() {
        return ngaySd;
    }

    public void setNgaySd(Date ngaySd) {
        this.ngaySd = ngaySd;
    }

    public Integer getTrangThaiVH() {
        return trangThaiVH;
    }

    public void setTrangThaiVH(Integer trangThaiVH) {
        this.trangThaiVH = trangThaiVH;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getToaNha() {
        return toaNha;
    }

    public void setToaNha(String toaNha) {
        this.toaNha = toaNha;
    }

    public Integer getSuDung() {
        return suDung;
    }

    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Double getChieuDai() {
        return chieuDai;
    }

    public void setChieuDai(Double chieuDai) {
        this.chieuDai = chieuDai;
    }

    public Double getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(Double chieuRong) {
        this.chieuRong = chieuRong;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public Long getIdToaNha() {
        return idToaNha;
    }

    public void setIdToaNha(Long idToaNha) {
        this.idToaNha = idToaNha;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}