package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DsKhaiBaoRackThiCongRequestDto implements Serializable {
    private Long phieuId;
    private String loaiKhaibao;
    private String maRack;
    private String sohuuId;
    private Long idcId;
    private String serial;
    private Long dvqlId;
    private String khonggian;
    private Long idChitiet;
    private String canhanQl;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public String getLoaiKhaibao() {
        return loaiKhaibao;
    }

    public void setLoaiKhaibao(String loaiKhaibao) {
        this.loaiKhaibao = loaiKhaibao;
    }

    public String getMaRack() {
        return maRack;
    }

    public void setMaRack(String maRack) {
        this.maRack = maRack;
    }

    public String getSohuuId() {
        return sohuuId;
    }

    public void setSohuuId(String sohuuId) {
        this.sohuuId = sohuuId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getDvqlId() {
        return dvqlId;
    }

    public void setDvqlId(Long dvqlId) {
        this.dvqlId = dvqlId;
    }

    public String getKhonggian() {
        return khonggian;
    }

    public void setKhonggian(String khonggian) {
        this.khonggian = khonggian;
    }

    public Long getIdChitiet() {
        return idChitiet;
    }

    public void setIdChitiet(Long idChitiet) {
        this.idChitiet = idChitiet;
    }

    public String getCanhanQl() {
        return canhanQl;
    }

    public void setCanhanQl(String canhanQl) {
        this.canhanQl = canhanQl;
    }
}
