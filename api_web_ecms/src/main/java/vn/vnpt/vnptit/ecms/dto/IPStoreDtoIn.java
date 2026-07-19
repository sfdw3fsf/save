package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class IPStoreDtoIn {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("ip_loai_id")
    private Long ip_loai_id;
    @JsonProperty("ip_phanloai_id")
    private Long ip_phanloai_id;
    @JsonProperty("idc_id")
    private Long idc_id;
    @JsonProperty("vrf_id")
    private Long vrf_id;
    @JsonProperty("zone_id")
    private Long zone_id;
    @JsonProperty("vlan_id")
    private Long vlan_id;
    @JsonProperty("subnet_id")
    private Long subnet_id;
    @JsonProperty("subnet_quyhoach_id")
    private Long subnet_quyhoach_id;
    @JsonProperty("hatangmang_id")
    private Long hatangmang_id;
    @JsonProperty("thietbimang_id")
    private Long thietbimang_id;
    @JsonProperty("ghi_chu")
    private String ghi_chu;
    @JsonProperty("hieu_luc")
    @Min(value = 0, message = "Hiệu lực chỉ được phép là 0 hoặc 1")
    @Max(value = 1, message = "Hiệu lực chỉ được phép là 0 hoặc 1")
    private int hieu_luc;

    public IPStoreDtoIn() {
    }

    public IPStoreDtoIn(Long id, String ip, Long ip_loai_id, Long ip_phanloai_id, Long idc_id, Long vrf_id, Long zone_id, Long vlan_id, Long subnet_id, Long subnet_quyhoach_id, Long hatangmang_id, Long thietbimang_id, String ghi_chu, int hieu_luc) {
        this.id = id;
        this.ip = ip;
        this.ip_loai_id = ip_loai_id;
        this.ip_phanloai_id = ip_phanloai_id;
        this.idc_id = idc_id;
        this.vrf_id = vrf_id;
        this.zone_id = zone_id;
        this.vlan_id = vlan_id;
        this.subnet_id = subnet_id;
        this.subnet_quyhoach_id = subnet_quyhoach_id;
        this.hatangmang_id = hatangmang_id;
        this.thietbimang_id = thietbimang_id;
        this.ghi_chu = ghi_chu;
        this.hieu_luc = hieu_luc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getIp_loai_id() {
        return ip_loai_id;
    }

    public void setIp_loai_id(Long ip_loai_id) {
        this.ip_loai_id = ip_loai_id;
    }

    public Long getIp_phanloai_id() {
        return ip_phanloai_id;
    }

    public void setIp_phanloai_id(Long ip_phanloai_id) {
        this.ip_phanloai_id = ip_phanloai_id;
    }

    public Long getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(Long idc_id) {
        this.idc_id = idc_id;
    }

    public Long getVrf_id() {
        return vrf_id;
    }

    public void setVrf_id(Long vrf_id) {
        this.vrf_id = vrf_id;
    }

    public Long getZone_id() {
        return zone_id;
    }

    public void setZone_id(Long zone_id) {
        this.zone_id = zone_id;
    }

    public Long getVlan_id() {
        return vlan_id;
    }

    public void setVlan_id(Long vlan_id) {
        this.vlan_id = vlan_id;
    }

    public Long getSubnet_id() {
        return subnet_id;
    }

    public void setSubnet_id(Long subnet_id) {
        this.subnet_id = subnet_id;
    }

    public Long getSubnet_quyhoach_id() {
        return subnet_quyhoach_id;
    }

    public void setSubnet_quyhoach_id(Long subnet_quyhoach_id) {
        this.subnet_quyhoach_id = subnet_quyhoach_id;
    }

    public Long getHatangmang_id() {
        return hatangmang_id;
    }

    public void setHatangmang_id(Long hatangmang_id) {
        this.hatangmang_id = hatangmang_id;
    }

    public Long getThietbimang_id() {
        return thietbimang_id;
    }

    public void setThietbimang_id(Long thietbimang_id) {
        this.thietbimang_id = thietbimang_id;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public int getHieu_luc() {
        return hieu_luc;
    }

    public void setHieu_luc(int hieu_luc) {
        this.hieu_luc = hieu_luc;
    }

}
