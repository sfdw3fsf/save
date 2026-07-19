package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.*;

public class QuyHoachScvLanDtoIn {
    private int phanvung_id;

    public void setPhanvung_id(int phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public int getPhanvung_id() {
        return phanvung_id;
    }

    static public class QuyHoachScvLanTraCuu {
        @NotNull(message = ModelValidation.NOT_NULL)
        private Long svlan_tu;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Long svlan_den;
        @NotNull(message = ModelValidation.NOT_NULL)
        private int kieu;

       // private int vung_id=0;
      //  private int loai_kn=0;
       // private int donvi_id=0;

//       public int getLoai_kn() {
//           return loai_kn; }
//
//        public int getDonvi_id() {
//           return donvi_id;
//       }
//
//        public void setDonvi_id(int donvi_id) {
//           this.donvi_id = donvi_id;
//       }
//
//
//        public int getVung_id() {
//           return vung_id;
//       }
//
//        public void setLoai_kn(int loai_kn) {
//           this.loai_kn = loai_kn;
//       }
//
//
//        public void setVung_id(int vung_id) {
//           this.vung_id = vung_id;
//       }



        public int getKieu() {
            return kieu;
        }

        public void setKieu(int kieu) {
            this.kieu = kieu;
        }

        public Long getSvlan_tu() {
            return svlan_tu;
        }

        public void setSvlan_tu(Long svlan_tu) {
            this.svlan_tu = svlan_tu;
        }

        public Long getSvlan_den() {
            return svlan_den;
        }

        public void setSvlan_den(Long svlan_den) {
            this.svlan_den = svlan_den;
        }
    }

    static public class danhMucThietBiDto{
        @NotNull(message = ModelValidation.NOT_NULL)
        private int kieu;
        @NotNull(message = ModelValidation.NOT_NULL)
        private int donvi_id;

        public int getKieu() {
            return kieu;
        }

        public void setKieu(int kieu) {
            this.kieu = kieu;
        }

        public int getDonvi_id() {
            return donvi_id;
        }

        public void setDonvi_id(int donvi_id) {
            this.donvi_id = donvi_id;
        }
    }
    static public class danhMucHuongKNDto{
        @NotNull(message = ModelValidation.NOT_NULL)
        private int kieu;

        public int getVung_id() {
            return vung_id;
        }

        public void setVung_id(int vung_id) {
            this.vung_id = vung_id;
        }

        @NotNull(message = ModelValidation.NOT_NULL)
        private int vung_id;

        public int getKieu() {
            return kieu;
        }

        public void setKieu(int kieu) {
            this.kieu = kieu;
        }


    }


   static public class QuyHoachScvLanUpd extends  QuyHoachScvLanIns  {
       @NotNull(message = ModelValidation.NOT_NULL)
        private Integer quyhoach_id=0;

        public Integer getQuyhoach_id() {
            return quyhoach_id;
        }

        public void setQuyhoach_id(Integer quyhoach_id) {
            this.quyhoach_id = quyhoach_id;
        }
    }
    static public class QuyHoachScvLanIns {
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer donvi_id;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Long dslam_id;
        @NotNull(message = ModelValidation.NOT_NULL)
//        @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
        private Long svlan;
        @NotNull(message = ModelValidation.NOT_NULL)
//        @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
        private Long cvlan_tu;
//        @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
        @NotNull(message = ModelValidation.NOT_NULL)
        private Long cvlan_den;
        @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer huongkn_id=0;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer method;

        public void setDonvi_id(Integer donvi_id) {
            this.donvi_id = donvi_id;
        }

        public Long getCvlan_den() {
            return cvlan_den;
        }

        public Long getCvlan_tu() {
            return cvlan_tu;
        }

        public Integer getDonvi_id() {
            return donvi_id;
        }

        public Long getDslam_id() {
            return dslam_id;
        }

        public Integer getMethod() {
            return method;
        }

        public Integer getHuongkn_id() {
            return huongkn_id;
        }

        public Long getSvlan() {
            return svlan;
        }

        public void setCvlan_den(Long cvlan_den) {
            this.cvlan_den = cvlan_den;
        }

        public void setCvlan_tu(Long cvlan_tu) {
            this.cvlan_tu = cvlan_tu;
        }

        public void setDslam_id(Long dslam_id) {
            this.dslam_id = dslam_id;
        }

        public void setHuongkn_id(Integer huongkn_id) {
            this.huongkn_id = huongkn_id;
        }

        public void setMethod(Integer method) {
            this.method = method;
        }

        public void setSvlan(Long svlan) {
            this.svlan = svlan;
        }
    }

}
