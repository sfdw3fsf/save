package vn.vnpt.vnptit.qlsc.dto.cntt;

import java.util.List;

public class DongThuTucInput {

    private Long p_id_giaophieu_thutuc;
    private Long p_id_phieu;
    private String p_ghichu;
    private List<Long> listAnh;

    public Long getP_id_giaophieu_thutuc() { return p_id_giaophieu_thutuc; }
    public void setP_id_giaophieu_thutuc(Long p_id_giaophieu_thutuc) { this.p_id_giaophieu_thutuc = p_id_giaophieu_thutuc; }

    public Long getP_id_phieu() { return p_id_phieu; }
    public void setP_id_phieu(Long p_id_phieu) { this.p_id_phieu = p_id_phieu; }

    public String getP_ghichu() { return p_ghichu; }
    public void setP_ghichu(String p_ghichu) { this.p_ghichu = p_ghichu; }

    public List<Long> getListAnh() { return listAnh; }
    public void setListAnh(List<Long> listAnh) { this.listAnh = listAnh; }
}
