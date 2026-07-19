<template src="./GiaoPhieuTon.html"></template>
<style scoped src="./style.scss">
</style>
<script>
import PopupLuotGiao from './components/PopupLuotGiao.vue'
import ComboboxGrid from '../components/ComboboxGrid'
import breadcrumb from '@/components/breadcrumb'
import { mapActions, mapState, mapGetters } from 'vuex'
import CommonsAPI from '@/modules/QLTN/api/Commons'
import GiaoPhieuTonAPI from '@/modules/QLTN/api/GiaoPhieuTonAPI'
import { currency } from '@/filters/currency'
import moment from 'moment'
import Vue from 'vue'

export default {
  components: { breadcrumb, PopupLuotGiao, appCombobox: ComboboxGrid },
  name: 'GiaoPhieuTon',

  async created () {
    if (this.danhSachNhanVienThuNganGetter === undefined || this.danhSachNhanVienThuNganGetter.length === 0) {
      await this.getDanhSachNhanVienThuNgan()
    }
    // if (this.danhSachNhanVienThuNganGetter.length > 0) {
    //     this.params.nhanVien_Options = this.danhSachNhanVienThuNganGetter;
    //     this.params.nhanVien_Selected = this.params.nhanVien_Options[0].id;
    // }
    await this.getDsNVTC()
    if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0) { await this.getKyHoaDonHienTai() }
    if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0) { this.params.kyHoaDonSelected = moment(this.kyHoaDonHienTaiGetter.KYCUOC, 'YYYYMMDD').toDate() }
  },

  mounted () {
    this.updateChuKyHoaDon(moment(this.params.kyHoaDonSelected).format('YYYYMM'))
    this.focusInput()
  },

  computed: {
    ...mapGetters('qltnCommon', ['danhSachNhanVienThuNganGetter', 'kyHoaDonHienTaiGetter']),
    aggregatesColumns () {
      return [
        { field: 'MA_TT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.countAggregate },
        { field: 'TIEN', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.tienAggregate },
        { field: 'TIEN_HD', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.tienHDAggregate }
      ]
    }
  },

  data () {
    return {
      params: {
        kyHoaDonSelected: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        max_kyhoadon: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        KCValue: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        chuKy: [],
        chuKySelected: null,
        kyCuoc: 0,
        nhanVien_Options: [],
        nhanVien_Selected: null,
        maTT: '',
        ngayGiao: new Date(),
        today: new Date(),
        layToanBo: false,
        kieu: 1,
        giaoPhieuId: ''
      },
      common_params: {
        tien_Checked: 0,
        tien_Sum: 0,
        tienHD_Checked: 0,
        tienHD_Sum: 0
      },
      phieuTon_grid: {
        items: [],
        checked: [],
        key: 1
      },
      luotGiao_Input: {
        kyCuoc: 0,
        kieu: 0,
        NVTCId: 0,
        NVTC: {}
      },
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data () { return { data: {data: {}}} }
        })
        }
      }
    }
  },

  methods: {
    ...mapActions('qltnCommon', ['getDanhSachNhanVienThuNgan', 'getKyHoaDonHienTai']),

    countAggregate () {
      return this.phieuTon_grid.checked.length + '/' + this.phieuTon_grid.items.length
    },

    tienAggregate () {
      return currency(this.common_params.tien_Checked) + '/' + currency(this.common_params.tien_Sum)
    },

    tienHDAggregate () {
      return currency(this.common_params.tienHD_Checked) + '/' + currency(this.common_params.tienHD_Sum)
    },

    focusInput () {
      this.$refs.maTT.focus()
    },

    nhanVienGiao_selectedChanged (args) {
      if (args != null) this.params.nhanVien_Selected = args.NHANVIEN_ID
      else this.params.nhanVien_Selected = null
    },

    onPhieuTonChecked (items) {
      this.phieuTon_grid.checked = items
      this.common_params.tien_Checked = 0
      this.common_params.tienHD_Checked = 0
      items.forEach(item => {
        let idx = this.phieuTon_grid.items.findIndex(e => e.MA_TT === item)
        if (idx !== -1) {
          this.common_params.tien_Checked += this.phieuTon_grid.items[idx].TIEN
          this.common_params.tienHD_Checked += this.phieuTon_grid.items[idx].TIEN_HD
        }
      })
      let dataGridObj = this.$refs.phieuTonGrid.$refs.grid.ej2Instances
      dataGridObj.aggregateModule.refresh()
    },

    async onGetAll () {
      if (this.params.layToanBo) {
        this.params.maTT = ''
        await this.getDSPhieuTon(this.params.giaoPhieuId, null, 1)
      } else {
        this.phieuTon_grid.items = []
        this.focusInput()
      }
    },

    async onSearch () {
      this.params.kieu = 1
      this.params.maTT = this.params.maTT.trim()
      if (this.params.maTT === '') { return this.$toast.error('Chưa nhập mã thanh toán!') }

      await this.getDSPhieuTon(null, this.params.maTT, this.params.kieu)
      if (this.phieuTon_grid.items.length <= 0) { this.$toast.error('Không có dữ liệu, vui lòng thử lại!') }
    },

    async onUpdate () {
      if (this.phieuTon_grid.items.length <= 0) {
        return this.$toast.error('Chưa có phiếu trên lưới để giao phiếu!')
      }

      if (this.phieuTon_grid.checked.length <= 0) {
        return this.$toast.error('Chưa chọn phiếu trên lưới để giao phiếu!')
      }

      if (this.params.nhanVien_Selected === null) {
        return this.$toast.error('Chưa chọn nhân viên để giao phiếu!')
      }

      if (this.params.ngayGiao === null) {
        return this.$toast.error('Vui lòng chọn ngày giao hợp lệ!')
      }

      let ds = []
      this.phieuTon_grid.checked.forEach(item => {
        let phieu = this.phieuTon_grid.items.find(e => e.MA_TT === item)
        let phieuGiao = {
          id: phieu.HOADON_ID,
          nhanVienId: this.$auth.getNguoiDungID(),
          thanhToanId: phieu.THANHTOAN_ID
        }
        ds.push(phieuGiao)
      })

      let result = await this.updateGiaoPhieuNVTC(ds, this.phieuTon_grid.checked.length, this.common_params.tienHD_Checked)
      if (result) this.$toast.success('Giao phiếu thành công!')

      await this.getDSPhieuTon(this.params.giaoPhieuId, this.params.maTT.trim(), this.params.kieu)
    },

    onRefresh () {
      this.phieuTon_grid.items = []
      this.phieuTon_grid.checked = []
    },

    onHistory () {
      this.luotGiao_Input = {
        kyCuoc: this.params.kyCuoc,
        kieu: 6,
        NVTCId: this.params.nhanVien_Selected,
        NVTC: this.params.nhanVien_Options.find(e => e.NHANVIEN_ID === this.params.nhanVien_Selected)
      }
      this.$refs.dlgLuotGiao.openDialog()
    },

    async onSelectLuotGiao (value) {
      this.params.kieu = 2
      this.params.giaoPhieuId = value
      await this.getDSPhieuTon(this.params.giaoPhieuId, this.params.maTT.trim(), this.params.kieu)
      if (this.phieuTon_grid.items.length <= 0) this.$toast.error('Không có dữ liệu, vui lòng thử lại!')
    },

    onExcelExport () {
      if (this.phieuTon_grid.items.length === 0) {
        return this.$toast.error('Không có dữ liệu để xuất Excel')
      } else {
        let excelExportProperties = {
          fileName: 'DSPhieuTon.xlsx',
          dataSource: this.phieuTon_grid.items
        }
        this.$refs.phieuTonGrid.excelExport(excelExportProperties)
        this.$toast.success('Xuất excel thành công!')
      }
    },

    /* kieu: 1 - Lay theo maTT; 2 - Lay theo giaophieuId */
    async getDSPhieuTon (giaoPhieuId, maTT, kieu) {
      try {
        this.loading(true)
        this.phieuTon_grid.key++
        this.phieuTon_grid.items = []
        this.common_params.tien_Sum = 0
        this.common_params.tienHD_Sum = 0
        let apiParams = {
          giaoPhieuId: giaoPhieuId,
          kieu: kieu,
          kyCuoc: this.params.kyCuoc,
          maTT: maTT,
          page: 0,
          size: 10000
        }
        let response = await GiaoPhieuTonAPI.getDSPhieuTon(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.phieuTon_grid.items = response.data.data.data
          this.phieuTon_grid.items.forEach(item => {
            this.common_params.tien_Sum += item.TIEN
            this.common_params.tienHD_Sum += item.TIEN_HD
          })
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    /* dsPhieuGiao: [{ id: Number, nhanVienId: Number, thanhToanId: Number }] */
    async updateGiaoPhieuNVTC (ds, soLuongHD, tongTienHD) {
      try {
        this.loading(true)
        let apiBody = {
          dsPhieuGiao: ds,
          giaoVet: 1,
          kieuGiao: 1,
          kyCuoc: this.params.kyCuoc,
          ngayGiao: moment(this.params.ngayGiao).format('DD/MM/YYYY'),
          nguoiGiaoId: this.$auth.getNhanVienID(),
          nhanVienTCId: this.params.nhanVien_Selected,
          soLuongHD: soLuongHD,
          tongTienHD: tongTienHD
        }
        let response = await GiaoPhieuTonAPI.updateGiaoPhieuNVTC(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') {
          return true
        } else {
          this.$toast.success(response.data.message_detail)
          return false
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },

    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
    },

    async updateChuKyHoaDon (pKyHoaDon) {
      try {
        this.loading(true)
        let response = await CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        if (response.data.error_code === 'BSS-00000000' &&
                    response.data.data !== undefined &&
                    response.data.data.length > 0) {
          this.params.chuKy = response.data.data
          this.params.chuKySelected = this.params.chuKy[0].CHUKY
        } else {
          this.params.chuKy = []
          this.params.chuKySelected = null
        }
        this.params.kyCuoc = moment(this.params.kyHoaDonSelected).format('YYYYMM') + this.params.chuKySelected
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    getDsNVTC: async function () {
      try {
        const { data } = await this.$root.context.get(
          '/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc'
        )
        if (data !== undefined && data.data !== undefined && data.data.length > 0) this.params.nhanVien_Options = data.data
        else this.params.nhanVien_Options = []
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    }
  }
}
</script>
