package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class DoiTuongUpdate {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long doituong_id;
    private String ten_ht;
    private Long loaidt_id;
    private Long doituong_map_id;
    private Long tuyentd_id;
    private Long vitri_x;
    private Long vitri_y;
    private Long vitri_td_x;
    private Long vitri_td_y;


    public DoiTuongUpdate(Long doituong_id, String ten_ht, Long loaidt_id, Long doituong_map_id, Long tuyentd_id, Long vitri_x, Long vitri_y, Long vitri_td_x, Long vitri_td_y) {
        this.doituong_id = doituong_id;
        this.ten_ht = ten_ht;
        this.loaidt_id = loaidt_id;
        this.doituong_map_id = doituong_map_id;
        this.tuyentd_id = tuyentd_id;
        this.vitri_x = vitri_x;
        this.vitri_y = vitri_y;
        this.vitri_td_x = vitri_td_x;
        this.vitri_td_y = vitri_td_y;
    }

    public Long getDoituong_id() {
        return doituong_id;
    }

    public void setDoituong_id(Long doituong_id) {
        this.doituong_id = doituong_id;
    }

    public String getTen_ht() {
        return ten_ht;
    }

    public void setTen_ht(String ten_ht) {
        this.ten_ht = ten_ht;
    }

    public Long getLoaidt_id() {
        return loaidt_id;
    }

    public void setLoaidt_id(Long loaidt_id) {
        this.loaidt_id = loaidt_id;
    }

    public Long getDoituong_map_id() {
        return doituong_map_id;
    }

    public void setDoituong_map_id(Long doituong_map_id) {
        this.doituong_map_id = doituong_map_id;
    }

    public Long getTuyentd_id() {
        return tuyentd_id;
    }

    public void setTuyentd_id(Long tuyentd_id) {
        this.tuyentd_id = tuyentd_id;
    }

    public Long getVitri_x() {
        return vitri_x;
    }

    public void setVitri_x(Long vitri_x) {
        this.vitri_x = vitri_x;
    }

    public Long getVitri_y() {
        return vitri_y;
    }

    public void setVitri_y(Long vitri_y) {
        this.vitri_y = vitri_y;
    }

    public Long getVitri_td_x() {
        return vitri_td_x;
    }

    public void setVitri_td_x(Long vitri_td_x) {
        this.vitri_td_x = vitri_td_x;
    }

    public Long getVitri_td_y() {
        return vitri_td_y;
    }

    public void setVitri_td_y(Long vitri_td_y) {
        this.vitri_td_y = vitri_td_y;
    }
}
