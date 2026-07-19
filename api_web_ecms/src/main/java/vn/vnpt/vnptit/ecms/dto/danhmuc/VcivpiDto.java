package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class VcivpiDto {
    static public class TramThiCong{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaidv_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer donvi_cha_id;

        public Integer getLoaidv_id() {
            return loaidv_id;
        }

        public void setLoaidv_id(Integer loaidv_id) {
            this.loaidv_id = loaidv_id;
        }

        public Integer getDonvi_cha_id() {
            return donvi_cha_id;
        }

        public void setDonvi_cha_id(Integer donvi_cha_id) {
            this.donvi_cha_id = donvi_cha_id;
        }
    }
    static public class VciVpiUpd extends VciVpiIns{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer vcivpi;

        public Integer getVcivpi() {
            return vcivpi;
        }

        public void setVcivpi(Integer vcivpi) {
            this.vcivpi = vcivpi;
        }
    }
    static public class VciVpiIns{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer vci;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer vpi;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer dslamid;
//        private String maycn;
//        private String nguoicn;
//        private Date ngaycn;

        public Integer getVci() {
            return vci;
        }

        public void setVci(Integer vci) {
            this.vci = vci;
        }

        public Integer getVpi() {
            return vpi;
        }

        public void setVpi(Integer vpi) {
            this.vpi = vpi;
        }

        public Integer getDslamid() {
            return dslamid;
        }

        public void setDslamid(Integer dslamid) {
            this.dslamid = dslamid;
        }

//        public String getMaycn() {
//            return maycn;
//        }
//
//        public void setMaycn(String maycn) {
//            this.maycn = maycn;
//        }
//
//        public String getNguoicn() {
//            return nguoicn;
//        }
//
//        public void setNguoicn(String nguoicn) {
//            this.nguoicn = nguoicn;
//        }
//
//        public Date getNgaycn() {
//            return ngaycn;
//        }
//
//        public void setNgaycn(Date ngaycn) {
//            this.ngaycn = ngaycn;
//        }
    }
}
