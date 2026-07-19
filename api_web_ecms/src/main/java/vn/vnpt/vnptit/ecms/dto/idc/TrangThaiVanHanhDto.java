package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class TrangThaiVanHanhDto {
    private Number ttvhId;
    private String trangThaiVh;
    private Boolean suDung;
    private String ghiChu;

    public void isValid() {
        boolean isValid = (getTrangThaiVh() != null && !getTrangThaiVh().trim().isEmpty());

        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: Trạng Thái Vận Hành");
        }
    }

    public Number getTtvhId() {
        return ttvhId;
    }

    public void setTtvhId(Number ttvhId) {
        this.ttvhId = ttvhId;
    }

    public String getTrangThaiVh() {
        return trangThaiVh != null ? trangThaiVh.trim() : null;
    }

    public void setTrangThaiVh(String trangThaiVh) {
        this.trangThaiVh = trangThaiVh;
    }

    public Boolean getSuDung() {
        return suDung != null ? suDung : true;
    }

    public void setSuDung(Boolean suDung) {
        this.suDung = suDung != null ? suDung : true;
    }

    public String getGhiChu() {
        return ghiChu != null ? ghiChu.trim() : null;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
