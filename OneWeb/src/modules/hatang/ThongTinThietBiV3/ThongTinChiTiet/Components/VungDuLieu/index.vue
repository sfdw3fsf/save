<template src="./index.template.html"></template>

<script>
import ChiTietVungDuLieu from './Modal/chitiet-vung-dulieu';

export default {
  components: {
    ChiTietVungDuLieu
  },
  data() {
    return {
      dsVungDuLieuList: [
        {STT: 1, ID: 'VDL001', TEN: 'Vùng dữ liệu A', MO_TA: 'Vùng dữ liệu miền Bắc', NGAY_TAO: '2023-01-10', TRANG_THAI: 'Hoạt động'},
        {STT: 2, ID: 'VDL002', TEN: 'Vùng dữ liệu B', MO_TA: 'Vùng dữ liệu miền Trung', NGAY_TAO: '2023-02-18', TRANG_THAI: 'Bảo trì'},
        {STT: 3, ID: 'VDL003', TEN: 'Vùng dữ liệu C', MO_TA: 'Vùng dữ liệu miền Nam', NGAY_TAO: '2023-03-22', TRANG_THAI: 'Ngưng hoạt động'}
      ],
    }
  },
  computed: {
    dsVungDuLieuGrd: function () {
      return this.$refs.dsVungDuLieuGrd;
    },
  },
  methods: {


    // =======================Sự kiện lưới danh sách hệ thống dịch vụ =======================
    dsVungDuLieuGrd_RowSelected: async function (data) {

    },
    dsVungDuLieuGrd_RowDeselected: async function (data) {

    },

    onRowDoubleClick: async function (args) {
      this.$refs.popupChiTietVungDuLieu.showModal();
    },
  }
}
</script>
