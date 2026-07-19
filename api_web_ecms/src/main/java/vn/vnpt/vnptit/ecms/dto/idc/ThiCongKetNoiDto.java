package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class ThiCongKetNoiDto {
    private Integer thiCongKnId;
    private Integer phieuTcId;
    private Integer ketNoiId;
    private Integer nhanVienId;
    private Integer trangThaiId;
    private String noiDung;

    public void isValid() {
        if (phieuTcId == null || ketNoiId == null || nhanVienId == null) {
            throw new BadRequestExceptionMessage("Không được để trống các trường bắt buộc");
        }
    }

    public Integer getThiCongKnId() {
        return thiCongKnId;
    }

    public Integer getPhieuTcId() {
        return phieuTcId;
    }

    public Integer getKetNoiId() {
        return ketNoiId;
    }

    public Integer getNhanVienId() {
        return nhanVienId;
    }

    public Integer getTrangThaiId() {
        return trangThaiId;
    }

    public String getNoiDung() {
        return noiDung;
    }
}
