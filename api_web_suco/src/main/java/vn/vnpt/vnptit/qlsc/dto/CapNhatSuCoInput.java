package vn.vnpt.vnptit.qlsc.dto;

public class CapNhatSuCoInput extends CapNhatThongTinSuCoInput {
    private String tinhTrang = null;
    private Long phieuId;
    private String anhChup;
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public Long getPhieuId() {
		return phieuId;
	}

	public void setPhieuId(Long phieuId) {
		this.phieuId = phieuId;
	}

	public String getAnhChup() {
		return anhChup;
	}

	public void setAnhChup(String anhChup) {
		this.anhChup = anhChup;
	}
}
