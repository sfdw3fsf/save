<template>
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnXacNhan.visible" :class="{ disabledInput: !tsbtnXacNhan.enabled }" @click="tsbtnXacNhan_Clicked">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>{{ tsbtnXacNhan.title }}</a>
        </li>
        <li v-if="tsbtnDong.visible" :class="{ disabledInput: !tsbtnDong.enabled }" @click="tsbtnDong_Clicked">
          <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>{{ tsbtnDong.title }}</a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w90">
                {{ cboLoaiPhieu.label }}
              </div>
              <div class="value">
                <SelectExt ref="cboNghiepVu" v-model="cboLoaiPhieu.value" :dataSource="cboLoaiPhieu.list" dataTextField="LOAIPHIEU" dataValueField="LOAIPHIEU_ID" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import api from './api'
export default {
  components: { breadcrumb },
  emits: ['tsbtnDong_Clicked', 'tsbtnXacNhan_Clicked'],
  props: {
    vdsLoaiPhieu: {
      type: [Array, Object],
      required: true
    }
  },
  data() {
    return {
      localLoading: 0,
      tsbtnXacNhan: { visible: true, enabled: true, title: 'Xác nhận' },
      tsbtnDong: { visible: true, enabled: true, title: 'Đóng' },
      cboLoaiPhieu: {
        visible: true,
        enabled: true,
        label: 'Loại phiếu',
        list: [],
        value: null
      }
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
    async tsbtnXacNhan_Clicked() {
      if (this.cboLoaiPhieu.value == null) {
        this.$toast.warning('Vui lòng chọn loại phiếu!')
        return
      }
      if (!confirm(`Bạn có muốn tạo phiếu đề nghị không?`)) return
      this.$emit('tsbtnXacNhan_Clicked', { loaiPhieuId: this.cboLoaiPhieu.value })
    },
    async tsbtnDong_Clicked() {
      this.$emit('tsbtnDong_Clicked', {})
    }
  },
  async mounted() {
    this.cboLoaiPhieu.list = this.vdsLoaiPhieu
    this.cboLoaiPhieu.value = this.cboLoaiPhieu.list[0].LOAIPHIEU_ID
  }
}
</script>
