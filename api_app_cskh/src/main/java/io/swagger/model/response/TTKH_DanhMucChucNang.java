package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.*;

import java.util.List;

/**
 * TTKH_DanhMucChucNang
 */
public class TTKH_DanhMucChucNang {
    @JsonProperty("dsVatTu")
    protected List<TTKH_VatTu> dsVatTu;
    @JsonProperty("dsKho")
    protected List<TTKH_Kho> dsKho;
    @JsonProperty("dsLoHang")
    protected List<TTKH_LoHang> dsLoHang;
    @JsonProperty("dsKieuTB")
    protected List<TTKH_KieuThietBi> dsKieuTB;
    @JsonProperty("dsTinhTrang")
    protected List<TTKH_TinhTrang> dsTinhTrang;
    @JsonProperty("dsTrangBi")
    protected List<TTKH_TrangBi> dsTrangBi;
    @JsonProperty("dsDichVuVT")
    protected List<TTKH_DichVuVT> dsDichVuVT;
    @JsonProperty("dsDonViTinh")
    protected List<TTKH_DonViTinh> dsDonViTinh;

    public List<TTKH_VatTu> getDsVatTu() {
        return dsVatTu;
    }

    public void setDsVatTu(List<TTKH_VatTu> dsVatTu) {
        this.dsVatTu = dsVatTu;
    }

    public List<TTKH_Kho> getDsKho() {
        return dsKho;
    }

    public void setDsKho(List<TTKH_Kho> dsKho) {
        this.dsKho = dsKho;
    }

    public List<TTKH_LoHang> getDsLoHang() {
        return dsLoHang;
    }

    public void setDsLoHang(List<TTKH_LoHang> dsLoHang) {
        this.dsLoHang = dsLoHang;
    }

    public List<TTKH_KieuThietBi> getDsKieuTB() {
        return dsKieuTB;
    }

    public void setDsKieuTB(List<TTKH_KieuThietBi> dsKieuTB) {
        this.dsKieuTB = dsKieuTB;
    }

    public List<TTKH_TinhTrang> getDsTinhTrang() {
        return dsTinhTrang;
    }

    public void setDsTinhTrang(List<TTKH_TinhTrang> dsTinhTrang) {
        this.dsTinhTrang = dsTinhTrang;
    }

    public List<TTKH_TrangBi> getDsTrangBi() {
        return dsTrangBi;
    }

    public void setDsTrangBi(List<TTKH_TrangBi> dsTrangBi) {
        this.dsTrangBi = dsTrangBi;
    }

    public List<TTKH_DichVuVT> getDsDichVuVT() {
        return dsDichVuVT;
    }

    public void setDsDichVuVT(List<TTKH_DichVuVT> dsDichVuVT) {
        this.dsDichVuVT = dsDichVuVT;
    }

    public List<TTKH_DonViTinh> getDsDonViTinh() {
        return dsDonViTinh;
    }

    public void setDsDonViTinh(List<TTKH_DonViTinh> dsDonViTinh) {
        this.dsDonViTinh = dsDonViTinh;
    }
}
