package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareCapnhatKqITCareMT03Request {


    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }

    public Integer getVgr_id() {
        return vgr_id;
    }

    public void setVgr_id(Integer vgr_id) {
        this.vgr_id = vgr_id;
    }

    public Integer getVhdkh_id() {
        return vhdkh_id;
    }

    public void setVhdkh_id(Integer vhdkh_id) {
        this.vhdkh_id = vhdkh_id;
    }

    public String getVds_id() {
        return vds_id;
    }

    public void setVds_id(String vds_id) {
        this.vds_id = vds_id;
    }

    public Integer getVbaohong_id() {
        return vbaohong_id;
    }

    public void setVbaohong_id(Integer vbaohong_id) {
        this.vbaohong_id = vbaohong_id;
    }

    public Integer getVloai_id() {
        return vloai_id;
    }

    public void setVloai_id(Integer vloai_id) {
        this.vloai_id = vloai_id;
    }

    public String getVnhanvien_th() {
        return vnhanvien_th;
    }

    public void setVnhanvien_th(String vnhanvien_th) {
        this.vnhanvien_th = vnhanvien_th;
    }

    public String getVdonvi() {
        return vdonvi;
    }

    public void setVdonvi(String vdonvi) {
        this.vdonvi = vdonvi;
    }

    public String getVdienthoai_nv() {
        return vdienthoai_nv;
    }

    public void setVdienthoai_nv(String vdienthoai_nv) {
        this.vdienthoai_nv = vdienthoai_nv;
    }

    public String getVten_kh() {
        return vten_kh;
    }

    public void setVten_kh(String vten_kh) {
        this.vten_kh = vten_kh;
    }

    public String getVnguoi_dd() {
        return vnguoi_dd;
    }

    public void setVnguoi_dd(String vnguoi_dd) {
        this.vnguoi_dd = vnguoi_dd;
    }

    public String getVchucvu() {
        return vchucvu;
    }

    public void setVchucvu(String vchucvu) {
        this.vchucvu = vchucvu;
    }

    public String getVdienthoai_kh() {
        return vdienthoai_kh;
    }

    public void setVdienthoai_kh(String vdienthoai_kh) {
        this.vdienthoai_kh = vdienthoai_kh;
    }

    public String getVgoi_dv() {
        return vgoi_dv;
    }

    public void setVgoi_dv(String vgoi_dv) {
        this.vgoi_dv = vgoi_dv;
    }

    public String getVsl_tbi() {
        return vsl_tbi;
    }

    public void setVsl_tbi(String vsl_tbi) {
        this.vsl_tbi = vsl_tbi;
    }

    public String getVtinh_trang() {
        return vtinh_trang;
    }

    public void setVtinh_trang(String vtinh_trang) {
        this.vtinh_trang = vtinh_trang;
    }

    public String getVnguyennhan() {
        return vnguyennhan;
    }

    public void setVnguyennhan(String vnguyennhan) {
        this.vnguyennhan = vnguyennhan;
    }

    public String getVkhacphuc() {
        return vkhacphuc;
    }

    public void setVkhacphuc(String vkhacphuc) {
        this.vkhacphuc = vkhacphuc;
    }

    public Integer getVketqua_id() {
        return vketqua_id;
    }

    public void setVketqua_id(Integer vketqua_id) {
        this.vketqua_id = vketqua_id;
    }

    @JsonProperty("vkieu")
    private Integer vkieu;
    @JsonProperty("vgr_id")
    private Integer vgr_id;
    @JsonProperty("vhdkh_id")
    private Integer vhdkh_id;
    @JsonProperty("vds_id")
    private String vds_id;
    @JsonProperty("vbaohong_id")
    private Integer vbaohong_id;
    @JsonProperty("vloai_id")
    private Integer vloai_id;
    @JsonProperty("vnhanvien_th")
    private String vnhanvien_th;
    @JsonProperty("vdonvi")
    private String vdonvi;
    @JsonProperty("vdienthoai_nv")
    private String vdienthoai_nv;
    @JsonProperty("vten_kh")
    private String vten_kh;
    @JsonProperty("vnguoi_dd")
    private String vnguoi_dd;
    @JsonProperty("vchucvu")
    private String vchucvu;
    @JsonProperty("vdienthoai_kh")
    private String vdienthoai_kh;
    @JsonProperty("vgoi_dv")
    private String vgoi_dv;
    @JsonProperty("vsl_tbi")
    private String vsl_tbi;
    @JsonProperty("vtinh_trang")
    private String vtinh_trang;
    @JsonProperty("vnguyennhan")
    private String vnguyennhan;
    @JsonProperty("vkhacphuc")
    private String vkhacphuc;
    @JsonProperty("vketqua_id")
    private Integer vketqua_id;
}
