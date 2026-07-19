<template src='./index.html'></template>
<script>
import ComboboxGrid from '../../../../../../components/Controls/ComboboxGrid'
import moment from 'moment'

export default {
  props: ['duan_id', 'toanha_id'],
  components: {ComboboxGrid},
  data() {
    return {
      target: '.main-wrapper',
      cbo_DuAn: {
        items: [],
        selected: null
      },
      cbo_ToaNha: {
        items: [],
        selected: null
      },
      dsThongTinTB: [],
      chuKy: moment(new Date()).subtract(1, 'months').endOf('months').toDate(),
      maTB: '',
      isCheck: false,
      numberOpenPopup: 0, // số lần mở popup,
    }
  },
  methods: {
    openPopup() {
      this.$refs.dlgTraCuu.show()
    },
    closePopup() {
      this.numberOpenPopup = 1 // reset
      this.cbo_DuAn.selected = this.duan_id
      this.cbo_ToaNha.selected = this.toanha_id
      this.$refs.dlgTraCuu.hide()
    },
    // sư kiện được load sau khi popup được mở lên
    async initDataPopup() {
      this.numberOpenPopup = 0; // reset
      this.dsThongTinTB = []
      await this.traCuuDuAn();
      await this.traCuuToaNha(this.duan_id);
      this.cbo_DuAn.selected = this.duan_id
      this.cbo_ToaNha.selected = this.toanha_id;
    },
    async traCuuDuAn() {
      try {
        this.loading(true)
        let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/tracuu_duan`)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.cbo_DuAn.items = response.data;
          this.cbo_DuAn.selected = this.duan_id
        } else {
          this.cbo_DuAn.items = []
        }
      } catch (err) {
        this.cbo_DuAn.items = []
      } finally {
        this.loading(false)
      }
    },
    async traCuuToaNha(duan_id) {
      try {
        this.loading(true)
        let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/tracuu_toanha/${duan_id}`)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.cbo_ToaNha.items = response.data
          this.cbo_ToaNha.selected = this.toanha_id;
          await this.onTimKiem();
        } else {
          this.cbo_ToaNha.items = []
        }
      } catch (err) {
        this.cbo_ToaNha.items = []
      } finally {
        this.loading(false)
      }
    },
    async kiemTraTonTaiBang(chuKy) {
      try {
        let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/kiemtra_tontai_bang?vchuky=${chuKy}`)
        if (response.error_code === 'BSS-00000000' && response.data != 0) {
          return true
        } else {
          return false
        }
      } catch (err) {
        return false
      }
    },
    async traCuuTBToaNha() {
      try {
        this.loading(true)
        let apiBody = {
          vkieu: this.isCheck ? 2 : 1,
          vchuky: this.isCheck ? moment(this.chuKy).format('yyyyMM').toString() + '01' : '',
          vduan_id: this.cbo_DuAn.selected,
          vtoanha_id: this.cbo_ToaNha.selected,
          vma_tb: this.maTB
        }
        let url = `web-toanha/tracuu-tb-toanha/tracuu_tb_toanha`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsThongTinTB = response.data
        } else {
          this.dsThongTinTB = []
        }
      } catch (err) {
        this.dsThongTinTB = []
      } finally {
        this.loading(false)
      }
    },
    async onTimKiem() {

      if (this.cbo_DuAn.selected == 0 && this.cbo_ToaNha.selected == 0 && this.maTB == '') {
        this.$root.toastError('Bạn chưa nhập thông tin tìm kiếm')
        return
      }

      await this.traCuuTBToaNha()
      if (this.isCheck && this.dsThongTinTB.length == 0) {
        this.$root.toastError(`Hiện chưa có dữ liệu chốt tháng ${moment(this.chuKy).format('MM/yyyy')}`)
      }
    },
    async duAnSelected(args){
      this.numberOpenPopup++;
      if (args != null && this.numberOpenPopup > 1) {
        await this.traCuuToaNha(args.duan_id)
        this.cbo_ToaNha.selected = this.toanha_id
      }
    },
    async toaNhaSelected(args) {
      if (args != null) {
        await this.onTimKiem();
        //this.cbo_ToaNha.selected = args.toanha_id
      }
    },
    onXuatExcel() {
      if (this.dsThongTinTB.length <= 0) {
        this.$toast.error('Chưa có danh sách để xuất excel!')
      } else {
        let excelExportProperties = {
          fileName: 'Danh-sach-thue-bao-toa-nha.xlsx',
          dataSource: this.dsThongTinTB
        }
        this.$refs.thongTinTBToaNhaGrid.excelExport(excelExportProperties)
      }
    },
  }
}
</script>
