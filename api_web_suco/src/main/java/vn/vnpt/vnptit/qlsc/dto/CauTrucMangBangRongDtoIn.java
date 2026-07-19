package vn.vnpt.vnptit.qlsc.dto;

import io.swagger.models.auth.In;
import vn.vnpt.message.Message;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CauTrucMangBangRongDtoIn {
    //----------------------Giản Đồ--------------------------
    static public class GianDo {
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer tuyen_td_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loai_doituong_id;

        public void setTuyen_td_id(Integer tuyen_td_id) {
            this.tuyen_td_id = tuyen_td_id;
        }

        public Integer getTuyen_td_id() {
            return tuyen_td_id;
        }

        public Integer getLoai_doituong_id() {
            return loai_doituong_id;
        }

        public void setLoai_doituong_id(Integer loai_doituong_id) {
            this.loai_doituong_id = loai_doituong_id;
        }
    }

    //--------------------DoiTuong---------------------------
    static public class DoiTuongUpd extends DoiTuongIns {
        @NotNull(message = ModelValidation.NOT_NULL)
        private Long doituong_id;

        public Long getDoituong_id() {
            return doituong_id;
        }

        public void setDoituong_id(Long doituong_id) {
            this.doituong_id = doituong_id;
        }
    }
    static public class DoiTuongIns {
        private String  ten_ht;
        private Long loaidt_id;
        private Long doituong_map_id;
        private Long tuyentd_id;
        private Long vitri_x;
        private Long vitri_y;
        private Long vitri_td_x;
        private Long vitri_td_y;

        public String getTen_ht() {
            return ten_ht;
        }

        public void setTen_ht(String ten_ht) {
            this.ten_ht = ten_ht;
        }

        public Long getLoaidt_id() {
            return loaidt_id;
        }

        public void setLoaidt_id(Long loaidt_id) {
            this.loaidt_id = loaidt_id;
        }

        public Long getDoituong_map_id() {
            return doituong_map_id;
        }

        public void setDoituong_map_id(Long doituong_map_id) {
            this.doituong_map_id = doituong_map_id;
        }

        public Long getTuyentd_id() {
            return tuyentd_id;
        }

        public void setTuyentd_id(Long tuyentd_id) {
            this.tuyentd_id = tuyentd_id;
        }

        public Long getVitri_x() {
            return vitri_x;
        }

        public void setVitri_x(Long vitri_x) {
            this.vitri_x = vitri_x;
        }

        public Long getVitri_y() {
            return vitri_y;
        }

        public void setVitri_y(Long vitri_y) {
            this.vitri_y = vitri_y;
        }

        public Long getVitri_td_x() {
            return vitri_td_x;
        }

        public void setVitri_td_x(Long vitri_td_x) {
            this.vitri_td_x = vitri_td_x;
        }

        public Long getVitri_td_y() {
            return vitri_td_y;
        }

        public void setVitri_td_y(Long vitri_td_y) {
            this.vitri_td_y = vitri_td_y;
        }
    }

    //------------------LiênKết-------------------------------
    static public class LienketUpd extends LienketIns {
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer lienket_id;

        public Integer getLienket_id() {
            return lienket_id;
        }

        public void setLienket_id(Integer lienket_id) {
            this.lienket_id = lienket_id;
        }
    }

    static public class LienketIns {
        private String ten_lk;
        private String ten_ht;
        private Integer loailk_id;
        private Integer doituong_n_id;
        private Integer doituong_d_id;
        private Integer tuyentd_id;
        private Float diemuon_x1;
        private Float diemuon_y1;
        private Float diemuon_x2;
        private Float diemuon_y2;
        private Integer vitri_td_x;
        private Integer vitri_td_y;

        public void setVitri_td_y(Integer vitri_td_y) {
            this.vitri_td_y = vitri_td_y;
        }

        public void setVitri_td_x(Integer vitri_td_x) {
            this.vitri_td_x = vitri_td_x;
        }

        public void setTuyentd_id(Integer tuyentd_id) {
            this.tuyentd_id = tuyentd_id;
        }

        public void setTen_ht(String ten_ht) {
            this.ten_ht = ten_ht;
        }

        public String getTen_ht() {
            return ten_ht;
        }

        public Integer getVitri_td_y() {
            return vitri_td_y;
        }

        public Integer getVitri_td_x() {
            return vitri_td_x;
        }

        public Integer getTuyentd_id() {
            return tuyentd_id;
        }


        public Integer getDoituong_d_id() {
            return doituong_d_id;
        }

        public Integer getDoituong_n_id() {
            return doituong_n_id;
        }

        public Integer getLoailk_id() {
            return loailk_id;
        }

        public String getTen_lk() {
            return ten_lk;
        }


        public void setDoituong_d_id(Integer doituong_d_id) {
            this.doituong_d_id = doituong_d_id;
        }

        public void setDoituong_n_id(Integer doituong_n_id) {
            this.doituong_n_id = doituong_n_id;
        }

        public void setLoailk_id(Integer loailk_id) {
            this.loailk_id = loailk_id;
        }

        public void setTen_lk(String ten_lk) {
            this.ten_lk = ten_lk;
        }

        public Float getDiemuon_x1() {
            return diemuon_x1;
        }

        public void setDiemuon_x1(Float diemuon_x1) {
            this.diemuon_x1 = diemuon_x1;
        }

        public Float getDiemuon_y1() {
            return diemuon_y1;
        }

        public void setDiemuon_y1(Float diemuon_y1) {
            this.diemuon_y1 = diemuon_y1;
        }

        public Float getDiemuon_x2() {
            return diemuon_x2;
        }

        public void setDiemuon_x2(Float diemuon_x2) {
            this.diemuon_x2 = diemuon_x2;
        }

        public Float getDiemuon_y2() {
            return diemuon_y2;
        }

        public void setDiemuon_y2(Float diemuon_y2) {
            this.diemuon_y2 = diemuon_y2;
        }
    }

    //-----------------Vòng-----------------------------------
    static public class VongUpd extends VongIns {
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer ring_id;

        public Integer getRing_id() {
            return ring_id;
        }

        public void setRing_id(Integer ring_id) {
            this.ring_id = ring_id;
        }
    }

    static public class VongIns {
        private String ten_ring;
        private Integer tuyentd_id;
        private String ds_lienket;
        private Integer vitri_x;
        private Integer vitri_y;
        private String maunen;

        public Integer getTuyentd_id() {
            return tuyentd_id;
        }

        public void setTuyentd_id(Integer tuyentd_id) {
            this.tuyentd_id = tuyentd_id;
        }

        public void setVitri_y(Integer vitri_y) {
            this.vitri_y = vitri_y;
        }

        public void setVitri_x(Integer vitri_x) {
            this.vitri_x = vitri_x;
        }

        public Integer getVitri_y() {
            return vitri_y;
        }

        public Integer getVitri_x() {
            return vitri_x;
        }

        public String getDs_lienket() {
            return ds_lienket;
        }

        public String getMaunen() {
            return maunen;
        }

        public String getTen_ring() {
            return ten_ring;
        }

        public void setDs_lienket(String ds_lienket) {
            this.ds_lienket = ds_lienket;
        }

        public void setMaunen(String maunen) {
            this.maunen = maunen;
        }

        public void setTen_ring(String ten_ring) {
            this.ten_ring = ten_ring;
        }
    }
   //----------------------------------------------------------
}
