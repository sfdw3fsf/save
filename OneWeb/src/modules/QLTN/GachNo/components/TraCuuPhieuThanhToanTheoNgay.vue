<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgTraCuuPhieuThanhToanTheoNgay"
    :header='"Tra cứu phiếu thanh toán theo ngày"'
    showCloseIcon=true
    width='90%'
    height=800
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <!-- TOOLBAR -->
      <div class="list-actions-top">
        <div class="pull-left">
            <ul class="list">
                <li>
                    <a href="#" v-on:click.prevent="getDanhSach">
                        <span class="icon one-file-attach1"></span> Lấy DS (F5)
                    </a>
                </li>
                <li>
                    <a href="#" v-on:click.prevent="updateNgayThanhToan">
                        <span class="icon one-reload1"></span> Cập nhật
                    </a>
                </li>
                <li>
                    <a href="#"  v-on:click.prevent="deletePhieuThu">
                        <span class="icon one-file-trash"></span> Xoá phiếu
                    </a>
                </li>
                <li>
                    <a href="#" v-on:click.prevent="editThanhToan">
                        <span class="icon one-file-edit1"></span> Sửa TT
                    </a>
                </li>
                <li>
                    <a href="#" v-on:click.prevent="editLuot">
                        <span class="icon one-file-list"></span> Sửa lượt
                    </a>
                </li>
                <li>
                    <a href="#" v-on:click.prevent="printPhieuThu">
                        <span class="icon one-print"></span>In phiếu
                    </a>
                </li>
                <li>
                    <a href="#"  v-on:click.prevent="exportExcel">
                        <span class="icon one-xlsx"></span> Xuất Excel
                    </a>
                </li>
                <li>
                    <a href="#"  v-on:click.prevent="syncVNP">
                        <span class="icon one-sync1"></span> Đồng bộ VNP
                    </a>
                </li>
            </ul>
        </div>
        <div class="pull-right fw6 mart10">
            <span>Kỳ cước : {{get_kycuoc}}</span>
            <span>Chu kỳ : {{get_chuky}}</span>
        </div>
        <div class="clearfix"></div>
      </div>

      <div class="popup-body">
        <div class="grid-stack-box">
          <Split style="height: 750px">
            <SplitArea :size="33" :minSize="100">
              <Split style="height: 700px;" direction="vertical">
              <SplitArea sizes="50">
                <div class="box-form marb0" id="boxTop">
                    <div class="legend-title mart5">Danh sách nhân viên thu cước</div>
                    <div class="table-content" style="max-height: calc(100% - 18px);">
                      <DataGrid
                      v-bind:columns="columnsNVTC"
                      v-bind:dataSource="resultDSNVTC"
                      v-bind:aggregates="sumColumnsNVTC"
                      :showFilter="true"
                      :allowPaging="true"
                      :allowExcelExport="true"
                      :totalRecords="pageinfoNVTC.totalElement"
                      :pageSize="pageinfoNVTC.maxSize"
                      :pageIndex="pageinfoNVTC.page"
                      @selectedRowChanged="RowChangedNVTC"
                      ref="gridDSNVTC"
                      >
                    </DataGrid>
                  </div>
                </div>
              </SplitArea>
              <SplitArea sizes="50">
                <div class="box-form marb0" id="boxBottom">
                  <div class="legend-title mart5">Danh sách lượt thanh toán</div>
                  <div class="table-content" style="max-height: calc(100% - 80px);">
                    <DataGrid
                      v-bind:columns="columnsLuotTT"
                      v-bind:dataSource="resultDSLuotTT"
                      v-bind:aggregates="sumColumnsLuotTT"
                      :showFilter="true"
                      :allowPaging="true"
                      :allowExcelExport="true"
                      :totalRecords="pageinfoLuotTT.totalElement"
                      :pageSize="pageinfoLuotTT.maxSize"
                      :pageIndex="pageinfoLuotTT.page"
                      @selectedRowChanged="RowChangedLuotTT"
                      ref="gridDSLuotTT"
                      >
                    </DataGrid>
                  </div>
                </div>
              </SplitArea>
            </Split>
            </SplitArea>
            <SplitArea :size="67" :minSize="100">
                <div class="box-col box-form marb0" id="boxRight">
                      <div class="legend-title padb3">
                          <div class="pull-left mart7">
                              Danh sách phiếu thanh toán
                          </div>
                          <div class="pull-right">
                            <div class="info-row marb0">
                              <div class="key w60 red">Ngày TT</div>
                              <div class="value">
                                <ejs-datepicker
                                  v-model="p_ngay_ttoan"
                                  :max="config.dateConfig.maxDate"
                                  :min="config.dateConfig.minDate"
                                  :value="config.dateConfig.value"
                                  :format="config.dateConfig.format"
                                  :allowEdit=false
                                  :showClearButton=false
                                  >
                                </ejs-datepicker>
                              </div>
                            </div>
                          </div>
                          <div class="clearfix"></div>
                      </div>
                      <div class="table-content" style="max-height: 600px;">
                          <DataGrid
                            v-bind:columns="columnsPhieuTT"
                            v-bind:dataSource="resultDSPhieuTT"
                            :showColumnCheckbox="true"
                            :showFilter="true"
                            :allowPaging="true"
                            :allowExcelExport="true"
                            :totalRecords="pageinfoPhieuTT.totalElement"
                            :pageSize="pageinfoPhieuTT.maxSize"
                            :pageIndex="pageinfoPhieuTT.page"
                            :editSettings="editSettings"
                            @actionComplete="actionComplete"
                            @selectedRowChanged="RowChangedPhieuTT"
                            ref="gridDSPhieuTT"
                            >
                          </DataGrid>
                      </div>
                  </div>
            </SplitArea>
          </Split>
        </div>
      </div>

    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import { mapActions, mapState } from 'vuex'
import GachNoAPI from '../../api/GachNoAPI'
import GachNoTuyenThuAPI from '../../api/GachNoTuyenThu'
import DoiNgayThanhToan from './DoiNgayThanhToan'
import SuaThongTinLuotThanhToan from './SuaThongTinLuotThanhToan'
import moment from 'moment'
import {
  NHOM_HTTT
} from '@/const/enums'

import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)

let DialogObj

export default Vue.extend({
  components: {
    appDoiNgayThanhToan: DoiNgayThanhToan,
    appSuaThongTinLuotThanhToan: SuaThongTinLuotThanhToan
  },
  props: {
    ngay_tt: {
      type: String,
      required: false
    },
    kycuoc: {
      type: String,
      required: false
    }
  },
  async created () {

  },
  computed: {
    ...mapState('qltnCommon', [
      'kyHoaDonHienTai'
    ]),
    get_kycuoc () {
      let v = this.params.p_kycuoc
      if (v !== undefined && v !== null) {
        v = '' + v
        return v.substr(4, 2) + '/' + v.substr(0, 4)
      } else return '-'
    },
    get_chuky () {
      let v = this.params.p_kycuoc
      if (v !== undefined && v !== null) {
        v = '' + v
        return v.substr(6, 2)
      } else return '-'
    },
    f_phieu_id () {
      return this.currentPhieuTTRow !== null ? this.currentPhieuTTRow.PHIEU_ID : null
    },
    f_ngay_tt () {
      return moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')
    },
    f_kycuoc () {
      return this.params.p_kycuoc
    },
    f_lantra () {
      return this.currentPhieuTTRow !== null ? this.currentPhieuTTRow.LANTRA : null
    },
    f_ghichu () {
      return this.currentPhieuTTRow !== null ? this.currentPhieuTTRow.GHICHU : null
    },
    p_ngay_ttoan: {
      get () {
        return this.params.p_ngay_ttoan
      },
      set (value) {
        this.params.p_ngay_ttoan = value
      }
    }
  },
  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          maxDate: moment().endOf('month').toDate(),
          minDate: moment().startOf('month').toDate()
        }
      },
      params: {
        p_ngay_ttoan: new Date(),
        p_kycuoc: null,
        p_thungan_id: null,
        p_ma_thungan: null,
        p_ten_thungan: null,
        p_nhom_httt: NHOM_HTTT.GACHNO_DAILY
      },
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      footerCount: function () {
        return { template: Vue.component('countTemplate', {
          template: `<span>{{data.Count}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      columnsNVTC: [
        {fieldName: 'MANV_TC', visible: false, isPrimaryKey: true},
        {fieldName: 'TEN_NVTC', headerText: 'Nhân viên', allowFiltering: true, width: 200},
        {fieldName: 'TONGPHIEU', headerText: 'Tổng phiếu', allowFiltering: true, format: 'N0', type: 'number', width: 130},
        {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, format: 'N0', type: 'number', width: 130}
      ],
      sumColumnsNVTC: [
        {field: 'TEN_NVTC', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
        {field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      resultDSNVTC: [],
      pageinfoNVTC: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },

      columnsLuotTT: [
        {fieldName: 'LANTRA', visible: false, isPrimaryKey: true},
        {fieldName: 'LUOT_TT', headerText: 'Lượt TT', allowFiltering: true},
        {fieldName: 'TONGPHIEU', headerText: 'Tổng phiếu', allowFiltering: true, format: 'N0', type: 'number'},
        {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, format: 'N0', type: 'number'}
      ],
      sumColumnsLuotTT: [
        {field: 'LUOT_TT', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
        {field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      resultDSLuotTT: [],
      pageinfoLuotTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },

      columnsPhieuTT: [
        {fieldName: 'STT', headerText: 'STT', allowFiltering: true, width: 50, allowEditing: false},
        {fieldName: 'NGAY_TT', headerText: 'Ngày trả', allowFiltering: true, textAlign: 'Right', width: 100, allowEditing: false},
        {fieldName: 'SOPHIEU', headerText: 'Số phiếu', allowFiltering: true, textAlign: 'Right', width: 100, allowEditing: false},
        {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, textAlign: 'Left', width: 130, allowEditing: false},
        {fieldName: 'MATB_DD', headerText: 'Mã đại diện', allowFiltering: true, textAlign: 'Left', width: 120, allowEditing: false},
        {fieldName: 'TIEN_TRA', headerText: 'Tiền trả', allowFiltering: true, format: 'N0', type: 'number', textAlign: 'Right', width: 100, allowEditing: false},
        {fieldName: 'SO_HD', headerText: 'Số HĐ', allowFiltering: true, textAlign: 'Right', width: 100, allowEditing: false},
        {fieldName: 'TEN_TT', headerText: 'Tên TT', allowFiltering: true, width: 200, allowEditing: false},
        {fieldName: 'MANV_TC', headerText: 'Mã NVTC', allowFiltering: true, width: 80, allowEditing: false},
        {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', allowFiltering: true, width: 200, allowEditing: false},
        {fieldName: 'NGUOI_CN', headerText: 'Người gạch', allowFiltering: true, width: 150, allowEditing: false},
        {fieldName: 'CHUNGTU', headerText: 'Chứng từ', allowFiltering: true, width: 80, allowEditing: false},
        {fieldName: 'THUNGAN', headerText: 'Thu ngân', allowFiltering: true, width: 130, allowEditing: false},
        {fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true, textAlign: 'Right', width: 130, allowEditing: false},
        {fieldName: 'HDDT', headerText: 'HĐĐT', allowFiltering: true, width: 80, allowEditing: false},
        {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, width: 100, allowEditing: true},
        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true, width: 100, allowEditing: false}
      ],
      resultDSPhieuTT: [],
      pageinfoPhieuTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      currentNVTCRow: null,
      currentLuotTTRow: null,
      currentPhieuTTRow: null
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getKyHoaDonHienTai'
    ]),
    async openDialog () {
      if (this.kycuoc !== null) {
        this.p_default_kycuoc = this.kycuoc
        this.params.p_kycuoc = this.kycuoc
        this.getDSDaiLy()
      } else {
        await this.getKyHoaDonHienTai()
        if (this.kyHoaDonHienTai !== undefined) {
          this.params.p_kycuoc = this.kyHoaDonHienTai.KYCUOC
          this.getDSDaiLy()
        }
      }

      if (this.ngay_tt !== undefined &&
        this.ngay_tt !== null) {
        this.params.p_ngay_ttoan = moment(this.ngay_tt, 'DD/MM/YYYY').toDate()
      }
      this.$refs.dlgTraCuuPhieuThanhToanTheoNgay.show()
    },

    closeDialog () {
      this.$refs.dlgTraCuuPhieuThanhToanTheoNgay.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },
    onDialogOpen () {

    },
    clearForm () {

    },
    clearResult () {

    },
    async getDSDaiLy () {
      try {
        let postData = {
          kyCuoc: this.params.p_kycuoc,
          ngayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')
        }

        this.clearResuls()
        // console.log(postData)
        await GachNoAPI.getDanhSachNVTCTheoTuyen(this.axios, postData).then(
          (res) => {
            // console.log(res)
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.resultDSNVTC = res.data.data
              this.pageinfoNVTC.totalElement = res.data.data.length
              // this.params.p_nvtc_options = res.data.data
              // this.params.p_nvtc = res.data.data[0].TUYENTHU_ID
            } else {

            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async getDSLuotTra (maNVTC) {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        ngayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        maNVTC: maNVTC

      }
      this.resultDSLuotTT = []
      this.pageinfoLuotTT.page = 0
      this.pageinfoLuotTT.totalElement = 0

      try {
        await GachNoTuyenThuAPI.getDSLuotTra(this.axios, postData).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.resultDSLuotTT = res.data.data
              this.pageinfoLuotTT.totalElement = res.data.data.length
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async getDSPhieuTT (lanTra, maNVTC, htttId) {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        lanTra: lanTra,
        ngayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        maNVTC: maNVTC,
        htttId: htttId
      }
      this.resultDSPhieuTT = []
      this.pageinfoPhieuTT.page = 0
      this.pageinfoPhieuTT.totalElement = 0

      try {
        await GachNoTuyenThuAPI.getDSPhieuTT(this.axios, postData).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.resultDSPhieuTT = res.data.data
              this.pageinfoPhieuTT.totalElement = res.data.data.length
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },

    clearResuls () {
      this.resultDSNVTC = []
      this.resultDSLuotTT = []
      this.resultDSPhieuTT = []
      this.currentNVTCRow = null
      this.currentLuotTTRow = null
      this.pageinfoNVTC.page = 0
      this.pageinfoNVTC.totalElement = 0
      this.pageinfoLuotTT.page = 0
      this.pageinfoLuotTT.totalElement = 0
      this.pageinfoPhieuTT.page = 0
      this.pageinfoPhieuTT.totalElement = 0
    },

    getDanhSach () {
      this.getDSDaiLy()
    },
    updateNgayThanhToan () {
      if (this.currentPhieuTTRow === null) {
        this.$toast.error('Chưa chọn phiếu để cập nhật')
        return false
      }
      this.$refs.dlgDoiNgayThanhToan.openDialog()
    },
    confirmDelete: function () {
      let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()
      let arrKeys = []
      selected.forEach((item) => {
        arrKeys.push(item.THUEBAO_ID)
      })

      selected.forEach((item, index) => {
        /** */
        try {
          let postData = {
            pKyCuoc: this.params.p_kycuoc,
            pPhieuId: item.PHIEU_ID,
            pMayHuy: '-',
            pNguoiHuy: this.$auth.getUserName()
          }
          GachNoAPI.deletePhieuThanhToan(this.axios, postData).then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000') {
                this.$toast.success('Xóa phiếu' + item.PHIEU_ID + ' thành công')
              } else { this.$toast.error(response.data.message) }
            }
          )
        } catch (error) {
          this.$toast.error(error)
        }
        /** */
      })
      DialogObj.hide()
      //      this.

      this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)
    },
    deletePhieuThu () {
      let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if (selected === null || selected.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu cần xóa.')
        return
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có thật sự muốn xóa " + selected.length + ' phiếu thanh toán đang được chọn không ?</div>',
        okButton: { text: 'Đồng ý', click: this.confirmDelete },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },
    editThanhToan () {
      if (this.currentPhieuTTRow === null) {
        this.$toast.error('Chưa chọn phiếu để cập nhật')
        return false
      }
      this.$refs.gridDSPhieuTT.$refs.grid.startEdit()
    },
    editLuot () {
      if (this.currentPhieuTTRow === null) {
        this.$toast.error('Chưa chọn phiếu để cập nhật')
        return false
      }
      this.$refs.dlgSuaThongTinLuotThanhToan.openDialog()
    },
    printPhieuThu () {

    },
    exportExcel () {
      if (this.resultDSPhieuTT.length <= 0) {
        this.$toast.error('Không có dữ liệu để xuất file')
      }
      let excelExportProperties = {
        dataSource: this.resultDSPhieuTT
      }
      this.$refs.gridDSPhieuTT.excelExport(excelExportProperties)
    },
    syncVNP () {

    },
    /* */
    RowChangedNVTC (e) {
      this.currentNVTCRow = e
      this.getDSLuotTra(e.MANV_TC)
    },
    RowChangedLuotTT (e) {
      this.currentLuotTTRow = e
      this.getDSPhieuTT(e.LANTRA, this.currentNVTCRow.MANV_TC, e.HTTT_ID)
    },
    RowChangedPhieuTT (e) {
      this.currentPhieuTTRow = e
    },
    updateNgayTToanHandler (e) {
      if (e !== undefined && e !== 0) {
        this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)
      }
    },
    actionComplete (args) {
      // console.log(args)
      if (args.action === 'edit' && args.requestType === 'save') {
        let postData = {
          phieuId: args.data.PHIEU_ID,
          kyCuoc: this.params.p_kycuoc,
          ghiChu: args.data.GHICHU
        }
        this.loading(true)
        GachNoAPI.updateGhiChuPhieuTra(this.axios, postData)
          .then(
            (res) => {
              if (res.data.error_code === 'BSS-00000000'
              ) {
                this.$toast.success('Cập nhật ghi chú thành công')
                this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)
              }
            }
          )
          .catch(err => { this.$toast.error(err.data.message_detail) })
          .finally(() => { this.loading(false) })
      }
    }
  }
})
</script>
