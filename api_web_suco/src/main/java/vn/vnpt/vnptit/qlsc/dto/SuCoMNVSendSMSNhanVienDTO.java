package vn.vnpt.vnptit.qlsc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SuCoMNVSendSMSNhanVienDTO {
    private long phanVungId;
    private long suCoId;
    private Long phieuId;
    private List<NhanVienDTO> nhanVienList;

    public static class NhanVienDTO {
        @JsonProperty("NHANVIEN_ID")
        private long nhanVienId;
        @JsonProperty("DONVI_ID")
        private long donViId;
        @JsonProperty("SO_DT")
        private String soDT;
        @JsonProperty("TEN_NV")
        private String tenNV;

        public long getNhanVienId() {
            return nhanVienId;
        }

        public long getDonViId() {
            return donViId;
        }

        public String getSoDT() {
            return soDT;
        }
    }

    public long getPhanVungId() {
        return phanVungId;
    }

    public long getSuCoId() {
        return suCoId;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public List<NhanVienDTO> getNhanVienList() {
        return nhanVienList;
    }

    public String valid() {
        StringBuilder sb = new StringBuilder();
        if (phanVungId == 0) sb.append("Param phanVungId is required;");
        if (suCoId == 0) sb.append("Param suCoId is required;");
        return sb.toString();
    }

    public String validSendSMS() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.valid());
        if (this.nhanVienList == null || this.nhanVienList.isEmpty()) sb.append("Param nhanVienList is required;");
        return sb.toString();
    }
}
