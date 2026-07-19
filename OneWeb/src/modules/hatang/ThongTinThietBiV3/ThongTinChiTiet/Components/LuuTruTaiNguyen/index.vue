<template src="./index.template.html"></template>

<script>
import ChiTietLuuTruTaiNguyen from './Modal/chitiet-luutru-tainguyen.vue';
export default {
  components: {
    ChiTietLuuTruTaiNguyen
  },
  data() {
    return {
      dsLuuTruTaiNguyenList: [
        {STT: 1, ID: 'LTTN001', TEN: 'Lưu trữ A', MO_TA: 'Lưu trữ dữ liệu miền Bắc', NGAY_TAO: '2023-01-12', TRANG_THAI: 'Hoạt động'},
        {STT: 2, ID: 'LTTN002', TEN: 'Lưu trữ B', MO_TA: 'Lưu trữ dữ liệu miền Trung', NGAY_TAO: '2023-02-25', TRANG_THAI: 'Bảo trì'},
        {STT: 3, ID: 'LTTN003', TEN: 'Lưu trữ C', MO_TA: 'Lưu trữ dữ liệu miền Nam', NGAY_TAO: '2023-03-30', TRANG_THAI: 'Ngưng hoạt động'}
      ],
    }
  },
  computed: {
    dsLuuTruTaiNguyenGrd: function () {
      return this.$refs.dsLuuTruTaiNguyenGrd;
    },
  },
  methods: {


    // =======================Sự kiện lưới danh sách hệ thống dịch vụ =======================
    dsLuuTruTaiNguyenGrd_RowSelected: async function (data) {

    },
    dsLuuTruTaiNguyenGrd_RowDeselected: async function (data) {

    },

    onRowDoubleClick: async function (args) {
      this.$refs.popupChiTietLuuTruTaiNguyen.showModal();
    },
  }
}
</script>
