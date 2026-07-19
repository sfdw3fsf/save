package vn.vnpt.vnptit.qlsc.dto.DanhMucToChucQLDA;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.*;

public class CapNhatChuDauTuDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer chuDtuId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer loaiCdtId;
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private String tenChuDtu;
    private String soDT;
    private String nguoiLH;
    private String chucVu;
    private String email;
    private String mst;
    private String taiKhoan;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nganHangId;
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private String diaChi;
    private String ghiChu;
    private Integer diaChiId;

    public Integer getChuDtuId() { return this.chuDtuId; }
    public Integer getLoaiCdtId() { return this.loaiCdtId; }
    public String getTenChuDtu() { return this.tenChuDtu; }
    public String getSoDT() { return this.soDT; }
    public String getNguoiLH() { return this.nguoiLH; }
    public String getChucVu() { return this.chucVu; }
    public String getEmail() { return this.email; }
    public String getMST() { return this.mst; }
    public String getTaiKhoan() { return this.taiKhoan; }
    public Integer getNganHangId() { return this.nganHangId; }
    public String getDiaChi() { return this.diaChi; }
    public String getGhiChu() { return this.ghiChu; }
    public Integer getDiaChiId() { return this.diaChiId; }
}
