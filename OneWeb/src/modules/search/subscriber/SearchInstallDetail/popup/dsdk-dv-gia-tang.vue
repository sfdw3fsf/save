<template>
  <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="row">
        <div class="box-form">
          <DataGrid v-bind:columns="dsdvGt.cols" v-bind:dataSource="dsdvGt.list" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabled-select-first-row="true"></DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from './../API'
export default {
  name: 'DsdkDvGiaTang',
  components: {},
  props: {
    hdtb_id: Number,
    required: true
  },
  data() {
    return {
      dsdvGt: {
        list: [],
        cols: [
          { fieldName: 'ten_dvgt', headerText: 'Tên dịch vụ' },
          { fieldName: 'ma_dvgt', headerText: 'Mã DVGT' },
          { fieldName: 'kieu_yc', headerText: 'Kiểu YC' },
          { fieldName: 'noidung', headerText: 'Nội dung' }
        ]
      }
    }
  },
  methods: {
    async lay_ds_dangky_dvgt() {
      this.loading(true)
      var data = await api.lay_ds_dangky_dvgt(this.axios, this.hdtb_id)
      if (data.data.data && data.data.data.length > 0) {
        this.dsdvGt.list = data.data.data
        this.dsdvGt.list = this.dsdvGt.list.map((e) => {
          if (e.kieu_yc == '1') e.kieu_yc = 'Đăng ký'
          else if (e.kieu_yc == '0') e.kieu_yc = 'Hủy'
          else e.kieu_yc = 'Thay đổi'
          return e
        })
      } else {
        this.dsdvGt.list = []
      }
      this.loading(false)
    }
  },
  async mounted() {
    await this.lay_ds_dangky_dvgt()
  }
}
</script>
