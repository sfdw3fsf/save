package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ChungLoaiThietBiDtoIn implements Serializable {
    private Long id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String model;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long nhasx_id;
    private Long chungloai_id;
    private Long chieucao;
    private Long cs_dinhdanh;
    private String ghi_chu;
    private Long hieu_luc;
    private Long fulldepth;
    private Integer phanLoai;
    private Integer so_line_card;
    private Integer so_power;
    private Integer so_control_card;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getNhasx_id() {
        return nhasx_id;
    }

    public void setNhasx_id(Long nhasx_id) {
        this.nhasx_id = nhasx_id;
    }

    public Long getChungloai_id() {
        return chungloai_id;
    }

    public void setChungloai_id(Long chungloai_id) {
        this.chungloai_id = chungloai_id;
    }

    public Long getChieucao() {
        return chieucao;
    }

    public void setChieucao(Long chieucao) {
        this.chieucao = chieucao;
    }

    public Long getCs_dinhdanh() {
        return cs_dinhdanh;
    }

    public void setCs_dinhdanh(Long cs_dinhdanh) {
        this.cs_dinhdanh = cs_dinhdanh;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public Long getHieu_luc() {
        return hieu_luc;
    }

    public void setHieu_luc(Long hieu_luc) {
        this.hieu_luc = hieu_luc;
    }

    public Long getFulldepth() {
        return fulldepth;
    }

    public void setFulldepth(Long fulldepth) {
        this.fulldepth = fulldepth;
    }

    public Integer getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(Integer phanLoai) {
        this.phanLoai = phanLoai;
    }

    public Integer getSo_line_card() {
        return so_line_card;
    }

    public void setSo_line_card(Integer so_line_card) {
        this.so_line_card = so_line_card;
    }

    public Integer getSo_power() {
        return so_power;
    }

    public void setSo_power(Integer so_power) {
        this.so_power = so_power;
    }

    public Integer getSo_control_card() {
        return so_control_card;
    }

    public void setSo_control_card(Integer so_control_card) {
        this.so_control_card = so_control_card;
    }
}
