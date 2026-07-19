<template src="./index.html"></template>
<script>
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import api from './index.js'
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
        title: 'Gói cước thoại nội ngoại mạng',
        list: [
          { name: 'Danh mục', link: { name: 'Ui.cards' } },
          {
            name: 'Gói cước thoại nội ngoại mạng',
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
  },
  methods: {
    async init() {
      try {
        this.SetButton(1)
        const response = await api.load_danhmuc_frm_GoiThoaiNoiNgoaiMang(this.axios)
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.options.ds_dichvu_gt = response.data.data[0]
          this.options.ds_nhom_dt = response.data.data[1]
          this.options.ds_tyle_giam = response.data.data[2]
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
        await api.lay_ds_goicuocthoainoingoaimang(this.axios).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.gvDanhSach.tableData = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
      }
    },
    selectItem(item) {
      if (!item || !item.data) return
      this.SetButton(3)
      let formdata = item.data
      this.formTT.dichvugt_id = formdata.dichvugt_id
      this.formTT.nhomdt_id = formdata.nhomdt_id
      this.formTT.tien_giam = formdata.tien_giam
      this.formTT.phut_giam = formdata.phut_giam
      this.formTT.phut_dau = formdata.phut_dau
      this.formTT.tyle_giam = formdata.tyle_giam
    },
    async btnGhilai_Click() {
      if (!this.KiemTraDL()) return false
      let index = this.actions.findIndex((item) => item.id === 'nhapmoi');
      let isNew = this.actions[index].active == false;
      if (isNew) this.capnhat(1)
      else this.capnhat(2)
    },
    async btnGhilai_ALL_Click() {
       this.capnhat(4)
    },
    async capnhat(loai) {
      this.loading(true)
      try {
        const p_data = {
          DICHVUGT_ID: this.formTT.dichvugt_id,
          NHOMDT_ID: this.formTT.nhomdt_id,
          TIEN_GIAM: this.formTT.tien_giam,
          PHUT_GIAM: this.formTT.phut_giam,
          PHUT_DAU: this.formTT.phut_dau,
          TYLE_GIAM: this.formTT.tyle_giam,
        }

        let input = {
          ds_para: JSON.stringify({MODE: loai, GOICUOC: p_data})
        }
        await api.fn_ghilai_goicuocthoainoingoaimang(this.axios, input).then((response) => {
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
      } else if (action.id === 'ghilai-all') {
        this.btnGhilai_ALL_Click()
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
      this.setActiveButton('ghilai-all', false)
      this.setActiveButton('xoahd', false)
      if (kieu == -1) {
        //Bat dau
        this.setActiveButton('ghilai', true)
        this.setActiveButton('ghilai-all', true)
      } else if (kieu == 0) {
        //Bat dau
        this.setActiveButton('nhapmoi', true)
        this.ClearGUI()
      } else if (kieu == 1) {
        //Them moi
        $('#ma_hd').focus()
        this.setActiveButton('ghilai', true)
        this.setActiveButton('ghilai-all', true)
        this.ClearGUI()
      } else if (kieu == 2) {
        //Huy
        this.setActiveButton('nhapmoi', true)
        this.ClearGUI()
      } else if (kieu == 3) {
        //Edit
        this.setActiveButton('nhapmoi', true)
        this.setActiveButton('ghilai', true)
        this.setActiveButton('ghilai-all', true)
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
        dichvugt_id: 0,
        nhomdt_id: 0,
        tien_giam: 0,
        phut_giam: 0,
        phut_dau: 0,
        tyle_giam: 0
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
