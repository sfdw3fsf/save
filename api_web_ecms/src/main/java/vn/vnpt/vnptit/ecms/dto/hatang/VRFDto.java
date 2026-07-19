package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class VRFDto {
    private Number id = 0;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private String ghiChu;
    private Number hieuLuc;
    private Number idcID;
    private String rd;
    private String rtExport;
    private String rtImport;
    private String asn;
    private Number thietBiMangId;
    private Number nhanVienId;
    private String mayCapNhap;
    private String nguoiCapNhap;
    private String ipCapNhap;

    public Number getId() {
        return id;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getTen() {
        return ten != null ? ten.trim() : null;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public Number getIdcID() {
        return idcID;
    }

    public String getRd() {
        return rd;
    }

    public String getRtExport() {
        return rtExport;
    }

    public String getRtImport() {
        return rtImport;
    }

    public String getAsn() {
        return asn;
    }

    public Number getThietBiMangId() {
        return thietBiMangId;
    }

    public Number getNhanVienId() {
        return nhanVienId;
    }

    public String getMayCapNhap() {
        return mayCapNhap;
    }

    public String getNguoiCapNhap() {
        return nguoiCapNhap;
    }

    public String getIpCapNhap() {
        return ipCapNhap;
    }
}
