package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.NotNull;

public class DongGiaoPhieuThuTucInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idGiaoPhieuThuTuc;
    
    private Long idPhieu;
    
    private String ghiChu;
    
    private String dsIdAnh;

    public Long getIdGiaoPhieuThuTuc() {
        return idGiaoPhieuThuTuc;
    }

    public void setIdGiaoPhieuThuTuc(Long idGiaoPhieuThuTuc) {
        this.idGiaoPhieuThuTuc = idGiaoPhieuThuTuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getIdPhieu() {
        return idPhieu;
    }

    public void setIdPhieu(Long idPhieu) {
        this.idPhieu = idPhieu;
    }

    public String getDsIdAnh() {
        return dsIdAnh;
    }

    public void setDsIdAnh(String dsIdAnh) {
        this.dsIdAnh = dsIdAnh;
    }
}
