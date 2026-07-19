<template>
  <b-modal
    ref="eInvoiceDetailRef"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chi tiết hóa đơn điện tử
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="isVisibleBtnChapNhan">
            <a @click="onOk">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div v-html="documentText"></div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from '../api'

export default {
  name: 'PopupEinvoiceDetail',
  data () {
    return {
      kiemtra_huy_hddt: false,
      xemtruoc_hddt: false,
      isview: true,
      isVisibleBtnChapNhan: false,
      viewFromText: false,
      documentText: '',
      vthaotac: 0,
      hoang_test: 0,
      vtthoadon: 1,
      kieu: 0,
      token: '',
      fkey: '',
      html: ''
    }
  },
  methods: {
    async showModal (data) {
      this.initPopup(data)
      await this.frmE_InvoiceDetail_Load()
      this.$refs.eInvoiceDetailRef.show()
    },
    hideModal () {
      this.$refs.eInvoiceDetailRef.hide()
    },
    initPopup (data) {
      if (this.isExistObject(data)) {
        if (data.hasOwnProperty('vthaotac')) this.vthaotac = data.vthaotac
        if (data.hasOwnProperty('viewFromText')) this.viewFromText = data.viewFromText
        if (data.hasOwnProperty('isview')) this.isview = data.isview
        if (data.hasOwnProperty('hoang_test')) this.hoang_test = data.hoang_test
        if (data.hasOwnProperty('vtthoadon')) this.vtthoadon = data.vtthoadon
        if (data.hasOwnProperty('vtoken_show')) this.token = data.vtoken_show
        if (data.hasOwnProperty('html')) this.html = data.html
      }
    },
    isExistObject (object) {
      return object && Object.getPrototypeOf(object) === Object.prototype && Object.keys(object).length > 0
    },
    async frmE_InvoiceDetail_Load () {
      const dsThamSo = await this.layDanhSachTSMacDinh()
      if (dsThamSo.length > 0) {
        const KIEMTRA_HUY_HDDT = this.getThamSoByMaThamSo(dsThamSo, 'KIEMTRA_HUY_HDDT')
        if (KIEMTRA_HUY_HDDT && KIEMTRA_HUY_HDDT.ten_ts.toString().trim() === '1') {
          this.kiemtra_huy_hddt = true
        }
        const XEMTRUOC_HDDT = this.getThamSoByMaThamSo(dsThamSo, 'XEMTRUOC_HDDT')
        if (XEMTRUOC_HDDT && XEMTRUOC_HDDT.ten_ts.toString().trim() === '1') {
          this.xemtruoc_hddt = true
        }
      }
      this.isVisibleBtnChapNhan = this.kiemtra_huy_hddt && !this.isview
      if (this.xemtruoc_hddt) {
        this.viewFromText ? this.ViewDataFromText() : await this.ViewDataFromService()
      } else {
        await this.ViewDataFromService()
      }
    },
    async layDanhSachTSMacDinh () {
      this.loading(true)
      try {
        const response = await api.lay_ds_thamso_md(this.axios)
        const result = await response.data
        if (result.error === '200' && result.error_code === 'BSS-00000000') {
          return result.data
        }
        return []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return []
      } finally {
        this.loading(false)
      }
    },
    getThamSoByMaThamSo (dsThamSo, maTS) {
      return dsThamSo.find(elm => elm.ma_ts === maTS)
    },
    ViewDataFromText () {
      if (this.html) {
        this.documentText = this.html
      }
    },
    async ViewDataFromService () {
      if (this.kieu === 0) {
        if (this.token) {
          const kq = await this.getInvViewNoPay(this.token)
          if (kq.errorCode !== 0) {
            this.$root.$toast.error(kq.faultString)
            this.isVisibleBtnChapNhan = false
            return false
          }
          this.documentText = kq.data.substring(0, 3) === 'ERR' ? '' : kq.data
        }
      } else if (this.fkey) {
        const kq = await this.getInvViewFkeyNoPay(this.fkey)
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          this.isVisibleBtnChapNhan = false
          return false
        }
        this.documentText = kq.data.substring(0, 3) === 'ERR' ? '' : kq.data
      }
    },
    async getInvViewNoPay (invToken) {
      this.loading(true)
      try {
        const response = await api.getInvViewNoPay(this.axios, { invToken })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return ''
      } finally {
        this.loading(false)
      }
    },
    async getInvViewFkeyNoPay (fkey) {
      this.loading(true)
      try {
        const response = await api.getInvViewFkeyNoPay(this.axios, { fkey })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return ''
      } finally {
        this.loading(false)
      }
    },
    onOk () {
      let thongbao = ''
      switch (this.vthaotac) {
        case 1:
          thongbao = 'Bạn có thực sự muốn xuất hóa đơn điện tử này?'
          break
        case 2:
          thongbao = 'Bạn có thực sự muốn điều chỉnh hóa đơn điện tử này?'
          break
        case 3:
          thongbao = 'Bạn có thực sự muốn thay thế hóa đơn điện tử này?'
          break
        case 4:
        case 5:
          thongbao = 'Bạn có thực sự muốn hủy hóa đơn điện tử này?'
          break
        default:
          thongbao = 'Bạn có thực sự xuất hóa đơn điện tử này?'
          break
      }
      this.$confirm(thongbao, 'Hóa đơn', { confirmButtonText: 'Có', cancelButtonText: 'Không' })
        .then(() => {
          this.$emit('onSuccess', { ok: true, thaoTacId: this.vthaotac })
        })
        .catch((e) => {
          this.$emit('onSuccess', { ok: false, thaoTacId: this.vthaotac })
        })
    }
  }
}
</script>
