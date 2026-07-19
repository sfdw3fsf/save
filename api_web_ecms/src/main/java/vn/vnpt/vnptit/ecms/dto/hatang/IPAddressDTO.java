package vn.vnpt.vnptit.ecms.dto.hatang;


import com.fasterxml.jackson.annotation.JsonFormat;
import vn.vnpt.common.Utils;

import java.util.Date;

public class IPAddressDTO {
    private Number id;
    private String diaChiIp;                 //unique value
    private Number hieuLuc;
    private Number ipStoreId;
    private String ipStoreName;
    private Number ipLoaiId;
    private String ipLoaiName;
    private Number ipSubnetId;          //thuoc subnet
    private String ipSubnetName;
    private Long haTangMangId;        //thuoc hatang mang
    private String haTangMangName;
    private Number subnetQuyHoachId;     //muc dich su dung
    private String subnetQuyHoachName;
    private Number doiTuongCapPhatId;
    private String doiTuongCapPhatName;
    private Number thietBiMangId;       //thuoc thiet bi mang
    private String thietBiMangName;
    private Number trangThaiCapPhat;    //0- Chờ cấp phát, 1- Đã cấp phát
    private Number nguoiCapId;
    private String nguoiCapName;
    private Number donViCapId;
    private String donViCapName;
    private Number nguoiSuDungId;
    private String nguoiSuDungName;
    private Number donViSuDungId;
    private String donViSuDungName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date ngayCap;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date ngayThuHoi;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date ngayHetHan;
    private String thamChieu;        //Sở cứ tham chiếu
    private String ghiChu;
    private Number nhanVienId;
    private String mayCapNhap;
    private String nguoiCapNhap;
    private String ipCapNhap;
    private Long phanLoaiIpId;
    private String phanLoaiIpTen;
    private Long idcId;
    private String idcTen;
    private Long loaiIpId;
    private String loaiIpTen;
    private Long subnetId;
    private Long trangThaiCcdvId;
    private Integer suDung;
    private Integer quangBaIp;
    private Long mucDichSdId;
    private Long noiBoId;
    private Long donViQuanLyId;
    private Long doiTuongSDId;
    private String soCu;

    public Number getId() {
        return id;
    }

    public String getDiaChiIp() {
        return diaChiIp;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public Number getIpStoreId() {
        return ipStoreId;
    }

    public String getIpStoreName() {
        return ipStoreName;
    }

    public Number getIpLoaiId() {
        return ipLoaiId;
    }

    public String getIpLoaiName() {
        return ipLoaiName;
    }

    public Number getIpSubnetId() {
        return ipSubnetId;
    }

    public String getIpSubnetName() {
        return ipSubnetName;
    }

    public Long getHaTangMangId() {
        return haTangMangId;
    }

    public String getHaTangMangName() {
        return haTangMangName;
    }

    public Number getSubnetQuyHoachId() {
        return subnetQuyHoachId;
    }

    public String getSubnetQuyHoachName() {
        return subnetQuyHoachName;
    }

    public Number getDoiTuongCapPhatId() {
        return doiTuongCapPhatId;
    }

    public String getDoiTuongCapPhatName() {
        return doiTuongCapPhatName;
    }

    public Number getThietBiMangId() {
        return thietBiMangId;
    }

    public String getThietBiMangName() {
        return thietBiMangName;
    }

    public Number getTrangThaiCapPhat() {
        return trangThaiCapPhat;
    }

    public Number getNguoiCapId() {
        return nguoiCapId;
    }

    public String getNguoiCapName() {
        return nguoiCapName;
    }

    public Number getDonViCapId() {
        return donViCapId;
    }

    public String getDonViCapName() {
        return donViCapName;
    }

    public Number getNguoiSuDungId() {
        return nguoiSuDungId;
    }

    public String getNguoiSuDungName() {
        return nguoiSuDungName;
    }

    public Number getDonViSuDungId() {
        return donViSuDungId;
    }

    public String getDonViSuDungName() {
        return donViSuDungName;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public Date getNgayThuHoi() {
        return ngayThuHoi;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public String getThamChieu() {
        return thamChieu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public Number getNhanVienId() {
        return nhanVienId;
    }

    public String getMayCapNhap() {
        return mayCapNhap;
    }

    public String getNguoiCapNhap() {
        return nguoiCapNhap;
    }

    public String getIpCapNhap() {
        return ipCapNhap;
    }

    public Long getPhanLoaiIpId() {
        return phanLoaiIpId;
    }

    public String getPhanLoaiIpTen() {
        return phanLoaiIpTen;
    }

    public Long getIdcId() {
        return idcId;
    }

    public String getIdcTen() {
        return idcTen;
    }

    public Long getLoaiIpId() {
        return loaiIpId;
    }

    public String getLoaiIpTen() {
        return loaiIpTen;
    }

    public Long getSubnetId() {
        return subnetId;
    }

    public Long getTrangThaiCcdvId() {
        return trangThaiCcdvId;
    }

    public Integer getSuDung() {
        return suDung;
    }

    public Integer getQuangBaIp() {
        return quangBaIp;
    }

    public Long getMucDichSdId() {
        return mucDichSdId;
    }

    public Long getNoiBoId() {
        return noiBoId;
    }

    public Long getDonViQuanLyId() {
        return donViQuanLyId;
    }

    public Long getDoiTuongSDId() {
        return doiTuongSDId;
    }

    public String getSoCu() {
        return soCu;
    }
}
