package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho thông tin Cụm hạ tầng IDG")
public class CumHaTangIdgDto {

    @Schema(description = "Mã cụm hạ tầng (cluster_id)")
    @JsonProperty("ma_cum")
    private String maCum;

    @Schema(description = "Tên cụm hạ tầng (cluster_name)")
    @JsonProperty("ten_cum")
    private String tenCum;

    @Schema(description = "ID IDC đặt tại")
    @JsonProperty("idc_id")
    private Number idcId;

    @Schema(description = "ID công nghệ triển khai")
    @JsonProperty("cong_nghe_id")
    private Number congNgheId;

    @Schema(description = "ID phân loại hạ tầng")
    @JsonProperty("phan_loai_id")
    private Number phanLoaiId;

    @Schema(description = "ID cấp độ hạ tầng")
    @JsonProperty("cap_do_id")
    private Number capDoId;

    @Schema(description = "ID loại hạ tầng")
    @JsonProperty("loai_ht_id")
    private Number loaiHtId;

    @Schema(description = "ID trạng thái CCDV")
    @JsonProperty("trang_thai_ccdv_id")
    private Number trangThaiCcdvId;

    @Schema(description = "ID đơn vị quản lý")
    @JsonProperty("donvi_ql_id")
    private Number donviQlId;

    @Schema(description = "ID phòng/tổ quản lý")
    @JsonProperty("phong_ql_id")
    private Number phongQlId;

    @Schema(description = "ID cá nhân quản lý")
    @JsonProperty("ca_nhan_ql_id")
    private Number caNhanQlId;

    @Schema(description = "Hiệu lực (1: Có, 0: Không)")
    @JsonProperty("hieu_luc")
    private Integer hieuLuc;

    @Schema(description = "Rancher Code")
    @JsonProperty("rancher_code")
    private String rancherCode;
    @Schema(description = "Danh sách ID loại dịch vụ")
    @JsonProperty("loai_dich_vu_ids")
    private String loaiDichVuIds;

    public String getLoaiDichVuIds() {
        return loaiDichVuIds;
    }

    public void setLoaiDichVuIds(String loaiDichVuIds) {
        this.loaiDichVuIds = loaiDichVuIds;
    }

    @Schema(description = "Region ID")
    @JsonProperty("region_id")
    private String regionId;

    @Schema(description = "Phiên bản")
    @JsonProperty("phien_ban")
    private String phienBan;

    @Schema(description = "IP quản trị")
    @JsonProperty("ip_quan_tri")
    private String ipQuanTri;

    @Schema(description = "Link quản trị")
    @JsonProperty("link_quan_tri")
    private String linkQuanTri;

    @Schema(description = "Trạng thái vận hành")
    @JsonProperty("trang_thai_vh")
    private String trangThaiVh;

    @Schema(description = "Ghi chú (cluster_description)")
    @JsonProperty("ghi_chu")
    private String ghiChu;

    @Schema(description = "Ảnh đại diện")
    @JsonProperty("avatar_url")
    private String avatarUrl;

    // Capacity fields
    @JsonProperty("total_vcpu")
    private Number totalVcpu;
    @JsonProperty("total_vram")
    private Number totalVram;
    @JsonProperty("total_vstorage")
    private Number totalVstorage;
    @JsonProperty("avail_vcpu")
    private Number availVcpu;
    @JsonProperty("avail_vram")
    private Number availVram;
    @JsonProperty("avail_vstorage")
    private Number availVstorage;
    @JsonProperty("used_vcpu")
    private Number usedVcpu;
    @JsonProperty("used_vram")
    private Number usedVram;
    @JsonProperty("used_vstorage")
    private Number usedVstorage;

    // Getters and Setters
    public String getMaCum() { return maCum; }
    public void setMaCum(String maCum) { this.maCum = maCum; }

    public String getTenCum() { return tenCum; }
    public void setTenCum(String tenCum) { this.tenCum = tenCum; }

    public Number getIdcId() { return idcId; }
    public void setIdcId(Number idcId) { this.idcId = idcId; }

    public Number getCongNgheId() { return congNgheId; }
    public void setCongNgheId(Number congNgheId) { this.congNgheId = congNgheId; }

    public Number getPhanLoaiId() { return phanLoaiId; }
    public void setPhanLoaiId(Number phanLoaiId) { this.phanLoaiId = phanLoaiId; }

    public Number getCapDoId() { return capDoId; }
    public void setCapDoId(Number capDoId) { this.capDoId = capDoId; }

    public Number getLoaiHtId() { return loaiHtId; }
    public void setLoaiHtId(Number loaiHtId) { this.loaiHtId = loaiHtId; }

    public Number getTrangThaiCcdvId() { return trangThaiCcdvId; }
    public void setTrangThaiCcdvId(Number trangThaiCcdvId) { this.trangThaiCcdvId = trangThaiCcdvId; }

    public Number getDonviQlId() { return donviQlId; }
    public void setDonviQlId(Number donviQlId) { this.donviQlId = donviQlId; }

    public Number getPhongQlId() { return phongQlId; }
    public void setPhongQlId(Number phongQlId) { this.phongQlId = phongQlId; }

    public Number getCaNhanQlId() { return caNhanQlId; }
    public void setCaNhanQlId(Number caNhanQlId) { this.caNhanQlId = caNhanQlId; }

    public Integer getHieuLuc() { return hieuLuc; }
    public void setHieuLuc(Integer hieuLuc) { this.hieuLuc = hieuLuc; }

    public String getRancherCode() { return rancherCode; }
    public void setRancherCode(String rancherCode) { this.rancherCode = rancherCode; }

    public String getRegionId() { return regionId; }
    public void setRegionId(String regionId) { this.regionId = regionId; }

    public String getPhienBan() { return phienBan; }
    public void setPhienBan(String phienBan) { this.phienBan = phienBan; }

    public String getIpQuanTri() { return ipQuanTri; }
    public void setIpQuanTri(String ipQuanTri) { this.ipQuanTri = ipQuanTri; }

    public String getLinkQuanTri() { return linkQuanTri; }
    public void setLinkQuanTri(String linkQuanTri) { this.linkQuanTri = linkQuanTri; }

    public String getTrangThaiVh() { return trangThaiVh; }
    public void setTrangThaiVh(String trangThaiVh) { this.trangThaiVh = trangThaiVh; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public Number getTotalVcpu() { return totalVcpu; }
    public void setTotalVcpu(Number totalVcpu) { this.totalVcpu = totalVcpu; }

    public Number getTotalVram() { return totalVram; }
    public void setTotalVram(Number totalVram) { this.totalVram = totalVram; }

    public Number getTotalVstorage() { return totalVstorage; }
    public void setTotalVstorage(Number totalVstorage) { this.totalVstorage = totalVstorage; }

    public Number getAvailVcpu() { return availVcpu; }
    public void setAvailVcpu(Number availVcpu) { this.availVcpu = availVcpu; }

    public Number getAvailVram() { return availVram; }
    public void setAvailVram(Number availVram) { this.availVram = availVram; }

    public Number getAvailVstorage() { return availVstorage; }
    public void setAvailVstorage(Number availVstorage) { this.availVstorage = availVstorage; }

    public Number getUsedVcpu() { return usedVcpu; }
    public void setUsedVcpu(Number usedVcpu) { this.usedVcpu = usedVcpu; }

    public Number getUsedVram() { return usedVram; }
    public void setUsedVram(Number usedVram) { this.usedVram = usedVram; }

    public Number getUsedVstorage() { return usedVstorage; }
    public void setUsedVstorage(Number usedVstorage) { this.usedVstorage = usedVstorage; }
}
