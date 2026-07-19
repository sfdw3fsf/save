package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoaiCongInterfaceDtoIn implements Serializable {
    private Long id;

    private Long phanloai;
    private Long chungloaiThietBiId;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaicongId;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long soCong;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long batdauTu;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String dinhdangTen;

    private Long mucdichsudungCongId;

    private Long bangthongcongId;

    private String thongtinModule;

    private Long ketnoi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(Long phanloai) {
        this.phanloai = phanloai;
    }

    public Long getChungloaiThietBiId() {
        return chungloaiThietBiId;
    }

    public void setChungloaiThietBiId(Long chungloaiThietBiId) {
        this.chungloaiThietBiId = chungloaiThietBiId;
    }

    public Long getLoaicongId() {
        return loaicongId;
    }

    public void setLoaicongId(Long loaicongId) {
        this.loaicongId = loaicongId;
    }

    public Long getSoCong() {
        return soCong;
    }

    public void setSoCong(Long soCong) {
        this.soCong = soCong;
    }

    public Long getBatdauTu() {
        return batdauTu;
    }

    public void setBatdauTu(Long batdauTu) {
        this.batdauTu = batdauTu;
    }

    public String getDinhdangTen() {
        return dinhdangTen;
    }

    public void setDinhdangTen(String dinhdangTen) {
        this.dinhdangTen = dinhdangTen;
    }

    public Long getMucdichsudungCongId() {
        return mucdichsudungCongId;
    }

    public void setMucdichsudungCongId(Long mucdichsudungCongId) {
        this.mucdichsudungCongId = mucdichsudungCongId;
    }

    public Long getBangthongcongId() {
        return bangthongcongId;
    }

    public void setBangthongcongId(Long bangthongcongId) {
        this.bangthongcongId = bangthongcongId;
    }

    public String getThongtinModule() {
        return thongtinModule;
    }

    public void setThongtinModule(String thongtinModule) {
        this.thongtinModule = thongtinModule;
    }

    public Long getKetnoi() {
        return ketnoi;
    }

    public void setKetnoi(Long ketnoi) {
        this.ketnoi = ketnoi;
    }
}
