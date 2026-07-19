package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.NotNull;

public class LuuBuocXuLyInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idBuoc;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer thanhCong;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idLyDo;

    private String ghiChu;
    
    private String maProc;

    public Long getIdBuoc() {
        return idBuoc;
    }

    public void setIdBuoc(Long idBuoc) {
        this.idBuoc = idBuoc;
    }

    public Integer getThanhCong() {
        return thanhCong;
    }

    public void setThanhCong(Integer thanhCong) {
        this.thanhCong = thanhCong;
    }

    public Long getIdLyDo() {
        return idLyDo;
    }

    public void setIdLyDo(Long idLyDo) {
        this.idLyDo = idLyDo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaProc() {
        return maProc;
    }

    public void setMaProc(String maProc) {
        this.maProc = maProc;
    }
}
