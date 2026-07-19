<template src="./index.template.html"></template>

<script>

import ChiTietHeThongDichVu from './Modal/chitiet-hethong-dichvu.vue';

export default {
  components: {
    ChiTietHeThongDichVu
  },
  data() {
    return {
      dsHeThongDichVuList: [
        {STT: 1, ID: 'HTDV001', TEN: 'Dịch vụ A', GHI_CHU: 'Triển khai ảo hóa', GHI_CHU: '2023-01-15', GHI_CHU: 'Cao', GHI_CHU: 'Nguyễn Văn A', GHI_CHU: 'Hoạt động'},
        {STT: 2, ID: 'HTDV002', TEN: 'Dịch vụ B', GHI_CHU: 'Triển khai vật lý', GHI_CHU: '2023-02-20', GHI_CHU: 'Trung bình', GHI_CHU: 'Trần Thị B', GHI_CHU: 'Bảo trì'},
        {STT: 3, ID: 'HTDV003', TEN: 'Dịch vụ C', GHI_CHU: 'Triển khai đám mây', GHI_CHU: '2023-03-10', GHI_CHU: 'Thấp', GHI_CHU: 'Lê Văn C', GHI_CHU: 'Ngưng hoạt động'}
      ],

    }
  },
  computed: {
    dsHeThongDichVuGrd: function () {
      return this.$refs.dsHeThongDichVuGrd;
    },
  },
  methods: {

    getNguoiQLItems: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "NGUOI-QL",
          thamSo1:this.$root.token.getDonViID()
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    // =======================Sự kiện lưới danh sách hệ thống dịch vụ =======================
    dsHeThongDichVuGrd_RowSelected: async function (data) {

    },
    dsHeThongDichVuGrd_RowDeselected: async function (data) {

    },

    onRowDoubleClick: async function (args) {
      this.$refs.popupChiTietHeThongDichVu.showModal();
    },
  }
}
</script>
