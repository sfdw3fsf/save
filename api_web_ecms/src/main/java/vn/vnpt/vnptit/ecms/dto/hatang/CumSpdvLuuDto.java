package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CumSpdvLuuDto implements Serializable {

    @JsonProperty("cumspdv_id")
    private Long cumspdvId;

    @JsonProperty("ten_cum_spdv")
    private String tenCumSpdv;

    @JsonProperty("hieu_luc")
    private Long hieuLuc;

    @JsonProperty("spdv_id")
    private Long spdvId;

    @JsonProperty("trang_thai_id")
    private Long trangThaiId;

    @JsonProperty("chuquan_dv_id")
    private Long chuquanDvId;

    @JsonProperty("ngay_khoi_tao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayKhoiTao;

    @JsonProperty("phien_ban")
    private String phienBan;

    @JsonProperty("hinhthuc_cungcap_id")
    private Long hinhthucCungcapId;

    @JsonProperty("donvi_khoi_tao_id")
    private Long donviKhoiTaoId;

    @JsonProperty("hatang_trienkhau_id")
    private Long hatangTrienkhaiId;

    @JsonProperty("hatang_trienkhai_id")
    private Long hatangTrienkhaiIdAlias;

    @JsonProperty("nguoi_khoi_tao_id")
    private Long nguoiKhoiTaoId;

    @JsonProperty("ghi_chu")
    private String ghiChu;

    @JsonProperty("donvi_ql_id")
    private Long donviQlId;

    @JsonProperty("phong_ql_id")
    private Long phongQlId;

    @JsonProperty("ca_nhan_ql_list")
    private List<CaNhanQlItem> caNhanQlList;

    @JsonProperty("ca_nhan_ql_id")
    private Long caNhanQlId;

    public CumSpdvLuuDto() {
    }

    public Long getCumspdvId() {
        return cumspdvId;
    }

    public void setCumspdvId(Long cumspdvId) {
        this.cumspdvId = cumspdvId;
    }

    public String getTenCumSpdv() {
        return tenCumSpdv;
    }

    public void setTenCumSpdv(String tenCumSpdv) {
        this.tenCumSpdv = tenCumSpdv;
    }

    public Long getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Long hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getSpdvId() {
        return spdvId;
    }

    public void setSpdvId(Long spdvId) {
        this.spdvId = spdvId;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Long getChuquanDvId() {
        return chuquanDvId;
    }

    public void setChuquanDvId(Long chuquanDvId) {
        this.chuquanDvId = chuquanDvId;
    }

    public Date getNgayKhoiTao() {
        return ngayKhoiTao;
    }

    public void setNgayKhoiTao(Date ngayKhoiTao) {
        this.ngayKhoiTao = ngayKhoiTao;
    }

    public String getPhienBan() {
        return phienBan;
    }

    public void setPhienBan(String phienBan) {
        this.phienBan = phienBan;
    }

    public Long getHinhthucCungcapId() {
        return hinhthucCungcapId;
    }

    public void setHinhthucCungcapId(Long hinhthucCungcapId) {
        this.hinhthucCungcapId = hinhthucCungcapId;
    }

    public Long getDonviKhoiTaoId() {
        return donviKhoiTaoId;
    }

    public void setDonviKhoiTaoId(Long donviKhoiTaoId) {
        this.donviKhoiTaoId = donviKhoiTaoId;
    }

    public Long getHatangTrienkhaiId() {
        return hatangTrienkhaiId != null ? hatangTrienkhaiId : hatangTrienkhaiIdAlias;
    }

    public void setHatangTrienkhaiId(Long hatangTrienkhaiId) {
        this.hatangTrienkhaiId = hatangTrienkhaiId;
    }

    public Long getHatangTrienkhaiIdAlias() {
        return hatangTrienkhaiIdAlias;
    }

    public void setHatangTrienkhaiIdAlias(Long hatangTrienkhaiIdAlias) {
        this.hatangTrienkhaiIdAlias = hatangTrienkhaiIdAlias;
    }

    public Long getNguoiKhoiTaoId() {
        return nguoiKhoiTaoId;
    }

    public void setNguoiKhoiTaoId(Long nguoiKhoiTaoId) {
        this.nguoiKhoiTaoId = nguoiKhoiTaoId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getDonviQlId() {
        return donviQlId;
    }

    public void setDonviQlId(Long donviQlId) {
        this.donviQlId = donviQlId;
    }

    public Long getPhongQlId() {
        return phongQlId;
    }

    public void setPhongQlId(Long phongQlId) {
        this.phongQlId = phongQlId;
    }

    public List<CaNhanQlItem> getCaNhanQlList() {
        return caNhanQlList;
    }

    public void setCaNhanQlList(List<CaNhanQlItem> caNhanQlList) {
        this.caNhanQlList = caNhanQlList;
    }

    public Long getCaNhanQlId() {
        return caNhanQlId;
    }

    public void setCaNhanQlId(Long caNhanQlId) {
        this.caNhanQlId = caNhanQlId;
    }

    public static class CaNhanQlItem implements Serializable {
        @JsonProperty("NHANVIEN_ID")
        private Long nhanvienId;
        @JsonProperty("MA_NV")
        private String maNv;
        @JsonProperty("TEN_NV")
        private String tenNv;
        @JsonProperty("EMAIL")
        private String email;
        @JsonProperty("SO_DT")
        private String soDt;
        @JsonProperty("NHIEMVU_ID")
        private Long nhiemvuId;

        public CaNhanQlItem() {
        }

        public Long getNhanvienId() {
            return nhanvienId;
        }

        public void setNhanvienId(Long nhanvienId) {
            this.nhanvienId = nhanvienId;
        }

        public String getMaNv() {
            return maNv;
        }

        public void setMaNv(String maNv) {
            this.maNv = maNv;
        }

        public String getTenNv() {
            return tenNv;
        }

        public void setTenNv(String tenNv) {
            this.tenNv = tenNv;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSoDt() {
            return soDt;
        }

        public void setSoDt(String soDt) {
            this.soDt = soDt;
        }

        public Long getNhiemvuId() {
            return nhiemvuId;
        }

        public void setNhiemvuId(Long nhiemvuId) {
            this.nhiemvuId = nhiemvuId;
        }
    }
}

