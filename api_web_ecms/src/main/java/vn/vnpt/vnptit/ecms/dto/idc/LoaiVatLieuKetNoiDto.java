package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho thông tin loại vật liệu kết nối")
public class LoaiVatLieuKetNoiDto {
    
    @Schema(description = "ID loại vật liệu kết nối (cho update/upsert)")
    private Long id;
    
    @Schema(description = "ID loại vật liệu")
    private Long loaivlId;
    
    @Schema(description = "Tên loại vật liệu kết nối")
    private String ten;
    
    @Schema(description = "Thông số kỹ thuật")
    private String thongSo;
    
    @Schema(description = "Ghi chú")
    private String ghiChu;

    @Schema(description = "Đường dẫn icon")
    private String iconPath;
    
    @Schema(description = "Trạng thái sử dụng (1: sử dụng, 0: không sử dụng)")
    private Integer suDung;
    
    @Schema(description = "Người cập nhật")
    private String nguoiCn;
    
    // Constructors
    public LoaiVatLieuKetNoiDto() {}
    
    public LoaiVatLieuKetNoiDto(Long loaivlId, String ten, String thongSo, String ghiChu, String iconPath, Integer suDung, String nguoiCn) {
        this.loaivlId = loaivlId;
        this.ten = ten;
        this.thongSo = thongSo;
        this.ghiChu = ghiChu;
        this.iconPath = iconPath;
        this.suDung = suDung;
        this.nguoiCn = nguoiCn;
    }
    
    public LoaiVatLieuKetNoiDto(Long id, Long loaivlId, String ten, String thongSo, String ghiChu, String iconPath, Integer suDung, String nguoiCn) {
        this.id = id;
        this.loaivlId = loaivlId;
        this.ten = ten;
        this.thongSo = thongSo;
        this.ghiChu = ghiChu;
        this.iconPath = iconPath;
        this.suDung = suDung;
        this.nguoiCn = nguoiCn;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getLoaivlId() {
        return loaivlId;
    }
    
    public void setLoaivlId(Long loaivlId) {
        this.loaivlId = loaivlId;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getThongSo() {
        return thongSo;
    }
    
    public void setThongSo(String thongSo) {
        this.thongSo = thongSo;
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
    
    public String getIconPath() {
        return iconPath;
    }
    
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}
