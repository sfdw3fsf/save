package vn.vnpt.vnptit.qlsc.dto.cntt;

public class GiaoViecNvCNTTInput {

    private Long phanvung_id;
    private Long phieu_id;
    private Long suco_ptm_id;
    private String ds_giaoviec;

    public Long getPhanvung_id() { return this.phanvung_id;}

    public void setPhanvung_id(Long phanvungId) { this.phanvung_id = phanvungId; }

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
}
