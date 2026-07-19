package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ThongTinPortDto {

    @Schema(description = "thiết bị port ID")
    private BigDecimal portId;

    @Schema(description = "thiết bị ID")
    private BigDecimal thietBiId;

    @Schema(description = "Tên thiết bị port")
    private String ten;

    @Schema(description = "Điện áp định danh (kV)")
    private Integer dienApDinhDanh;

    @Schema(description = "Dòng điện định danh (A)")
    private Integer ddDinhDanh;

    @Schema(description = "Loại nguồn điện")
    private Integer loaiNguonDien;

    @Schema(description = "Trạng thái port (ví dụ: Đang hoạt động  \n" +
            "1: hoạt động\n" +
            "0: không kết nối\n" +
            "2: Hỏng)")
    private Long trangThaiSD;

    @Schema(description = "Trạng thái kết nối (ví dụ: Đã kết nối 1: Kết nối\n" +
            "0: không kết nối)")
    private Long trangThaiKetNoi;

    @Schema(description = "loại cổng:  1: Đầu vào, 2: Đầu ra")
    private Long loaiCongId;

    @Schema(description = "người cập nhật")
    private String nguoiCn;

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public BigDecimal getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(BigDecimal thietBiId) {
        this.thietBiId = thietBiId;
    }

    public Long getTrangThaiKetNoi() {
        return trangThaiKetNoi;
    }

    public void setTrangThaiKetNoi(Long trangThaiKetNoi) {
        this.trangThaiKetNoi = trangThaiKetNoi;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getLoaiCongId() {
        return loaiCongId;
    }

    public void setLoaiCongId(Long loaiCongId) {
        this.loaiCongId = loaiCongId;
    }

    public Long getTrangThaiSD() {
        return trangThaiSD;
    }

    public void setTrangThaiSD(Long trangThaiSD) {
        this.trangThaiSD = trangThaiSD;
    }

    public Integer getLoaiNguonDien() {
        return loaiNguonDien;
    }

    public void setLoaiNguonDien(Integer loaiNguonDien) {
        this.loaiNguonDien = loaiNguonDien;
    }

    public Integer getDdDinhDanh() {
        return ddDinhDanh;
    }

    public void setDdDinhDanh(Integer ddDinhDanh) {
        this.ddDinhDanh = ddDinhDanh;
    }

    public Integer getDienApDinhDanh() {
        return dienApDinhDanh;
    }

    public void setDienApDinhDanh(Integer dienApDinhDanh) {
        this.dienApDinhDanh = dienApDinhDanh;
    }

    public BigDecimal getPortId() {
        return portId;
    }

    public void setPortId(BigDecimal portId) {
        this.portId = portId;
    }
}
