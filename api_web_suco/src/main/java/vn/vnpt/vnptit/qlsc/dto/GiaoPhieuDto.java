package vn.vnpt.vnptit.qlsc.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.core.annotation.Order;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GiaoPhieuDto {

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class DsHopDongChuaGiaoDto {
        @NotNull(message = ModelValidation.NOT_NULL)
        @Max(value = 99999L, message = ModelValidation.INVALID)
        @Min(value = 1L, message = ModelValidation.INVALID)
        @Order(value = 1)
        private Long huonggiaoId;

        public Long getHuonggiaoId() {
            return huonggiaoId;
        }

        public void setHuonggiaoId(Long huonggiaoId) {
            this.huonggiaoId = huonggiaoId;
        }
    }
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class DsHopDongDaGiaoDto {
        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 1)
        private Long dichvuvtId;

        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 2)
        private Long loaihdId;

        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 3)
        private Long huonggiaoId;

        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 4)
        private Long donviId;

        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 5)
        private Long loaiptId;

        //@Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 6)
        private String ngayGiao;


        //@Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 7)
        private String maGd;

        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 8)
        private Long nhanvienId;

        public Long getDichvuvtId() {
            return dichvuvtId;
        }

        public void setDichvuvtId(Long dichvuvtId) {
            this.dichvuvtId = dichvuvtId;
        }

        public Long getLoaihdId() {
            return loaihdId;
        }

        public void setLoaihdId(Long loaihdId) {
            this.loaihdId = loaihdId;
        }

        public Long getHuonggiaoId() {
            return huonggiaoId;
        }

        public void setHuonggiaoId(Long huonggiaoId) {
            this.huonggiaoId = huonggiaoId;
        }

        public Long getDonviId() {
            return donviId;
        }

        public void setDonviId(Long donviId) {
            this.donviId = donviId;
        }

        public Long getLoaiptId() {
            return loaiptId;
        }

        public void setLoaiptId(Long loaiptId) {
            this.loaiptId = loaiptId;
        }

        public String getNgayGiao() {
            return ngayGiao;
        }

        public void setNgayGiao(String ngayGiao) {
            this.ngayGiao = ngayGiao;
        }

        public String getMaGd() {
            return maGd;
        }

        public void setMaGd(String maGd) {
            this.maGd = maGd;
        }

        public Long getNhanvienId() {
            return nhanvienId;
        }

        public void setNhanvienId(Long nhanvienId) {
            this.nhanvienId = nhanvienId;
        }
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class HuyPhieuDto {
        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 1)
        private Long phieuId;

        @Max(value = 9999999999L, message = ModelValidation.INVALID)
        @Order(value = 2)
        private Long hdtbId;

        public Long getPhieuId() {
            return phieuId;
        }

        public void setPhieuId(Long phieuId) {
            this.phieuId = phieuId;
        }

        public Long getHdtbId() {
            return hdtbId;
        }

        public void setHdtbId(Long hdtbId) {
            this.hdtbId = hdtbId;
        }
    }

    @NotNull(message = ModelValidation.NOT_NULL)
    @Min(value = 0, message = ModelValidation.INVALID)
    @Max(value = 1, message = ModelValidation.INVALID)
    @Order(value=2)
    private Integer kieudvId;

    @Max(value = 9999999999L, message = ModelValidation.INVALID)
    @Order(value=3)
    private Long hdtbId;

    @Max(value = 9999999999L, message = ModelValidation.INVALID)
    @Order(value=4)
    private Long donviId;

    @Max(value = 9999999999L, message = ModelValidation.INVALID)
    @Order(value=5)
    private Long nhanvienId;

    @Max(value = 9999999999L, message = ModelValidation.INVALID)
    @Order(value=6)
    private Long donvinhanId;

    @Max(value = 9L, message = ModelValidation.INVALID)
    @NotNull(message = ModelValidation.NOT_NULL)
    @Order(value=7)
    private Long tinhtrang;

    @Max(value = 999999999999L, message = ModelValidation.INVALID)
    @NotNull(message = ModelValidation.NOT_NULL)
    @Order(value=8)
    private Long huonggiaoId;

    @Size(min= 0, max = 400, message = ModelValidation.INVALID)
    @Order(value=9)
    private String ndGiao;

    @Max(value = 9999999999L, message = ModelValidation.INVALID)
    @Order(value=11)
    private Long phieuChaId;

    @Max(value = 99999L, message = ModelValidation.INVALID)
    @Order(value=12)
    private Long loaiptId;

    @Max(value = 99, message = ModelValidation.INVALID)
    @Order(value=13)
    private Long tthdId;

    @Size(min= 0, max = 100, message = ModelValidation.INVALID)
    @Order(value=14)
    private String sophieu;

    @Size(min= 0, max = 50, message = ModelValidation.INVALID)
    @Order(value=15)
    private String nguoiCn;

    @Size(min= 0, max = 800, message = ModelValidation.INVALID)
    @Order(value=16)
    private String may_cn;

    @Size(min= 0, max = 800, message = ModelValidation.INVALID)
    @Order(value=17)
    private String ipCn;

    @Max(value = 9999999999L, message = ModelValidation.INVALID)
    @Order(value=18)
    private Long daucuoiId;

    @Order(value=19)
    private String ngayCn;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Order(value=20)
    private String ngaygiao;

    @Max(value = 99L, message = ModelValidation.INVALID)
    @Order(value=21)
    private Long ttph_id;


    public String getNgaygiao() {
        return ngaygiao;
    }

    public void setNgaygiao(String ngaygiao) {
        this.ngaygiao = ngaygiao;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public Integer getKieudvId() {
        return kieudvId;
    }

    public void setKieudvId(Integer kieudvId) {
        this.kieudvId = kieudvId;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getDonviId() {
        return donviId;
    }

    public void setDonviId(Long donviId) {
        this.donviId = donviId;
    }

    public Long getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(Long nhanvienId) {
        this.nhanvienId = nhanvienId;
    }

    public Long getDonvinhanId() {
        return donvinhanId;
    }

    public void setDonvinhanId(Long donvinhanId) {
        this.donvinhanId = donvinhanId;
    }

    public Long getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(Long tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public Long getHuonggiaoId() {
        return huonggiaoId;
    }

    public void setHuonggiaoId(Long huonggiaoId) {
        this.huonggiaoId = huonggiaoId;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public Long getTtph_id() {
        return ttph_id;
    }

    public void setTtph_id(Long ttph_id) {
        this.ttph_id = ttph_id;
    }

    public Long getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(Long phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public Long getLoaiptId() {
        return loaiptId;
    }

    public void setLoaiptId(Long loaiptId) {
        this.loaiptId = loaiptId;
    }

    public Long getTthdId() {
        return tthdId;
    }

    public void setTthdId(Long tthdId) {
        this.tthdId = tthdId;
    }

    public String getSophieu() {
        return sophieu;
    }

    public void setSophieu(String sophieu) {
        this.sophieu = sophieu;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getMay_cn() {
        return may_cn;
    }

    public void setMay_cn(String may_cn) {
        this.may_cn = may_cn;
    }

    public String getIpCn() {
        return ipCn;
    }

    public void setIpCn(String ipCn) {
        this.ipCn = ipCn;
    }

    public Long getDaucuoiId() {
        return daucuoiId;
    }

    public void setDaucuoiId(Long daucuoiId) {
        this.daucuoiId = daucuoiId;
    }
}
