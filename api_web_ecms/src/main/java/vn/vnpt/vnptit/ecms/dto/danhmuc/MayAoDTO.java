package vn.vnpt.vnptit.ecms.dto.danhmuc;

import org.codehaus.jackson.annotate.JsonAnyGetter;

import java.io.Serializable;


public class MayAoDTO implements Serializable {
    private String id;
    private String mamayao_infra;
    private String ten;
    private String hatangaohoa_id;
    private String hatangidg_id;
    private String loaivaitro_id;
    private String nguon_yc;
    private String nguoi_yc_id;
    private String hedieuhanh_id;
    private String hatang_phanloai_id;
    private String trangthai_mayao_id;
    private String trangthai_hoatdong_id;
    private String ngay_tao;
    private String ngay_hethan;
    private String tinhgia;
    private String ghi_chu;
    private String hieu_luc;
    private String donviqly_id;
    private String phongbanqly_id;
    private String nguoiqly_id;
    private String cumdichvu;
    private String spdv_id;
    private String donvi_chuquan_id;
    private String vcpu;
    private String vram;
    private String vgpu;
    private String dichvu_iops_id;
    private String dichvu_throughtput_id;
    private String ip_address_id;
    private String network_card;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMamayao_infra() {
        return mamayao_infra;
    }

    public void setMamayao_infra(String mamayao_infra) {
        this.mamayao_infra = mamayao_infra;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHatangaohoa_id() {
        return hatangaohoa_id;
    }

    public void setHatangaohoa_id(String hatangaohoa_id) {
        this.hatangaohoa_id = hatangaohoa_id;
    }

    public String getHatangidg_id() {
        return hatangidg_id;
    }

    public void setHatangidg_id(String hatangidg_id) {
        this.hatangidg_id = hatangidg_id;
    }

    public String getLoaivaitro_id() {
        return loaivaitro_id;
    }

    public void setLoaivaitro_id(String loaivaitro_id) {
        this.loaivaitro_id = loaivaitro_id;
    }

    public String getNguon_yc() {
        return nguon_yc;
    }

    public void setNguon_yc(String nguon_yc) {
        this.nguon_yc = nguon_yc;
    }

    public String getNguoi_yc_id() {
        return nguoi_yc_id;
    }

    public void setNguoi_yc_id(String nguoi_yc_id) {
        this.nguoi_yc_id = nguoi_yc_id;
    }

    public String getHedieuhanh_id() {
        return hedieuhanh_id;
    }

    public void setHedieuhanh_id(String hedieuhanh_id) {
        this.hedieuhanh_id = hedieuhanh_id;
    }

    public String getHatang_phanloai_id() {
        return hatang_phanloai_id;
    }

    public void setHatang_phanloai_id(String hatang_phanloai_id) {
        this.hatang_phanloai_id = hatang_phanloai_id;
    }

    public String getTrangthai_mayao_id() {
        return trangthai_mayao_id;
    }

    public void setTrangthai_mayao_id(String trangthai_mayao_id) {
        this.trangthai_mayao_id = trangthai_mayao_id;
    }

    public String getTrangthai_hoatdong_id() {
        return trangthai_hoatdong_id;
    }

    public void setTrangthai_hoatdong_id(String trangthai_hoatdong_id) {
        this.trangthai_hoatdong_id = trangthai_hoatdong_id;
    }

    public String getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(String ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getNgay_hethan() {
        return ngay_hethan;
    }

    public void setNgay_hethan(String ngay_hethan) {
        this.ngay_hethan = ngay_hethan;
    }

    public String getTinhgia() {
        return tinhgia;
    }

    public void setTinhgia(String tinhgia) {
        this.tinhgia = tinhgia;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public String getHieu_luc() {
        return hieu_luc;
    }

    public void setHieu_luc(String hieu_luc) {
        this.hieu_luc = hieu_luc;
    }

    public String getDonviqly_id() {
        return donviqly_id;
    }

    public void setDonviqly_id(String donviqly_id) {
        this.donviqly_id = donviqly_id;
    }

    public String getPhongbanqly_id() {
        return phongbanqly_id;
    }

    public void setPhongbanqly_id(String phongbanqly_id) {
        this.phongbanqly_id = phongbanqly_id;
    }

    public String getNguoiqly_id() {
        return nguoiqly_id;
    }

    public void setNguoiqly_id(String nguoiqly_id) {
        this.nguoiqly_id = nguoiqly_id;
    }

    public String getCumdichvu() {
        return cumdichvu;
    }

    public void setCumdichvu(String cumdichvu) {
        this.cumdichvu = cumdichvu;
    }

    public String getSpdv_id() {
        return spdv_id;
    }

    public void setSpdv_id(String spdv_id) {
        this.spdv_id = spdv_id;
    }

    public String getDonvi_chuquan_id() {
        return donvi_chuquan_id;
    }

    public void setDonvi_chuquan_id(String donvi_chuquan_id) {
        this.donvi_chuquan_id = donvi_chuquan_id;
    }

    public String getVcpu() {
        return vcpu;
    }

    public void setVcpu(String vcpu) {
        this.vcpu = vcpu;
    }

    public String getVram() {
        return vram;
    }

    public void setVram(String vram) {
        this.vram = vram;
    }

    public String getVgpu() {
        return vgpu;
    }

    public void setVgpu(String vgpu) {
        this.vgpu = vgpu;
    }

    public String getDichvu_iops_id() {
        return dichvu_iops_id;
    }

    public void setDichvu_iops_id(String dichvu_iops_id) {
        this.dichvu_iops_id = dichvu_iops_id;
    }

    public String getDichvu_throughtput_id() {
        return dichvu_throughtput_id;
    }

    public void setDichvu_throughtput_id(String dichvu_throughtput_id) {
        this.dichvu_throughtput_id = dichvu_throughtput_id;
    }

    public String getIp_address_id() {
        return ip_address_id;
    }

    public void setIp_address_id(String ip_address_id) {
        this.ip_address_id = ip_address_id;
    }

    public String getNetwork_card() {
        return network_card;
    }

    public void setNetwork_card(String network_card) {
        this.network_card = network_card;
    }


}
