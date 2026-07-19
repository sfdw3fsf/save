<template src="./index.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import Vue from 'vue'
import { DropDownListPlugin } from '@syncfusion/ej2-vue-dropdowns'
import KTable from '@/components/KTable.vue'
import ActionTop from '@/components/ActionTop.vue'
import Modal from "./../PhanKCChoKVBangFile/Modal";
Vue.use(DropDownListPlugin)
export default {
  name: 'GanKhuVucKetCuoi',
  components: { breadcrumb, KTable, ActionTop, Modal },
  data() {
    return {
      dsTTVT: [],
      dsToQL: [],
      dsTongDai: [],
      dsNhomCap: [],
      dsCapGoc: [],
      dsKhuVuc: [],
      dsKetCuoiChuaPhan: [],
      dsKetCuoiDaPhan: [],
      dataSelected: {
        TTVT: null,
        ToQL: null,
        TongDai: null,
        NhomCap: null,
        CapGoc: null,
        KhuVuc: null
      },
      header: {
        title: 'Gán khu vực kết cuối'
      },
      dsKetCuoiCP: [],
      dsKetCuoiDP: [],
      params: {
        p_loaiso_options: [],
        p_loaiso: null,
        p_nhomsodep_options: [],
        p_nhomsodep: null,
        p_kieusodep_options: [],
        p_kieusodep: null,
        p_txtMoTa: null,
        p_dichvuvt_options: [],
        p_dichvuvt: null,
        p_prefix: null
      },
      NhanVienDaGan: {
        columns: [
          {
            fieldName: 'ma_nv',
            headerText: 'Ký hiệu',
            allowFiltering: true,
            allowSorting: false,
            width: '130'
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Dung lượng',
            allowFiltering: true,
            allowSorting: false,
            width: '140'
          },
          {
            fieldName: 'email',
            headerText: 'Địa chỉ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_dt',
            headerText: 'Mã Khu vực',
            allowFiltering: true,
            allowSorting: false,
            width: '140'
          }
        ],
        data: [],
        selectedItems: []
      },
      NhanVienChuaGan: {
        columns: [
          {
            fieldName: 'nhanvien_id',
            headerText: 'Ký hiệu',
            allowFiltering: true,
            allowSorting: false,
            width: '120'
          },
          {
            fieldName: 'ma_nv',
            headerText: 'Dung lượng',
            allowFiltering: true,
            allowSorting: false,
            width: '130'
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Địa chỉ',
            allowFiltering: true,
            allowSorting: false,
            width: '140'
          }
        ],
        data: [],
        selectedItems: []
      },
      txtMaNV: {
        value: ''
      }
    }
  },
  watch: {
    'txtMaNV.value': {
      handler(value) {
        if (value.trim() != '') {
          this.actions[4].active = true
        } else {
          this.actions[4].active = false
        }
      }
    }
  },
  methods: {
    btnNhapTuFile() {
      this.$refs['file'].click()
    },
    select_selectedItemsChangedDagan: function (selectedItems) {},
    select_selectedItemsChangedChuagan: function (selectedItems) {},
    checkData: function () {
      if (this.dataSelected.KhuVuc == null && !this.flag) {
        this.$root.toastError('Chưa chọn khu vực')
        let nv = this.$refs['khuvuc'].querySelector('div>span>span>span').getAttribute('class')
        nv = 'form-control is-invalid ' + nv
        this.$refs['khuvuc'].querySelector('div>span>span>span').setAttribute('class', nv)
        return false
      }
      if (this.dataSelected.CapGoc == null) {
        this.$root.toastError('Chưa chọn cáp gốc')
        return false
      }
      return true
    },
    RightOne: async function () {
      try {
        this.$root.showLoading(true)
        if (this.checkData()) {
          if (this.dsKetCuoiCP.length == 0) {
            this.$root.toastError('Bạn chưa tick chọn kết cuối!')
            return
          }
          let params = {
            p_ketcuoi_ids: this.dsKetCuoiCP.join(';'),
            p_khuvuc_id: this.dataSelected.KhuVuc
          }
          var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/gan-ketcuoi-kv', params)
          this.$toast.success('Cập nhật thành công! ')
        }
      } catch (error) {
        this.$root.toastError('Lỗi: ' + error.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
        this.dsKetCuoiChuaPhan = []
        this.dsKetCuoiDaPhan = []
        await this.getdsketCuoiChuaPhanKhuVuc()
        await this.getdsketCuoiDaPhanKhuVuc()
      }
    },
    RightAll: async function () {
      try {
        this.$root.showLoading(true)
        if (this.checkData()) {
          if (this.dsKetCuoiCP.length == 0) {
            this.$root.toastError('Bạn chưa tick chọn kết cuối!')
            return
          }
          let params = {
            p_ketcuoi_ids: this.dsKetCuoiChuaPhan.map((x) => x.KETCUOI_ID).join(';'),
            p_khuvuc_id: this.dataSelected.KhuVuc
          }
          var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/gan-ketcuoi-kv', params)
          this.$toast.success('Cập nhật thành công! ')
        }
      } catch (error) {
        this.$root.toastError('Lỗi: ' + error.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
        this.dsKetCuoiChuaPhan = []
        this.dsKetCuoiDaPhan = []
        await this.getdsketCuoiChuaPhanKhuVuc()
        await this.getdsketCuoiDaPhanKhuVuc()
      }
    },
    LeftOne: async function () {
      try {
        this.flag = true
        this.$root.showLoading(true)
        if (this.checkData()) {
          if (this.dsKetCuoiDP.length == 0) {
            this.$root.toastError('Bạn chưa tick chọn kết cuối!')
            return
          }
          let params = {
            p_ketcuoi_ids: this.dsKetCuoiDP.join(';')
          }
          var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/bogan-ketcuoi-kv', params)
          this.$toast.success('Cập nhật thành công! ')
        }
      } catch (error) {
        this.$toast.error('Lỗi: ' + error.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
        this.dsKetCuoiChuaPhan = []
        this.dsKetCuoiDaPhan = []
        await this.getdsketCuoiChuaPhanKhuVuc()
        await this.getdsketCuoiDaPhanKhuVuc()
      }
    },
    LeftAll: async function () {
      try {
        this.flag = true
        this.$root.showLoading(true)
        if (this.checkData()) {
          if (this.dsKetCuoiDP.length == 0) {
            this.$root.toastError('Bạn chưa tick chọn kết cuối!')
            return
          }
          let params = {
            p_ketcuoi_ids: this.dsKetCuoiDaPhan.map((x) => x.KETCUOI_ID).join(';')
          }
          var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/bogan-ketcuoi-kv', params)
          this.$toast.success('Cập nhật thành công! ')
        }
      } catch (error) {
        this.$toast.error('Lỗi: ' + error.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
        this.dsKetCuoiChuaPhan = []
        this.dsKetCuoiDaPhan = []
        await this.getdsketCuoiChuaPhanKhuVuc()
        await this.getdsketCuoiDaPhanKhuVuc()
      }
    },
    getTTVT: async function () {
      try {
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/dsttvt_theonv')
        this.dsTTVT = rs.data
      } catch (error) {
        console.log(error)
      }
    },
    getToQL: async function () {
      try {
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/ds_to_kythuat_theonv/' + this.dataSelected.TTVT)
        this.dsToQL = rs.data
      } catch (error) {
        console.log(error)
      }
    },
    getTongDai: async function () {
      try {
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/ds_tram_tong_dai/' + this.dataSelected.ToQL)
        this.dsTongDai = rs.data
      } catch (error) {
        console.log(error)
      }
    },
    getNhomCap: async function () {
      try {
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/ds_loai_nhom_cap')
        this.dsNhomCap = rs.data
      } catch (error) {
        console.log(error)
      }
    },
    getCapGoc: async function () {
      try {
        let loai_cap = this.dataSelected.NhomCap == 'Cáp quang' ? 1 : 0
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/ds_cap_goc', { loai_nhom_cap: loai_cap, tong_dai_id: this.dataSelected.TongDai })
        this.dsCapGoc = rs.data
      } catch (error) {
        console.log(error)
      }
    },
    onChangeTTVT: async function () {
      await this.getToQL()
      this.dataSelected.ToQL = null
      this.dataSelected.TongDai = null
      this.dataSelected.NhomCap = null
      this.dataSelected.CapGoc = null
      this.dataSelected.KhuVuc = null
      this.dsKetCuoiChuaPhan = []
      this.dsKetCuoiDaPhan = []
    },
    onChangeToQL: async function () {
      await this.getTongDai()
      await this.getdsKhuVuc()
    },
    onChangeTongDai: async function () {
      await this.getCapGoc()
    },
    onChangeNhomCap: async function () {
      if (this.dataSelected.TongDai != null) await this.getCapGoc()
    },
    onChangeCapGoc: async function () {
      try {
        await this.getdsketCuoiChuaPhanKhuVuc()
        await this.getdsketCuoiDaPhanKhuVuc()
      } catch (e) {
        console.log(e)
      }
    },
    async cbb_selectedChanged(dataItem) {
      if (dataItem != null) {
        this.dataSelected.CapGoc = dataItem.CAP_ID
        this.tenCapGoc = 'Cáp gốc: ' + dataItem.TEN_CAP
        this.showTable = 'none'
        await this.getdsketCuoiChuaPhanKhuVuc()
        await this.getdsketCuoiDaPhanKhuVuc()
      }
    },
    async onChangeKhuVuc() {
      await this.getdsketCuoiDaPhanKhuVuc()
    },
    getdsketCuoiChuaPhanKhuVuc: async function () {
      try {
        if (this.dataSelected.CapGoc != null) {
          const params = {
            p_capgoc_id: this.dataSelected.CapGoc
          }
          var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/danhsach-ketcuoi-chuagan-kv', params)
          this.dsKetCuoiChuaPhan = rs.data
        }
      } catch (error) {
        console.log(error)
      }
    },
    getdsketCuoiDaPhanKhuVuc: async function () {
      try {
        if (this.dataSelected.CapGoc != null) {
          const params = {
            p_capgoc_id: this.dataSelected.CapGoc,
            p_khuvuc_id: this.dataSelected.KhuVuc
          }
          var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/danhsach-ketcuoi-dagan-kv', params)
          if (rs.data != null) {
            this.dsKetCuoiDaPhan = rs.data
          }
        }
      } catch (error) {
        console.log(error)
      }
    },
    selectCP_selectedItemsChanged: function (dataKeys) {
      this.dsKetCuoiCP = dataKeys
    },
    selectDP_selectedItemsChanged: function (dataKeys) {
      this.dsKetCuoiDP = dataKeys
    },
    getdsKhuVuc: async function () {
      try {
        var rs = await this.$root.context.post('/web-cabman/ketcuoi-khuvuc/danhsach-khuvuc-ketcuoi', {
          p_donvi_id: this.dataSelected.ToQL
        })
        this.dsKhuVuc = rs.data
        this.dsKhuVuc.forEach((element) => {
          element.TEN_HIEN_THI = element.MA_KV + ' - ' + element.TEN_KV;
        })
      } catch (error) {
        console.log(error)
      }
    }
  },
  created: async function () {
    await this.getTTVT()
    await this.getNhomCap()
  }
}
</script>

<style scoped src="./index.scss"></style>
