package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class DanhMucNhaCungCapV2DTO {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    String loaiDanhMuc;
    Number thamSo1;

    public DanhMucNhaCungCapV2DTO(String loaiDanhMuc, Number thamSo1) {
        this.loaiDanhMuc = loaiDanhMuc;
        this.thamSo1 = thamSo1;
    }

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
}
