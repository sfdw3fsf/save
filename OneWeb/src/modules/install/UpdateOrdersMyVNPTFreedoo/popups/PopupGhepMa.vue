<template>
  <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">
          <span class="icon fa fa-angle-up"></span>
          Ghép mã
        </div>
        <div class="box-center-button">
          <div class="box-col">
            <div class="title-bg-main">
              <span class="title">Hợp đồng - Phụ lục cần ghép</span>
            </div>
            <div class="info-row">
              <div class="key w60">Mã GD</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="onShowHopDongCanGhep">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input
                    type="text"
                    class="form-control highlight"
                    ref="maGiaoDichCanGhepRef"
                    v-model="maGiaoDichCanGhep"
                    @keyup.enter="onEnterGiaoDichCanGhep"
                  >
                </div>
              </div>
            </div>
            <div class="note red fw6">
              <span v-html="contentCanGhepRpl"></span>
            </div>
          </div>
          <div class="actions">
            <button class="btn btn-primary" @click="ghepMaHopDong">
              Ghép
            </button>
          </div>
          <div class="box-col">
            <div class="title-bg-main">
              <span class="title">Hơp đồng yêu cầu</span>
            </div>
            <div class="info-row">
              <div class="key w60">Mã GD</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="onShowHopDongYeuCau" :disabled="isDisabledYeuCau">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input
                    type="text"
                    class="form-control highlight"
                    ref="maGiaoDichYeuCauRef"
                    v-model="maGiaoDichYeuCau"
                    @keyup.enter="onEnterGiaoDichYeuCau"
                    :readonly="isDisabledYeuCau"
                  >
                </div>
              </div>
            </div>
            <div class="article text-warning fw6">
              <span v-html="contentYeuCauRpl"></span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <b-modal
      ref="popupFrmTraCuuHopDong"
      size="xl"
      title="Tra cứu hợp đồng, phụ lục"
      hide-footer
      body-class="modal-body p-0"
      modal-class="bss-modal bss-modal-tracuu-danhba"
      @close="onCloseSearchContract"
    >
      <search-contract
        :loaihd_id="loaiHdId"
        :dichvuvt_id="0"
        :ngay_yc="'0'"
        :tthd_id="0"
        :kieu="1"
        :isPopup="1"
        @accept="acceptSearch"
      />
    </b-modal>
  </div>
</template>

<script>
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import api from '../api'
import { LoaiHopDong } from '@/const/enums'

export default {
  name: 'PopupGhepMa',
  components: { SearchContract },
  data () {
    return {
      maGiaoDichCanGhep: '',
      contentCanGhep: '',
      maGiaoDichYeuCau: '',
      contentYeuCau: '',
      isDisabledYeuCau: true,
      loaiHdId: 0,
      hdkhIdCanGhep: null,
      hdkhIdYeuCau: null,
      position: { X: 'center', Y: 'top' },
      isFindMaGiaoDichCanGhep: false
    }
  },
  computed: {
    contentCanGhepRpl () {
      if (this.contentCanGhep) {
        return this.contentCanGhep
          .replace(/\\t/g, '')
          .replace(/'/g, '')
          .replace(/(?:\\r\\n|\\r|\\n)/g, '<br />')
      }
      return ''
    },
    contentYeuCauRpl () {
      if (this.contentYeuCau) {
        return this.contentYeuCau
          .replace(/\\t/g, '')
          .replace(/'/g, '')
          .replace(/(?:\\r\\n|\\r|\\n)/g, '<br />')
      }
      return ''
    }
  },
  methods: {
    clear () {
      this.maGiaoDichCanGhep = ''
      this.contentCanGhep = ''
      this.maGiaoDichYeuCau = ''
      this.contentYeuCau = ''
      this.isDisabledYeuCau = true
      this.loaiHdId = 0
      this.hdkhIdCanGhep = null
      this.hdkhIdYeuCau = null
    },
    onShowHopDongCanGhep () {
      this.loaiHdId = 0
      this.isFindMaGiaoDichCanGhep = true
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    onShowHopDongYeuCau () {
      this.loaiHdId = LoaiHopDong.TUVAN_DV
      this.isFindMaGiaoDichCanGhep = false
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    async acceptSearch (data) {
      if (!data.hopdong.ma_gd) {
        this.$root.$toast.warning('Bạn chưa chọn mã giao dịch cần ghép')
        this.$refs.popupFrmTraCuuHopDong.hide()
        return false
      }
      if (this.loaiHdId === 0) {
        this.maGiaoDichCanGhep = data.hopdong.ma_gd
        await this.onEnterGiaoDichCanGhep()
      } else {
        this.maGiaoDichYeuCau = data.hopdong.ma_gd
        await this.onEnterGiaoDichYeuCau()
      }
      this.$refs.popupFrmTraCuuHopDong.hide()
    },
    onCloseSearchContract () {
      if (this.isFindMaGiaoDichCanGhep && !this.maGiaoDichCanGhep) {
        this.$root.$toast.warning('Bạn chưa chọn mã giao dịch cần ghép')
        return false
      }

      if (!this.isFindMaGiaoDichCanGhep && !this.maGiaoDichYeuCau) {
        this.$root.$toast.warning('Bạn chưa chọn mã giao dịch cần ghép')
        return false
      }
    },
    async onEnterGiaoDichCanGhep () {
      if (this.maGiaoDichCanGhep === '') {
        this.$root.$toast.warning('Hãy nhập mã giao dịch cần ghép')
        this.$refs.maGiaoDichCanGhepRef.focus()
        this.contentCanGhep = ''
        return false
      }
      this.loading(true)
      try {
        const response = await api.getThongTinHopDongCanGhep(this.axios, { ma_gd: this.maGiaoDichCanGhep })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.contentCanGhep = data.data.thongtin_hdpl
          this.contentYeuCau = data.data.thongtin_hdyc
          this.hdkhIdCanGhep = data.data.hdkh
          const message = await this.kiemTraHopDong(this.hdkhIdCanGhep)
          if (message.toUpperCase() === 'OK') {
            this.isDisabledYeuCau = false
            this.contentYeuCau = 'Hãy chọn đơn hàng yêu cầu để gán'
            this.maGiaoDichYeuCau = ''
            this.hdkhIdYeuCau = null
          } else {
            this.contentCanGhep = message
            this.isDisabledYeuCau = true
          }
        } else {
          this.$root.$toast.warning('Không tìm thấy thông tin hợp đồng cần ghép')
          this.contentCanGhep = ''
          this.isDisabledYeuCau = true
        }
      } catch (e) {
        this.$root.$toast.error('Không lấy được thông tin hợp đồng cần ghép')
      } finally {
        this.loading(false)
      }
    },
    async onEnterGiaoDichYeuCau () {
      if (this.maGiaoDichYeuCau === '') {
        this.$root.$toast.warning('Hãy nhập mã giao dịch yêu cầu')
        this.$refs.maGiaoDichYeuCauRef.focus()
        this.contentYeuCau = ''
        return false
      }
      this.loading(true)
      try {
        const response = await api.getThongTinHopDongYeuCau(this.axios, { ma_gd: this.maGiaoDichYeuCau })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.contentYeuCau = data.data.thongtin_hdyc
          this.hdkhIdYeuCau = data.data.hdkh
        } else {
          this.contentYeuCau = ''
        }
      } catch (e) {
        this.$root.$toast.error('Không lấy được thông tin hợp đồng yêu cầu')
      } finally {
        this.loading(false)
      }
    },
    async kiemTraHopDong (hdkhId) {
      this.loading(true)
      try {
        const response = await api.kiemTraHopDong(this.axios, { hdkh_id: hdkhId })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') return data.message
        return ''
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra hợp đồng')
        return false
      } finally {
        this.loading(false)
      }
    },
    async ghepMaHopDong () {
      if (!this.hdkhIdYeuCau || !this.hdkhIdCanGhep) {
        this.$root.$toast.warning('Hãy chọn mã giao dịch cần ghép trước')
        return false
      }
      this.$confirm(
        'Tiến hành ghép mã ?',
        'Ghép mã',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        this.loading(true)
        try {
          const response = await api.ghepMaHopDong(
            this.axios,
            { ma_ghep_id: this.hdkhIdCanGhep, ma_yc_id: this.hdkhIdYeuCau }
          )
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && data.error === '200' && data.data === '1') {
            this.$root.$toast.success('Ghép mã thành công')
            this.contentCanGhep = ''
            this.contentYeuCau = ''
            this.hdkhIdCanGhep = null
            this.hdkhIdYeuCau = null
          } else {
            this.$root.$toast.error('Ghép mã không thành công')
          }
        } catch (e) {
          this.$root.$toast.error('Không thể ghép mã')
          return false
        } finally {
          this.loading(false)
        }
      }).catch((e) => {})
    }
  }
}
</script>
