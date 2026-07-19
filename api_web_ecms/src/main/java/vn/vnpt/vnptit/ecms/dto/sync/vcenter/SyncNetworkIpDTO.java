package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

public class SyncNetworkIpDTO {
    private String networkName;   // ERD: IP_MAYAO.API_NETWORK_NAME
    private String ipAddress;     // ERD: IP_MAYAO.API_NETWORK_IP

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
