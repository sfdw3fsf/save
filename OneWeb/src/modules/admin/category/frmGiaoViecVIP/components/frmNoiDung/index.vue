<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="btnGhiLai.visible" :class="{ disabledInput: !btnGhiLai.enabled }" @click="btnGhiLai_Click">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <textarea name="" v-model="txtNoiDung.value" class="form-control" style="height: 300px; resize: none;" :readonly="!txtNoiDung.enabled"></textarea>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import api from './api'
export default {
  components: { breadcrumb },
  emits: ['btnGhiLai_Clicked'],
  props: {
    row: {
      type: [Object],
      default: null,
      required: true
    },
    tientrinhtc_id: {
      type: [String, Number],
      default: null,
      required: false
    },
    
  },
  data() {
    return {
      localLoading: 0,
      tinh_khdn: [97, 100],
      btnGhiLai: { visible: true, enabled: true },
      txtNoiDung: { value: '', visible: true, enabled: true }
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  computed: {},
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async btnGhiLai_Click() {
      console.log('frmGiaoViecVip - frmNoiDung - btnGhiLai_Click')
      console.log(this.row)
      let kq = await this.callApiWrapper(api.sp_khdn_capnhat_tientrinh_tc, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vtinh_id: this.row.tinh_id,
        vhdtb_id: this.row.hdtb_id,
        vtientrinhtc_id: this.vtientrinhtc_id,
        vnoidung: `${this.txtNoiDung.value}`
      })
      if(kq == 'OK') {
        this.$toast.success('Thành công')
      } else {
        this.$toast.error('Không thành công')
      }
      this.$emit('btnGhiLai_Clicked')
    }
  },
  async mounted() {
    console.log('this.txtNoiDung.value')
    console.log(this.row)
    this.txtNoiDung.value = `${this.row.ma_tb}|${this.row.ten_tb}|${this.row.diachi_ld}`
  }
}
</script>
