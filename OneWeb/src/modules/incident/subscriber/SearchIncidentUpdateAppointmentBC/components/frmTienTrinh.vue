<template>
  <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="table-content">
        <div>
          <DataGridCustom ref="ds_ttxl" :columns="columns" :showColumnCheckbox="false" :dataSource="list"
                          :showFilter="true" :enabledSelectFirstRow="true" :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                          panelDataHeight="220" :allowPaging="true" :enablePagingServer="false">
          </DataGridCustom>
        </div>
      </div>
    </div>
  </div>
</template>


<script>

import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import API from '../Api';
import Vue from 'vue'

export default {
  components: { DataGridCustom },
  name: 'frmDanhSachTienTrinh',
  props: ['params'],

  async mounted() {
    await this.onLoadData();
  },

  data() {
    return {
      columns: [
        { fieldName: "nguoi_cn", headerText: "Người CN", textAlign: "left", width: "100px" },
        { fieldName: "ngay_cn", headerText: "Ngày CN", textAlign: "left", width: "100px" },
        {
          fieldName: "noidung", headerText: "Nội dung", textAlign: "left", autoFit: "200px",
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
        this.loading(true)
        let data = {
        //   // "vbaohong_id": params.vbaohong_id_t,
          "vbaohong_id": this.params.vbaohong_id_t,
        //   "vnhom_id": params.thuebao_id
          "vnhom_id": 2
        }
        console.log('params')
        console.log(data);
        // var response = await API.getDanhSachTienTrinhXuLy(this.axios, this.hdtb_id)
        let response = await API.getDanhSachTienTrinhXuLy(this.axios, data)
        if (response && response.data &&
          response.data.data && response.data.error &&
          response.data.error_code === 'BSS-00000000') {
          this.list = response.data.data

        } else {
          this.list = [];
          this.$toast.warning('không tìm thấy tiến trình')
        }
        this.loading(false)
      } catch (e) {
        this.$toast.warning('không tìm thấy tiến trình')
      }
      // console.log('data')
      // console.log(this.list)
      console.log(this.list)
    }
  }
}
</script>
