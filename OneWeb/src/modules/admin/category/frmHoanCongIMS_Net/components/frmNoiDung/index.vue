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
      <textarea name="" v-model="txtNoiDung.value" class="form-control" style="height: 300px;resize: none;" :readonly="!txtNoiDung.enabled"></textarea>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
export default {
  components: { breadcrumb },
  props: {
    row: {
      type: [Object],
      default: null,
      required: true
    }
  },
  data() {
    return {
      localLoading: 0,
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
    async btnGhiLai_Click() {}
  },
  async mounted() {
    console.log('this.txtNoiDung.value')
    console.log(this.row)
    this.txtNoiDung.value =`${this.row.MA_TB}|${this.row.TEN_TB}|${this.row.DIACHI_LD}`
  }
}
</script>
