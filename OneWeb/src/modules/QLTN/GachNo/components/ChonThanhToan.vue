<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgChonThanhToan"
    :header='"Chọn thanh toán"'
    showCloseIcon=true
    width='1100px'
    height='400'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-top-action">
        <div class="acept item" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
        </div>
      </div>
      <div class="popup-body">
        <div class="table-content" style="height:400px">
            <DataGrid
            v-bind:columns="columnsTT"
            v-bind:dataSource="dsTT"
            :showFilter="true"
            :allowPaging="false"
            :enablePagingServer="false"
            height="100%"
            ref="gridChonThanhToan"
            @selectedRowChanged="rowChanged"
            >
          </DataGrid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
Vue.use(DialogPlugin)
export default Vue.extend({
  components: {
  },
  props: {
    ds_tt: {
      type: Array,
      required: false
    }
  },
  computed: {
    get_ds_tt () {
      return this.ds_tt
    }
  },
  async created () {

  },
  mounted () {
  },
  destroyed () {
  },
  data () {
    return {
      columnsTT: [
        {fieldName: 'THANHTOAN_ID', visible: false},
        {fieldName: 'DAILY_ID', visible: false},
        {fieldName: 'MIG_ID', visible: false},
        {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowEditing: false},
        {fieldName: 'MATB_DD', headerText: 'Mã đại diện', allowFiltering: true, allowEditing: false},
        {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true, allowEditing: false},
        {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ', allowFiltering: true, allowEditing: false},
        {fieldName: 'MST', headerText: 'Mã số thuế', allowFiltering: true, allowEditing: false},
        {fieldName: 'TENTUYEN', headerText: 'Tuyến thu', allowFiltering: true, allowEditing: false},
        {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, format: 'N0', type: 'number'}
      ],
      dsTT: [],
      selected: null,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    openDialog () {
      this.dsTT = this.ds_tt
      this.$refs.dlgChonThanhToan.show()
    },
    closeDialog () {
      this.dsTT = []
      this.$refs.dlgChonThanhToan.hide()
    },
    onDialogClose () {
    },
    onDialogOpen () {
    },
    rowChanged (e) {
      this.selected = e
    },
    doAccept () {
      if (this.selected === null) {
        this.$toast.error('Bạn chưa chọn mã thanh toán.')
        return false
      }
      this.$emit('acceptChonMaThanhToan', this.selected)
      this.$refs.dlgChonThanhToan.hide()
    }
  },
  watch: {
    get_ds_tt: {
      handler: function (newValue, oldValue) {
        this.dsTT = newValue
      }
    }
  }
})
</script>
