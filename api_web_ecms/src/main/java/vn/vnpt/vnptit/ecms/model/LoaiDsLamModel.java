package vn.vnpt.vnptit.ecms.model;

public class LoaiDsLamModel {
    private int nhom_dslam_id;

    private int loai_dslam_id;

    private String loai_dslam;

    private String ghiChu;

    private int thamSo;

    private int port_td;

    private int bras_ts;

    private long[] ids;

    public LoaiDsLamModel() {
        super();
    };

    public LoaiDsLamModel(int nhom_dslam_id, int loai_dslam_id, String loai_dslam, String ghiChu, int thamSo,
                          int port_td, int bras_ts) {
        super();
        this.nhom_dslam_id = nhom_dslam_id;
        this.loai_dslam_id = loai_dslam_id;
        this.loai_dslam = loai_dslam;
        this.ghiChu = ghiChu;
        this.thamSo = thamSo;
        this.port_td = port_td;
        this.bras_ts = bras_ts;
    }

    public int getNhom_dslam_id() {
        return nhom_dslam_id;
    }

    public void setNhom_dslam_id(int nhom_dslam_id) {
        this.nhom_dslam_id = nhom_dslam_id;
    }

    public int getLoai_dslam_id() {
        return loai_dslam_id;
    }

    public void setLoai_dslam_id(int loai_dslam_id) {
        this.loai_dslam_id = loai_dslam_id;
    }

    public String getLoai_dslam() {
        return loai_dslam;
    }

    public void setLoai_dslam(String loai_dslam) {
        this.loai_dslam = loai_dslam;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getThamSo() {
        return thamSo;
    }

    public void setThamSo(int thamSo) {
        this.thamSo = thamSo;
    }

    public int getPort_td() {
        return port_td;
    }

    public void setPort_td(int port_td) {
        this.port_td = port_td;
    }

    public int getBras_ts() {
        return bras_ts;
    }

    public void setBras_ts(int bras_ts) {
        this.bras_ts = bras_ts;
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
}
