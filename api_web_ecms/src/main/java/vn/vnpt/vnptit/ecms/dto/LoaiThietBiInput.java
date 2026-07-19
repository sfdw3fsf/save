package vn.vnpt.vnptit.ecms.dto;

import org.hibernate.validator.constraints.Length;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoaiThietBiInput {
    static public class LoaiTbiIns{
        @NotBlank(message = "Trường loaiTbi không được để trống!")
        private  String loaiTbi;
        private  String ghiChu;

        public void setLoaiTbi(String loaiTbi) {
            this.loaiTbi = loaiTbi;
        }
        public String getLoaiTbi() {
            return loaiTbi;
        }

        public void setGhiChu(String ghiChu) {
            this.ghiChu = ghiChu;
        }

        public String getGhiChu() {
            return ghiChu;
        }
    }
    static public class LoaiTbiUpd extends LoaiTbiIns {
	@NotNull(message = ModelValidation.NOT_NULL)
    private  Integer loaiTbiId;

    public void setLoaiTbiId(Integer loaiTbiId) {
        this.loaiTbiId = loaiTbiId;
    }
    public Integer getLoaiTbiId() {
        return loaiTbiId;
    }
    }
}
