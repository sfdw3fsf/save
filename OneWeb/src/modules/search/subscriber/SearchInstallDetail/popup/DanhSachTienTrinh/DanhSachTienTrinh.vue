<template src='./DanhSachTienTrinh.html'>
</template>
<script>

import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import API from '../../API';
import Vue from 'vue';

export default {
  components: { DataGridCustom },
  name: 'DanhSachTienTrinh',
  props: {
    hdtb_id: Number,
    required: true
  },

  async mounted () {
    await this.onLoadData();
  },

  data () {
    return {
      columns: [
        { fieldName: "nguoi_cn", headerText: "Người CN", textAlign: "left", width: "100px" },
        { fieldName: "ngay_cn", headerText: "Ngày CN", textAlign: "left", width: "100px" },
        { fieldName: "noidung", headerText: "Nội dung", textAlign: "left", autoFit: "200px",
          template: function () {
            return {
              template: Vue.component("columnTemplate", {
                template: `<div style="white-space: pre-wrap;">{{noidung}}</div>`,
                data: function () {
                  return { data: {} };
                },
                computed: {
                  noidung: function () {
                    return this.data.noidung.replace(/[\n\r]+/g, '');
                  }
                },
              }),
            };
          },
        },
      ],
      list: [],
    }
  },

  methods: {
    async onLoadData() {
      try {
        var response = await API.getDanhSachTienTrinhXuLy(this.axios, this.hdtb_id)
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
