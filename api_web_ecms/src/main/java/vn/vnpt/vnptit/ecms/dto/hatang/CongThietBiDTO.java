package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

// DTO này dùng cho cổng thiết bị sinh ra từ khai báo thiết bị
public class CongThietBiDTO {
    private Number id = 0;
    private Number thietbi_id;
    private Number phanloai;
    private Number sothutu;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private Number loaiCongID;
    private Number mucDichSuDungCongID;
    private Number bangThongCongID;
    private Number thongTinModuleID;
    private Number ketnoi;
    private Number trangthai_sd;
    private Number hieuLuc;

    public Number getId() {
        return id;
    }

    public Number getThietbi_id() {
        return thietbi_id;
    }

    public Number getPhanloai() {
        return phanloai;
    }

    public Number getSothutu() {
        return sothutu;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getTen() {
        return ten;
    }

    public Number getLoaiCongID() {
        return loaiCongID;
    }

    public Number getMucDichSuDungCongID() {
        return mucDichSuDungCongID;
    }

    public Number getBangThongCongID() {
        return bangThongCongID;
    }

    public Number getThongTinModuleID() {
        return thongTinModuleID;
    }

    public Number getKetnoi() {
        return ketnoi;
    }

    public Number getTrangthai_sd() {
        return trangthai_sd;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }
}
