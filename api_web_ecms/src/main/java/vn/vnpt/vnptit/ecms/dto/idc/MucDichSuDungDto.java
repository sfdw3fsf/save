package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho thông tin mục đích sử dụng")
public class MucDichSuDungDto {
    
    @Schema(description = "ID mục đích sử dụng (cho update/upsert)")
    private Long id;
    
    @Schema(description = "Tên mục đích sử dụng")
    private String ten;
    
    @Schema(description = "Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)")
    private Integer hieuLuc;
    
    @Schema(description = "ID loại mục đích sử dụng (NHOMMD_ID)")
    private Long nhomMucDichId;
    
    @Schema(description = "Ghi chú")
    private String ghiChu;
    
    @Schema(description = "Người cập nhật")
    private String nguoiCn;
    
    // Constructors
    public MucDichSuDungDto() {}
    
    public MucDichSuDungDto(String ten, Integer hieuLuc, Long nhomMucDichId, String ghiChu, String nguoiCn) {
        this.ten = ten;
        this.hieuLuc = hieuLuc;
        this.nhomMucDichId = nhomMucDichId;
        this.ghiChu = ghiChu;
        this.nguoiCn = nguoiCn;
    }
    
    public MucDichSuDungDto(Long id, String ten, Integer hieuLuc, Long nhomMucDichId, String ghiChu, String nguoiCn) {
        this.id = id;
        this.ten = ten;
        this.hieuLuc = hieuLuc;
        this.nhomMucDichId = nhomMucDichId;
        this.ghiChu = ghiChu;
        this.nguoiCn = nguoiCn;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public Integer getHieuLuc() {
        return hieuLuc;
    }
    
    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }
    
    public Long getNhomMucDichId() {
        return nhomMucDichId;
    }
    
    public void setNhomMucDichId(Long nhomMucDichId) {
        this.nhomMucDichId = nhomMucDichId;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}

