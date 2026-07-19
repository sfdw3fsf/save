<template src="./index.html"></template>

<script>
import DateTimeLib from "@/utils/DateTimeLib";
import ChiTietCongViec from "../../tiepnhan-thicong/popups/ChiTiet.vue";
import api from './api'
export default {
  name: 'ThongTinCongViec',
  components: {
    ChiTietCongViec,
  },
  props: {
    phieuThiCongId: {
      type: Number,
      required: true,
    },
  },
  data: function () {
    return {
      phieuThiCongDetail: {
        MA_CV: null,
        TEN_DV: null,
        TEN_NV: null,
        LOAI_CV: null,
        DANHSACH_NV_PHOIHOP: null,
        TEN_CV: null,
        NGAY_YC: null,
        CANCU_YC: null,
        TRANG_THAI: null,
        MA_CV_CHA: null,
        NGAY_HT: null,
        SOLUONG_IP: null,
        DIACHI_IP: null,
        TRANGTHAI_ID: null,
      },
    };
  },
  computed: {
    // Điều kiện hiển thị phần thông tin IP
    shouldShowIPInfo() {
      if (!this.phieuThiCongDetail.LOAI_CV) {
        return false;
      }

      const loaiCV = this.phieuThiCongDetail.LOAI_CV.toLowerCase();

      return (
        loaiCV.includes('cấp phát') && loaiCV.includes('ip') ||
        loaiCV.includes('thu hồi') && loaiCV.includes('ip') ||
        loaiCV.includes('dải ip') ||
        this.phieuThiCongDetail.LOAICV_ID === 39 || // Cấp phát dải IP
        this.phieuThiCongDetail.LOAICV_ID === 40 || // Cấp phát IP  
        this.phieuThiCongDetail.LOAICV_ID === 45 || // Thu hồi dải IP
        this.phieuThiCongDetail.LOAICV_ID === 46    // Thu hồi IP
      );
    },

    // Xác định loại công việc IP
    ipJobType() {
      if (!this.phieuThiCongDetail.LOAI_CV) {
        return null;
      }

      const loaiCV = this.phieuThiCongDetail.LOAI_CV.toLowerCase();
      const loaiCVID = this.phieuThiCongDetail.LOAICV_ID;

      // Kiểm tra theo ID trước
      if (loaiCVID === 39 || loaiCVID === 40 || loaiCV.includes('cấp phát')) {
        return 'cap_phat';
      } else if (loaiCVID === 45 || loaiCVID === 46 || loaiCV.includes('thu hồi')) {
        return 'thu_hoi';
      }

      return null;
    },

    // Text cho label số lượng IP
    soLuongIPLabel() {
      const jobType = this.ipJobType;
      switch (jobType) {
        case 'cap_phat':
          return 'Số lượng IP cần cấp phát';
        case 'thu_hoi':
          return 'Số lượng IP cần thu hồi';
        default:
          return 'Số lượng IP';
      }
    },

    // Text cho label địa chỉ IP
    diaChiIPLabel() {
      const jobType = this.ipJobType;
      switch (jobType) {
        case 'cap_phat':
          return 'Địa chỉ IP cần cấp phát';
        case 'thu_hoi':
          return 'Địa chỉ IP cần thu hồi';
        default:
          return 'Địa chỉ IP';
      }
    },

    // Formatted date for NGAY_YC (Thời điểm yêu cầu)
    formattedNgayYC() {
      return DateTimeLib.toDateTimeDisplayWithFormat(this.phieuThiCongDetail.NGAY_YC, "DD/MM/YYYY HH:mm:ss");
    },

    // Formatted date for NGAY_HT (Thời điểm hoàn thành)
    formattedNgayHT() {
      return DateTimeLib.toDateTimeDisplayWithFormat(this.phieuThiCongDetail.NGAY_HT, "DD/MM/YYYY HH:mm:ss");
    }
  },
  watch: {

  },
  created: async function () {
    this.loadPhieuThiCongDetail();
  },
  methods: {
    loadPhieuThiCongDetail: async function () {
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danhsach-congviec/chitiet/' + this.phieuThiCongId)
        this.phieuThiCongDetail = rs.data[0] || {};
        // Emit the data to parent component
        this.$emit('phieuThiCongLoaded', this.phieuThiCongDetail);
      } catch (error) {
        console.error('Error loading phieu thi cong detail:', error);
      } finally {
      }
    },
    async onSave() {
      await this.$refs.chiTietCongViecRef.onGhiLai()
      await this.loadPhieuThiCongDetail()
      return true;
    },
    async xacNhanThucHien() {
      let result = false
      try {

        this.$root.showLoading(true)
        let rs = await api.chuyenPhieuThiCong(this.axios, {
          phieuThiCongId: this.phieuThiCongDetail.PHIEUTC_ID,
          trangThaiId: 22,
          nhanVienId: this.phieuThiCongDetail.NHANVIEN_TH_ID
        })
        if (rs && rs.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Xác nhận thực hiện phiếu thi công thành công!')
          result = true
          this.loadPhieuThiCongDetail()
        } else {
          this.$root.toastError(rs?.message_detail || 'Có lỗi xảy ra khi xác nhận thực hiện phiếu thi công')
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi xác nhận thực hiện phiếu thi công')
      } finally {
        this.loadPhieuThiCongDetail()
        this.$root.showLoading(false)
      }
      return result
    },
    async chuyenThucHien() {
      let result = false
      try {
        this.$root.showLoading(true)
        let rs = await api.chuyenPhieuThiCong(this.axios, {
          phieuThiCongId: this.phieuThiCongDetail.PHIEUTC_ID,
          trangThaiId: 81,
          nhanVienId: this.phieuThiCongDetail.NHANVIEN_TH_ID
        })
        if (rs && rs.error_code === 'BSS-00000000') {
          this.$root.toastSuccess('Chuyển phiếu thi công sang trạng thái Nhiệm vụ mới thành công!')
          this.loadPhieuThiCongDetail()
          result = true
        } else {
          this.$root.toastError(rs?.message_detail || 'Có lỗi xảy ra khi chuyển phiếu thi công')
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi chuyển phiếu thi công')
      } finally {
        this.$root.showLoading(false)
      }
      return result;
    }
  },
};
</script>
<style></style>
