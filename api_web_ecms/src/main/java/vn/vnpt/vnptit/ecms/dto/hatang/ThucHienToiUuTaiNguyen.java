package vn.vnpt.vnptit.ecms.dto.hatang;

import java.util.List;

public class ThucHienToiUuTaiNguyen {
    private String thang_nam ;
    private String loai_toiuu ;
    private String trangthai_xacnhan;
    private  String phieu_toiuu;
    private List<MayAoThuHoiDTO> danhsach_mayao;
	public String getThang_nam() {
		return thang_nam;
	}
	public void setThang_nam(String thang_nam) {
		this.thang_nam = thang_nam;
	}
	public String getLoai_toiuu() {
		return loai_toiuu;
	}
	public void setLoai_toiuu(String loai_toiuu) {
		this.loai_toiuu = loai_toiuu;
	}
	public String getTrangthai_xacnhan() {
		return trangthai_xacnhan;
	}
	public void setTrangthai_xacnhan(String trangthai_xacnhan) {
		this.trangthai_xacnhan = trangthai_xacnhan;
	}
	public String getPhieu_toiuu() {
		return phieu_toiuu;
	}
	public void setPhieu_toiuu(String phieu_toiuu) {
		this.phieu_toiuu = phieu_toiuu;
	}
	public List<MayAoThuHoiDTO> getDanhsach_mayao() {
		return danhsach_mayao;
	}
	public void setDanhsach_mayao(List<MayAoThuHoiDTO> danhsach_mayao) {
		this.danhsach_mayao = danhsach_mayao;
	}
    
    


}
