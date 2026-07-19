package vn.vnpt.vnptit.qlsc.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class XuLyGiaoViecInput {
    private Long  phieu_id;
    private String  noiDung;
	public Long getPhieu_id() {
		return phieu_id;
	}
	public void setPhieu_id(Long phieu_id) {
		this.phieu_id = phieu_id;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
}
