<template src="./PrintHandoverDoc.html"></template>

<style src="./PrintHandoverDoc.scss">
</style>
<script>
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import API from './API.js'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import InBienBan from '@/modules/print/InBienBan/InBienBan'
import gridview from './gridview.vue'
import { MultiSelectPlugin } from '@syncfusion/ej2-vue-dropdowns'
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns'
MultiSelect.Inject(CheckBoxSelection)
Vue.use(MultiSelectPlugin)

export default {
  components: { breadcrumb, gridview, InBienBan, DatePicker },
  name: 'PrintHandoverDoc',
  destroyed() {},
  data() {
    return {
      date_enable: false,
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
      },
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',

      modelIn: {},

      denNgay: '',
      hdtbId: '',
      tuNgay: '',
      txtKieuLD: '',
      txtDiaChiLD: '',
      txtGhiChu: '',
      txtMaTB: '',
      listbox: {
        loai_hd: {
          list: [],
          fields: { value: 'id', text: 'text' },
          value: '',
          isEnabled: false
        },
        loai_dichvu_vt: {
          list: [],
          value: '',
          isEnabled: false
        },
        nhanvien: {
          list: [],
          value_gv: '',
          value_th: '',
          isEnabled: false
        },
        ds_phieu_yc: {
          list: [],
          header: [
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
            { fieldName: 'ten_tb', headerText: 'Tên TB' },
            { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ' },
            { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB' },
            { fieldName: 'ten_kieuld', headerText: 'Kiểu LĐ' }
          ]
        }
      }
    }
  },
  async mounted() {
    await Promise.all([this.formLoad()])
  },
  methods: {
    formLoad: async function () {
      try {
        await API.get_ds_loai_hd(this.axios, null).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            // this.listbox.loai_hd.list = response.data.data.map(x=>{return {id:x['loaihd_id'],text:x['ten_loaihd']}});
            response.data.data.forEach((element) => {
              this.listbox.loai_hd.list.push({
                text: element.ten_loaihd,
                id: element.loaihd_id
              })
            })
            this.listbox.loai_hd.value = this.listbox.loai_hd.list.map((x) => x.id)
          }
        })

        await API.get_ds_loai_dichvu_vt(this.axios, null).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.loai_dichvu_vt.list = response.data.data.map((x) => {
              return { id: x['dichvuvt_id'], text: x['ten_dvvt'] }
            })
            this.listbox.loai_dichvu_vt.value = response.data.data[0].dichvuvt_id
          }
        })

        this.date_enable = false
        $('.datepicker').attr('disabled', 'disabled')

        await API.get_nhanviens(this.axios, this.$root.token.getDonViID()).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.nhanvien.list = response.data.data.map((x) => {
              return { id: x['ma_nv'], text: x['ten_nv'] }
            })
            this.listbox.nhanvien.value_gv = response.data.data[0].ma_nv
            this.listbox.nhanvien.value_th = response.data.data[0].ma_nv
          }
        })

        this.tuNgay = moment(new Date()).format('DD/MM/YYYY')
        this.denNgay = moment(new Date()).format('DD/MM/YYYY')
        // console.log(this.tuNgay);
        // console.log(this.denNgay);
      } catch (e) {}
    },
    load_ds_phieu_yc: function () {
      if (!this.txtMaTB) {
        this.$toast.error('Phải nhập Số máy/Acc!')
        return
      }
      this.loading(true)

      var p_tuNgay, p_denNgay, p_matb, p_loai_hd, p_loai_dichvu_vt

      p_tuNgay = !this.date_enable ? '0' : this.tuNgay
      p_denNgay = !this.date_enable ? '0' : this.denNgay
      p_matb = this.txtMaTB
      p_loai_hd = !this.listbox.loai_hd.value ? '0' : this.listbox.loai_hd.value.join()
      p_loai_dichvu_vt = !this.listbox.loai_dichvu_vt.value ? '0' : this.listbox.loai_dichvu_vt.value

      var data = { denNgay: p_denNgay, dichVuVTId: p_loai_dichvu_vt, dsLoaihdId: p_loai_hd, hdtbId: '0', tuNgay: p_tuNgay, maTb: p_matb }

      API.get_ds_phieu_yc(this.axios, data).then((response) => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_phieu_yc.list = response.data.data
          this.loading(false)
        } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
          this.listbox.ds_phieu_yc.list = []
          this.$toast.error('Không có thông tin phiếu')
          this.loading(false)
        } else {
          this.listbox.ds_phieu_yc.list = []
          this.$toast.error(response.data.message_detail)
          this.loading(false)
        }
      })
    },

    filterMedia: function (event) {
      if (!this.date_enable) {
        this.date_enable = true
        $('.datepicker').removeAttr('disabled')
        //  this.tuNgay = "";
        //  this.denNgay = "";
      } else {
        this.date_enable = false
        $('.datepicker').attr('disabled', 'disabled')
        // this.tuNgay = "";
        //  this.denNgay = "";
      }
    },
    clear: function () {
      this.txtMaTB = ''
      this.txtKieuLD = ''
      this.txtDiaChiLD = ''
      this.txtGhiChu = ''
      this.listbox.nhanvien.value_gv = ''
      this.listbox.nhanvien.value_th = ''
    },

    grddsPhieuYCChanged: function (obj) {
      this.clear()
      this.txtMaTB = obj[0].ma_tb
      this.txtKieuLD = obj[0].ten_kieuld
      this.txtDiaChiLD = obj[0].diachi_ld
      this.txtGhiChu = obj[0].ghichu
      this.listbox.nhanvien.value_gv = obj[0].nhanvien_giao_id
      this.listbox.nhanvien.value_th = obj[0].nhanvien_th_id
    },

    tsbtnInBB_Click() {
      if (this.listbox.ds_phieu_yc.list.length == 0) this.$toast.error('Không có thông tin phiếu')
      else {
        var grv = this.$refs.DsPhieuYC
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        grv.getSelectedRecords().forEach((obj) => {
          nhdkhid.push(obj.hdkh_id)
          nhdtbid.push(obj.hdtb_id)
          nphieuid.push(obj.phieu_id)
        })

        this.modelIn.n_phieu_id = nphieuid.join(',')
        this.modelIn.n_hdkh_id = nhdkhid.join(',')
        this.modelIn.n_hdtb_id = nhdtbid.join(',')
        //console.log(this.modelIn);

        this.$refs.popupInBB.showModal()
      }
    }
  }
}
</script>
