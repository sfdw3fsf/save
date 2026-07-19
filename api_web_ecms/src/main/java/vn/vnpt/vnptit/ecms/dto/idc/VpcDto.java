package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;
import java.util.List;

public class VpcDto {
    private List<Number> vpcIds;
    private Number vpcId;
    private String maVpc;
    private String tenVpc;
    private Number loaiVpcId;
    private Number tinhPhi;
    private Number hieuLuc;
    private String maHaTang;
    private Number trangThaiId;
    private Number nguoiYcId;
    private Number doiTuongId;
    private Number donViYcYId;
    private Number cumHaTangId;
    private Number nguonYcId;
    private Date ngayTao;
    private Date ngayHetHan;
    private String ghiChu;
    private Number donViQlId;
    private Number caNhanQlId;
    private Number phongQlId;
    private Number donViCqId;
    
    // Capacity fields
    private Number vcpu;
    private Number vram;
    private Number vgpu;
    private Number hdd;
    private Number ssd;
    private Number iops;
    private Number throughput;
    private Number soLuongIp;

    private String nguoiCn;
    private Date ngayCn;

    private Number hieulucId;
    private Number tinhPhiId;
    private String ngaytaoTu;
    private String ngaytaoDen;
    private String ngayHethanTu;
    private String ngayHethanDen;

    // SmartCloud sync params
    private String maTB;
    private Long smCloudId;
    private Integer smCloudServiceType;
    private String regionCode;

    public VpcDto() {
    }

    public Number getVpcId() {
        return vpcId;
    }

    public void setVpcId(Number vpcId) {
        this.vpcId = vpcId;
    }

    public List<Number> getVpcIds() {
        return vpcIds;
    }

    public void setVpcIds(List<Number> vpcIds) {
        this.vpcIds = vpcIds;
    }

    public String getMaVpc() {
        return maVpc;
    }

    public void setMaVpc(String maVpc) {
        this.maVpc = maVpc;
    }

    public String getTenVpc() {
        return tenVpc;
    }

    public void setTenVpc(String tenVpc) {
        this.tenVpc = tenVpc;
    }

    public Number getLoaiVpcId() {
        return loaiVpcId;
    }

    public void setLoaiVpcId(Number loaiVpcId) {
        this.loaiVpcId = loaiVpcId;
    }

    public Number getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Number tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Number hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getMaHaTang() {
        return maHaTang;
    }

    public void setMaHaTang(String maHaTang) {
        this.maHaTang = maHaTang;
    }

    public Number getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Number trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Number getNguoiYcId() {
        return nguoiYcId;
    }

    public void setNguoiYcId(Number nguoiYcId) {
        this.nguoiYcId = nguoiYcId;
    }

    public Number getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Number doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public Number getDonViYcYId() {
        return donViYcYId;
    }

    public void setDonViYcYId(Number donViYcYId) {
        this.donViYcYId = donViYcYId;
    }

    public Number getCumHaTangId() {
        return cumHaTangId;
    }

    public void setCumHaTangId(Number cumHaTangId) {
        this.cumHaTangId = cumHaTangId;
    }

    public Number getNguonYcId() {
        return nguonYcId;
    }

    public void setNguonYcId(Number nguonYcId) {
        this.nguonYcId = nguonYcId;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Number getDonViQlId() {
        return donViQlId;
    }

    public void setDonViQlId(Number donViQlId) {
        this.donViQlId = donViQlId;
    }

    public Number getCaNhanQlId() {
        return caNhanQlId;
    }

    public void setCaNhanQlId(Number caNhanQlId) {
        this.caNhanQlId = caNhanQlId;
    }

    public Number getPhongQlId() {
        return phongQlId;
    }

    public void setPhongQlId(Number phongQlId) {
        this.phongQlId = phongQlId;
    }

    public Number getDonViCqId() {
        return donViCqId;
    }

    public void setDonViCqId(Number donViCqId) {
        this.donViCqId = donViCqId;
    }

    public Number getVcpu() {
        return vcpu;
    }

    public void setVcpu(Number vcpu) {
        this.vcpu = vcpu;
    }

    public Number getVram() {
        return vram;
    }

    public void setVram(Number vram) {
        this.vram = vram;
    }

    public Number getVgpu() {
        return vgpu;
    }

    public void setVgpu(Number vgpu) {
        this.vgpu = vgpu;
    }

    public Number getHdd() {
        return hdd;
    }

    public void setHdd(Number hdd) {
        this.hdd = hdd;
    }

    public Number getSsd() {
        return ssd;
    }

    public void setSsd(Number ssd) {
        this.ssd = ssd;
    }

    public Number getIops() {
        return iops;
    }

    public void setIops(Number iops) {
        this.iops = iops;
    }

    public Number getThroughput() {
        return throughput;
    }

    public void setThroughput(Number throughput) {
        this.throughput = throughput;
    }

    public Number getSoLuongIp() {
        return soLuongIp;
    }

    public void setSoLuongIp(Number soLuongIp) {
        this.soLuongIp = soLuongIp;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Date getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(Date ngayCn) {
        this.ngayCn = ngayCn;
    }

    public Number getHieulucId() { 
        return hieulucId; 
    }

    public void setHieulucId(Number hieulucId) { 
        this.hieulucId = hieulucId; 
    }

    public Number getTinhPhiId() { 
        return tinhPhiId; 
    }

    public void setTinhPhiId(Number tinhPhiId) { 
        this.tinhPhiId = tinhPhiId; 
    }

    public String getNgaytaoTu() { 
        return ngaytaoTu; 
    }

    public void setNgaytaoTu(String ngaytaoTu) { 
        this.ngaytaoTu = ngaytaoTu; 
    }

    public String getNgaytaoDen() { 
        return ngaytaoDen; 
    }

    public void setNgaytaoDen(String ngaytaoDen) { 
        this.ngaytaoDen = ngaytaoDen; 
    }

    public String getNgayHethanTu() { 
        return ngayHethanTu; 
    }

    public void setNgayHethanTu(String ngayHethanTu) { 
        this.ngayHethanTu = ngayHethanTu; 
    }

    public String getNgayHethanDen() { 
        return ngayHethanDen; 
    }

    public void setNgayHethanDen(String ngayHethanDen) { 
        this.ngayHethanDen = ngayHethanDen; 
    }

    // SmartCloud sync getters/setters
    public String getMaTB() { return maTB; }
    public void setMaTB(String maTB) { this.maTB = maTB; }

    public Long getSmCloudId() { return smCloudId; }
    public void setSmCloudId(Long smCloudId) { this.smCloudId = smCloudId; }

    public Integer getSmCloudServiceType() { return smCloudServiceType; }
    public void setSmCloudServiceType(Integer smCloudServiceType) { this.smCloudServiceType = smCloudServiceType; }

    public String getRegionCode() { return regionCode; }
    public void setRegionCode(String regionCode) { this.regionCode = regionCode; }
}
