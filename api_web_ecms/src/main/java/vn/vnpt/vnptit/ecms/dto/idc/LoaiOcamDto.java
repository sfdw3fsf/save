package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho thông tin loại ổ cắm")
public class LoaiOcamDto {
    
    @Schema(description = "ID loại ổ cắm (cho update/upsert)")
    private Long loaiocamId;
    
    @Schema(description = "Tên loại ổ cắm (bắt buộc, không trùng)")
    private String ten;
    
    @Schema(description = "ID chuẩn đấu nối (1: IEC 60320, 2: IEC 60309, 3: NEMA)")
    private Long chuanId;
    
    @Schema(description = "Sử dụng (1: Sử dụng, 0: Không sử dụng)")
    private Integer sudung;
    
    @Schema(description = "Người cập nhật")
    private String nguoiCn;
    
    // Constructors
    public LoaiOcamDto() {}
    
    public LoaiOcamDto(String ten, Long chuanId, Integer sudung, String nguoiCn) {
        this.ten = ten;
        this.chuanId = chuanId;
        this.sudung = sudung;
        this.nguoiCn = nguoiCn;
    }
    
    public LoaiOcamDto(Long loaiocamId, String ten, Long chuanId, Integer sudung, String nguoiCn) {
        this.loaiocamId = loaiocamId;
        this.ten = ten;
        this.chuanId = chuanId;
        this.sudung = sudung;
        this.nguoiCn = nguoiCn;
    }
    
    // Getters and Setters
    public Long getLoaiocamId() {
        return loaiocamId;
    }
    
    public void setLoaiocamId(Long loaiocamId) {
        this.loaiocamId = loaiocamId;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public Long getChuanId() {
        return chuanId;
    }
    
    public void setChuanId(Long chuanId) {
        this.chuanId = chuanId;
    }
    
    public Integer getSudung() {
        return sudung;
    }
    
    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}

