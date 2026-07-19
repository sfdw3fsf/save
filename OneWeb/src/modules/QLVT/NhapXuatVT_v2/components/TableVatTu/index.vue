<template>
  <div>
    <DataGrid ref="gridVatTu" :columns="gridVatTu.header" :dataSource="dataSource" @selectedItemsChanged="selectedItemsChanged" :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" :showColumnCheckbox="true" @selectedRowChanged="selectedRowChanged" />
  </div>
</template>

<script>
import api from './api'
import CustomColumns from './custom_columns'
export default {
  name: 'TableVatTu',
  components: {},
  props: {
    dataSource: {
      type: Array,
      default: () => {
        return []
      }
    },
    kieuPhieu: {
      type: [Number, String],
      default: null
    }
  },
  emits: ['update:dataSource'],
  data() {
    return {
      model: this.dataSource,
      cboLoaiKho: { list: [] },
      cboMaKhoTD: { list: [] },
      gridVatTu: {
        header: [
          //   { width: 70, headerText: 'Xóa', allowFiltering: false, template: GridVatTuBtnXoaTemplate(this), freeze: 'Left' },
          { width: 100, fieldName: 'ID_NHAPGOC', headerText: 'IDG', allowFiltering: true, freeze: 'Left' },
          { width: 100, fieldName: 'CTCT_ID', headerText: 'CTCT', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { width: 150, fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { width: 350, fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, format: 'N0', type: 'number', allowEditing: true, editType: 'numericedit' },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, format: 'N0', type: 'number', allowEditing: true, editType: 'numericedit' },
          { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true },
          { width: 350, fieldName: 'LOAIKHO', headerText: 'Loại kho', allowFiltering: true, template: CustomColumns.LoaiKhoTemplate(this), visible: true },
          { fieldName: 'MAKHOTD', headerText: 'Mã kho TD', allowFiltering: true, template: CustomColumns.MaKhoTDTemplate(this), visible: true },
          { fieldName: 'TK_KHO', headerText: 'TK Kho', allowFiltering: true },
          { fieldName: 'TK_DOIUNG', headerText: 'TK Đối ứng', allowFiltering: true },
          { fieldName: 'TY_LE', headerText: 'Tỷ lệ thu hồi (%)', allowFiltering: true, visible: false },
          { fieldName: 'MATHETS', headerText: 'Thẻ TS', allowFiltering: true, allowEditing: true },
          { fieldName: 'SOTHANG_PB', headerText: 'Số tháng PB', type: 'number', allowEditing: true },
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị' },
          { fieldName: 'HAN_BH', headerText: 'Hạn BH' },
          { fieldName: 'MA_HD', headerText: 'Mã HĐ' },
          { fieldName: 'MA_TH', headerText: 'Đợt TH' },
          { fieldName: 'MATHUNG', headerText: 'Mã Thùng' },
          { fieldName: 'TONGSL', headerText: 'Tổng SL' },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', allowEditing: true },
          { fieldName: '', headerText: 'Dự án' },
          { fieldName: 'GHICHU_HD', headerText: 'Ghi chú HĐ' },
          { fieldName: 'TEN_CONGTRINH', headerText: 'Tên CT' },
          { fieldName: '', headerText: 'Cuộn cáp', template: this.CuonCapTemplate, textAlign: 'Center' },
          { fieldName: 'DS_DVI_TC', headerText: 'Đơn vị TC' },
          { fieldName: 'NGUON_GOC', headerText: 'Nguồn gốc', allowEditing: true }
          // { fieldName: 'DONGTBI_ID', headerText: 'Dòng Tbi', template: this.gridVatTuCboDongTB(this) },
          // { fieldName: 'HETHONG_ID', headerText: 'Hệ thống', template: this.gridVatTuCboHeThong(this) },
          //   { fieldName: 'CHATLUONG_ID', headerText: 'Chất lượng', template: GridVatTuChatLuongTemplate(this) }
          // { fieldName: 'VATTU_ID', headerText: '', visible: false, isPrimaryKey: true }
        ],
        list: [],
        value: null
      }
    }
  },
  computed: {
    value: {
      get() {
        return this.modelValue
      },
      set(value) {
        this.$emit('update:modelValue', value)
      }
    }
  },
  async mounted() {
    await this.init()
    console.log('mounted')
    console.log(this.model)
    console.log(this)
    
  },
  watch: {
    // dataSource(currentValue) {
    //   console.log('watch dataSource')
    //   this.$emit('input', currentValue)
    // },
    // value(new_value, old_value) {
    //   console.log('watcher gridVatTu.list')
    //   if (new_value != null && new_value.length > 0 && new_value[0].IDX == null) {
    //     this.$nextTick(() => {
    //       this.gridVatTu.list.map((e, idx) => {
    //         e.IDX = idx
    //         return e
    //       })
    //     })
    //   }
    // }
  },
  methods: {
    async init() {
      this.cboLoaiKho.list = (await api.ds_loai_kho_td(this.axios, { mucdich_id: this.kieuPhieu })).data.data
      this.cboMaKhoTD.list = (await api.sp_lay_ds_makhotd(this.axios, {})).data.data
      this.$emit(
      'input',
      this.model.map((e, idx) => {
        e.IDX = idx
        return e
      }))
      console.log('init')
    console.log(this.model)
    console.log(this)
      this.onRefreshDataGrid()
    },
    selectedItemsChanged(e) {
      console.log('selectedItemsChanged')
      console.log(e)
    },
    selectedRowChanged(e) {
      console.log('selectedRowChanged')
      console.log(e)
    },
    onRefreshDataGrid(value) {
      console.log('onRefreshDataGrid')
      console.log(value)
      this.dataSource = value ? value : this.dataSource
    }
  },
  created() {
    window.addEventListener('keydown', this.keyDownHandler)
  },
  destroyed() {
    window.removeEventListener('keydown', this.keyDownHandler)
  }
}
</script>
<style scoped></style>
