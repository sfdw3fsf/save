<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgCapNhatNhom_CK"
    :header='"Cập nhật nhóm chuyển khoản"'
    showCloseIcon=true
    width='900px'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#"  v-on:click.prevent="doAddNew()">
                    <span class="icon one-file-bookmark"></span>Tạo mới
                </a>
            </li>
            <li>
                <a href="#"  v-on:click.prevent="doConfirm()">
                    <span class="icon one-xlsx-import"></span>Xác nhận
                </a>
            </li>
            <li>
                <a href="#"  v-on:click.prevent="doCancel()">
                    <span class="icon one-xlsx-import"></span>Hủy bỏ
                </a>
            </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
            <div class="info-row">
                <div class="key w100">Tìm kiếm</div>
                <div class="value">
                  <input type="text" class="form-control highlight" v-model="params.p_tim_kiem" ref="refTimKiem" autofocus @keyup.enter="TRA_CUU_TEN_NHOM">
                  <!-- -->
                  <div class="vattu_table" id="dsnhom-pd" :class="{ displayNone: hideTable }">
                    <DataGrid2
                        v-bind:columns="[
                        {fieldName: 'NHOMCK_ID', visible: false},
                        {fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', allowFiltering: true, allowSorting: false, width: 170},
                        {fieldName: 'STK', headerText: 'Số TK', allowFiltering: true, allowSorting: false},
                        {fieldName: 'CHUNGTU', headerText: 'Chứng từ', allowFiltering: true, allowSorting: false},
                        {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false},
                        ]"
                        v-bind:dataSource="dsNhomCK" :showFilter="false" :showColumnCheckbox="false"
                        dataKeyField="NHOMCK_ID" :defaultColumnCheckboxChecked="false" :selectedRow="-1"
                        @selectedRowChanged="onSelectNhomCK"
                        ref="tableNvt">
                    </DataGrid2>
                    <div class="p-2">
                        <button type="button" @click="onChangeCustomDropDown('#dsnhom-pd')" class="btn btn-secondary btn-sm"
                            aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                  </div>
                  <!-- -->
                </div>
            </div>
          </div>
          <div class="row">
            <div class="table-content mart10">
              <div class="nav tabs tab-over">
                <a href="#tabThongTinNhomCK" data-toggle="tab" class="active">Thông tin nhóm chuyển khoản</a>
                <a href="#tabCTNhomCK" data-toggle="tab">Chi tiết nhóm chuyển khoản</a>
              </div>
              <div class="tab-content">
                <div id="tabThongTinNhomCK" class="tab-pane active">
                  <div class="row">
                      <div class="col-sm-6 col-12">
                          <div class="info-row">
                              <div class="key w80">Mã nhóm</div>
                              <div class="value">
                                <input type="text" class="form-control" v-model="params.p_ma_nhom" :disabled=true title="Tên chủ đại lý">
                              </div>
                          </div>
                      </div>
                      <div class="col-sm-6 col-12">
                          <div class="info-row">
                              <div class="key w80">Ngày tạo</div>
                              <div class="value">
                                  <ejs-datepicker
                                  v-model="p_ngay_tao"
                                  :value="configs.dateConfig.value"
                                  :max="configs.dateConfig.maxDate"
                                  :format="configs.dateConfig.format"
                                  :allowEdit=false
                                  :showClearButton=false
                                  :disabled="f_khoaform"
                                  >
                                </ejs-datepicker>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-sm-12 col-12">
                          <div class="info-row">
                              <div class="key w80">Tên nhóm</div>
                              <div class="value">
                                <input type="text" class="form-control" ref="refTenNhom" v-model="params.p_ten_nhom" :disabled="f_khoaform" style="font-weight: bold" title="Tên chủ đại lý">
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-sm-12 col-12">
                          <div class="info-row">
                              <div class="key w80">Số TK</div>
                              <div class="value">
                                <input type="text" class="form-control" v-model="params.p_so_tk" :disabled="f_khoaform">
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-sm-12 col-12">
                          <div class="info-row">
                              <div class="key w80">Chứng từ</div>
                              <div class="value">
                                <input type="text" class="form-control" v-model="params.p_chung_tu" :disabled="f_khoaform">
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-sm-12 col-12">
                          <div class="info-row">
                              <div class="key w80">Ghi chú</div>
                              <div class="value">
                                <textarea class="form-control" v-model="params.p_ghi_chu" rows="4" :disabled="f_khoaform">
                                </textarea>
                              </div>
                          </div>
                      </div>
                  </div>
                </div>
                <div id="tabCTNhomCK" class="tab-pane">
                  <div class="table-content">
                    <DataGrid
                      v-bind:columns="[
                        {fieldName: 'MA_TT', headerText: 'Mã Thanh toán', textAlign: 'Left', allowFiltering: true, width: 120},
                        {fieldName: 'MATB_DD', headerText: 'Mã đại diện', textAlign: 'Left', allowFiltering: true, width: 120},
                        {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', textAlign: 'Left', allowFiltering: true, width: 200},
                        {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', textAlign: 'Left', allowFiltering: true, width: 200},
                        {fieldName: 'MA_NV', headerText: 'Tuyến thu', textAlign: 'Left', allowFiltering: true, width: 120}
                        ]"
                      v-bind:dataSource="dsNhomCKDaGan"
                      :enablePagingServer="false"
                      :showFilter="true"
                      :allowPaging="false"
                      :allowExcelExport="true"
                      :key="keyCTNhomCK"
                      ref="dsUploaded"
                      >
                    </DataGrid>
                  </div>
                </div>
              </div>
            </div>
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
// import EventBus from '@/utils/eventBus'
import NhomChuyenKhoanAPI from '../../api/NhomChuyenKhoan'
import moment from 'moment'

import { DialogUtility } from '@syncfusion/ej2-popups'

Vue.use(DialogPlugin)
Vue.use(DialogUtility)
let DialogObj

export default Vue.extend({
  props: {
    create: {
      type: Boolean,
      required: false
    },
    ds_thanhtoan: {
      type: Array,
      required: false
    }
  },
  computed: {
    p_ngay_tao: {
      get () {
        return this.params.p_ngay_tao
      },
      set (value) {
        this.params.p_ngay_tao = value
      }
    }
  },
  data () {
    return {
      configs: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        }
      },
      params: {
        p_tim_kiem: '',
        p_ma_nhom: '',
        p_ten_nhom: '',
        p_so_tk: '',
        p_ngay_tao: new Date(),
        p_chung_tu: '',
        p_ghi_chu: '',
        p_vnhom_id: 0
      },
      f_khoaform: false,
      hideTable: true,
      keyCTNhomCK: 0,
      dsNhomCK: [],
      dsNhomCKDaGan: [],
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  }, // end of data
  methods: {
    openDialog () {
      this.$refs.dlgCapNhatNhom_CK.show()
      this.$refs.refTimKiem.focus()
    },
    closeDialog () {
      this.$refs.dlgCapNhatNhom_CK.hide()
    },
    onDialogClose () {
    },
    onDialogOpen () { // call before open Dialog
      this.CLEAR()
      this.KHOAMO_FORM(false)
    },

    KHOAMO_FORM (mo) {
      this.f_khoaform = !mo
    },
    THAOTAC_NUT (kieu) {
      if (kieu === 1) {
        this.XOA_FORM()
        this.f_khoaform = false
        this.$refs.refTenNhom.focus()
        this.CLEAR()
        this.params.p_ngay_tao = new Date()
      } else if (kieu === 3) {
        // Xóa form
        this.XOA_FORM()
      }
    },
    XOA_FORM () {
      this.params.p_vnhom_id = 0
      this.params.p_ma_nhom = ''
      this.params.p_ten_nhom = ''
      this.params.p_so_tk = ''
      this.params.p_chung_tu = ''
      this.params.p_ghi_chu = ''
      this.params.p_ngay_tao = new Date()
      this.f_khoaform = false
    },
    CLEAR () {
      this.params.p_tim_kiem = ''
      this.params.p_ma_nhom = ''
      this.params.p_ten_nhom = ''
      this.params.p_so_tk = ''
      this.params.p_chung_tu = ''
      this.params.p_ghi_chu = ''
      this.params.p_ngay_tao = new Date()
      this.f_khoaform = false
      this.dsNhomCK = []
      this.dsNhomCKDaGan = []
    },
    doAddNew () {
      this.THAOTAC_NUT(1)
    },
    doConfirm () {
      if (this.params.p_vnhom_id === 0) {
        this.params.p_ten_nhom = this.params.p_ten_nhom.trim()
        if (this.params.p_ten_nhom === '') {
          this.$toast.error('Bạn chưa nhập tên nhóm')
          return
        }

        if (!this.create) {
          this.$toast.error('Đã có nhóm chứa danh sách mã thanh toán rồi bạn không thể tạo')
          return
        }

        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có muốn tạo nhóm cho danh sách các mã thanh toán này không?</div>",
          okButton: { text: 'Đồng ý', click: this.confirmCreate },
          cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false } },
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      } else {
        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có muốn thêm danh sách mã thanh toán vào nhóm " + this.params.p_ten_nhom + ' không ?</div>',
          okButton: { text: 'Đồng ý', click: this.confirmUpdate },
          cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false } },
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      }
    },
    doCancel () {
      this.closeDialog()
    },

    confirmCreate () {
      DialogObj.hide()

      let postData = {
        'donViId': this.$auth.getDonViID(),
        'tenNhom': this.params.p_ten_nhom.trim(),
        'chungTu': this.params.p_chung_tu.trim(),
        'soTaiKhoan': this.params.p_so_tk.trim(),
        'ngayTao': moment(this.params.p_ngay_tao).format('DD/MM/YYYY'),
        'ghiChu': this.params.p_ghi_chu.trim()
      }

      this.loading(true)

      NhomChuyenKhoanAPI.taoNhomChuyenKhoan(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0
          ) {
            let nhomId = res.data.data[0].ID_NHOM
            let data = {
              nhomId: nhomId,
              ds: []
            }

            this.ds_thanhtoan.forEach(item => {
              data.ds.push({
                maTT: item,
                thanhToanId: null
              })
            })
            NhomChuyenKhoanAPI.taoDSTheoNhom(this.axios, data)
              .then((response) => {
                if (response.data.error_code === 'BSS-00000000') {
                  this.$toast.success('Tạo nhóm thành công.')
                  this.closeDialog()
                }
              })
              .catch(err => {
                this.$toast.error(err.data.message_detail)
              })
              .finally(() => {
                this.loading(false)
              })
          }
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        })
        .finally(() => { })
    },

    confirmUpdate () {
      DialogObj.hide()

      let postData = {
        'ds': [],
        'nhomId': this.params.p_vnhom_id
      }

      this.ds_thanhtoan.forEach((item) => {
        postData.ds.push({
          'maTT': item,
          'thanhToanId': null
        })
      })

      this.loading(true)

      NhomChuyenKhoanAPI.taoDSTheoNhom(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            if (res.data.data.length > 0 && res.data.data[0].CODE_ERROR === 'BCSS-00000000') {
              this.$toast.success(res.data.data[0].CODE_MESSAGE)
              this.closeDialog()
            } else {
              this.$toast.error(res.data.data[0].CODE_MESSAGE)
            }
          }
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    TRA_CUU_TEN_NHOM () {
      if (this.params.p_tim_kiem === '' || this.params.p_tim_kiem.trim() === '') {
        this.$toast.error('Bạn phải nhập tên nhóm.')
        return false
      }

      this.params.p_tim_kiem = this.params.p_tim_kiem.trim()

      this.dsNhomCK = []

      this.loading(true)

      NhomChuyenKhoanAPI.tracuuNhomChuyenKhoan(this.axios, this.params.p_tim_kiem)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0
          ) {
            if (res.data.data.length === 1) {
              this.params.p_vnhom_id = res.data.data[0].NHOMCK_ID
              this.params.p_tim_kiem = res.data.data[0].MA_NHOM + ' - ' + res.data.data[0].TEN_NHOM

              this.f_khoaform = true

              this.params.p_ma_nhom = res.data.data[0].MA_NHOM
              this.params.p_ten_nhom = res.data.data[0].TEN_NHOM
              this.params.p_so_tk = res.data.data[0].STK === null ? '' : res.data.data[0].STK
              this.params.p_ghi_chu = res.data.data[0].GHICHU === null ? '' : res.data.data[0].GHICHU
              this.params.p_chung_tu = res.data.data[0].CHUNGTU === null ? '' : res.data.data[0].CHUNGTU
              this.params.p_ngay_tao = moment(res.data.data[0].NGAYTAO, 'DD/MM/YYYY').toDate()

              this.HIENTHI_DS_DAGAN()
            } else {
              this.dsNhomCK = res.data.data
              this.onChangeCustomDropDown('#dsnhom-pd')
            }
          } else {
            this.$toast.error('Không tìm thấy tên nhóm.')
            return false
          }
        })
        .catch((error) => {
          if (error.data !== undefined) this.$toast.error(error.data.message_detail)
          else console.log(error)
        })
        .finally(() => { this.loading(false) })
    },

    HIENTHI_DS_DAGAN () {
      this.dsNhomCKDaGan = []

      this.loading(true)

      NhomChuyenKhoanAPI.getDSThanhToanDaGan(this.axios, this.params.p_vnhom_id)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0
          ) {
            this.dsNhomCKDaGan = res.data.data
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },
    onChangeCustomDropDown (id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    onSelectNhomCK (args) {
      if (args === null) return

      // console.log(args)
      this.params.p_vnhom_id = args.NHOMCK_ID
      this.params.p_ma_nhom = args.MA_NHOM
      this.params.p_ten_nhom = args.TEN_NHOM

      this.f_khoaform = false

      this.params.p_so_tk = args.STK
      this.params.p_ghi_chu = args.GHICHU
      this.params.p_chung_tu = args.CHUNGTU

      this.onChangeCustomDropDown('#dsnhom-pd')

      this.HIENTHI_DS_DAGAN()
    }
  } // end of methods
})
</script>
<style scoped>
.displayNone {
  display: none;
}
</style>
