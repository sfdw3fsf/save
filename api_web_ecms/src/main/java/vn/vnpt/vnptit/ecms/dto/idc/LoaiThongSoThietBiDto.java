package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class LoaiThongSoThietBiDto {
    private Number idLoaiThongSoTB;
    private Number idLoaiTB;
    private String tenLoaiThongSoTB;
    private Number giaTriDinhDanh;
    private Number trangThaiVanHanhId;
    private String ghiChu;
    private Boolean hieuLuc;
    private String moTa;
    private String tenTat;

    public void isValid() {
        boolean isValid = (getIdLoaiTB() != null &&
                getTenLoaiThongSoTB() != null && !getTenLoaiThongSoTB().trim().isEmpty() &&
                getGiaTriDinhDanh() != null &&
                getTrangThaiVanHanhId() != null);

        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: ID Loại TB, Tên Loại Thông Số TB, Giá Trị Định Danh, Trạng Thái Vận Hành");
        }

        if (getTrangThaiVanHanhId() != null) {
            int trangThai = getTrangThaiVanHanhId().intValue();
            if (trangThai < 0 || trangThai > 2) {
                throw new BadRequestExceptionMessage(
                        "Trạng thái vận hành chỉ được nhận giá trị 0 (Hoạt động), 1 (Không hoạt động), 2 (Lỗi)");
            }
        }
    }

    public Number getIdLoaiThongSoTB() {
        return idLoaiThongSoTB;
    }

    public void setIdLoaiThongSoTB(Number idLoaiThongSoTB) {
        this.idLoaiThongSoTB = idLoaiThongSoTB;
    }

    public Number getIdLoaiTB() {
        return idLoaiTB;
    }

    public void setIdLoaiTB(Number idLoaiTB) {
        this.idLoaiTB = idLoaiTB;
    }

    public String getTenLoaiThongSoTB() {
        return tenLoaiThongSoTB != null ? tenLoaiThongSoTB.trim() : null;
    }

    public void setTenLoaiThongSoTB(String tenLoaiThongSoTB) {
        this.tenLoaiThongSoTB = tenLoaiThongSoTB;
    }

    public Number getGiaTriDinhDanh() {
        return giaTriDinhDanh;
    }

    public void setGiaTriDinhDanh(Number giaTriDinhDanh) {
        this.giaTriDinhDanh = giaTriDinhDanh;
    }

    public Number getTrangThaiVanHanhId() {
        return trangThaiVanHanhId;
    }

    public void setTrangThaiVanHanhId(Number trangThaiVanHanhId) {
        this.trangThaiVanHanhId = trangThaiVanHanhId;
    }

    public String getGhiChu() {
        return ghiChu != null ? ghiChu.trim() : null;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Boolean getHieuLuc() {
        return hieuLuc != null ? hieuLuc : false;
    }

    public void setHieuLuc(Boolean hieuLuc) {
        this.hieuLuc = hieuLuc != null ? hieuLuc : false;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }
}
