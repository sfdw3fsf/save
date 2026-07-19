package vn.vnpt.vnptit.ecms.dto.danhmuc;

public class SubnetMaskDto {
    private Integer id;
    private String subnetmask;
    private Integer phanLoaiIP;
    private String ghiChu;
    private Short hieuLuc;
    private Short ipStore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubnetmask() {
        return subnetmask;
    }

    public void setSubnetmask(String subnetmask) {
        this.subnetmask = subnetmask;
    }

    public Integer getPhanLoaiIP() {
        return phanLoaiIP;
    }

    public void setPhanLoaiIP(Integer phanLoaiIP) {
        this.phanLoaiIP = phanLoaiIP;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Short getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Short hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Short getIpStore() {
        return ipStore;
    }

    public void setIpStore(Short ipStore) {
        this.ipStore = ipStore;
    }
}
