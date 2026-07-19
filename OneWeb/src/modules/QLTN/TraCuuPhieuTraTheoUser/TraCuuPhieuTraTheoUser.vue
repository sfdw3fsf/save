<template src='./TraCuuPhieuTraTheoUser.html'></template>
<style scoped src='./TraCuuPhieuTraTheoUser.scss'></style>
<script>

import Vue from 'vue'
import { mapActions, mapState } from 'vuex'
import Calculator from '@/components/Calculator'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc.vue'
import EventBus from '@/utils/eventBus'
import TraCuuAPI from '../api/TraCuuAPI'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'

// import PopupThayDoiNgayThanhToan from '../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiNgayThanhToan'
import SuaNgayTT from '../TraCuuPhieuThuTrongNgayCuaNVTC/components/SuaNgayTT'
import PopupThayDoiThongTinThanhToan from '../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiThongTinThanhToan'
import PopupInPhieuThu from '../components/PopupInXoaSuaPhieuThanhToan/PopupInPhieuThu'
import PopupXacNhanXoaPhieu from '../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'
import {previewPrint} from '@/utils/util'
import { currency } from '@/filters/currency'
import { DialogUtility } from '@syncfusion/ej2-popups'

import {
  NHOM_HTTT
} from '@/const/enums'

Vue.use(DialogUtility)
let DialogObj
export default {
  components: {
    appCalculator: Calculator,
    appKyCuoc: KyCuoc,
    // PopupThayDoiNgayThanhToan,
    SuaNgayTT,
    PopupThayDoiThongTinThanhToan,
    PopupInPhieuThu,
    PopupXacNhanXoaPhieu
  },
  name: 'TraCuuPhieuTraTheoUser',
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    EventBus.$on('nguoiCapNhatChange', this.nguoiCNChangeHandler)
    EventBus.$on('htttChange', this.htttChangeHandler)
    if (this.$route.query.httt_id !== undefined) this.params.p_httt_id = this.$route.query.httt_id
    this.getThamSoGachNo()
    // check quyền di động
    if (this.$auth.getMaCCBS() !== null) {
      this.config.p_quyen_dd = true
      this.getTSMacDinhAdmin('GACHNO_CHUAN')
    }

    await this.getKyHoaDonHienTai()
    if (this.kyHoaDonHienTai !== undefined) {
      this.config.p_kyhd_hientai = this.kyHoaDonHienTai.KYCUOC
    }
    await this.getKiemTraMatKhau()
    // this.onCreate()

    // let config = this.getDSThamSoMacDinh()
  },
  mounted () {
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed: {
    ...mapState('qltnCommon', [
      'kyHoaDonHienTai'
    ]),
    f_phieu_id () {
      return this.selectedRow !== null ? this.selectedRow.PHIEU_ID : null
    },
    f_ngay_tt () {
      return this.selectedRow !== null ? this.selectedRow.NGAY_TT : null
    },
    f_kycuoc () {
      return moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no
    },
    f_dengay_enabled () {
      return this.params.p_chk_dengay_thanhtoan === '1'
    },
    label_kycuoc () {
      /*
      let nextMonth = moment(this.params.p_kyhoadon).add(1, 'M')
      let startDate = moment(nextMonth).startOf('month').format('DD/MM/YYYY')
      let endDate = moment(nextMonth).endOf('month').format('DD/MM/YYYY');
      */
      if (this.params.p_kyhoadon !== null) {
        return 'Kỳ cước ' + moment(this.params.p_kyhoadon).format('MM/yyyy') + ', chu kỳ ' + this.params.p_chuky_no + ' tính từ ngày ' + moment(this.config.p_ngay_bd_gach).format('DD/MM/yyyy') + ' - đến ngày ' + moment(this.config.p_ngay_kt_gach).format('DD/MM/yyyy')
      } else return ''
    }
  },
  data () {
    return {
      splitterObj: null,
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      config: {
        dateConfig: {
          default: new Date(),
          max: new Date(),
          format: 'dd/MM/yyyy'
        },
        p_kyhd_hientai: null,
        p_ngay_bd_gach: null,
        p_ngay_kt_gach: null,
        p_ngayxoa_lui: -1, // Số lượng ngày được phép xóa
        p_ngayxoa_lui_tt: -1, // Số lượng ngày được phép xóa
        p_quyen_dd: false, // quyền gạch di động
        p_gachno_chuan: null // tham số gạch nợ chuẩn
      },
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      statusTemplate: function () {
        return {
          template: Vue.component('statusTemplate', {
            template: `<span :class="{'e-icons': true, '': data.TRANGTHAI == -1, 'grid-status-0': data.TRANGTHAI == 0, 'grid-status-1': data.TRANGTHAI == 1}"></span>`,
            data: function () { return { data: {} } }
          })
        }
      },
      XPParams: {},
      p_kiemtra_mk: true,
      params: {
        p_kyhoadon: null,
        // p_kycuoc: null,
        p_chuky_no: null,
        p_tungay_thanhtoan: new Date(),
        p_denngay_thanhtoan: new Date(),
        p_chk_dengay_thanhtoan: '1',
        p_tongtra: 0,
        p_ma_ttoan: null,
        p_httt_id: null,
        p_loai_tien_id: null,
        p_nguoi_cn_id: null
      },
      selectedRows: [],
      isCalculatorShow: false,
      columnsDSUsers: [
        {fieldName: 'NGUOIDUNG_ID', isPrimaryKey: true, visible: false},
        {fieldName: 'LOAITIEN_ID', visible: false},
        {fieldName: 'STT', headerText: 'STT', allowFiltering: false, width: 50},
        {fieldName: 'THUNGAN', headerText: 'Thu ngân', allowFiltering: true, width: 200},
        {fieldName: 'TONGTIEN', headerText: 'Tiền thu', allowFiltering: true, format: 'N0', type: 'number', width: 100},
        {fieldName: 'TONGPHIEU', headerText: 'Tổng phiếu', allowFiltering: true, format: 'N0', type: 'number', width: 100},
      ],
      // sumColumnsDSUsers: [
      //   {field: 'STT', type: 'Count', format: 'N0', footerTemplate: this.footerSum},
      //   {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
      //   {field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
      // ],
      // sumColumnsDSHTTT: [
      //   {field: 'STT', type: 'Count', format: 'N0', footerTemplate: this.footerSum},
      //   {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
      //   {field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
      // ],
      columnsDSHTTT: [
        {fieldName: 'HTTT_ID', isPrimaryKey: true, visible: false},
        {fieldName: 'STT', headerText: 'STT', allowFiltering: false, width: 50},
        {fieldName: 'HINHTHUC', headerText: 'Hình thức TT', allowFiltering: true, width: 200},
        {fieldName: 'TONGTIEN', headerText: 'Tiền thu', allowFiltering: true, format: 'N0', type: 'number', width: 100, cssClass: 'tien-tra'},
        {fieldName: 'TONGPHIEU', headerText: 'Tổng phiếu', allowFiltering: true, format: 'N0', type: 'number', width: 100}
      ],
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
          data () { return { data: { data: {} } } }
        })
        }
      },
      pageinfoDSUsers: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      pageinfoDSHTTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      sumColumnsDSPhieuTT: [
        {field: 'TIEN_TRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
      ],
      columnsDSPhieuTT: [],
      pageinfoDSPhieuTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      listUserResult: [],
      dsUserKey: 0,
      listHTTTResult: [],
      dsHTTTKey: 0,
      listPhieuThanhToanResult: [],
      selectedRow: null,
      selectedTotal: 0,
      total: 0,
      keyDSPhieuTT: 1,
      phieuSelected: []
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getKyHoaDonHienTai'
    ]),
    customAggregate () {
      let selected = this.listPhieuThanhToanResult.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TIEN_TRA, 0)
      let total = this.listPhieuThanhToanResult.reduce((a, b) => +a + +b.TIEN_TRA, 0)
      return currency(selected) + '/' + currency(total)
    },
    aggCountUser () {
      return currency(this.listUserResult.length)
    },
    customCountAgg () {
      let selected = this.listPhieuThanhToanResult.filter(x => x._CHECK === true).length
      return currency(selected) + '/' + currency(this.listPhieuThanhToanResult.length)
    },
    aggSumTONGTIENUser () {
      let total = this.listUserResult.reduce((a, b) => +a + +b.TONGTIEN, 0)
      return currency(total)
    },
    aggSumTONGPHIEUUser () {
      let total = this.listUserResult.reduce((a, b) => +a + +b.TONGPHIEU, 0)
      return currency(total)
    },
    aggCountHTTT () {
      return currency(this.listHTTTResult.length)
    },
    aggSumTONGTIENHTTT () {
      let total = this.listHTTTResult.reduce((a, b) => +a + +b.TONGTIEN, 0)
      return currency(total)
    },
    aggSumTONGPHIEUHTTT () {
      let total = this.listHTTTResult.reduce((a, b) => +a + +b.TONGPHIEU, 0)
      return currency(total)
    },
    getTSMacDinhAdmin (key) {
      CommonsAPI.getDSThamSoMacDinhAdmin(this.axios, key)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined && res.data.data.length > 0) {
            this.config.p_gachno_chuan = res.data.data[0].GIATRI
          } else {
            this.config.p_gachno_chuan = null
            this.$toast.error('Không lấy được phương thức gạch di động')
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.$toast.error('Không lấy được phương thức gạch di động')
        })
        .finally(() => {})
    },
    async getDSThamSoMacDinh (postData) {
      try {
        await CommonsAPI.getDSThamSoMacDinh(this.axios, postData)
          .then((res) => {
            return res
          })
      } catch (error) {
        return false
      }
    },
    async getKiemTraMatKhau () {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'KHONG_KT_MATKHAU_XOA_GACHNO'})
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          let ts = Number(data.data[0].TEN_TS)
          if (ts === 1) {
            this.params.p_kiemtra_mk = false
          }
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    getThamSoGachNo: function () {
      GachNoAPI.getThamSoGachNo(this.axios)
        .then(
          (response) => {
            // console.log(response)
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
              try {
                this.config.p_ngayxoa_lui_tt = parseInt(response.data.data[0].NGAY_GL)
              } catch (err) {
                this.config.p_ngayxoa_lui_tt = -1
              }
              try {
                this.config.p_ngayxoa_lui = parseInt(response.data.data[0].NGAY_XL)
              } catch (err) {
                this.config.p_ngayxoa_lui = -1
              }
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },
    onCreate () {
      this.splitterObj = new Splitter ({
        paneSettings: [ { size: '50%', min: '20%' }, { size: '50%', min: '20%' } ],
        orientation: 'Vertical'
      })
      this.splitterObj.appendTo('#vertical_splitter')
    },
    clearResult () {
      this.listUserResult = []
      this.pageinfoDSUsers.page = 0
      this.pageinfoDSUsers.totalElement = 0
      this.params.p_tongtra = 0

      this.listHTTTResult = []
      this.pageinfoDSHTTT.page = 0
      this.pageinfoDSHTTT.totalElement = 0

      this.listPhieuThanhToanResult = []
      this.pageinfoDSPhieuTT.page = 0
      this.pageinfoDSPhieuTT.totalElement = 0
    },
    getDanhSach: function () {
      this.clearResult()
      this.getTongTienUser()
      // this.getTongTienHinhThucThanhToanUser()
      // this.getChiTietPhieuThanhToanUser()
    },
    getTongTienUser: function () {
      if (this.validateData()) return false

      let postData = {
        ngayTT: moment(this.params.p_tungay_thanhtoan).format('DD/MM/YYYY'),
        denNgay: (this.params.p_chk_dengay_thanhtoan === '1') ? moment(this.params.p_denngay_thanhtoan).format('DD/MM/YYYY') : null,
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        nguoiCN: this.$auth.getUserName(),
        nguoiDungId: this.$auth.getNguoiDungID(),
        tinhThuHo: null
      }

      // this.loading(true)

      TraCuuAPI.getTongTienUser(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.listUserResult = response.data.data
              this.dsUserKey++
              this.pageinfoDSUsers.totalElement = response.data.data.length
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          //  this.loading(false)
        })
    },
    validateData: function () {
      let error = false
      if (this.params.p_kyhoadon === null) {
        error = true
        this.$toast.error('Vui lòng chọn kỳ hóa đơn')
      }
      if (this.params.p_tungay_thanhtoan === null) {
        this.$toast.error('Vui lòng chọn ngày thanh toán')
        error = true
      }
      if (this.params.p_chk_dengay_thanhtoan === '1' && this.params.p_denngay_thanhtoan === null) {
        this.$toast.error('Vui lòng chọn thanh toán đến ngày')
        error = true
      }
      return error
    },
    getTongTienHinhThucThanhToanUser: function () {
      if (this.validateData()) return false
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        nguoiCN: this.params.p_nguoi_cn_id,
        ngayTT: moment(this.params.p_tungay_thanhtoan).format('DD/MM/YYYY'),
        denNgay: (this.params.p_chk_dengay_thanhtoan === '1') ? moment(this.params.p_denngay_thanhtoan).format('DD/MM/YYYY') : null,
        tinhThuHo: null
      }

      this.loading(true)

      this.listHTTTResult = []
      this.pageinfoDSHTTT.page = 0
      this.pageinfoDSHTTT.totalElement = 0     

      TraCuuAPI.getTongTienHinhThucThanhToanUser(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.listHTTTResult = response.data.data
              this.dsHTTTKey++
              this.pageinfoDSHTTT.page = 0
              this.pageinfoDSHTTT.totalElement = response.data.data.length
            }
          }
        )
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    getChiTietPhieuThanhToanUser: function () {
      if (this.validateData()) return false

      let postData = {
        loaiTien: this.params.p_loai_tien_id,
        ngayTT: moment(this.params.p_tungay_thanhtoan).format('DD/MM/YYYY'),
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTT: this.params.p_ma_ttoan,
        maTB: null,
        htttId: this.params.p_httt_id,
        denNgay: (this.params.p_chk_dengay_thanhtoan === '1') ? moment(this.params.p_denngay_thanhtoan).format('DD/MM/YYYY') : null,
        tinhThuHo: null,
        nguoiCN: this.params.p_nguoi_cn_id
      }

      /*
      let postData = {
        loaiTien : 1,
        ngayTT : moment(this.params.p_tungay_thanhtoan).format('DD/MM/YYYY'),
        kyCuoc : moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTT : 'HPG-09-372343',
        maTB : null,
        htttId : 113,
        denNgay : (this.params.p_chk_dengay_thanhtoan === '1') ? moment(this.params.p_denngay_thanhtoan).format('DD/MM/YYYY') : null,
        nguoiDungId : null,
        tinhThuHo : null,
        phanVungId : 26
      }
*/
      this.loading(true)
      this.listPhieuThanhToanResult = []
      this.pageinfoDSPhieuTT.page = 0
      this.pageinfoDSPhieuTT.totalElement = 0
      this.total = 0
      this.selectedTotal = 0
      // this.selectedRow = null

      TraCuuAPI.getChiTietPhieuThanhToanUser(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              let obj = [...response.data.data]
              this.listPhieuThanhToanResult = obj.map(x => ({ ...x, _CHECK: false }))
              this.pageinfoDSPhieuTT.page = 0
              this.pageinfoDSPhieuTT.totalElement = response.data.data.length
              this.keyDSPhieuTT++
              this.total = response.data.data.reduce((acc, curr) => {
                return acc = acc + Number(curr.TIEN_TRA)
              }, 0)
            }
          }
        )
        .catch(error => {
          if (error.data.message_detail !== undefined) this.$toast.error(error.data.message_detail)
          else console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    checkDataOnGrid (type) {
      let action = ''
      if (type === 0) action = 'cập nhật'
      else if (type === 1) action = 'xóa'

      const selectedRows = this.listPhieuThanhToanResult.filter(x => x._CHECK === true)

      if (selectedRows.length <= 0) {
        this.$toast.error(`Chưa có danh sách phiếu thanh toán để ${action}!`)
        return false
      }
      if (selectedRows.length <= 0) {
        this.$toast.error(`Chưa chọn phiếu thanh toán để ${action}!`)
        return false
      }
      let s = moment(this.params.p_kyhoadon).format('YYYYMMDD')     
      if (this.config.p_kyhd_hientai !== Number(s) && this.config.p_ngayxoa_lui_tt === -1) {
        this.$toast.error('Bạn không được ${action} phiếu của kỳ hóa đơn khác.')
        return false
      }
      return true
    },
    editPhieu: function () {
      // this.$refs.dlgDoiNgayThanhToan.openDialog()
      // this.$refs.dlgThayDoiNgayTT.openDialog()
      if (this.checkDataOnGrid(0)) {
        const selectedRows = this.listPhieuThanhToanResult.filter(x => x._CHECK === true)
        
        this.phieuSelected = [...selectedRows]

        this.$refs.dlgThayDoiNgayTT.openDialog()
      }
    },
    deletePhieu (e) {
      this.phieuSelected = this.listPhieuThanhToanResult.filter(x => x._CHECK === true) // this.$refs.gridDSPhieuTT.getSelectedRecords()

      if (this.phieuSelected.length <= 0) {
        this.$toast.error('Chưa chọn phiếu để xóa.')
        return false
      }

      let s = moment(this.params.p_kyhoadon).format('YYYYMMDD')     
      if (this.config.p_kyhd_hientai !== Number(s) && this.config.p_ngayxoa_lui_tt === -1) {
        this.$toast.error('Bạn không được xóa phiếu của kỳ hóa đơn khác.')
        return false
      }

      /*

      let tongTien = 0;
      let tongPhieu = this.selectedRows.length;
      this.selectedRows.forEach(element => {
        tongTien += Number(element.TIEN_TRA);
      });
      this.XPParams = {
        tongTien: currency(tongTien),
        tongPhieu: currency(tongPhieu)
      };
      */

      this.$refs.refXoaPhieuTT.openDialog()
    },
    /*
    confirmDelete(e){

      let postData = {
        "pKyCuoc": moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        "pPhieuId": this.selectedRow.PHIEU_ID,
        "pMayHuy": "-"
      }

      this.loading(true)

      GachNoAPI.deletePhieuThu(this.axios, postData)
        .then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              this.$toast.success('Xoá phiếu ' + postData.pPhieuId + ' thành công!')
              this.clearResult()
              this.getDanhSach()
            }
          }
        )
        .catch(error => {
          $this.$toast.error(error.data.message_detail)
        })
        .finally( () => {
          DialogObj.hide()
          this.loading(false)
        })

    },
    */
    exportExcel () {
      if (this.listPhieuThanhToanResult.length <= 0) {
        this.$toast.error('Chưa có dữ liệu để xuất excel!')
      }
      
      this.$refs.gridDSPhieuTT.$refs.grid.getColumns()[0].visible = false
      this.$refs.gridDSPhieuTT.$refs.grid.getColumns()[1].visible = false
      this.$refs.gridDSPhieuTT.$refs.grid.getColumns()[27].visible = true
      this.$refs.gridDSPhieuTT.$refs.grid.getColumns()[28].visible = false

      let dataGridObj = this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances
      let columns = dataGridObj.columnModel
      // console.log(columns)
      let excelExportProperties = {
        fileName: 'DSPhieuThanhToan.xlsx',
        dataSource: this.listPhieuThanhToanResult,
        columns: columns
      }
      this.$refs.gridDSPhieuTT.$refs.grid.excelExport(excelExportProperties)
    },

    printPhieu () {
      this.selectedRows = this.listPhieuThanhToanResult.filter(x => x._CHECK === true)

      if (this.selectedRows.length <= 0) {
        this.$toast.error('Chưa chọn phiếu để in.')
        return false
      }

      let account = this.$auth.getUserName()

      this.selectedRows.forEach((item) => {
        if (item.NGUOI_CN.toUpperCase() !== account.toUpperCase()) {
          this.$toast.error('Bạn không in phiếu thanh toán của user ' + item.NGUOI_CN)
          return false
        }
      })

      // this.$toast.error('Chức năng In đang được phát triển. Vui lòng thử lại sau.')
      this.$refs.refPopupInPhieuThu.openDialog()
    },
    async syncVNP () {
      // this.$toast.error('Chức năng đồng bộ VNP đang được phát triển. Vui lòng thử lại sau.')
      if (!this.config.p_quyen_dd) {
        this.$toast.error('Đồng bộ VNP chưa được thiết lập bởi tỉnh.')
        return
      }

      if (this.listPhieuThanhToanResult.length <= 0) {
        this.$toast.error('Không có dữ liệu phiếu trả đồng bộ.')
        return
      }

      let selectedRows = this.listPhieuThanhToanResult.filter(x => x._CHECK === true)

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa check chọn phiếu trả để đồng bộ VNP')
        return false
      }

      let t = selectedRows.filter(x => (x.PHIEU_ID_NEO === null && (x.TRANGTHAI === -1 || x.TRANGTHAI === 1)))

      if (t.length > 0) {
        this.$toast.error('Bạn không được chọn phiếu đã đồng bộ hoặc phiếu không phải dịch vụ di động')
        return
      }

      let user = this.$auth.getUserName()
      let t_user = selectedRows.filter(x => x.PHIEU_ID_NEO !== null && x.PHIEU_ID_NEO !== 0 && x.TRANGTHAI !== -1 && x.TRANGTHAI !== 1 && x.NGUOI_CN !== user)

      if (t_user.length > 0) {
        this.$toast.error('Bạn không được đồng bộ phiếu của user vnp khác')
        return
      }

      let promises = []
      selectedRows.forEach((item) => {
        let postData = {
          'kieu': 1,
          'kyCuoc': this.params.p_kycuoc,
          'phieuId': item.PHIEU_ID
        }
        promises.push(this.doSyncCCBS(postData))
      })
      this.loading(true)
      const res = await Promise.all(promises)
      this.loading(false)
      let ok = res.filter(x => x === true)
      let failed = res.filter(x => x !== true)

      if (ok.length === promises.length) {
        this.$toast.success('Đã đồng bộ thành công ' + ok.length + ' phiếu.')
        this.doSearch()
      } else {
        this.$toast.error('Đã phát sinh lỗi khi đồng bộ. Tổng số phiếu lỗi: ' + failed.length)
        this.$toast.error(failed.join('\r\n'))
      }

      // let postData = {
      //   'nhomHTTT': NHOM_HTTT.GACHNO_DAILY,
      //   'data': []
      // }

      // selectedRows.forEach((item) => {
      //   postData.data.push(
      //     {
      //       'phieuIdNeo': item.PHIEU_ID_NEO,
      //       'trangThai': item.TRANGTHAI,
      //       'phieuId': item.PHIEU_ID,
      //       'nguoiCN': user,
      //       'httt': item.HTTT_ID,
      //       'kyCuoc': this.params.p_kycuoc,
      //       'maTT': item.MA_TT
      //     }
      //   )
      // })

      // this.loading(true)

      // GachNoAPI.syncCCBS(this.axios, postData)
      //   .then((response) => {
      //     this.$toast.success('Đồng bộ VNP thành công.')
      //   })
      //   .catch((error) => {
      //     this.$toast.error(error.data.message_detail)
      //   })
      //   .finally(() => { this.loading(false) })
    },

    async doSyncCCBS (postData) {
      try {
        const res = await GachNoAPI.syncCCBS(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000') {
          if (data.data !== undefined && data.data[0].phieuIdNeo !== null) return true
          else return `Phiếu ID ${postData.PHIEU_ID} : ${data.message_detail}`
        }
        return false
      } catch (error) {
        return false
      } finally {}
    },
    printPhieuThuHandler (e) {
      console.log(e)
    },
    chukycuocChangeHandler (e) {
      this.params.p_kyhoadon = e.p_kyhoadon
      this.params.p_chuky_no = e.p_chuky_no

      let nextMonth = moment(this.params.p_kyhoadon).add(1, 'M')
      this.config.p_ngay_bd_gach = moment(nextMonth).startOf('month').toDate()
      this.config.p_ngay_kt_gach = moment(nextMonth).endOf('month').toDate()
    },
    dsUsersRowChanged (e) {
      if (e === null) return
      this.params.p_nguoi_cn_id = e.NGUOIDUNG_ID
      this.params.p_loai_tien_id = e.LOAITIEN_ID
      this.params.p_tongtra = e.TONGTIEN
      this.getTongTienHinhThucThanhToanUser()
    },
    dsHTTTRowChanged (e) {
      if (e === null) {
        this.listPhieuThanhToanResult = []
        this.pageinfoDSPhieuTT.page = 0
        this.pageinfoDSPhieuTT.totalElement = 0
        return
      }
      this.params.p_httt_id = e.HTTT_ID
      this.getChiTietPhieuThanhToanUser()
    },
    dsPhieuTTRowChanged (e) {
      this.selectedRow = e
    },
    /*
    async deleteProcess(args) {
      let phieuCount = 0;
      this.loading(true);
      try {
        let apiBody = {
          pKyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
          pMayHuy: '-',
          pNguoiHuy: this.$auth.getUserName(),
          pPhieuId: 0,
          pPassword: args.password
        };
        for (let item of this.selectedRows) {
          apiBody.pPhieuId = item.PHIEU_ID
          let result = await this.deletePhieuThanhToan(apiBody);
          if (result) {
            phieuCount ++;
          }
          else {
            this.$toast.error('Không thể xóa phiếu thanh toán có mã TT:' + item.MA_TT);
          }
        };

        if (phieuCount === this.phieuSelected.length) {
          this.$toast.success('Xóa phiếu thanh toán thành công!');
        };
        this.selectedRows = []
        await this.getDanhSach();
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }
    },
    async deletePhieuThanhToan(apiBody) {
      try {
        let response = await XuLyPhieuApi.deletePhieuThanhToan(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        console.log(error);
        return false;
      }
      finally {}
    },
    */
    async updateDatePhieuThuHandler (flag) {
      if (flag) { // thành công
        this.getChiTietPhieuThanhToanUser()
      }
    },
    updateNgayTToanHandler (e) {
      if (e !== undefined && e !== 0) {
        this.getChiTietPhieuThanhToanUser()
      } else {
        // this.$toast.error('Đã phát sinh lỗi khi cập nhật ngày thanh toán. Vui lòng thử lại.')
      }
    },
    async deletePhieuThuHandler (flag) {
      if (flag) { // xóa thành công
        // this.pageinfoDSPhieuTT.currentRow = 0
        this.getDanhSach()
      }
    },
    updateSeriPhieuThuHandler (flag) {
      if (flag) { // xóa thành công
        this.getDanhSach()
      }
    },
    async acceptPrintPhieuThuHandler (e) {
      // let phieu_id = this.selectedRow.PHIEU_ID
      let selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()
      let phieuCount = 0
      let seri = parseInt(e.so_seri)

      if (e.is_print === 1) {
        let dsPhieuCanIn = []
        // this.dsPhieuCanIn.push(this.phieuSelected[0].PHIEU_ID)
        selectedRows.forEach((item) => {
          dsPhieuCanIn.push(item.PHIEU_ID)
        })

        // this.$refs.refPrintPreview.openDialog();
        let postData = {
          kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
          dsPhieu: dsPhieuCanIn
        }
        this.loading(true)
        try {
          const baseUrl = process.env.API
          this.axios.post(baseUrl + '/web-thuno/api/thu-no/gach-no/bien-nhan', postData, {responseType: 'blob'})
            .then( (response) => {
              previewPrint(response.data)
            })
        } catch (err) {
          this.$toast.error(err.data.message_detail)
        } finally {
          this.loading(false)
        }
      } else {
        let apiBody = {
          'pKyCuoc': moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
          'pSeri': e.seri,
          'pQuyen': e.thung,
          'pSoSeri': 0,
          'pPhieuId': 0
        }

        for (let item of selectedRows) {
          apiBody.pPhieuId = item.PHIEU_ID
          apiBody.pSoSeri = seri + phieuCount

          let result = await this.updateSeriPhieuThu(apiBody)
          if (result) {
            phieuCount++
          } else {
            this.$toast.error('Không thể cập nhật phiếu thanh toán có mã TT:' + item.MA_TT)
          }
        };

        if (phieuCount === selectedRows.length) {
          this.$toast.success('Cập nhật phiếu thanh toán thành công!')
        };
        await this.getDanhSach()
      }
    },
    async updateSeriPhieuThu (apiBody) {
      this.loading(true)
      try {
        let response = await GachNoAPI.updateSeriThanhToan(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000' ? true : false
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },
    actionComplete (e) {
      // if (e.requestType === 'refresh') {
      //   if (this.selectedRow !== null) {
      //     let index = this.$refs.gridDSPhieuTT.$refs.grid.getRowIndexByPrimaryKey(this.selectedRow.PHIEU_ID)
      //     if (index >= 0) this.$refs.gridDSPhieuTT.$refs.grid.selectRow(index)
      //   }
      // }
    },
    checkboxPhieuTTChange (args) {
      /*
      let selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if(selectedRows.length > 0)
      {
        this.selectedTotal = selectedRows.reduce((acc, curr) => {
          return acc = Number(curr.TIEN_TRA) + acc
        }, 0)
      } else
      {
        this.selectedTotal = 0
      }

      this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances.aggregateModule.refresh()
      */
    },
    dsPhieuTTRowSelected (args) {
      this.refreshAgg()
    },
    dsPhieuTTRowDeselected (args) {
      this.refreshAgg()
    },
    refreshAgg () {
      let selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if (selectedRows.length > 0) {
        this.selectedTotal = selectedRows.reduce((acc, curr) => {
          return acc = Number(curr.TIEN_TRA) + acc
        }, 0)
      } else this.selectedTotal = 0
      this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuTTQueryCellInfo (args) {
      // console.log(args)
      if (args.column.field === 'TIEN_TRA') {
        args.cell.classList.add('red')
      }
    },
    dsPhieuTTDatabound (args) {
      // console.log('databound')
      // let obj = this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances
      // if (obj.currentViewData.length > 0) {
      //   obj.selectRowsByRange(0, obj.currentViewData.length - 1)
      // }
    },
    keyPressedHandler (args) {
      if (args.key === 'Insert') {
        this.checkAll(true)
      } else if (args.key === 'Delete') {
        this.checkAll(false)
      }
    },
    checkAll (f) {
      let temp = [...this.listPhieuThanhToanResult]
      if (f) { // true
        let filter = this.$refs.gridDSPhieuTT.getCurrentFilter()
        if (filter !== undefined && filter.fieldName !== undefined && filter.value !== '') {
          let checked = this.$refs.gridDSPhieuTT.filteredDataSource.map(x => x.PHIEU_ID)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i].PHIEU_ID)) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }        
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.listPhieuThanhToanResult = [...temp]
    },
    checkBoxChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        // for (let i = 0; i < this.results.length; i++) {
        //   this.results[i]._CHECK = args.checked
        // }
        this.checkAll(args.checked)
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowSelected (args) {
      // console.log('sel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.listPhieuThanhToanResult.find(x => x.PHIEU_ID === args.data.PHIEU_ID)
        if (f !== undefined) f._CHECK = true
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowDeselected (args) {
      // console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.listPhieuThanhToanResult.find(x => x.PHIEU_ID === args.data.PHIEU_ID)
        if (f !== undefined) f._CHECK = false
        this.refreshAggPhieu()
      }
    },
    refreshAggPhieu () {
      this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuDatabound (args) {
      // console.log('db')
      this.refreshGrid()
    },
    refreshGrid () {
      let obj = this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    }
  },
  watch: {}
}
</script>
<style>
.grid-status--1:before{
  content:'\e718';
}
.grid-status-0:before{
  content:'\e80b';
}
.grid-status-1:before{
  content:'\e718';
}
.grid-status--1{
  color: red!important;
  font-weight: bold!important;
}
.grid-status-0{
  color: rgb(202, 133, 4)!important;
  font-weight: bold!important;
}
.grid-status-1{
  color: green!important;
  font-weight: bold!important;
}
#ds-phieutt .grid-ext .e-grid .e-rowcell.e-active{
  font-weight: bold;
}
</style>
