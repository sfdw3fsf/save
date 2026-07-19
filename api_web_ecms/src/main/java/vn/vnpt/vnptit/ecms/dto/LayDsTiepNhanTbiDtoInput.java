package vn.vnpt.vnptit.ecms.dto;

public class LayDsTiepNhanTbiDtoInput {
    private Long ttvh_id;
    private Long thietbi_id;
    private Long nhanvien_id;
    private Long loaivh_id;

    public Long getTtvh_id() {
        return ttvh_id;
    }

    public void setTtvh_id(Long ttvh_id) {
        this.ttvh_id = ttvh_id;
    }

    public Long getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Long thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public Long getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(Long nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public Long getLoaivh_id() {
        return loaivh_id;
    }

    public void setLoaivh_id(Long loaivh_id) {
        this.loaivh_id = loaivh_id;
    }
}
