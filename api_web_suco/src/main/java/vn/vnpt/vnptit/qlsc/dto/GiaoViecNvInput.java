package vn.vnpt.vnptit.qlsc.dto;

public class GiaoViecNvInput {

    private Long phieu_id;
    private Long suco_ptm_id;
    private String ds_giaoviec;

    private Integer type = 1; // 1: Giao viec NV ky thuat dia ban, 2: Giao viec NV xa hoi hoa
    private String tenNvXHH;
    private String sdtNvXHH;
    private String nhiemVuNvXHH;
    private String ghiChuNvXHH;

    public Long getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Long phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Long getSuco_ptm_id() {
        return suco_ptm_id;
    }

    public void setSuco_ptm_id(Long suco_ptm_id) {
        this.suco_ptm_id = suco_ptm_id;
    }

    public String getDs_giaoviec() {
        return ds_giaoviec;
    }

    public void setDs_giaoviec(String ds_giaoviec) {
        this.ds_giaoviec = ds_giaoviec;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTenNvXHH() {
        return tenNvXHH;
    }

    public void setTenNvXHH(String tenNvXHH) {
        this.tenNvXHH = tenNvXHH;
    }

    public String getSdtNvXHH() {
        return sdtNvXHH;
    }

    public void setSdtNvXHH(String sdtNvXHH) {
        this.sdtNvXHH = sdtNvXHH;
    }

    public String getNhiemVuNvXHH() {
        return nhiemVuNvXHH;
    }

    public void setNhiemVuNvXHH(String nhiemVuNvXHH) {
        this.nhiemVuNvXHH = nhiemVuNvXHH;
    }

    public String getGhiChuNvXHH() {
        return ghiChuNvXHH;
    }

    public void setGhiChuNvXHH(String ghiChuNvXHH) {
        this.ghiChuNvXHH = ghiChuNvXHH;
    }
}
