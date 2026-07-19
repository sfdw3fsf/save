package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThietBiKhachHangDto {
    @JsonProperty(value = "thietBiKHId", required = false)
    Long thietBiKHId;
    @JsonProperty(value = "heThongTbiId", required = false)
    Long heThongTbiId;
    @JsonProperty(value = "mangTbiId", required = false)
    Long mangTbiId;
    @JsonProperty(value = "loaiThietBiId", required = false)
    Long loaiThietBiId;
    @JsonProperty(value = "thanhPhanThietBiIds", required = false)
    List<Long> thanhPhanThietBiIds;
    @JsonProperty(value = "ten", required = false)
    String ten;
    @JsonProperty(value = "model", required = false)
    String model;
    @JsonProperty(value = "cauHinh", required = false)
    String cauHinh;
    @JsonProperty(value = "serialNumber", required = false)
    String serialNumber;
    @JsonProperty(value = "partNumber", required = false)
    String partNumber;
    @JsonProperty(value = "csDinhDanh", required = false)
    String csDinhDanh;
    @JsonProperty(value = "soPortConsole", required = false)
    String soPortConsole;
    @JsonProperty(value = "soPortInterface", required = false)
    String soPortInterface;
    @JsonProperty(value = "hangSxId", required = false)
    Long hangSxId;
    @JsonProperty(value = "noiSanXuatId", required = false)
    Long noiSanXuatId;
    @JsonProperty(value = "namSx", required = false)
    String namSx;
    @JsonProperty(value = "iconPath", required = false)
    String iconPath;
    @JsonProperty(value = "loaiKhachHang", required = false)
    String loaiKhachHang;
    @JsonProperty(value = "donViQLId", required = false)
    Long donViQLId;
    @JsonProperty(value = "nguoiQLId", required = false)
    Long nguoiQLId;
    @JsonProperty(value = "phongId", required = false)
    Long phongId;
    @JsonProperty(value = "phamViIDCId", required = false)
    Long phamViIDCId;

    @JsonProperty(value = "idcId", required = false)
    Long idcId;
    @JsonProperty(value = "idcRackId", required = false)
    Long idcRackId;
    @JsonProperty(value = "upUnitId", required = false)
    Long upUnitId;
    @JsonProperty(value = "downUnitId", required = false)
    Long downUnitId;
    @JsonProperty(value = "ngayLapDat", required = false)
    Date ngayLapDat;
    @JsonProperty(value = "canCuLD", required = false)
    String canCuLD;
    @JsonProperty(value = "trangThaiSD", required = false)
    String trangThaiSD;
    @JsonProperty(value = "trangThaiTsId", required = false)
    String trangThaiTsId;
    @JsonProperty(value = "maTs", required = false)
    String maTs;
    @JsonProperty(value = "hieuLuc", required = false)
    Long hieuLuc;
    @JsonProperty(value = "tsNoiBo", required = false)
    String tsNoiBo;
    @JsonProperty(value = "moTa", required = false)
    String moTa;
    @JsonProperty(value = "nguoiCn", required = false)
    Long nguoiCn;
    @JsonProperty(value = "ngayCn", required = false)
    Date ngayCn;
    @JsonProperty(value = "trangThaiVanHanhId", required = false)
    String trangThaiVanHanhId;
    @JsonProperty(value = "toaNhaId", required = false)
    Long toaNhaId;
    @JsonProperty(value = "loaiCongId", required = false)
    Long loaiCongId;
    @JsonProperty(value = "portId", required = false)
    Long portId;
    @JsonProperty(value = "hdtbId", required = false)
    Long hdtbId;
    @JsonProperty(value = "tinhId", required = false)
    Long tinhId;

    public ThietBiKhachHangDto() {
    }

    public ThietBiKhachHangDto(Long thietBiKHId, Long heThongTbiId, Long mangTbiId, Long loaiThietBiId,
            List<Long> thanhPhanThietBiIds, String ten,
            String model,
            String cauHinh, String serialNumber, String partNumber, String csDinhDanh, String soPortConsole,
            String soPortInterface, Long hangSxId, String namSx, String iconPath, String loaiKhachHang,
            Long donViQLId, Long nguoiQLId, Long phongId, Long idcId, Long idcRackId, Long upUnitId, Long downUnitId,
            Date ngayLapDat, String canCuLD, String trangThaiSD, String trangThaiTsId, String maTs, Long hieuLuc,
            String tsNoiBo, String moTa, Long nguoiCn, Date ngayCn, String trangThaiVanHanhId, Long toaNhaId,
            Long noiSanXuatId, Long phamViIDCId,
            Long loaiCongId, Long portId, Long hdtbId, Long tinhId) {
        this.thietBiKHId = thietBiKHId;
        this.heThongTbiId = heThongTbiId;
        this.mangTbiId = mangTbiId;
        this.loaiThietBiId = loaiThietBiId;
        this.thanhPhanThietBiIds = thanhPhanThietBiIds;
        this.ten = ten;
        this.model = model;
        this.cauHinh = cauHinh;
        this.serialNumber = serialNumber;
        this.partNumber = partNumber;
        this.csDinhDanh = csDinhDanh;
        this.soPortConsole = soPortConsole;
        this.soPortInterface = soPortInterface;
        this.hangSxId = hangSxId;
        this.noiSanXuatId = noiSanXuatId;
        this.namSx = namSx;
        this.iconPath = iconPath;
        this.loaiKhachHang = loaiKhachHang;
        this.donViQLId = donViQLId;
        this.nguoiQLId = nguoiQLId;
        this.phamViIDCId = phamViIDCId;
        this.phongId = phongId;
        this.idcId = idcId;
        this.idcRackId = idcRackId;
        this.upUnitId = upUnitId;
        this.downUnitId = downUnitId;
        this.ngayLapDat = ngayLapDat;
        this.canCuLD = canCuLD;
        this.trangThaiSD = trangThaiSD;
        this.trangThaiTsId = trangThaiTsId;
        this.maTs = maTs;
        this.hieuLuc = hieuLuc;
        this.tsNoiBo = tsNoiBo;
        this.moTa = moTa;
        this.nguoiCn = nguoiCn;
        this.ngayCn = ngayCn;
        this.trangThaiVanHanhId = trangThaiVanHanhId;
        this.toaNhaId = toaNhaId;
        this.loaiCongId = loaiCongId;
        this.portId = portId;
        this.hdtbId = hdtbId;
        this.tinhId = tinhId;
    }

    public Long getThietBiKHId() {
        return thietBiKHId;
    }

    public void setThietBiKHId(Long thietBiKHId) {
        this.thietBiKHId = thietBiKHId;
    }

    public Long getHeThongTbiId() {
        return heThongTbiId;
    }

    public void setHeThongTbiId(Long heThongTbiId) {
        this.heThongTbiId = heThongTbiId;
    }

    public Long getMangTbiId() {
        return mangTbiId;
    }

    public void setMangTbiId(Long mangTbiId) {
        this.mangTbiId = mangTbiId;
    }

    public Long getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(Long loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }

    public List<Long> getThanhPhanThietBiIds() {
        return thanhPhanThietBiIds;
    }

    public void setThanhPhanThietBiIds(List<Long> thanhPhanThietBiIds) {
        this.thanhPhanThietBiIds = thanhPhanThietBiIds;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCauHinh() {
        return cauHinh;
    }

    public void setCauHinh(String cauHinh) {
        this.cauHinh = cauHinh;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCsDinhDanh() {
        return csDinhDanh;
    }

    public void setCsDinhDanh(String csDinhDanh) {
        this.csDinhDanh = csDinhDanh;
    }

    public String getSoPortConsole() {
        return soPortConsole;
    }

    public void setSoPortConsole(String soPortConsole) {
        this.soPortConsole = soPortConsole;
    }

    public String getSoPortInterface() {
        return soPortInterface;
    }

    public void setSoPortInterface(String soPortInterface) {
        this.soPortInterface = soPortInterface;
    }

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Long getNoiSanXuatId() {
        return noiSanXuatId;
    }

    public void setNoiSanXuatId(Long noiSanXuatId) {
        this.noiSanXuatId = noiSanXuatId;
    }

    public String getNamSx() {
        return namSx;
    }

    public void setNamSx(String namSx) {
        this.namSx = namSx;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getLoaiKhachHang() {
        return loaiKhachHang;
    }

    public void setLoaiKhachHang(String loaiKhachHang) {
        this.loaiKhachHang = loaiKhachHang;
    }

    public Long getDonViQLId() {
        return donViQLId;
    }

    public void setDonViQLId(Long donViQLId) {
        this.donViQLId = donViQLId;
    }

    public Long getNguoiQLId() {
        return nguoiQLId;
    }

    public void setNguoiQLId(Long nguoiQLId) {
        this.nguoiQLId = nguoiQLId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getIdcRackId() {
        return idcRackId;
    }

    public void setIdcRackId(Long idcRackId) {
        this.idcRackId = idcRackId;
    }

    public Long getUpUnitId() {
        return upUnitId;
    }

    public void setUpUnitId(Long upUnitId) {
        this.upUnitId = upUnitId;
    }

    public Long getDownUnitId() {
        return downUnitId;
    }

    public void setDownUnitId(Long downUnitId) {
        this.downUnitId = downUnitId;
    }

    public Date getNgayLapDat() {
        return ngayLapDat;
    }

    public void setNgayLapDat(Date ngayLapDat) {
        this.ngayLapDat = ngayLapDat;
    }

    public String getCanCuLD() {
        return canCuLD;
    }

    public void setCanCuLD(String canCuLD) {
        this.canCuLD = canCuLD;
    }

    public String getTrangThaiSD() {
        return trangThaiSD;
    }

    public void setTrangThaiSD(String trangThaiSD) {
        this.trangThaiSD = trangThaiSD;
    }

    public String getTrangThaiTsId() {
        return trangThaiTsId;
    }

    public void setTrangThaiTsId(String trangThaiTsId) {
        this.trangThaiTsId = trangThaiTsId;
    }

    public String getMaTs() {
        return maTs;
    }

    public void setMaTs(String maTs) {
        this.maTs = maTs;
    }

    public Long getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Long hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getTsNoiBo() {
        return tsNoiBo;
    }

    public void setTsNoiBo(String tsNoiBo) {
        this.tsNoiBo = tsNoiBo;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(Long nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Date getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(Date ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getTrangThaiVanHanhId() {
        return trangThaiVanHanhId;
    }

    public void setTrangThaiVanHanhId(String trangThaiVanHanhId) {
        this.trangThaiVanHanhId = trangThaiVanHanhId;
    }

    public Long getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(Long toaNhaId) {
        this.toaNhaId = toaNhaId;
    }

    public Long getLoaiCongId() {
        return loaiCongId;
    }

    public void setLoaiCongId(Long loaiCongId) {
        this.loaiCongId = loaiCongId;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getTinhId() {
        return tinhId;
    }

    public void setTinhId(Long tinhId) {
        this.tinhId = tinhId;
    }

    public Long getPhamViIDCId() {
        return phamViIDCId;
    }

    public void setPhamViIDCId(Long phamViIDCId) {
        this.phamViIDCId = phamViIDCId;
    }
}
