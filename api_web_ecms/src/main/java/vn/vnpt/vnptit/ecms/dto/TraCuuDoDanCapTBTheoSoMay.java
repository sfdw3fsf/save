package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TraCuuDoDanCapTBTheoSoMay {
    @NotBlank(message = ModelValidation.NOT_NULL)
    private String so_may;

    private Integer thue_bao_id;

    private Integer hdtb_id;

    private Integer id; //id chung tb va hd

    private Integer dich_vu_id;

    private Integer ket_cuoi_id;

    private Integer dau_cuoi_id;

    private Integer jumper_vao;

    private String ky_hieu;

    String ghichu;

    private Integer vitri;
    @NotNull(message = ModelValidation.NOT_NULL)
    private  Integer loai_tra_cuu;

    public Integer getHdtb_id() {
        return hdtb_id;
    }

    public void setHdtb_id(Integer hdtb_id) {
        this.hdtb_id = hdtb_id;
    }

    public Integer getLoai_tra_cuu() {
        return loai_tra_cuu;
    }

    public void setLoai_tra_cuu(Integer loai_tra_cuu) {
        this.loai_tra_cuu = loai_tra_cuu;
    }

    public String getKy_hieu() {
        return ky_hieu;
    }

    public void setKy_hieu(String ky_hieu) {
        this.ky_hieu = ky_hieu;
    }

    public Integer getVitri() {
        return vitri;
    }

    public void setVitri(Integer vitri) {
        this.vitri = vitri;
    }

    public String getSo_may() {
        return so_may;
    }

    public void setSo_may(String so_may) {
        this.so_may = so_may;
    }

    public Integer getThue_bao_id() {
        return thue_bao_id;
    }

    public void setThue_bao_id(Integer thue_bao_id) {
        this.thue_bao_id = thue_bao_id;
    }

    public Integer getDich_vu_id() {
        return dich_vu_id;
    }

    public void setDich_vu_id(Integer dich_vu_id) {
        this.dich_vu_id = dich_vu_id;
    }

    public Integer getKet_cuoi_id() {
        return ket_cuoi_id;
    }

    public void setKet_cuoi_id(Integer ket_cuoi_id) {
        this.ket_cuoi_id = ket_cuoi_id;
    }

    public Integer getDau_cuoi_id() {
        return dau_cuoi_id;
    }

    public void setDau_cuoi_id(Integer dau_cuoi_id) {
        this.dau_cuoi_id = dau_cuoi_id;
    }

    public Integer getJumper_vao() {
        return jumper_vao;
    }

    public void setJumper_vao(Integer jumper_vao) {
        this.jumper_vao = jumper_vao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
