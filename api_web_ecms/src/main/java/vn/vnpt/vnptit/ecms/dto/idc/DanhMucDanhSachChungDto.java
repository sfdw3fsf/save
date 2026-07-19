package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class DanhMucDanhSachChungDto {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    String loaiDanhMuc;
    Number thamSo1;
    Number thamSo2;

    public String getLoaiDanhMuc() {
        return loaiDanhMuc;
    }

    public void setLoaiDanhMuc(String loaiDanhMuc) {
        this.loaiDanhMuc = loaiDanhMuc;
    }

    public Number getThamSo1() {
        return thamSo1;
    }

    public void setThamSo1(Number thamSo1) {
        this.thamSo1 = thamSo1;
    }

    public Number getThamSo2() {
        return thamSo2;
    }

    public void setThamSo2(Number thamSo2) {
        this.thamSo2 = thamSo2;
    }
}
