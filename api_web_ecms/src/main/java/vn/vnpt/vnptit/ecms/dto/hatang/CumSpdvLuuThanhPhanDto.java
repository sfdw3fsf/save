package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CumSpdvLuuThanhPhanDto implements Serializable {

    @JsonProperty("cumspdvId")
    private Long cumspdvId;

    @JsonProperty("ds_may_ao")
    private List<ThanhPhanItem> dsMayAo;

    @JsonProperty("ds_thiet_bi_vat_ly")
    private List<ThanhPhanItem> dsThietBiVatLy;

    @JsonProperty("ds_dich_vu_idg")
    private List<ThanhPhanItem> dsDichVuIdg;

    @JsonProperty("ds_dich_vu_ha_tang_khac")
    private List<ThanhPhanItem> dsDichVuHaTangKhac;

    @JsonProperty("ds_vpc")
    private List<ThanhPhanItem> dsVpc;

    public CumSpdvLuuThanhPhanDto() {
    }

    public Long getCumspdvId() {
        return cumspdvId;
    }

    public void setCumspdvId(Long cumspdvId) {
        this.cumspdvId = cumspdvId;
    }

    public List<ThanhPhanItem> getDsMayAo() {
        return dsMayAo;
    }

    public void setDsMayAo(List<ThanhPhanItem> dsMayAo) {
        this.dsMayAo = dsMayAo;
    }

    public List<ThanhPhanItem> getDsThietBiVatLy() {
        return dsThietBiVatLy;
    }

    public void setDsThietBiVatLy(List<ThanhPhanItem> dsThietBiVatLy) {
        this.dsThietBiVatLy = dsThietBiVatLy;
    }

    public List<ThanhPhanItem> getDsDichVuIdg() {
        return dsDichVuIdg;
    }

    public void setDsDichVuIdg(List<ThanhPhanItem> dsDichVuIdg) {
        this.dsDichVuIdg = dsDichVuIdg;
    }

    public List<ThanhPhanItem> getDsDichVuHaTangKhac() {
        return dsDichVuHaTangKhac;
    }

    public void setDsDichVuHaTangKhac(List<ThanhPhanItem> dsDichVuHaTangKhac) {
        this.dsDichVuHaTangKhac = dsDichVuHaTangKhac;
    }

    public List<ThanhPhanItem> getDsVpc() {
        return dsVpc;
    }

    public void setDsVpc(List<ThanhPhanItem> dsVpc) {
        this.dsVpc = dsVpc;
    }

    public static class ThanhPhanItem implements Serializable {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("NGAY_BO_SUNG")
        @JsonFormat(pattern = "dd/MM/yyyy")
        private Date ngayBoSung;

        @JsonProperty("NGUOI_BO_SUNG_ID")
        private Long nguoiBoSungId;

        public ThanhPhanItem() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Date getNgayBoSung() {
            return ngayBoSung;
        }

        public void setNgayBoSung(Date ngayBoSung) {
            this.ngayBoSung = ngayBoSung;
        }

        public Long getNguoiBoSungId() {
            return nguoiBoSungId;
        }

        public void setNguoiBoSungId(Long nguoiBoSungId) {
            this.nguoiBoSungId = nguoiBoSungId;
        }
    }
}

