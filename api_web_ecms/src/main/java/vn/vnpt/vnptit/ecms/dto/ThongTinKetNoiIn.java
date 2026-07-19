package vn.vnpt.vnptit.ecms.dto;

import org.hibernate.validator.constraints.Range;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class ThongTinKetNoiIn {
    static public class LoadThongTin{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer madslam_port ;

        public Integer getMadslam_port() {
            return madslam_port;
        }

        public void setMadslam_port(Integer madslam_port) {
            this.madslam_port = madslam_port;
        }
    }
    static public class MaKetNoi extends  LoadThongTin{
        @NotNull(message = ModelValidation.NOT_NULL)
        private long port_dich;

        public long getPort_dich() {
            return port_dich;
        }

        public void setPort_dich(long port_dich) {
            this.port_dich = port_dich;
        }
    }
    static public class GoKetNoi extends  MaKetNoi{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaithietbi_id_n;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer loaithietbi_id_d;

        public Integer getLoaithietbi_id_n() {
            return loaithietbi_id_n;
        }

        public void setLoaithietbi_id_n(Integer loaithietbi_id_n) {
            this.loaithietbi_id_n = loaithietbi_id_n;
        }

        public Integer getLoaithietbi_id_d() {
            return loaithietbi_id_d;
        }

        public void setLoaithietbi_id_d(Integer loaithietbi_id_d) {
            this.loaithietbi_id_d = loaithietbi_id_d;
        }
    }

    static public class KetNoi extends  GoKetNoi{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer thietbi_id_n;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer thietbi_id_d;
        @Range(min = 0, max = 2,message = "Chỉ nhập 0,1 hoặc 2")
        private int link_code;
        @Range(min = 0, max = 1,message = "0: Check; 1: Bỏ check")
        private int chk_port;

        @Range(min = 1, max = 2, message = "1: Hướng chính; 2: Hướng phụ")
        private int huong_ket_noi;

        public Integer getThietbi_id_n() {
            return thietbi_id_n;
        }

        public void setThietbi_id_n(Integer thietbi_id_n) {
            this.thietbi_id_n = thietbi_id_n;
        }

        public Integer getThietbi_id_d() {
            return thietbi_id_d;
        }

        public void setThietbi_id_d(Integer thietbi_id_d) {
            this.thietbi_id_d = thietbi_id_d;
        }

        public int getLink_code() {
            return link_code;
        }

        public void setLink_code(int link_code) {
            this.link_code = link_code;
        }

        public int getChk_port() {
            return chk_port;
        }

        public void setChk_port(int chk_port) {
            this.chk_port = chk_port;
        }

        public int getHuong_ket_noi() {
            return huong_ket_noi;
        }

        public void setHuong_ket_noi(int huong_ket_noi) {
            this.huong_ket_noi = huong_ket_noi;
        }
    }

    static public class InputItem{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer type;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer id;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
    static public class LayTuRack{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer tbi_d_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer tbi_n_id;

        public Integer getTbi_d_id() {
            return tbi_d_id;
        }

        public void setTbi_d_id(Integer tbi_d_id) {
            this.tbi_d_id = tbi_d_id;
        }

        public Integer getTbi_n_id() {
            return tbi_n_id;
        }

        public void setTbi_n_id(Integer tbi_n_id) {
            this.tbi_n_id = tbi_n_id;
        }
    }
}
