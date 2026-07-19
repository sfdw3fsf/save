<template src="./HuyVoiceBrnNgoaiMang.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component'
// import API from './API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'

import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

import moment from 'moment'
import { async, Promise, reject } from 'q'
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    // API,
    DatePicker
  },
  // name: 'HuyVoiceBrnNgoaiMang',
  mounted() {
    this.frmDangKySoThanhVien_Load()
    this.showModal()
  },
  watch: {},
  props: ['hdtb_id', 'thuebao_id', 'lst_hdtbct', 'lst_dbtbct', 'kieuld_id'],
  data() {
    return {
      // hdtb_id : 0,
      // thuebao_id : 0,
      // lst_hdtbct : [],
      // lst_dbtbct : [],
      tdan: {},
      tinh_id: 0,
      // kieuld_id : 0,
      settingSelect2: {
        language: 'vi'
      },
      grcTBDuocChon: {
        list: [],
        header: [
          { fieldName: 'SO_DICH', headerText: 'Số thuê bao', allowFiltering: true, width: 'auto' },
          { fieldName: 'TELCO_NAME', headerText: 'Nhà mạng', allowFiltering: true, width: 'auto' },
          { fieldName: 'DIACHI_LD', headerText: 'Nội dung', allowFiltering: true, width: 'auto' },
          { fieldName: 'NGAY_BD', headerText: 'Ngày cập nhật', allowFiltering: true, width: 'auto' }
        ],
        value: {},
        isEnabled: true
      },
      grcDanhBaTBI: {
        list: [],
        header: [
          { fieldName: 'SO_DICH', headerText: 'Số thuê bao', allowFiltering: true, width: 'auto' },
          { fieldName: 'TELCO_NAME', headerText: 'Nhà mạng', allowFiltering: true, width: 'auto' },
          { fieldName: 'DIACHI_LD', headerText: 'Nội dung', allowFiltering: true, width: 'auto' },
          { fieldName: 'NGAY_BD', headerText: 'Ngày cập nhật', allowFiltering: true, width: 'auto' }
        ],
        value: {},
        isEnabled: true
      }
    }
  },
  computed: {},
  methods: {
    showAlert(mesage) {
      this.$toast.error(mesage)
    },
    showSuccess(mesage) {
      this.$toast.success(mesage)
    },

    getData: function(response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '0') {
        if (response.data.data) {
          console.log(this.stringToJsonData(response.data.data))
          return this.stringToJsonData(response.data.data)
        }
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    stringToJsonData(str) {
      console.log(str)
      if (str == '[]') return JSON.parse(str)
      str = str.replace('[{', '').replace('}]', '')
      const jsonArray = []

      str.split('}, {').forEach((el) => {
        const jsonItem = {}
        el.split(', ').forEach((item) => {
          const keyValuePairs = item.split(',')
          for (const pair of keyValuePairs) {
            const [key, value] = pair.split('=')
            jsonItem[key.toUpperCase()] = value
          }
        })
        jsonArray.push(jsonItem)
      })

      var kq = JSON.stringify(jsonArray)
      console.log(kq)
      return JSON.parse(kq)
    },

    closeModal() {
      this.$bvModal.hide('HuyVoiceBrnNgoaiMangModal')
    },
    showModal() {
      this.$bvModal.show('HuyVoiceBrnNgoaiMangModal')
    },

    async frmDangKySoThanhVien_Load() {
      if (!this.tinh_id) this.tinh_id = this.$root.token.getPhanVungID()
      if (this.hdtb_id != 0) {
        // this.lst_hdtbct = this.getData(await API.lay_ds_thietbi_idc_dangky(this.axios, input))
        let rs = await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'LAY_DULIEU_HDTB_CNTT_SUB',
          p_ds_para: JSON.stringify({ HDTB_ID: this.hdtb_id, TINH_ID: this.tinh_id, THUEBAO_ID: 0 })
        })
        if (rs.data && rs.data.data) {
          this.grcTBDuocChon.list = this.UpperCasePropertyList(rs.data.data)
        }
      }

      if (this.thuebao_id != 0) {
        let rs2 = await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'LAY_DULIEU_DB_CNTT_SUB',
          p_ds_para: JSON.stringify({ HDTB_ID: 0, TINH_ID: this.tinh_id, THUEBAO_ID: this.thuebao_id })
        })
        if (rs2.data && rs2.data.data) {
          // this.lst_dbtbct = this.getData(await API.lay_ds_thietbi_idc_sudung(this.axios, input))
          this.grcDanhBaTBI.list = this.UpperCasePropertyList(rs2.data.data)
        }
      }
    },

    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },

    async btnDangky_Click() {},

    async btnGhiLai_ItemClick() {
      try {
        let kq = await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          p_nghiepvu: 'CAPNHAT_HDTB_CNTT_SUB_COMMON',
          p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id, TINH_ID: this.tinh_id, vds_hdtb_cntt_sub: JSON.stringify(this.grcTBDuocChon.list) })
        })

        this.showSuccess('Đăng ký thuê bao thành công!')
        // this.Close();
        return
      } catch (error) {
        this.showAlert(error)
      }
    },

    formatDateTime(date = new Date()) {
      const day = String(date.getDate()).padStart(2, '0')
      const month = String(date.getMonth() + 1).padStart(2, '0') // Months are 0-based
      const year = date.getFullYear()
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      const seconds = String(date.getSeconds()).padStart(2, '0')

      return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`
    },
    btnDangky_Click() {
      let data = this.$refs.danhSachThueBaoDangHoatDong.getSelectedRecords()
      var a = this.grcDanhBaTBI.list

      for (var i = 0; i < data.length; i++) {
        data[i].NGAY_BD = this.formatDateTime()
        data[i].DIACHI_LD = 'Yêu cầu hủy'
      }

      this.grcTBDuocChon.list = data
      //  foreach(DB_THUEBAO_CT item in lst_dbtbct.FindAll(x => x.chon == true))
      // {
      //           HD_THUEBAO_CT a = new HD_THUEBAO_CT();
      //   Mapper.Map < DB_THUEBAO_CT, HD_THUEBAO_CT > (item, a);
      //   if (!lst_hdtbct.Exists(x => x.SO_DT_CT == item.SO_DT_CT)) {
      //     a.TRANGTHAI_CT = "Yêu cầu " + cboKieuYc.Text;
      //     lst_hdtbct.Add(a);
      //   }
      // }
      // grcTBDuocChon.DataSource = new BindingList < HD_THUEBAO_CT > (lst_hdtbct);
    },
    btnHuy_Click() {
      let data = this.$refs.danhSachThueBaoBiBienDong.getSelectedRecords()
      this.grcTBDuocChon.list = this.grcTBDuocChon.list.filter(function(el) {
        return !data.includes(el)
      })
    },
    btnThoat_ItemClick() {}
  }
}
</script>

<style>
.searchAccountDialogClass .e-dlg-header-content {
  display: none;
}
</style>
