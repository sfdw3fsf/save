package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class PhieuThiCongTheoKQDto {
    private Long idIdc;
    private Long loaiCv;
    private Long idNhanVienTh;
    private Long congViec;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayYcFrom;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayYcTo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayHoanThanhFrom;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayHoanThanhTo;
    private String khachHang;

    public Long getIdIdc() {
        return idIdc;
    }

    public Long getLoaiCv() {
        return loaiCv;
    }

    public Long getIdNhanVienTh() {
        return idNhanVienTh;
    }

    public Long getCongViec() {
        return congViec;
    }

    public Date getNgayYcFrom() {
        return ngayYcFrom;
    }

    public Date getNgayYcTo() {
        return ngayYcTo;
    }

    public Date getNgayHoanThanhFrom() {
        return ngayHoanThanhFrom;
    }

    public Date getNgayHoanThanhTo() {
        return ngayHoanThanhTo;
    }

    public String getKhachHang() {
        return khachHang;
    }
}
