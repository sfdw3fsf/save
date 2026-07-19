package vn.vnpt.vnptit.ecms.model;

public class ThongTinNguoiDungModel {
    private Integer phanVungId;
    private Integer donViId;
    private Integer nhanVienId;
    private String userName;
    private String mayCN;
    private String ipCN;

    public void setPhanVungId(Integer phanVungId) {
        this.phanVungId = phanVungId;
    }
    public Integer getPhanVungId() {
        return phanVungId;
    }

    public void setDonViId(Integer donViId) {
        this.donViId = donViId;
    }
    public Integer getDonViId() {
        return donViId;
    }

    public void setNhanVienId(Integer nhanVienId) {
        this.nhanVienId = nhanVienId;
    }
    public Integer getNhanVienId() {
        return nhanVienId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setMayCN(String mayCN) {
        this.mayCN = mayCN;
    }
    public String getMayCN() {
        return mayCN;
    }

    public void setIpCN(String ipCN) {
        this.ipCN = ipCN;
    }
    public String getIpCN() {
        return ipCN;
    }
}
