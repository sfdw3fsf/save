package vn.vnpt.vnptit.ecms.dto.hatang;

import java.util.List;

public class InsertBatchSubnetVLanDto {
    private Long vlanId;
    private List<Integer> subnetList;

    public Long getVlanId() {
        return vlanId;
    }

    public void setVlanId(Long vlanId) {
        this.vlanId = vlanId;
    }

    public List<Integer> getSubnetList() {
        return subnetList;
    }

    public void setSubnetList(List<Integer> subnetList) {
        this.subnetList = subnetList;
    }
}
