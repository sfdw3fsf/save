package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
public class DsBTSNodebDtoIn {
    private Long p_upe_id;

    private Long p_swl2_id;

    private String p_phan_loai = "";

    private String p_ma_node = "";

    private String p_ten_thiet_bi = "";

    private String p_diachi = "";
    private Long p_khuvuc_id;
    private Long p_khuvuc_xuly_id;
    private Long p_nhanvien_ttdh_id;
    private String p_so_dt_ttdh;
    private Long p_donvi_xlsc_id;
    private Long p_donvi_quanly_id;

    public Long getP_upe_id() {
        return p_upe_id;
    }

    public void setP_upe_id(Long p_upe_id) {
        this.p_upe_id = p_upe_id;
    }

    public Long getP_swl2_id() {
        return p_swl2_id;
    }

    public void setP_swl2_id(Long p_swl2_id) {
        this.p_swl2_id = p_swl2_id;
    }

    public String getP_phan_loai() {
        return p_phan_loai;
    }

    public void setP_phan_loai(String p_phan_loai) {
        this.p_phan_loai = p_phan_loai;
    }

    public String getP_ma_node() {
        return p_ma_node;
    }

    public void setP_ma_node(String p_ma_node) {
        this.p_ma_node = p_ma_node;
    }

    public String getP_ten_thiet_bi() {
        return p_ten_thiet_bi;
    }

    public void setP_ten_thiet_bi(String p_ten_thiet_bi) {
        this.p_ten_thiet_bi = p_ten_thiet_bi;
    }

    public String getP_diachi() {
        return p_diachi;
    }

    public void setP_diachi(String p_diachi) {
        this.p_diachi = p_diachi;
    }

    public Long getP_khuvuc_id() {
        return p_khuvuc_id;
    }

    public void setP_khuvuc_id(Long p_khuvuc_id) {
        this.p_khuvuc_id = p_khuvuc_id;
    }

    public Long getP_khuvuc_xuly_id() {
        return p_khuvuc_xuly_id;
    }

    public void setP_khuvuc_xuly_id(Long p_khuvuc_xuly_id) {
        this.p_khuvuc_xuly_id = p_khuvuc_xuly_id;
    }

    public Long getP_nhanvien_ttdh_id() {
        return p_nhanvien_ttdh_id;
    }

    public void setP_nhanvien_ttdh_id(Long p_nhanvien_ttdh_id) {
        this.p_nhanvien_ttdh_id = p_nhanvien_ttdh_id;
    }

    public String getP_so_dt_ttdh() {
        return p_so_dt_ttdh;
    }

    public void setP_so_dt_ttdh(String p_so_dt_ttdh) {
        this.p_so_dt_ttdh = p_so_dt_ttdh;
    }

    public Long getP_donvi_xlsc_id() {
        return p_donvi_xlsc_id;
    }

    public void setP_donvi_xlsc_id(Long p_donvi_xlsc_id) {
        this.p_donvi_xlsc_id = p_donvi_xlsc_id;
    }

    public Long getP_donvi_quanly_id() {
        return p_donvi_quanly_id;
    }

    public void setP_donvi_quanly_id(Long p_donvi_quanly_id) {
        this.p_donvi_quanly_id = p_donvi_quanly_id;
    }
}
