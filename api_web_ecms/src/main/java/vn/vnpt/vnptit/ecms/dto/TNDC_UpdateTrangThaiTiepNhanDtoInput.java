package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TNDC_UpdateTrangThaiTiepNhanDtoInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private int vkieu;
    private int vvanhanh_id;
    private int vloaivh_id;
    private int vthietbi_id;
    private String vnoidung;
    private String vngay_yc;
    private int vdonvi_id;
    private String vketqua_td;
    private String vketqua;
    private int vttvh_id;
    private String vngay_ht;
    private String vghichu;
    private String vngay_cn;
    private String vdexuat;
    private String vmota;
    private int vphieuvh_id;
    private int vdonvi_nhan_id;
    private int vthietbi_cha_id;
    private int vchuyen_tbcon;

    public int getVkieu() {
        return vkieu;
    }

    public void setVkieu(int vkieu) {
        this.vkieu = vkieu;
    }

    public int getVvanhanh_id() {
        return vvanhanh_id;
    }

    public void setVvanhanh_id(int vvanhanh_id) {
        this.vvanhanh_id = vvanhanh_id;
    }

    public int getVloaivh_id() {
        return vloaivh_id;
    }

    public void setVloaivh_id(int vloaivh_id) {
        this.vloaivh_id = vloaivh_id;
    }

    public int getVthietbi_id() {
        return vthietbi_id;
    }

    public void setVthietbi_id(int vthietbi_id) {
        this.vthietbi_id = vthietbi_id;
    }

    public String getVnoidung() {
        return vnoidung;
    }

    public void setVnoidung(String vnoidung) {
        this.vnoidung = vnoidung;
    }

    public String getVngay_yc() {
        return vngay_yc;
    }

    public void setVngay_yc(String vngay_yc) {
        this.vngay_yc = vngay_yc;
    }

    public int getVdonvi_id() {
        return vdonvi_id;
    }

    public void setVdonvi_id(int vdonvi_id) {
        this.vdonvi_id = vdonvi_id;
    }

    public String getVketqua_td() {
        return vketqua_td;
    }

    public void setVketqua_td(String vketqua_td) {
        this.vketqua_td = vketqua_td;
    }

    public String getVketqua() {
        return vketqua;
    }

    public void setVketqua(String vketqua) {
        this.vketqua = vketqua;
    }

    public int getVttvh_id() {
        return vttvh_id;
    }

    public void setVttvh_id(int vttvh_id) {
        this.vttvh_id = vttvh_id;
    }

    public String getVngay_ht() {
        return vngay_ht;
    }

    public void setVngay_ht(String vngay_ht) {
        this.vngay_ht = vngay_ht;
    }

    public String getVghichu() {
        return vghichu;
    }

    public void setVghichu(String vghichu) {
        this.vghichu = vghichu;
    }

    public String getVngay_cn() {
        return vngay_cn;
    }

    public void setVngay_cn(String vngay_cn) {
        this.vngay_cn = vngay_cn;
    }

    public String getVdexuat() {
        return vdexuat;
    }

    public void setVdexuat(String vdexuat) {
        this.vdexuat = vdexuat;
    }

    public String getVmota() {
        return vmota;
    }

    public void setVmota(String vmota) {
        this.vmota = vmota;
    }

    public int getVphieuvh_id() {
        return vphieuvh_id;
    }

    public void setVphieuvh_id(int vphieuvh_id) {
        this.vphieuvh_id = vphieuvh_id;
    }

    public int getVdonvi_nhan_id() {
        return vdonvi_nhan_id;
    }

    public void setVdonvi_nhan_id(int vdonvi_nhan_id) {
        this.vdonvi_nhan_id = vdonvi_nhan_id;
    }

    public int getVthietbi_cha_id() {
        return vthietbi_cha_id;
    }

    public void setVthietbi_cha_id(int vthietbi_cha_id) {
        this.vthietbi_cha_id = vthietbi_cha_id;
    }

    public int getVchuyen_tbcon() {
        return vchuyen_tbcon;
    }

    public void setVchuyen_tbcon(int vchuyen_tbcon) {
        this.vchuyen_tbcon = vchuyen_tbcon;
    }
}
