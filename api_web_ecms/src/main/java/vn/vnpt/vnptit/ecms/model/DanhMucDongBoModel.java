package vn.vnpt.vnptit.ecms.model;

import javax.validation.constraints.NotBlank;

public class DanhMucDongBoModel {
    private int dong_bo_id;
    @NotBlank(message = "Tên danh mục đồng bộ không được để trống")
    private String dong_bo;

    public int getDong_bo_id() {
        return dong_bo_id;
    }

    public void setDong_bo_id(int dong_bo_id) {
        this.dong_bo_id = dong_bo_id;
    }

    public String getDong_bo() {
        return dong_bo;
    }

    public void setDong_bo(String dong_bo) {
        this.dong_bo = dong_bo;
    }
}
