package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class SoDoKetNoiDto implements Serializable {

    private Long id;
    private Long doiTuongId;
    private String doiTuong;
    private String duLieu;
    private String moTa;

    public SoDoKetNoiDto() {
    }

    public SoDoKetNoiDto(Long id, Long doiTuongId, String doiTuong, String duLieu, String moTa) {
        this.id = id;
        this.doiTuongId = doiTuongId;
        this.doiTuong = doiTuong;
        this.duLieu = duLieu;
        this.moTa = moTa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Long doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public String getDuLieu() {
        return duLieu;
    }

    public void setDuLieu(String duLieu) {
        this.duLieu = duLieu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
