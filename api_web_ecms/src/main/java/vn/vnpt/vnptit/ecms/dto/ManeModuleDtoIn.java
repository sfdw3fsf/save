package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ManeModuleDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer cardmane_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer vitri;
    private String serial ;
    private String partnumber;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer loaimodule_id;


    public Integer getCardmane_id() {
        return cardmane_id;
    }

    public void setCardmane_id(Integer cardmane_id) {
        this.cardmane_id = cardmane_id;
    }

    public Integer getVitri() {
        return vitri;
    }

    public void setVitri(Integer vitri) {
        this.vitri = vitri;
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

    public Integer getLoaimodule_id() {
        return loaimodule_id;
    }

    public void setLoaimodule_id(Integer loaimodule_id) {
        this.loaimodule_id = loaimodule_id;
    }



    static public class ManeModuleDtoInUpd extends  ManeModuleDtoIn{
        @NotNull(message = ModelValidation.NOT_NULL)
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        private Integer modulemane_id;

        public Integer getModulemane_id() {
            return modulemane_id;
        }

        public void setModulemane_id(Integer modulemane_id) {
            this.modulemane_id = modulemane_id;
        }
    }
}
