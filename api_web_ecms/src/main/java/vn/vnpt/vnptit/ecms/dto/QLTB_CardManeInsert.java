package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_CardManeInsert {
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer dslam_id = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer loaicard_id = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer vitri = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ten_card = null;
	private String serial = null;
	private String partnumber = null;
	
	public Integer getDslam_id() {
		return dslam_id;
	}
	public void setDslam_id(Integer dslam_id) {
		this.dslam_id = dslam_id;
	}
	public Integer getLoaicard_id() {
		return loaicard_id;
	}
	public void setLoaicard_id(Integer loaicard_id) {
		this.loaicard_id = loaicard_id;
	}
	public Integer getVitri() {
		return vitri;
	}
	public void setVitri(Integer vitri) {
		this.vitri = vitri;
	}
	public String getTen_card() {
		return ten_card;
	}
	public void setTen_card(String ten_card) {
		this.ten_card = ten_card;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getPartnumber() {
		return partnumber;
	}
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}
}
