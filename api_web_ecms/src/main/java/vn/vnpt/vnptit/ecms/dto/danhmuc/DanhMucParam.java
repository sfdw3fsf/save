package vn.vnpt.vnptit.ecms.dto.danhmuc;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class DanhMucParam {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    String loaiDanhMuc;
    String thamSo1;
    String thamSo2;

    public String getLoaiDanhMuc() {
        return loaiDanhMuc;
    }

    public void setLoaiDanhMuc(String loaiDanhMuc) {
        this.loaiDanhMuc = loaiDanhMuc;
    }

    public String getThamSo1() {
        return thamSo1;
    }

    public void setThamSo1(String thamSo1) {
        this.thamSo1 = thamSo1;
    }

    public String getThamSo2() {
        return thamSo2;
    }

    public void setThamSo2(String thamSo2) {
        this.thamSo2 = thamSo2;
    }
}
