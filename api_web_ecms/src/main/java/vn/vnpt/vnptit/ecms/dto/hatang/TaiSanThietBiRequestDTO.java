package vn.vnpt.vnptit.ecms.dto.hatang;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TaiSanThietBiRequestDTO {
        private Long thietBiId;
        private String maDa;
        private String tenDa;
        private String soTheTs;
        private String taiSanId;
        private String maVtu;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private Date ngayKh;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private Date ngayBaoHanh;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private Date ngayLapDat;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private Date ngaySuDung;
        private Long donViQlyId;
        private Long phongBanQlyId;
        public Long getThietBiId() {
                return thietBiId;
        }
        public String getMaDa() {
                return maDa;
        }
        public String getTenDa() {
                return tenDa;
        }
        public String getSoTheTs() {
                return soTheTs;
        }
        public String getTaiSanId() {
                return taiSanId;
        }
        public String getMaVtu() {
                return maVtu;
        }
        public Date getNgayKh() {
                return ngayKh;
        }
        public Date getNgayBaoHanh() {
                return ngayBaoHanh;
        }
        public Date getNgayLapDat() {
                return ngayLapDat;
        }
        public Date getNgaySuDung() {
                return ngaySuDung;
        }
        public Long getDonViQlyId() {
                return donViQlyId;
        }
        public Long getPhongBanQlyId() {
                return phongBanQlyId;
        }
}
