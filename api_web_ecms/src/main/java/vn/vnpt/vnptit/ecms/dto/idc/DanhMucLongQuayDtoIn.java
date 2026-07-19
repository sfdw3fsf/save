package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanhMucLongQuayDtoIn {
    @JsonProperty("longquay_id")
    private Long longQuayId;

    @JsonProperty("loai_lq_id")
    private Long loaiLqId;

    @JsonProperty("phong_id")
    private Long phongId;

    @JsonProperty("ten")
    private String ten;

    @JsonProperty("chieu_dai")
    private Double chieuDai;

    @JsonProperty("chieu_rong")
    private Double chieuRong;

    @JsonProperty("chieu_cao")
    private Double chieuCao;

    @JsonProperty("camera")
    private Integer camera;

    @JsonProperty("khoa_id")
    private Long khoaId;

    @JsonProperty("trangthai_vh")
    private Integer trangThaiVh;

    @JsonProperty("ghi_chu")
    private String ghiChu;

    @JsonProperty("ten_tat")
    private String tenTat;

    @JsonProperty("mo_ta")
    private String moTa;

    @JsonProperty("su_dung")
    private Integer suDung;

    @JsonProperty("toa_do_x")
    private Double toaDoX;

    @JsonProperty("toa_do_y")
    private Double toaDoY;

    @JsonProperty("nguoi_cn")
    private String nguoiCn;

    @JsonProperty("ds_rack")
    private String dsRack;

    @JsonProperty("idc_id")
    private Long idcId;

    @JsonProperty("donvi_sh")
    private Long donviSHId;

    public Long getLongQuayId() {
        return longQuayId;
    }

    public void setLongQuayId(Long longQuayId) {
        this.longQuayId = longQuayId;
    }


    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getLoaiLqId() {
        return loaiLqId;
    }

    public void setLoaiLqId(Long loaiLqId) {
        this.loaiLqId = loaiLqId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public Double getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Double chieuCao) {
        this.chieuCao = chieuCao;
    }

    public Integer getCamera() {
        return camera;
    }

    public void setCamera(Integer camera) {
        this.camera = camera;
    }

    public Long getKhoaId() {
        return khoaId;
    }

    public void setKhoaId(Long khoaId) {
        this.khoaId = khoaId;
    }

    public Integer getTrangThaiVh() {
        return trangThaiVh;
    }

    public void setTrangThaiVh(Integer trangThaiVh) {
        this.trangThaiVh = trangThaiVh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getSuDung() {
        return suDung;
    }

    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }

    public Double getToaDoX() {
        return toaDoX;
    }

    public void setToaDoX(Double toaDoX) {
        this.toaDoX = toaDoX;
    }

    public Double getToaDoY() {
        return toaDoY;
    }

    public void setToaDoY(Double toaDoY) {
        this.toaDoY = toaDoY;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getDsRack() {
        return dsRack;
    }

    public void setDsRack(String dsRack) {
        this.dsRack = dsRack;
    }

    public Long getDonviSHId() {
        return donviSHId;
    }

    public void setDonviSHId(Long donviSHId) {
        this.donviSHId = donviSHId;
    }
}