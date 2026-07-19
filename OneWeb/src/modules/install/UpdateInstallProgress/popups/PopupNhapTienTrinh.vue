<template>
  <div class="modal-dialog" style="width: 100%; max-width: 600px;">
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onSave">
              <span class="icon one-save"></span> Ghi lại
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <textarea name="" class="form-control" style="height: 400px;resize: none;" v-model="noiDungFilter"></textarea>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api'

export default {
  name: 'PopupNhapTienTrinh',
  props: {
    tienTrinh: {
      type: Object,
      default: () => {}
    }
  },
  data () {
    return {
      noiDungFilter: ''
    }
  },
  computed: {
    phanVungId () {
      return +this.$root.token.getPhanVungID()
    }
  },
  methods: {
    onInit () {
      this.noiDungFilter = this.tienTrinh.noiDung || ''
    },
    async onSave () {
      this.loading(true)
      try {
        const response = await api.ghiLaiTienTrinh(
          this.axios,
          {
            vphanvung_bancheo_id: this.phanVungId,
            vhdtb_id: this.tienTrinh.hdtbId,
            vtientrinhtc_id: this.tienTrinh.tienTrinhTCId,
            vnoidung: this.noiDungFilter
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200' && data.data === 'OK') {
          this.$root.$toast.success('Cập nhật thành công')
          this.$emit('onSuccess')
        } else {
          this.$root.$toast.error('Cập nhật không thành công')
        }
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
