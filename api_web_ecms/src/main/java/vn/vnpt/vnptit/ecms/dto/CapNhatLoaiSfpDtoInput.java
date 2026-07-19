package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;

import vn.vnpt.message.ModelValidation;

public class CapNhatLoaiSfpDtoInput {
	 @NotNull(message = ModelValidation.NOT_NULL)
	    private Number port_id;
	    @NotNull(message = ModelValidation.NOT_NULL)
	    private Number loai_sfp_id;
	    
		public Number getPort_id() {
			return port_id;
		}
		public void setPort_id(Number port_id) {
			this.port_id = port_id;
		}
		public Number getLoai_sfp_id() {
			return loai_sfp_id;
		}
		public void setLoai_sfp_id(Number loai_sfp_id) {
			this.loai_sfp_id = loai_sfp_id;
		}

}
