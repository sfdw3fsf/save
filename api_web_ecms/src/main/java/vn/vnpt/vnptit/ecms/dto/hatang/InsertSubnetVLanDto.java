package vn.vnpt.vnptit.ecms.dto.hatang;

public class InsertSubnetVLanDto {
    private String vlanId;
    private String subnetId;

    public InsertSubnetVLanDto(String vlanId, String subnetId) {
        this.vlanId = vlanId;
        this.subnetId = subnetId;
    }

    public String getVlanId() {
        return vlanId;
    }

    public String getSubnetId() {
        return subnetId;
    }

}
