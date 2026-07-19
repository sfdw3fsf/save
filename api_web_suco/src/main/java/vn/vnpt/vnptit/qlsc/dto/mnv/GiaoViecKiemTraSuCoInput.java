package vn.vnpt.vnptit.qlsc.dto.mnv;

public class GiaoViecKiemTraSuCoInput {
    private String ma_suco;
    private Integer nhom_suco;
    private Long nhanvien_id;
    private String noidung_giao;

    public Integer getNhom_suco() {
        return nhom_suco;
    }

    public void setNhom_suco(Integer nhom_suco) {
        this.nhom_suco = nhom_suco;
    }

    public String getMa_suco() {
        return ma_suco;
    }

    public void setMa_suco(String ma_suco) {
        this.ma_suco = ma_suco;
    }

    public Long getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(Long nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public String getNoidung_giao() {
        return noidung_giao;
    }

    public void setNoidung_giao(String noidung_giao) {
        this.noidung_giao = noidung_giao;
    }
}
