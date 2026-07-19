package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class DanDoCapTDDtoIn {

    public static class ThietBiChaIn{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaithietbi_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer donvi_id;

        public Integer getDonvi_id() {
            return donvi_id;
        }

        public Integer getLoaithietbi_id() {
            return loaithietbi_id;
        }

        public void setDonvi_id(Integer donvi_id) {
            this.donvi_id = donvi_id;
        }

        public void setLoaithietbi_id(Integer loaithietbi_id) {
            this.loaithietbi_id = loaithietbi_id;
        }
}

    public static class ThietBiConIn{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaithietbi_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer thietbi_cha_id;

        public Integer getLoaithietbi_id() {
            return loaithietbi_id;
        }

        public void setLoaithietbi_id(Integer loaithietbi_id) {
            this.loaithietbi_id = loaithietbi_id;
        }

        public Integer getThietbi_cha_id() {
            return thietbi_cha_id;
        }

        public void setThietbi_cha_id(Integer thietbi_cha_id) {
            this.thietbi_cha_id = thietbi_cha_id;
        }
    }

    public static class CongThietBiIn{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaithietbi_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer thietbi_id;
        public Integer getThietbi_id() {
            return thietbi_id;
        }

        public Integer getLoaithietbi_id() {
            return loaithietbi_id;
        }

        public void setThietbi_id(Integer donvi_id) {
            this.thietbi_id = donvi_id;
        }

        public void setLoaithietbi_id(Integer loaithietbi_id) {
            this.loaithietbi_id = loaithietbi_id;
        }
    }

    public static class TraCuuDanDo{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaitbi_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer thietbi_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer vitri;

        public Integer getLoaitbi_id() {
            return loaitbi_id;
        }

        public Integer getThietbi_id() {
            return thietbi_id;
        }

        public Integer getVitri() {
            return vitri;
        }

        public void setLoaitbi_id(Integer loaitbi_id) {
            this.loaitbi_id = loaitbi_id;
        }

        public void setThietbi_id(Integer thietbi_id) {
            this.thietbi_id = thietbi_id;
        }

        public void setVitri(Integer vitri) {
            this.vitri = vitri;
        }
    }
}