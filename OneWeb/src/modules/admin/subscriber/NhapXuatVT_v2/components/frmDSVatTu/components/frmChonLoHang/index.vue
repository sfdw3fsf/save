<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="onBtnXacNhanClicked()">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Xác nhận </a>
        </li>
      </ul>
      <div class="popup-body">
        <div class="box-form">
          <DataGrid ref="gridLoHang" :columns="gridLoHang.header" :dataSource="gridLoHang.list" :enabledSelectFirstRow="false" :showFilter="true" :allowPaging="true" :enablePagingServer="false" @selectedRowChanged="gridLoHangSelectedRowChanged" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from './api'
export default {
  emits: ['onBtnXacNhanClicked'],
  data() {
    return {
      gridLoHang: {
        header: [
          { fieldName: 'loHang', headerText: 'LOHANG' },
          { fieldName: 'prefix', headerText: 'PREFIX' },
          { fieldName: 'donGia', headerText: 'DONGIA' }
        ],
        list: [],
        value: null,
        selected: []
      }
    }
  },
  methods: {
    async init() {
      this.ds_lohang = (await api.lo_hang(this.axios, {})).data.data
      this.ds_lohang.shift()
      this.ds_lohang.unshift({ loHang: '', prefix: '1', donGia: '0' })
      this.gridLoHang.list = this.ds_lohang
    },
    async gridLoHangSelectedRowChanged(e) {
      this.gridLoHang.value = e
    },
    async onBtnXacNhanClicked() {
      if (!this.gridLoHang.value) {
        this.$toast.warning('Vui lòng chọn lô hàng!')
        return
      }
      this.$emit('onBtnXacNhanClicked', this.gridLoHang.value)
    }
  },
  async mounted() {
    await this.init()
  }
}
</script>
<style scoped></style>
