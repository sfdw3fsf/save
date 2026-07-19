<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgChonThueBao"
    :header='"Chọn thuê bao"'
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
            v-bind:columns="columnsTB"
            v-bind:dataSource="dsTB"
            :showFilter="true"
            :allowPaging="false"
            :enablePagingServer="false"
            height="100%"
            ref="gridChonThueBao"
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
    ds_tb: {
      type: Array,
      required: false
    }
  },
  computed: {
    get_ds_tb () {
      return this.ds_tb
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
      columnsTB: [
        {fieldName: 'THUEBAO_ID', visible: false},
        {fieldName: 'GOI_DDV_ID', visible: false},
        {fieldName: 'MA_TB', headerText: 'Mã TB', allowFiltering: true, allowEditing: false},
        {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowEditing: false},
        {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ', allowFiltering: true, allowEditing: false},
        {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowEditing: false},
        {fieldName: 'TEN_GOI_DDV', headerText: 'Gói đa dịch vụ tham gia', allowFiltering: true, allowEditing: false}
      ],
      dsTB: [],
      selected: null,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    openDialog () {
      this.dsTB = this.ds_tb
      this.$refs.dlgChonThueBao.show()
    },
    closeDialog () {
      this.dsTB = []
      this.$refs.dlgChonThueBao.hide()
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
        this.$toast.error('Bạn chưa chọn thuê bao.')
        return false
      }
      this.$emit('acceptChonMaThueBao', this.selected)
      this.$refs.dlgChonThueBao.hide()
    }
  },
  watch: {
    get_ds_tb: {
      handler: function (newValue, oldValue) {
        this.dsTB = newValue
      }
    }
  }
})
</script>
