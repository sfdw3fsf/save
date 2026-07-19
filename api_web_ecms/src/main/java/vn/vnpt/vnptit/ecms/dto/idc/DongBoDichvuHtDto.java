package vn.vnpt.vnptit.ecms.dto.idc;

/**
 * DTO request cho API đồng bộ dịch vụ hạ tầng.
 * FE truyền mã dịch vụ + tham số routing, BE phân luồng gọi API nội bộ/public tương ứng.
 */
public class DongBoDichvuHtDto {
    /**
     * Cờ phân luồng:
     * 1 = nội bộ (gọi dongBoChiTietDichVu)
     * 0 = không nội bộ / public (gọi dongBoChiTietDichVuPublic)
     */
    private Number noibo;

    /** Mã/Tên dịch vụ cần đồng bộ (VD: "user-k8s-logs") */
    private String serviceName;

    /** Mã cụm hạ tầng (VD: "local") - dùng chung cho cả nội bộ và public. Mặc định: "local" */
    private String maCumHaTang;

    /** Rancher code - dùng cho nội bộ (noibo = 1). Mặc định: "xxx" */
    private String rancherCode;

    /** VPC ID - dùng cho public (noibo = 0) */
    private String vpcId;

    public DongBoDichvuHtDto() {
    }

    public Number getNoibo() {
        return noibo;
    }

    public void setNoibo(Number noibo) {
        this.noibo = noibo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMaCumHaTang() {
        return maCumHaTang;
    }

    public void setMaCumHaTang(String maCumHaTang) {
        this.maCumHaTang = maCumHaTang;
    }

    public String getRancherCode() {
        return rancherCode;
    }

    public void setRancherCode(String rancherCode) {
        this.rancherCode = rancherCode;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }
}
