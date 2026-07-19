package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;
import java.util.Date;

public class KetQuaDanhSachTacNghiepDto implements Serializable {
    
    private Long loaiCvId;
    private Long donviId;
    private Long trangthaiId;
    private Long nhanvienThId;
    private Date ngayYcStart;
    private Date ngayYcEnd;
    private String maThuebao;
    private String tenKh;
    private Integer phanloai;
    private String khachhang;
    private String congviec;
    private Long idcId;
    private String ipStoreName;
    private Date ngayHTStart;
    private Date ngayHTEnd;
    
    public KetQuaDanhSachTacNghiepDto() {}
    
    public KetQuaDanhSachTacNghiepDto(Long loaiCvId, Long donviId, Long trangthaiId,
                                      Long nhanvienThId, Date ngayYcStart, Date ngayYcEnd,
                                      String maThuebao, String tenKh, Integer phanloai, String khachhang, String congviec, Long idcId, String ipStoreName, Date ngayHTStart, Date ngayHTEnd) {
        this.loaiCvId = loaiCvId;
        this.donviId = donviId;
        this.trangthaiId = trangthaiId;
        this.nhanvienThId = nhanvienThId;
        this.ngayYcStart = ngayYcStart;
        this.ngayYcEnd = ngayYcEnd;
        this.maThuebao = maThuebao;
        this.tenKh = tenKh;
        this.phanloai = phanloai;
        this.khachhang = khachhang;
        this.congviec = congviec;
        this.idcId = idcId;
        this.ipStoreName = ipStoreName;
        this.ngayHTStart = ngayHTStart;
        this.ngayHTEnd = ngayHTEnd;
    }
    
    public Long getLoaiCvId() {
        return loaiCvId;
    }
    
    public void setLoaiCvId(Long loaiCvId) {
        this.loaiCvId = loaiCvId;
    }
    
    public Long getDonviId() {
        return donviId;
    }
    
    public void setDonviId(Long donviId) {
        this.donviId = donviId;
    }
    
    public Long getTrangthaiId() {
        return trangthaiId;
    }
    
    public void setTrangthaiId(Long trangthaiId) {
        this.trangthaiId = trangthaiId;
    }
    
    public Long getNhanvienThId() {
        return nhanvienThId;
    }
    
    public void setNhanvienThId(Long nhanvienThId) {
        this.nhanvienThId = nhanvienThId;
    }
    
    public Date getNgayYcStart() {
        return ngayYcStart;
    }
    
    public void setNgayYcStart(Date ngayYcStart) {
        this.ngayYcStart = ngayYcStart;
    }
    
    public Date getNgayYcEnd() {
        return ngayYcEnd;
    }
    
    public void setNgayYcEnd(Date ngayYcEnd) {
        this.ngayYcEnd = ngayYcEnd;
    }
    
    public String getMaThuebao() {
        return maThuebao;
    }
    
    public void setMaThuebao(String maThuebao) {
        this.maThuebao = maThuebao;
    }
    
    public String getTenKh() {
        return tenKh;
    }
    
    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public Integer getPhanloai() {
        return phanloai;
    }
    
    public void setPhanloai(Integer phanloai) {
        this.phanloai = phanloai;
    }
    
    public String getKhachhang() {
        return khachhang;
    }
    
    public void setKhachhang(String khachhang) {
        this.khachhang = khachhang;
    }
    
    public String getCongviec() {
        return congviec;
    }
    
    public void setCongviec(String congviec) {
        this.congviec = congviec;
    }

    public Long getIdcId() {
        return idcId;
    }
    
    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public String getIpStoreName() {
        return ipStoreName;
    }
    
    public void setIpStoreName(String ipStoreName) {
        this.ipStoreName = ipStoreName;
    }

    public Date getNgayHTStart() {
        return ngayHTStart;
    }

    public void setNgayHTStart(Date ngayHTStart) {
        this.ngayHTStart = ngayHTStart;
    }
    
    public Date getNgayHTEnd() {
        return ngayHTEnd;
    }

    public void setNgayHTEnd(Date ngayHTEnd) {
        this.ngayHTEnd = ngayHTEnd;
    }
}
