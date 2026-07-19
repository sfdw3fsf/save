package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChiaSeSuCoCNTTInput {
    private Number phanVungId;
    private Number suCoId;
    private List<NhanVien> dsChiaSe;
    private String noiDungGiao;
    private Number sms;

    public static class NhanVien {
        @JsonProperty("NHANVIEN_ID")
        private long nhanVienId;
        @JsonProperty("SO_DT")
        private String soDT;
        @JsonProperty("DONVI_ID")
        private long donViId;

        public long getNhanVienId() {
            return nhanVienId;
        }

        public String getSoDT() {
            return soDT;
        }

        public long getDonViId() {
            return donViId;
        }
    }

    public Number getPhanVungId() {
        return phanVungId;
    }

    public Number getSuCoId() {
        return suCoId;
    }

    public List<NhanVien> getDsChiaSe() {
        return dsChiaSe;
    }

    public String getNoiDungGiao() {
        return noiDungGiao;
    }

    public Number getSms() {
        return sms;
    }
}
