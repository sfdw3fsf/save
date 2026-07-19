package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "DTO cho thông tin phòng/zone")
public class PhongZoneDto {
    
    @Schema(description = "ID phòng/zone (cho update/upsert)")
    private Long id;
    
    @Schema(description = "ID mặt sàn")
    private Long matsanId;
    
    @Schema(description = "Tên phòng/zone")
    private String ten;
    
    @Schema(description = "Ghi chú")
    private String ghiChu;
    
    @Schema(description = "Trạng thái sử dụng (1: sử dụng, 0: không sử dụng)")
    private Integer suDung;
    
    @Schema(description = "Diện tích")
    private Double dientich;
    
    @Schema(description = "Ảnh đại diện")
    private String anhDaidien;
    
    @Schema(description = "Trạng thái vận hành")
    private Integer trangthaiVh;
    
    @Schema(description = "Ngày sử dụng")
    private Date ngaySd;
    
    @Schema(description = "ID mục đích sử dụng")
    private Long mucdichsdId;
    
    @Schema(description = "Số lượng rack thiết kế")
    private Integer slRackThietke;
    
    @Schema(description = "Tọa độ X")
    private Double toadoX;
    
    @Schema(description = "Tọa độ Y")
    private Double toadoY;
    
    @Schema(description = "Hướng trục hoành: 1: Hướng bên phải; 0: Hướng bên trái")
    private Integer huongX;
    
    @Schema(description = "Hướng trục tung: 1: Hướng lên trên; 0: Hướng xuống dưới")
    private Integer huongY;
    
    @Schema(description = "Người cập nhật")
    private String nguoiCn;
    
    @Schema(description = "Mã viết tắt")
    private String tenTat;
    
    // Constructors
    public PhongZoneDto() {}
    
    public PhongZoneDto(Long matsanId, String ten, String ghiChu, Integer suDung, String nguoiCn) {
        this.matsanId = matsanId;
        this.ten = ten;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
        this.nguoiCn = nguoiCn;
    }
    
    public PhongZoneDto(Long id, Long matsanId, String ten, String ghiChu, Integer suDung, String nguoiCn) {
        this.id = id;
        this.matsanId = matsanId;
        this.ten = ten;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
        this.nguoiCn = nguoiCn;
    }
    
    // Getters and Setters
    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMatsanId() {
        return matsanId;
    }
    
    public void setMatsanId(Long matsanId) {
        this.matsanId = matsanId;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public Integer getSuDung() {
        return suDung;
    }
    
    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
    
    public Double getDientich() {
        return dientich;
    }
    
    public void setDientich(Double dientich) {
        this.dientich = dientich;
    }
    
    public String getAnhDaidien() {
        return anhDaidien;
    }
    
    public void setAnhDaidien(String anhDaidien) {
        this.anhDaidien = anhDaidien;
    }
    
    public Integer getTrangthaiVh() {
        return trangthaiVh;
    }
    
    public void setTrangthaiVh(Integer trangthaiVh) {
        this.trangthaiVh = trangthaiVh;
    }
    
    public Date getNgaySd() {
        return ngaySd;
    }
    
    public void setNgaySd(Date ngaySd) {
        this.ngaySd = ngaySd;
    }
    
    public Long getMucdichsdId() {
        return mucdichsdId;
    }
    
    public void setMucdichsdId(Long mucdichsdId) {
        this.mucdichsdId = mucdichsdId;
    }
    
    public Integer getSlRackThietke() {
        return slRackThietke;
    }
    
    public void setSlRackThietke(Integer slRackThietke) {
        this.slRackThietke = slRackThietke;
    }
    
    public Double getToadoX() {
        return toadoX;
    }
    
    public void setToadoX(Double toadoX) {
        this.toadoX = toadoX;
    }
    
    public Double getToadoY() {
        return toadoY;
    }
    
    public void setToadoY(Double toadoY) {
        this.toadoY = toadoY;
    }
    
    public Integer getHuongX() {
        return huongX;
    }
    
    public void setHuongX(Integer huongX) {
        this.huongX = huongX;
    }
    
    public Integer getHuongY() {
        return huongY;
    }
    
    public void setHuongY(Integer huongY) {
        this.huongY = huongY;
    }
}
