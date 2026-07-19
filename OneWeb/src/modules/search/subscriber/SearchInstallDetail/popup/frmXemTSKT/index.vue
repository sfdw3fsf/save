<template>
  <div class="">
    <!-- Form -->
    <div class="">
      <!-- <div class="list-actions-top">
        <ul class="list">
          <li @click="onClickXacNhan">
            <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận </a>
          </li>
        </ul>
      </div> -->
      <div class="popup-body">
        <div class="box-form">
          <div style="flex-grow: 1; background: white; margin: 0 0 10px 0;">
            <div ref="wbrTSKT" v-html="wbrTSKT.value" class="info-wrapper"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import api from './api'

export default {
  components: {},
  name: 'frmTSKT',
  props: {
    vphanvung_id: {
      type: [Number, String],
      require: true,
      default: null
    },
    vhdtb_id: {
      type: [Number, String],
      require: true,
      default: null
    }
  },
  data() {
    return {
      localLoading: 0,
      tinh_khdn: [97, 100],
      wbrTSKT: { enabled: true, visible: true, value: null }
    }
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
  },
  methods: {
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    }
  },
  async mounted() {
    try {
      let dt
      if (this.tinh_khdn.includes(this.vphanvung_id))
        dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat2, {
          vhdtb_id: this.vhdtb_id
        })
      else
        dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat_2, {
          vphanvung_id: this.vphanvung_id,
          vhdtb_id: this.vhdtb_id
        })
      this.wbrTSKT.value = ''
      for (let dr of dt) {
        this.wbrTSKT.value += `${dr.format_tt.replace('{0}', dr.ten_truong)}${dr.format_gt.replace('{0}', dr.giatri)}`.replaceAll('null', '')
      }
    } catch (ex) {
      this.wbrTSKT.value = `Chưa có dữ liệu; ${ex}`
    }
  }
}
</script>
<style scoped></style>
