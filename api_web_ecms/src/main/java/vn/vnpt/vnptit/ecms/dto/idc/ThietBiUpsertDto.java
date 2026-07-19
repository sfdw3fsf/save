package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;
import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.common.converter.JsonConvert;

public class ThietBiUpsertDto implements Serializable {
    
    // ID - không bắt buộc cho insert, bắt buộc cho update
    @JsonProperty("id")
    private Long id;
    
    // Đơn vị quản lý ID - Bắt buộc nhập
    @JsonProperty("donViQuanLyId")
    private Long donViQuanLyId;
    
    // Tên thiết bị - Bắt buộc nhập, max 200 ký tự
    @JsonProperty("tenThietBi")
    private String tenThietBi;
    
    // Mạng thiết bị ID
    @JsonProperty("mangThietBiId")
    private Long mangThietBiId;
    
    // Loại thiết bị ID
    @JsonProperty("loaiThietBiId")
    private Long loaiThietBiId;
    
    // Dòng sản phẩm - max 100 ký tự
    @JsonProperty("dongSanPham")
    private String dongSanPham;
    
    // Serial number - max 100 ký tự
    @JsonProperty("serialNumber")
    private String serialNumber;
    
    // Tag number - max 100 ký tự
    @JsonProperty("tagNumber")
    private String tagNumber;
    
    // Năm sản xuất - max 4 ký tự
    @JsonProperty("namSanXuat")
    private Long namSanXuat;
    
    // Hãng sản xuất ID
    @JsonProperty("hangSanXuatId")
    private Long hangSanXuatId;
    
    // Nơi sản xuất ID
    @JsonProperty("noiSanXuatId")
    private Long noiSanXuatId;
    
    // Công suất thiết kế
    @JsonProperty("congSuatThietKe")
    private Long congSuatThietKe;
    
    // Số cổng nguồn đầu vào - max 10 ký tự
    @JsonProperty("soCongNguonDauVao")
    private Long soCongNguonDauVao;
    
    // Số cổng nguồn đầu ra - max 10 ký tự
    @JsonProperty("soCongNguonDauRa")
    private Long soCongNguonDauRa;
    
    // Trạng thái tài sản ID
    @JsonProperty("trangThaiTaiSanId")
    private Long trangThaiTaiSanId;
    
    // Trạng thái vận hành ID
    @JsonProperty("trangThaiVanHanhId")
    private Long trangThaiVanHanhId;
    
    @JsonProperty("thanhPhanThietBiIds")
    private List<Long> thanhPhanThietBiIds;
    
    // Thiết bị cha ID
    @JsonProperty("thietBiChaId")
    private Long thietBiChaId;
    
    // Ghi chú - max 500 ký tự
    @JsonProperty("ghiChu")
    private String ghiChu;
    
    // Người quản lý ID
    @JsonProperty("nguoiQuanLyId")
    private Long nguoiQuanLyId;
    
    // Thời điểm lắp đặt
    @JsonProperty("thoiDiemLapDat")
    private LocalDateTime thoiDiemLapDat;
    
    // Ngày đưa vào sử dụng
    @JsonProperty("ngayDuaVaoSuDung")
    private LocalDateTime ngayDuaVaoSuDung;
    
    // Hệ thống ID
    @JsonProperty("heThongId")
    private Long heThongId;
    
    // Lắp đặt tại IDC ID
    @JsonProperty("lapDatTaiIdcId")
    private Long lapDatTaiIdcId;
    
    // Tòa nhà ID
    @JsonProperty("toaNhaId")
    private Long toaNhaId;
    
    // Mặt sàn ID
    @JsonProperty("matSanId")
    private Long matSanId;
    
    // Phòng/Zone ID
    @JsonProperty("phongZoneId")
    private Long phongZoneId;
    
    // Rack ID
    @JsonProperty("rackId")
    private Long rackId;
    
    // U trên ID
    @JsonProperty("uTrenId")
    private Long uTrenId;
    
    // U dưới ID
    @JsonProperty("uDuoiId")
    private Long uDuoiId;
    
    // Mã thẻ tài sản - max 100 ký tự
    @JsonProperty("maTheTaiSan")
    private String maTheTaiSan;
    
    // Icons - max 500 ký tự
    @JsonProperty("icons")
    private String icons;
    
    // Ports data
    @JsonProperty("portList")
    private List<ThietBiPortDto> portList;

    // Constructors
    public ThietBiUpsertDto() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDonViQuanLyId() {
        return donViQuanLyId;
    }

    public void setDonViQuanLyId(Long donViQuanLyId) {
        this.donViQuanLyId = donViQuanLyId;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public Long getMangThietBiId() {
        return mangThietBiId;
    }

    public void setMangThietBiId(Long mangThietBiId) {
        this.mangThietBiId = mangThietBiId;
    }

    public Long getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(Long loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }

    public String getDongSanPham() {
        return dongSanPham;
    }

    public void setDongSanPham(String dongSanPham) {
        this.dongSanPham = dongSanPham;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Long getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(Long namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public Long getHangSanXuatId() {
        return hangSanXuatId;
    }

    public void setHangSanXuatId(Long hangSanXuatId) {
        this.hangSanXuatId = hangSanXuatId;
    }

    public Long getNoiSanXuatId() {
        return noiSanXuatId;
    }

    public void setNoiSanXuatId(Long noiSanXuatId) {
        this.noiSanXuatId = noiSanXuatId;
    }

    public Long getCongSuatThietKe() {
        return congSuatThietKe;
    }

    public void setCongSuatThietKe(Long congSuatThietKe) {
        this.congSuatThietKe = congSuatThietKe;
    }

    public Long getSoCongNguonDauVao() {
        return soCongNguonDauVao;
    }

    public void setSoCongNguonDauVao(Long soCongNguonDauVao) {
        this.soCongNguonDauVao = soCongNguonDauVao;
    }

    public Long getSoCongNguonDauRa() {
        return soCongNguonDauRa;
    }

    public void setSoCongNguonDauRa(Long soCongNguonDauRa) {
        this.soCongNguonDauRa = soCongNguonDauRa;
    }

    public Long getTrangThaiTaiSanId() {
        return trangThaiTaiSanId;
    }

    public void setTrangThaiTaiSanId(Long trangThaiTaiSanId) {
        this.trangThaiTaiSanId = trangThaiTaiSanId;
    }

    public Long getTrangThaiVanHanhId() {
        return trangThaiVanHanhId;
    }

    public void setTrangThaiVanHanhId(Long trangThaiVanHanhId) {
        this.trangThaiVanHanhId = trangThaiVanHanhId;
    }

    public List<Long> getThanhPhanThietBiIds() {
        return thanhPhanThietBiIds;
    }

    public void setThanhPhanThietBiIds(List<Long> thanhPhanThietBiIds) {
        this.thanhPhanThietBiIds = thanhPhanThietBiIds;
    }

    public Long getThietBiChaId() {
        return thietBiChaId;
    }

    public void setThietBiChaId(Long thietBiChaId) {
        this.thietBiChaId = thietBiChaId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getNguoiQuanLyId() {
        return nguoiQuanLyId;
    }

    public void setNguoiQuanLyId(Long nguoiQuanLyId) {
        this.nguoiQuanLyId = nguoiQuanLyId;
    }

    public Long getHeThongId() {
        return heThongId;
    }

    public void setHeThongId(Long heThongId) {
        this.heThongId = heThongId;
    }

    public Long getLapDatTaiIdcId() {
        return lapDatTaiIdcId;
    }

    public void setLapDatTaiIdcId(Long lapDatTaiIdcId) {
        this.lapDatTaiIdcId = lapDatTaiIdcId;
    }

    public Long getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(Long toaNhaId) {
        this.toaNhaId = toaNhaId;
    }

    public Long getMatSanId() {
        return matSanId;
    }

    public void setMatSanId(Long matSanId) {
        this.matSanId = matSanId;
    }

    public Long getPhongZoneId() {
        return phongZoneId;
    }

    public void setPhongZoneId(Long phongZoneId) {
        this.phongZoneId = phongZoneId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getUTrenId() {
        return uTrenId;
    }

    public void setUTrenId(Long uTrenId) {
        this.uTrenId = uTrenId;
    }

    public Long getUDuoiId() {
        return uDuoiId;
    }

    public void setUDuoiId(Long uDuoiId) {
        this.uDuoiId = uDuoiId;
    }

    public String getMaTheTaiSan() {
        return maTheTaiSan;
    }

    public void setMaTheTaiSan(String maTheTaiSan) {
        this.maTheTaiSan = maTheTaiSan;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public List<ThietBiPortDto> getPortList() {
        return portList;
    }

    public void setPortList(List<ThietBiPortDto> portList) {
        this.portList = portList;
    }

    public LocalDateTime getThoiDiemLapDat() {
        return thoiDiemLapDat;
    }

    public void setThoiDiemLapDat(LocalDateTime thoiDiemLapDat) {
        this.thoiDiemLapDat = thoiDiemLapDat;
    }

    public LocalDateTime getNgayDuaVaoSuDung() {
        return ngayDuaVaoSuDung;
    }

    public void setNgayDuaVaoSuDung(LocalDateTime ngayDuaVaoSuDung) {
        this.ngayDuaVaoSuDung = ngayDuaVaoSuDung;
    }
    
    public String getPortsJson() {
        if (portList == null || portList.isEmpty()) {
            return null;
        }
        try {
            return JsonConvert.serialize(portList);
        } catch (IOException e) {
            throw new RuntimeException("Error converting ports to JSON", e);
        }
    }
}
