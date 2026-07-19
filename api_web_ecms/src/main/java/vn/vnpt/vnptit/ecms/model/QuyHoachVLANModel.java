package vn.vnpt.vnptit.ecms.model;

public class QuyHoachVLANModel {
    private int quyhoach_id;
    private int donvi_id; // Vùng quy hoạch
    private int dslam_id;
    private int vcid_tu;
    private int vcid_den;
    private int vlan_tu;
    private int vlan_den;
    private int kieu;
    private int loaitb_id;
    private int trangthai;
    private int dichvu_id;
    private int vlan;
    private int page;
    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getQuyhoach_id() {
        return quyhoach_id;
    }

    public void setQuyhoach_id(int quyhoach_id) {
        this.quyhoach_id = quyhoach_id;
    }

    public int getDonvi_id() {
        return donvi_id;
    }

    public void setDonvi_id(int donvi_id) {
        this.donvi_id = donvi_id;
    }

    public int getDslam_id() {
        return dslam_id;
    }

    public void setDslam_id(int dslam_id) {
        this.dslam_id = dslam_id;
    }

    public int getVcid_tu() {
        return vcid_tu;
    }

    public void setVcid_tu(int vcid_tu) {
        this.vcid_tu = vcid_tu;
    }

    public int getVcid_den() {
        return vcid_den;
    }

    public void setVcid_den(int vcid_den) {
        this.vcid_den = vcid_den;
    }

    public int getVlan_tu() {
        return vlan_tu;
    }

    public void setVlan_tu(int vlan_tu) {
        this.vlan_tu = vlan_tu;
    }

    public int getVlan_den() {
        return vlan_den;
    }

    public void setVlan_den(int vlan_den) {
        this.vlan_den = vlan_den;
    }

    public int getKieu() {
        return kieu;
    }

    public void setKieu(int kieu) {
        this.kieu = kieu;
    }

    public int getLoaitb_id() {
        return loaitb_id;
    }

    public void setLoaitb_id(int loaitb_id) {
        this.loaitb_id = loaitb_id;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getDichvu_id() {
        return dichvu_id;
    }

    public void setDichvu_id(int dichvu_id) {
        this.dichvu_id = dichvu_id;
    }

    public int getVlan() {
        return vlan;
    }

    public void setVlan(int vlan) {
        this.vlan = vlan;
    }
}
