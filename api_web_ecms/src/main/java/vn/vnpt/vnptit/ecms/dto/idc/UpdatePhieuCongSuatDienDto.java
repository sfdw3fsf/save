package vn.vnpt.vnptit.ecms.dto.idc;

public class UpdatePhieuCongSuatDienDto {
    private Long phieuTcId;
    private Long nguoiThucHienId;
    private Long trangthaiId;
    private String lyDoTraPhieu;

    public String getLyDoTraPhieu() {
        return lyDoTraPhieu;
    }

    public void setLyDoTraPhieu(String lyDoTraPhieu) {
        this.lyDoTraPhieu = lyDoTraPhieu;
    }

    public Long getNguoiThucHienId() {
        return nguoiThucHienId;
    }

    public void setNguoiThucHienId(Long nguoiThucHienId) {
        this.nguoiThucHienId = nguoiThucHienId;
    }

    public Long getPhieuTcId() {
        return phieuTcId;
    }

    public void setPhieuTcId(Long phieuTcId) {
        this.phieuTcId = phieuTcId;
    }

    public Long getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(Long trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

}