<template src="./frmTraCuuTBBaoHong.html"></template>
<style scoped src="./frmTraCuuTBBaoHong.scss"></style>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import XLSX from 'xlsx'
import moment from 'moment'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'

export default {
  name: 'frmTraCuuTBBaoHong',
  components: { moment, DatePicker, api, frmApi, ExportDataModal, XLSX },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data() {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      ckbSendSMS: true,
      txtGhiChu: '',
      dtpNgayHenTu: '',
      dtpNgayHenDen: '',
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      gridviewDanhSach: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'ma_kh', headerText: 'Mã KH' },
          { fieldName: 'ngay_bh', headerText: 'Ngày báo hỏng' },
          { fieldName: 'ngay_ht', headerText: 'Ngày hoàn thành' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao' },
          { fieldName: 'dienthoai_lh', headerText: 'SĐT liên hệ' },
          { fieldName: 'trangthai_bh', headerText: 'Trạng thái' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ' },
          { fieldName: 'tram_vt', headerText: 'Trạm VT' },
          { fieldName: 'nguoi_cn', headerText: 'Người CN' }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      donvi_dl_id: 0,
      dichvuvt_id: 0,
      ma_tb: '',
      baohong_id: 0,
      thuebao_id: 0,
      chapnhan: false,
      cboDKTraCuu: '',
      cboDKTraCuuList: [],
      cboDichVuVT: '',
      cboDichVuVTList: [],
      dtpNgayTN: '',
      dtpDenNgay: '',
      txTextBoxTC: '',
      cboTrangThaiBH: '',
      cboTrangThaiBHList: [],
      txtMaTB: '',
      chkTrangThai: false,
      chkDVVT: false,
      chkNgayLap: false
    }
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  mounted() {
    this.dtpNgayTN = moment().format(this.dateFormat)
    this.dtpDenNgay = moment().format(this.dateFormat)
  },
  methods: {
    openDialog() {
      try {
        this.getDataFromProp()
        this.Clear()
        this.Loading = true
        Promise.all([this.frmTraCuuTBBaoHong_Load()])
          .then((r) => {
            console.log(r)
          })
          .finally((x) => {
            this.Loading = false
          })
      } catch (ex) {
        this.Loading = false
      } finally {
        this.Loading = false
        this.$refs.dialogTraCuuTBBaoHong.show()
      }
    },
    Clear() {
      this.txtMaTB = ''
      this.chkDVVT = false
      this.chkNgayLap = false
      this.txTextBoxTC = ''
      this.gridviewDanhSach.data = []
      this.gridviewDanhSach.selectedItems = []
      this.gridviewDanhSach.selectedItem = null
      this.dtpNgayTN = moment().format(this.dateFormat)
      this.dtpDenNgay = moment().format(this.dateFormat)
    },
    closeDialog() {
      let result = { chapnhan: false, dichvuvt_id: this.dichvuvt_id, baohong_id: this.baohong_id, ma_tb: this.ma_tb }
      this.$emit('closeDialog', result)
    },
    Close() {
      this.$refs.dialogTraCuuTBBaoHong.hide()
      let result = { chapnhan: this.chapnhan, dichvuvt_id: this.dichvuvt_id, baohong_id: this.baohong_id, ma_tb: this.ma_tb }
      this.$emit('closeDialog', result)
    },
    Thoat() {
      this.$refs.dialogTraCuuTBBaoHong.hide()
    },
    async frmTraCuuTBBaoHong_Load() {
      await this.lay_dk_tracuu(5)
      await this.lay_ds_dvvt()
      await this.HT_TrangThai_BaoHong_Combobox()
    },
    async HienThiDanhSach() {
      try {
        let dieukien = ''
        //int loaihd_id;
        let ttbh_id
        let ngay_tn = ''
        let den_ngay = ''

        if (this.txTextBoxTC == '') dieukien = '0'
        else {
          dieukien = this.cboDKTraCuu.replace(':values', this.txTextBoxTC)
        }

        if (this.chkDVVT) this.dichvuvt_id = parseInt(this.cboDichVuVT)
        else this.dichvuvt_id = 0

        if (this.chkTrangThai) this.ttbh_id = parseInt(this.cboTrangThaiBH)
        else this.ttbh_id = 0

        if (this.chkNgayLap) {
          ngay_tn = this.dtpNgayTN
          den_ngay = this.dtpDenNgay
        } else {
          ngay_tn = ''
          den_ngay = ''
        }
        // BaoHong_Facade baohong = new BaoHong_Facade();
        // Ds_list = baohong.Tra_cuu_tb_bh(dieukien, ttbh_id, ngay_tn, den_ngay, dichvuvt_id, donvi_dl_id);
        // gridDanhSach.DataSource = Ds_list.Tables[0];
        this.Loading = true
        let data = await this.tra_cuu_tb_bh(dieukien, this.ttbh_id, ngay_tn, den_ngay, this.dichvuvt_id, this.donvi_dl_id)
        if (data != null) {
          data.forEach(function (item) {
            if (item.ngay_ht != null && item.ngay_ht != '') {
              item.ngay_ht = moment(item.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
            }
            if (item.ngay_bh != null && item.ngay_bh != '') {
              item.ngay_bh = moment(item.ngay_bh, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
            }
          })
        }
        this.gridviewDanhSach.data = data
        if (data != null && data.length > 0) {
          await this.gridviewDanhSach_RowClicked(0, data[0])
        }
        this.Loading = false
      } catch (err) {
        this.Loading = false
        this.ShowError(`${err.message}`)
      }
    },
    async tra_cuu_tb_bh(dieukien, ttbh_id, ngay_tn, den_ngay, dichvuvt_id, donvi_dl_id) {
      let input = { dieukien: dieukien, ttbh_id: ttbh_id, ngay_tn: ngay_tn, denngay: den_ngay, dichvuvt_id: dichvuvt_id, donvi_dl_id: donvi_dl_id }
      let data = this.GetData(await frmApi.tra_cuu_tb_bh(this.axios, input))
      return data
    },
    async lay_dk_tracuu(nhom_id) {
      let input = {
        nhom_id: nhom_id
      }
      let data = this.GetData(await frmApi.lay_dk_tracuu(this.axios, input))
      this.cboDKTraCuuList = data
      if (data != null && data.length > 0) {
        this.cboDKTraCuu = data[0].dieukien
      }
      return data
    },
    async lay_ds_dvvt() {
      let input = {}
      let data = this.GetData(await frmApi.lay_ds_dvvt(this.axios, input))
      this.cboDichVuVTList = data
      if (data != null) {
        this.cboDichVuVT = data[0].dichvuvt_id
      }
      return data
    },
    async HT_TrangThai_BaoHong_Combobox() {
      // let data = this.GetData(
      //   await api.lay_dulieu_bang_theo_dieukien(this.axios, {
      //     schema: 'baohong',
      //     table_name: 'trangthai_bh',
      //     list_of_cols: 'trangthai_bh,ttbh_id',
      //     where: ``,
      //     order: ''
      //   })
      // )
      let data = this.GetData(
        await api.sp_tt_trangthai_bh(this.axios, {
          p_param: '',
          p_type: 1
        })
      )
      this.cboTrangThaiBHList = data
      if (data != null && data.length > 0) this.cboTrangThaiBH = data[0].ttbh_id
      return data
    },
    getDataFromProp() {
      if (this.params != null) {
        let keys = Object.keys(this.params)
        for (const k of keys) {
          console.log('getDataFromProp:', k, ',value:', this.params[k])
          if (this.params[k] != null && this.params[k] != undefined) this[k] = this.params[k]
        }
      }
    },
    async tsbtnTimKiem_Click() {
      try {
        this.Loading = true
        await this.HienThiDanhSach()
        this.Loading = false
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnChapNhan_Click() {
      try {
        this.Loading = true
        if (this.gridviewDanhSach.data == null || this.gridviewDanhSach.data.length <= 0) {
          this.ShowError('Không có thuê bao để chọn!')
          return
        }
        if (this.gridviewDanhSach.selectedItem == null) {
          //  this.ShowError('Không có thuê bao để chọn!')
          return
        }
        let item = this.gridviewDanhSach.selectedItem
        this.ma_tb = item.ma_tb // gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "MA_TB").ToString();
        this.dichvuvt_id = item.dichvuvt_id // Convert.ToInt32(gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "DICHVUVT_ID").ToString());
        this.baohong_id = item.baohong_id // Convert.ToInt32(gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "BAOHONG_ID").ToString());
        this.thuebao_id = item.thuebao_id //  Convert.ToInt32(gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "THUEBAO_ID").ToString());
        this.chapnhan = true
        this.Close()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnXuatFile_Click() {
      try {
        if (this.gridviewDanhSach.data != null && this.gridviewDanhSach.data.length > 0) {
          let newData = []
          let headers = this.gridviewDanhSach.headers
          for (const item of this.gridviewDanhSach.data) {
            let newItem = {}
            for (const header of headers) {
              newItem[header.headerText] = item[header.fieldName]
            }
            newData.push(newItem)
          }
          let data = XLSX.utils.json_to_sheet(newData)
          let wb = XLSX.utils.book_new() // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, data, 'DsTB_BaoHong') // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, 'DsTB_BaoHong.xlsx')
        } else this.$toast.error('Không có dữ liệu!')
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnXuatExcel_Click() {
      try {
        if (this.gridviewDanhSach.data != null && this.gridviewDanhSach.data.length > 0) {
          if (this.gridviewDanhSach.data.length > 0) {
            let newData = []
            let headers = this.gridviewDanhSach.headers
            for (const item of this.gridviewDanhSach.data) {
              let newItem = {}
              for (const header of headers) {
                newItem[header.headerText] = item[header.fieldName]
              }
              newData.push(newItem)
            }
            this.$refs.exportDataModal.data = newData
            this.$refs.exportDataModal.showModal()
          } else {
            this.$root.$toast.warning('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
          }
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {}
    },
    gridviewDanhSach_RowClicked(i, item) {
      this.gridviewDanhSach.selectedItem = item
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>