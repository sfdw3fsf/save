package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ThongTinPhanHoi {
    @JsonProperty("trangthai")
    private String trangthai;

    @JsonProperty("noiDungPhanHoi")
    private String noiDungPhanHoi;

    @JsonProperty("ketQuaXuLy")
    private String ketQuaXuLy;

    @JsonProperty("thongTinLoaiKiemTra")
    private List<ThongTinLoaiKiemTra> thongTinLoaiKiemTra;

    @JsonProperty("fileDinhKem")
    private List<String> fileDinhKem;

    public ThongTinPhanHoi() {
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getNoiDungPhanHoi() {
        return noiDungPhanHoi;
    }

    public void setNoiDungPhanHoi(String noiDungPhanHoi) {
        this.noiDungPhanHoi = noiDungPhanHoi;
    }

    public String getKetQuaXuLy() {
        return ketQuaXuLy;
    }

    public void setKetQuaXuLy(String ketQuaXuLy) {
        this.ketQuaXuLy = ketQuaXuLy;
    }

    public List<ThongTinLoaiKiemTra> getThongTinLoaiKiemTra() {
        return thongTinLoaiKiemTra;
    }

    public void setThongTinLoaiKiemTra(List<ThongTinLoaiKiemTra> thongTinLoaiKiemTra) {
        this.thongTinLoaiKiemTra = thongTinLoaiKiemTra;
    }

    public List<String> getFileDinhKem() {
        return fileDinhKem;
    }

    public void setFileDinhKem(List<String> fileDinhKem) {
        this.fileDinhKem = fileDinhKem;
    }
}
