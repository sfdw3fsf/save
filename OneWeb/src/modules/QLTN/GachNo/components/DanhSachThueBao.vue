<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgDanhSachThueBao"
    :header='"Danh sách thuê bao"'
    showCloseIcon=true
    width='80%'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-top-action">
        <div class="acept item" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
        </div>
        <div class="acept item" v-on:click.prevent="doClose">
          <span class="icon one-export"></span> Thoát
        </div>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <DataGrid
              v-bind:columns="this.loai_tien==1?[
                {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', textAlign: 'Left', allowFiltering: true, isGroupedColumn: true},
                {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'THUCNO', headerText: 'Còn nợ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0'},
                {fieldName: 'DOITUONG', headerText: 'Đối tượng', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true},
                ]:[
                {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', textAlign: 'Left', allowFiltering: true, isGroupedColumn: true},
                {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'THUCNO', headerText: 'Còn nợ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2'},
                {fieldName: 'DOITUONG', headerText: 'Đối tượng', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true},
                ]"
              v-bind:dataSource="results"
              :showFilter="false"
              :allowPaging="false"
              :allowExcelExport="true"
              @recordDoubleClick="recordDoubleClickHandler"
              ref="dsThueBao"
              >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GachNoAPI from '../../api/GachNoAPI'

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
  },
  props: {
    ma_tt: {
      type: String
    },
    kycuoc: {
      type: String
    },
    loai_tien:{
      type:Number,
      default:1
    }
  },
  computed () {
  },
  async created () {

  },
  destroyed () {
  },
  data () {
    return {
      params: {
        p_ma_tt: this.ma_tt,
        p_kycuoc: this.kycuoc
      },
      results: [],
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    async getDSSThueBao () {
      try {
        let postData = {
          kyCuoc: this.kycuoc,
          maTT: this.ma_tt,
          dichVuVTId: null,
          dsMaTB: '',
          pLoaiTien:this.loai_tien,
        }
        const results = await GachNoAPI.getDanhSachTBNo(this.axios, postData)
        return results
      } catch (err) {
        console.log(err)
      }
    },
    openDialog () {
      this.$refs.dlgDanhSachThueBao.show()
    },
    closeDialog () {
      this.$refs.dlgDanhSachThueBao.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },
    onDialogOpen () {
      if (this.kycuoc !== null) this.params.p_kycuoc = this.kycuoc
      this.getDSSThueBao().then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
            this.results = response.data.data
          }
        }
      )
    },
    clearForm () {

    },
    clearResult () {

    },
    doAccept () {
      let selectedRow = this.$refs.dsThueBao.getSelectedRecords()
      if (selectedRow.length <= 0) {
        this.$toast.error('Chưa chọn thuê bao trong danh sách.')
        return false
      } else {
        this.$emit('accept', selectedRow[0])
        this.closeDialog()
      }
    },
    doClose () {
      this.closeDialog()
    },
    recordDoubleClickHandler (args) {
      this.doAccept()
    }
  },
  watch: {
    ma_tt: {
      handler: function (newValue, oldValue) {
        this.params.p_ma_tt = newValue
      }
    },
    kycuoc: {
      handler: function (newValue, oldValue) {
        this.params.p_kycuoc = newValue
      }
    }
  }
})
</script>
