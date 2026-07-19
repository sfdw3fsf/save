package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho thông tin chuẩn đấu nối")
public class ChuanDauNoiDto {
    
    @Schema(description = "ID chuẩn đấu nối (cho update/upsert)")
    private Long id;
    
    @Schema(description = "Tên chuẩn đấu nối (bắt buộc, không trùng)")
    private String chuanDauNoi;
    
    @Schema(description = "ID chuẩn (1: IEC 60320, 2: IEC 60309, 3: NEMA)")
    private Long chuanId;
    
    @Schema(description = "Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)")
    private Integer hieuLuc;
    
    @Schema(description = "Người cập nhật")
    private String nguoiCn;
    
    // Constructors
    public ChuanDauNoiDto() {}
    
    public ChuanDauNoiDto(String chuanDauNoi, Long chuanId, Integer hieuLuc, String nguoiCn) {
        this.chuanDauNoi = chuanDauNoi;
        this.chuanId = chuanId;
        this.hieuLuc = hieuLuc;
        this.nguoiCn = nguoiCn;
    }
    
    public ChuanDauNoiDto(Long id, String chuanDauNoi, Long chuanId, Integer hieuLuc, String nguoiCn) {
        this.id = id;
        this.chuanDauNoi = chuanDauNoi;
        this.chuanId = chuanId;
        this.hieuLuc = hieuLuc;
        this.nguoiCn = nguoiCn;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getChuanDauNoi() {
        return chuanDauNoi;
    }
    
    public void setChuanDauNoi(String chuanDauNoi) {
        this.chuanDauNoi = chuanDauNoi;
    }
    
    public Long getChuanId() {
        return chuanId;
    }
    
    public void setChuanId(Long chuanId) {
        this.chuanId = chuanId;
    }
    
    public Integer getHieuLuc() {
        return hieuLuc;
    }
    
    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}

