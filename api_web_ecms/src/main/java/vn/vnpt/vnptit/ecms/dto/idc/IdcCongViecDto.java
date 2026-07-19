package vn.vnpt.vnptit.ecms.dto.idc;

public class IdcCongViecDto {
    Number phanVungId;
    Number congViecId;
    Number phanLoai;
    Number nhanVienId;

    public Number getCongViecId() {
        return congViecId;
    }

    public void setCongViecId(Number congViecId) {
        this.congViecId = congViecId;
    }

    public Number getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(Number phanLoai) {
        this.phanLoai = phanLoai;
    }

    public Number getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Number nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Number getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(Number phanVungId) {
        this.phanVungId = phanVungId;
    }
}
