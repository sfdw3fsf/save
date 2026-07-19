<template src="./index.html"></template>
<script>
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import api from './api.js'
import state from './define.js'
import DatePicker from "vue2-datepicker";



export default {
  components: {
    moment,
    breadcrumb,
    gridview,
    DatePicker
  },
  name: '',
  validations: {},
  data() {
    return {
      header: {
        title: 'SMS MKT - BẢNG CƯỚC CP',
        list: [
          { name: 'Danh mục', link: { name: 'Ui.cards' } },
          {
            name: 'Bảng cước dịch vụ SMS Brandname',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      dateFormat: "DD/MM/YYYY",
      typeFormat: "datetime",
      typeFormatMonth: "month",
      dateConfig: {
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      ...state
    }
  },
  created() {
    this.init()
    this.layDS()
  },
  watch: {
    "formTT.phanloai_id": function(newVal) {
      console.log(newVal)
      this.options.ds_label_type = this.ds_label_type.filter(x => x.phanloai_id == newVal);
    }
  },
  methods: {
    async init() {
      try {
        this.SetButton(1)
        const response = await api.load_danhmuc_frm_bangcuoc_smsbrandname(this.axios)
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.options.ds_phanloai = response.data.data[0]
          this.options.ds_telco = response.data.data[1]
          this.options.ds_loai_daily = response.data.data[2]
          this.options.ds_label_type = response.data.data[3]
          this.ds_label_type = response.data.data[3]
          this.options.ds_ht_thanhtoan = response.data.data[4]
        }
        this.may_cn = await this.$root.token.getMachine()
        this.ip_cn = await this.$root.token.getIP()
        console.log(this.may_cn)
      } catch (error) {
        console.log(error)
      }
    },
    async layDS() {
      try {
        await api.lay_danhsach_bangcuoc_brandname(this.axios).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.gvDanhSach.tableData = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
      }
    },
    HandleDate(Prop, DateInput) {
      this[Prop] = DateInput
      console.log(DateInput)
    },
    phanloaiIDChanged(item) {
      console.log(item);
    },
    selectItem(item) {
      if (!item || !item.data) return
      this.SetButton(3)
      let formdata = item.data
      this.formTT.bangcuoc_id = formdata.bangcuoc_id
      this.formTT.phanloai_id = formdata.phanloai_id
      this.formTT.nhamang_id = formdata.nhamang_id
      this.formTT.loai_daily = formdata.loai_daily
      this.formTT.loainhan_id = formdata.loainhan_id
      this.formTT.ht_thanhtoan = formdata.ht_thanhtoan
      this.formTT.sl_tu = formdata.sl_tu
      this.formTT.sl_den = formdata.sl_den
      this.formTT.dongia = formdata.dongia
      this.formTT.thoihan = formdata.thoihan
      this.formTT.ghichu = formdata.ghichu
      this.formTT.ngay_bd = formdata.ngay_bd
      this.formTT.ngay_kt = formdata.ngay_kt
      //this.formTT.linhvuc_id = formdata.linhvuc_id
    },
    async btnGhilai_Click() {
      if (!this.KiemTraDL()) return false

      let isNew = this.formTT.bangcuoc_id == 0
      if (isNew) this.capnhat(1)
      else this.capnhat(2)
    },
    async capnhat(loai) {
      this.loading(true)
      try {
        const p_data = {
          BANGCUOC_ID: this.formTT.bangcuoc_id,
          PHANLOAI_ID: this.formTT.phanloai_id,
          NHAMANG_ID: this.formTT.nhamang_id,
          LOAI_DAILY: this.formTT.loai_daily,
          LOAINHAN_ID: this.formTT.loainhan_id,
          HT_THANHTOAN: this.formTT.ht_thanhtoan,
          SL_TU: this.formTT.sl_tu,
          SL_DEN: this.formTT.sl_den,
          DONGIA: this.formTT.dongia,
          THOIHAN: this.formTT.thoihan,
          GHICHU: this.formTT.ghichu,
          NGAY_BD: this.formTT.ngay_bd,
          NGAY_KT: this.formTT.ngay_kt,
        }

        let input = {
          ds_para: JSON.stringify({MODE: loai, BANGCUOC: p_data})
        }
        await api.fn_ghilai_bangcuoc_brandname(this.axios, input).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Cập nhật thông tin thành công.')
            this.layDS()
            this.loading(false)
          } else if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
            this.loading(false)
          } else {
            this.$toast.error('Cập nhật thông tin thất bại.')
            this.loading(false)
          }
        })
        this.loading(false)
      } catch (ex) {
        this.loading(false)
        this.$toast.error('Có lỗi khi cập nhật thông tin!' + ex)
      }
    },
    async btnXoa_Click() {
      if (this.formTT.loainha_id == 0) {
        this.$toast.error('Vui lòng chọn dòng cần xóa!')
        return
      }
      if (!window.confirm('Bạn thật sự muốn xóa không ?')) {
        return
      }
      this.capnhat(3)
    },
    btnLayTT_Click() {
      this.SetButton(1)
      this.layDS()
    },
    onActionClick(action) {
      if (action.id === 'nhapmoi') {
        this.btnThemmoi_Click()
      } else if (action.id === 'ghilai') {
        this.btnGhilai_Click()
      } else if (action.id === 'xoa') {
        this.btnXoa_Click()
      } else if (action.id === 'laytt') {
        this.btnLayTT_Click()
      }
    },
    setActiveButton(id, status) {
      let index = this.actions.findIndex((item) => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    btnThemmoi_Click() {
      console.log('btnThemmoi_Click')
      this.SetButton(1)
    },
    SetButton(kieu) {
      this.setActiveButton('nhapmoi', false)
      this.setActiveButton('ghilai', false)
      this.setActiveButton('xoahd', false)
      if (kieu == -1) {
        //Bat dau
        this.setActiveButton('ghilai', true)
      } else if (kieu == 0) {
        //Bat dau
        this.setActiveButton('nhapmoi', true)
        this.ClearGUI()
      } else if (kieu == 1) {
        //Them moi
        $('#ma_hd').focus()
        this.setActiveButton('ghilai', true)
        this.ClearGUI()
      } else if (kieu == 2) {
        //Huy
        this.setActiveButton('nhapmoi', true)
        this.ClearGUI()
      } else if (kieu == 3) {
        //Edit
        this.setActiveButton('nhapmoi', true)
        this.setActiveButton('ghilai', true)
        this.setActiveButton('xoa', true)
      }
    },

    KiemTraDL() {
      // if (this.formTT.ten_loainha == null || this.formTT.ten_loainha == '') {
      //   this.$toast.error('Hãy nhập tên loại nhà!')
      //   $('#ten_loainha').focus()
      //   return false
      // }
      return true
    },
    ClearGUI() {
      this.formTT = {
        bangcuoc_id: 0,
        phanloai_id: 0,
        nhamang_id: 0,
        loai_daily: 0,
        loainhan_id: 0,
        ht_thanhtoan: 0,
        sl_tu: 0,
        sl_den: 0,
        dongia: 0,
        thoihan: 0,
        ghichu: '',
        ngay_bd: '',
        ngay_kt: '',
        linhvuc_id: 0
      }
    }
  }
}
</script>

<style scoped>
.input-icon-right.disabled input {
  cursor: default;
  pointer-events: none;
  text-decoration: none !important;
  color: grey !important;
}
</style>
