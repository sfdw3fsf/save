package vn.vnpt.vnptit.qlsc.dto.DanhMucDoiTac;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.*;

public class CapNhatDoiTacDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer doiTacId;
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private String tenDT;
    private String soDT;
    private String nguoiDD;
    private String taiKhoan;
    private Integer nganHangId;
    private String diaChi;
    private String maDTVisa;
    private String userNameSub;
    private String ghiChu;

    public Integer getDoiTacId() { return this.doiTacId; }
    public String getTenDT() { return this.tenDT; }
    public String getSoDT() { return this.soDT; }
    public String getNguoiDD() { return this.nguoiDD; }
    public String getTaiKhoan() { return this.taiKhoan; }
    public Integer getNganHangId() { return this.nganHangId; }
    public String getDiaChi() { return this.diaChi; }
    public String getMaDTVisa() { return this.maDTVisa; }
    public String getUserNameSub() { return this.userNameSub; }
    public String getGhiChu() { return this.ghiChu; }
}
