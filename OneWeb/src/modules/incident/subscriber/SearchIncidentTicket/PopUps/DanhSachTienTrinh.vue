<template src='./DanhSachTienTrinh.html'>
</template>

<script>

import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import API from '../Api';

export default {
  components: { DataGridCustom },
  name: 'DanhSachTienTrinh',
  props: {
    BaoHongId: Number,
    required: true
  },

  async mounted () {
    await this.onLoadData();
  },

  data () {
    return {
      columns: [
        { fieldName: "nguoi_cn", headerText: "Người CN", textAlign: "left", width: "200px" },
        { fieldName: "ngay_cn", headerText: "Ngày CN", textAlign: "left", width: "200px" },
        { fieldName: "noidung", headerText: "Nội dung", textAlign: "left", autoFit: "200px" },
      ],
      list: [],
    }
  },

  methods: {
    async onLoadData() {
      try {
        var response = await API.getDanhSachTienTrinhXuLy(this.axios, this.BaoHongId)
        // var data = await API.getDanhSachTienTrinhXuLy(this.axios, 16537693)
        if (response && response.data &&
          response.data.data && response.data.error &&
          response.data.error_code === 'BSS-00000000') {
          this.list = response.data.data

        } else {
          this.list = [];
          this.$toast.warning('Không tìm thấy tiến trình!')
        }
      }
      catch (e) {
        this.$toast.error('Có lỗi xảy ra')
      }
    },
  }
}
</script>
