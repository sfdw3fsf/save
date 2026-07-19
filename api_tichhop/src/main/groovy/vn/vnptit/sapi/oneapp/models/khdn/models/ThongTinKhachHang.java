package vn.vnptit.sapi.oneapp.models.khdn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ThongTinKhachHang {

    @SerializedName("MA_KH")
    @Expose
    private String maKh;

    @SerializedName("FULL_NAME")
    @Expose
    private String tenKh;

    @SerializedName("MST")
    @Expose
    private String mst;

    @SerializedName("ADDR")
    @Expose
    private String diaChi;

    @SerializedName("COUNTRY")
    @Expose
    private String quocGia;

    @SerializedName("PROVINCE")
    @Expose
    private String tinh;

    @SerializedName("DISTRICT")
    @Expose
    private String huyen;

    @SerializedName("TOWN")
    @Expose
    private String xa;

    @SerializedName("DD_NAME")
    @Expose
    private String tenNguoiDaiDien;

    @SerializedName("PHONE")
    @Expose
    private String sdt;

    @SerializedName("DD_PHONE")
    @Expose
    private String sdtNguoiDaiDien;

    @SerializedName("DD_BIRTHDAY")
    @Expose
    private String ngaySinhNguoiDaiDien;

    @SerializedName("LOAI_GT")
    @Expose
    private String loaiGiayTo;

    @SerializedName("NGANH_NGHE")
    @Expose
    private String nganhNghe;

    @SerializedName("MA_NGANH")
    @Expose
    private String maNganh;

    @SerializedName("SO_LUONG_LAODONG")
    @Expose
    private String soLuongLaoDong;

    @SerializedName("LOAI_HINH_TO_CHUC")
    @Expose
    private String loaiHinhToChuc;

    @SerializedName("LINE_KH")
    @Expose
    private String lineKh;

    @SerializedName("QUY_MO_KH")
    @Expose
    private String quyMoKh;

    @SerializedName("SUB_LINE")
    @Expose
    private String subLine;

    @SerializedName("MA_KHTN")
    @Expose
    private String maKhtn;

    @SerializedName("LOAI_DN")
    @Expose
    private String loaiDn;

    @SerializedName("PARENT_ID")
    @Expose
    private String parentId;

    @SerializedName("CREATED_USER")
    @Expose
    private String nguoiTao;

    @SerializedName("CREATED_DATE")
    @Expose
    private String ngayTao;

    @SerializedName("MODIFIED_DATE")
    @Expose
    private String ngayCapNhat;

    @SerializedName("TRACE")
    @Expose
    private String trace;

    @SerializedName("DONVI_ID")
    @Expose
    private String donViId;

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }

    public String getTenNguoiDaiDien() {
        return tenNguoiDaiDien;
    }

    public void setTenNguoiDaiDien(String tenNguoiDaiDien) {
        this.tenNguoiDaiDien = tenNguoiDaiDien;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSdtNguoiDaiDien() {
        return sdtNguoiDaiDien;
    }

    public void setSdtNguoiDaiDien(String sdtNguoiDaiDien) {
        this.sdtNguoiDaiDien = sdtNguoiDaiDien;
    }

    public String getNgaySinhNguoiDaiDien() {
        return ngaySinhNguoiDaiDien;
    }

    public void setNgaySinhNguoiDaiDien(String ngaySinhNguoiDaiDien) {
        this.ngaySinhNguoiDaiDien = ngaySinhNguoiDaiDien;
    }

    public String getLoaiGiayTo() {
        return loaiGiayTo;
    }

    public void setLoaiGiayTo(String loaiGiayTo) {
        this.loaiGiayTo = loaiGiayTo;
    }

    public String getNganhNghe() {
        return nganhNghe;
    }

    public void setNganhNghe(String nganhNghe) {
        this.nganhNghe = nganhNghe;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getSoLuongLaoDong() {
        return soLuongLaoDong;
    }

    public void setSoLuongLaoDong(String soLuongLaoDong) {
        this.soLuongLaoDong = soLuongLaoDong;
    }

    public String getLoaiHinhToChuc() {
        return loaiHinhToChuc;
    }

    public void setLoaiHinhToChuc(String loaiHinhToChuc) {
        this.loaiHinhToChuc = loaiHinhToChuc;
    }

    public String getLineKh() {
        return lineKh;
    }

    public void setLineKh(String lineKh) {
        this.lineKh = lineKh;
    }

    public String getQuyMoKh() {
        return quyMoKh;
    }

    public void setQuyMoKh(String quyMoKh) {
        this.quyMoKh = quyMoKh;
    }

    public String getSubLine() {
        return subLine;
    }

    public void setSubLine(String subLine) {
        this.subLine = subLine;
    }

    public String getMaKhtn() {
        return maKhtn;
    }

    public void setMaKhtn(String maKhtn) {
        this.maKhtn = maKhtn;
    }

    public String getLoaiDn() {
        return loaiDn;
    }

    public void setLoaiDn(String loaiDn) {
        this.loaiDn = loaiDn;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(String ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getDonViId() {
        return donViId;
    }

    public void setDonViId(String donViId) {
        this.donViId = donViId;
    }
}
