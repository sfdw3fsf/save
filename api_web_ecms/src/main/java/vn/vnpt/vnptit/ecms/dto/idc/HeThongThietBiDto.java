package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeThongThietBiDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("ten")
    private String ten;

    @JsonProperty("idcId")
    private Integer idcId;

    @JsonProperty("donviQlId")
    private Integer donviQlId;

    @JsonProperty("nhanvienQlId")
    private Integer nhanvienQlId;

    @JsonProperty("trangthaiVhId")
    private Integer trangthaiVhId;

    @JsonProperty("mota")
    private String mota;

    @JsonProperty("ghichu")
    private String ghichu;

    @JsonProperty("ngayLd")
    private Date ngayLd;

    @JsonProperty("sudung")
    private Integer sudung;

    @JsonProperty("trangThaiTaiSanId")
    private Integer trangThaiTaiSanId;

    @JsonProperty("mangThietBiId")
    private Integer mangThietBiId;

    // Constructors
    public HeThongThietBiDto() {
    }

    public HeThongThietBiDto(Integer id, String ten, Integer idcId, Integer donviQlId, Integer nhanvienQlId,
                             Integer trangthaiVhId, String mota, String ghichu, Date ngayLd, Integer sudung, Integer trangThaiTaiSanId) {
        this.id = id;
        this.ten = ten;
        this.idcId = idcId;
        this.donviQlId = donviQlId;
        this.nhanvienQlId = nhanvienQlId;
        this.trangthaiVhId = trangthaiVhId;
        this.mota = mota;
        this.ghichu = ghichu;
        this.ngayLd = ngayLd;
        this.sudung = sudung;
        this.trangThaiTaiSanId = trangThaiTaiSanId;
    }

    public HeThongThietBiDto(Integer id, String ten, Integer idcId, Integer donviQlId, Integer nhanvienQlId,
                             Integer trangthaiVhId, String mota, String ghichu, Date ngayLd, Integer sudung, Integer trangThaiTaiSanId, Integer mangThietBiId) {
        this.id = id;
        this.ten = ten;
        this.idcId = idcId;
        this.donviQlId = donviQlId;
        this.nhanvienQlId = nhanvienQlId;
        this.trangthaiVhId = trangthaiVhId;
        this.mota = mota;
        this.ghichu = ghichu;
        this.ngayLd = ngayLd;
        this.sudung = sudung;
        this.trangThaiTaiSanId = trangThaiTaiSanId;
        this.mangThietBiId = mangThietBiId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getIdcId() {
        return idcId;
    }

    public void setIdcId(Integer idcId) {
        this.idcId = idcId;
    }

    public Integer getDonviQlId() {
        return donviQlId;
    }

    public void setDonviQlId(Integer donviQlId) {
        this.donviQlId = donviQlId;
    }

    public Integer getNhanvienQlId() {
        return nhanvienQlId;
    }

    public void setNhanvienQlId(Integer nhanvienQlId) {
        this.nhanvienQlId = nhanvienQlId;
    }

    public Integer getTrangthaiVhId() {
        return trangthaiVhId;
    }

    public void setTrangthaiVhId(Integer trangthaiVhId) {
        this.trangthaiVhId = trangthaiVhId;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Date getNgayLd() {
        return ngayLd;
    }

    public void setNgayLd(Date ngayLd) {
        this.ngayLd = ngayLd;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Integer getTrangThaiTaiSanId() {
        return trangThaiTaiSanId;
    }

    public void setTrangThaiTaiSanId(Integer trangThaiTaiSanId) {
        this.trangThaiTaiSanId = trangThaiTaiSanId;
    }

    public Integer getMangThietBiId() {
        return mangThietBiId;
    }

    public void setMangThietBiId(Integer mangThietBiId) {
        this.mangThietBiId = mangThietBiId;
    }
}
