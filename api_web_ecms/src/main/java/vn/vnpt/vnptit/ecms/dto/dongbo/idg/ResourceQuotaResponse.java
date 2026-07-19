package vn.vnpt.vnptit.ecms.dto.dongbo.idg;

public class ResourceQuotaResponse {
    private String totalCpu;
    private String totalRam;
    private String totalStorage;
    private String serviceName;
    private String serviceOrderCode;
    private String timestamp;
    private String status;
    private String serviceType;

    public String getTotalCpu() { return totalCpu; }
    public void setTotalCpu(String totalCpu) { this.totalCpu = totalCpu; }

    public String getTotalRam() { return totalRam; }
    public void setTotalRam(String totalRam) { this.totalRam = totalRam; }

    public String getTotalStorage() { return totalStorage; }
    public void setTotalStorage(String totalStorage) { this.totalStorage = totalStorage; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getServiceOrderCode() { return serviceOrderCode; }
    public void setServiceOrderCode(String serviceOrderCode) { this.serviceOrderCode = serviceOrderCode; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
}
