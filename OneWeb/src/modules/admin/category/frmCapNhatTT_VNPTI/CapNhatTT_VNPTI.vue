<template src="./template.html" />
<style src="./style.scss" lang="scss" scope></style>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import { Icon } from '@iconify/vue2'
import api from './API.js'
export default {
  components: {
    breadcrumb,
    Icon,
    SearchAccount
  },
  data() {
    return {
      phanvung_id: null,
      p_nguoi_cn: null,
      p_may_cn: null,
      p_ip_cn: null,
      popupComponentAttr: {},
      popupComponent: null,
      popupComponentName: null,
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      rbValue: '2',
      txtDiaChiLD: {
        text: ''
      },
      txtDiaChiTB: {
        text: ''
      },
      txtTenTB: {
        text: ''
      },
      txtTrangThaiTB: {
        text: '',
        readonly: true
      },
      txtMaTBCu: {
        text: ''
      },
      txtTimKiem: {
        text: ''
      },
      txtMaTB: {
        text: ''
      },
      txtMaKenhDD: {
        text: '',
        readonly: true
      },
      thuebao_id: 0,
      gridSoMay: {
        data: []
      },
      gridTimKiem: {
        data: []
      },
      cbbDichVuVT: {
        options: [],
        value: null,
        disabled: false
      },
    }
  },
  methods: {
    clear() {
      this.txtDiaChiLD.text = ''
      this.txtDiaChiTB.text = ''
      this.txtTenTB.text = ''
      this.txtTrangThaiTB.text = ''
      this.txtMaTBCu.text = ''
      this.thuebao_id = 0
      this.gridSoMay.data = []
      this.gridTimKiem.data = []
    },
    async form_load() {
      const resDVVT = await api.getDSDichVuVT(this.axios)
      if (resDVVT.data.data.length) {
        this.cbbDichVuVT.options = resDVVT.data.data.map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt }))
        this.cbbDichVuVT.value = this.cbbDichVuVT.options[0].id
      }
    },
    txtMaTB_focus() {
      document.addEventListener('keydown', this.txtMaTB_keydown);
    },
    txtMaTB_blur(){
      document.removeEventListener('keydown', this.txtMaTB_keydown);
    },
    txtTimKiem_focus() {
      document.addEventListener('keydown', this.txtTimKiem_keydown);
    },
    txtTimKiem_blur(){
      document.removeEventListener('keydown', this.txtTimKiem_keydown);
    },
    txtTimKiem_keydown(e) {
      if (e.keyCode == 13) {
        this.btnTimKiem_click()
      }
    },
    txtMaTB_keydown(e) {
      if (e.keyCode == 13) {
        this.getTT()
      }
    },

    tbtnHuy_click() {
      this.clear()
    },
    bntLayMaKh_Click() {
      // mở form tra cứu danh bạ
    },
    async tsbtnGanNhom_Click() {
      if (!this.gridTimKiem.data.length) {
        this.$toast.error('Hãy tìm kênh cần gán')
        return
      }
      if (!this.thuebao_id) {
        this.$toast.error('Hãy nhập kênh toàn trình')
        return
      }
      // console.log(this.$refs.gridTimKiem.getSelectedRecords());
      if (!this.$refs.gridTimKiem.getSelectedRecords().length) {
        this.$toast.error('Hãy chọn kênh phân đoạn')
        return
      }
      const rec = this.$refs.gridTimKiem.getSelectedRecords()[0]
      if (rec.ma_vnpti) {
        this.$toast.error(`Kênh đã gán mã kênh toàn trình là: ${rec.ma_vnpti}`)
        return
      }
      const res = await api.ganKenh(this.axios, {
        p_phanvung_id: this.phanvung_id,
        p_thuebao_id: this.thuebao_id,
        p_thuebao_id_con: rec.thuebao_id,
        p_nguoi_cn: this.p_nguoi_cn,
        p_may_cn: this.p_may_cn,
        p_ip_cn: this.p_ip_cn,
        p_kieu: 1
      })
      if (res.data.error_code == 'BSS-00000000') {
        this.getTT()
        this.btnTimKiem_click()
        this.$toast.success('Gán kênh thành công')
      } else this.$toast.error('Gán kênh thất bại! Vui lòng thử lại sau')
    },
    async tsbtnBoGanNhom_Click() {
      console.log(this.$refs);
      if (!this.gridSoMay.data.length) {
        this.$toast.error('Không tìm thấy kênh phân đoạn')
        return
      }
      if (!this.thuebao_id) {
        this.$toast.error('Hãy nhập kênh toàn trình')
        return
      }
      // console.log(this.$refs.gridTimKiem.getSelectedRecords());
      if (!this.$refs.gridSoMay.getSelectedRecords().length) {
        this.$toast.error('Hãy chọn kênh phân đoạn')
        return
      }
      const rec = this.$refs.gridSoMay.getSelectedRecords()[0]
      if (rec.ma_vnpti) {
        this.$toast.error(`Kênh đã gán mã kênh toàn trình là: ${rec.ma_vnpti}`)
        return
      }
      const res = await api.ganKenh(this.axios, {
        p_phanvung_id: this.phanvung_id,
        p_thuebao_id: this.thuebao_id,
        p_thuebao_id_con: rec.thuebao_id,
        p_nguoi_cn: this.p_nguoi_cn,
        p_may_cn: this.p_may_cn,
        p_ip_cn: this.p_ip_cn,
        p_kieu: 0
      })
      if (res.data.error_code == 'BSS-00000000') {
        this.$toast.success('Bỏ gán kênh thành công')
        this.getTT()
        this.btnTimKiem_click()
      } else this.$toast.error('Bỏ gán kênh thất bại! Vui lòng thử lại sau')
    },
    ShowSearchAccount() {
      console.log('ShowSearchAccount')
      this.popupComponentAttr = { vISKHDN: this.$root.token.getPhanVungID() == 97 }
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchAccount'
      this.Popup('popupComponents')
    },
    test() {
      console.log('test')
    },
    Popup(modalId) {
      this.loading(false)
      if (this.$refs[modalId]) this.$refs[modalId].show()
      else this.$bvModal.show(modalId)
    },
    async popupClosed(val) {
      if (!val) {
        // this.popupRetVal = {}
        this.ClosePopup('popupComponents')
        return
      }
      try {
        this.loading(true)
        console.log(val)
        this.txtMaTB.text = val.ma_tb
        this.getTT()
      } catch (error) {
        
      } finally {
        this.loading(false)
        this.ClosePopup('popupComponents')
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async getTT() {
      this.clear()
      const res = await api.getTTTB(this.axios, {
        p_phanvung_id: this.phanvung_id,
        p_ma_tb: this.txtMaTB.text,
        p_dichvuvt_id: this.cbbDichVuVT.value * 1
      })
      let dt
      if (res.data.data && res.data.data.length) dt = res.data.data[0]
      else {
        this.$toast.error('Không tìm thấy thông tin thuê bao')
        return
      }
      this.txtDiaChiLD.text = dt.diachi_ld
      this.txtDiaChiTB.text = dt.diachi_tb
      this.txtTenTB.text = dt.ten_tb
      this.txtTrangThaiTB.text = dt.trangthai_tb
      this.thuebao_id = dt.thuebao_id
      this.txtMaTBCu.text = dt.ma_tb_cu
      this.txtMaKenhDD.text = dt.ma_vnpti
      const res1 = await api.getSoMay(this.axios, {
        p_phanvung_id: this.phanvung_id,
        p_ma_vnpti: this.txtMaTB.text
      })
      if (res1.data.data) this.gridSoMay.data = res1.data.data
    },
    async btnTimKiem_click() {
      const res = await api.timKiem(this.axios, {
        p_phanvung_id: this.phanvung_id,
        p_ma_tb: this.txtTimKiem.text
      })
      if (res.data.data) this.gridTimKiem.data = res.data.data
    }
  },

  async mounted() {
    this.form_load()
    this.p_nguoi_cn = await this.$root.token.getUserName()
    this.p_may_cn = await this.$root.token.getMachine()
    this.p_ip_cn = await this.$root.token.getIP()
    this.phanvung_id = this.$root.token.getPhanVungID() * 1
  },

}
</script>
