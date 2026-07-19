<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <!-- Actions khi hiển thị form chi tiết -->
        <li v-if="detailCurrentMode == 'EDIT' || detailCurrentMode == 'ADD'">
          <b-link href="javascript:void(0)" @click="onSaveDetail"> <span class="icon one-save"></span>Lưu</b-link>
        </li>
        <li v-if="detailCurrentMode == 'VIEW'">
          <b-link href="javascript:void(0)" @click="onEditDetail"> <span class="icon fa fa-edit"></span>Sửa</b-link>
        </li>
        <li v-if="detailCurrentMode === 'VIEW'">
          <b-link href="javascript:void(0)" @click="onAddNew"> <span class="icon one-file-plus"></span>Nhập mới</b-link>
        </li>
        <li>
          <b-link href="javascript:void(0)" @click="onRefreshDetail" :class="{ disabled: loading }">
            <span class="icon fa one-refresh"></span>Làm mới</b-link
          >
        </li>
        <li>
          <b-link href="javascript:void(0)" @click="onCancel" :disabled="detailCurrentMode == 'VIEW'">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span>Huỷ</b-link
          >
        </li>
      </ul>
    </div>
    <div class="page-content">
      <!-- Thông tin chi tiết thiết bị -->
      <ChiTietThietBiPageV2
        ref="deviceDetail"
        :device-type-id="deviceTypeId"
        @mode-changed="onModeChanged"
        @saved="onDeviceSaved"
      />
    </div>
  </div>
</template>
<script>
import ChiTietThietBiPageV2 from '../chitiet-thietbi-idc/index.vue'
import { DEVICE_TYPE_MAP, getDeviceUrls } from '../const.js'

export default {
  name: 'PhysicalequpmentDeviceWrapper',
  components: {
    ChiTietThietBiPageV2
  },
  props: {
    deviceTypeId: {
      type: Number,
      required: true
    }
  },
  computed: {
    deviceUrls() {
      return getDeviceUrls(this.deviceTypeId)
    }
  },
  data() {
    return {
      id: null,
      DEVICE_TYPE_MAP,
      loading: false,
      detailCurrentMode: 'VIEW'
    }
  },
  watch: {
    '$route.query.id': {
      handler() {
        // Đợi component mount xong trước khi xử lý route
        this.$nextTick(() => {
          this.handleRouteLogic()
        })
      },
      immediate: true
    }
  },
  methods: {
    async handleRouteLogic() {
      if (!this.$refs.deviceDetail || !this.$refs.deviceDetail.show) {
        this.$nextTick(() => {
          if (this.$refs.deviceDetail && this.$refs.deviceDetail.show) {
            this.handleRouteLogic()
          }
        })
        return
      }

      const deviceId = this.$route.query.id
      const idNumber = deviceId ? parseInt(deviceId, 10) : 0
      
      // Nếu id <= 0, hiển thị form thêm mới
      if (!deviceId || isNaN(idNumber) || idNumber <= 0) {
        this.detailCurrentMode = 'ADD'
        const item = {
          loaiThietBiId: this.deviceTypeId
        }
        this.$refs.deviceDetail.show(item, 'ADD')
        this.id = null
        return
      }
      this.id = idNumber
      this.detailCurrentMode = 'VIEW'
        await this.$refs.deviceDetail.show({ id: idNumber }, 'VIEW')
        // Sau khi load thành công, kiểm tra loại thiết bị
        await this.$nextTick()
        const loadedItem = this.$refs.deviceDetail?.currentItem
        if (loadedItem && loadedItem.loaiThietBiId) {
          this.checkDeviceTypeMatch(loadedItem.loaiThietBiId)
        }
    },
    checkDeviceTypeMatch(loadedDeviceTypeId) {
      // Nếu loại thiết bị không khớp với route hiện tại, redirect sang route đúng
      if (loadedDeviceTypeId !== this.deviceTypeId) {
        const correctUrls = getDeviceUrls(loadedDeviceTypeId)
        if (correctUrls) {
          // Redirect sang route đúng với loại thiết bị tương ứng
          this.$router.push({
            path: correctUrls.detailUrl,
            query: { ...this.$route.query, id: this.id }
          })
        } else {
          // Nếu không tìm thấy route tương ứng, về danh sách hiện tại
          this.$root.toastError('Loại thiết bị không khớp với trang hiện tại')
          const query = { ...this.$route.query }
          delete query.id
          this.$router.push({
            path: this.deviceUrls.listUrl,
            query: query
          })
        }
      }
    },
    onModeChanged(mode) {
      this.detailCurrentMode = mode
      this.updateDetailActions()
    },
    onDeviceSaved() {
      // Sau khi lưu thành công, chuyển về VIEW mode
      this.detailCurrentMode = 'VIEW'
      this.updateDetailActions()
    },
    updateDetailActions() {
      if (this.$refs.deviceDetail) {
        this.detailCurrentMode = this.$refs.deviceDetail.currentMode || 'VIEW'
      }
    },
    async onSaveDetail() {
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.onSave) {
        this.loading = true
        try {
          await this.$refs.deviceDetail.onSave()
          const newId = this.$refs.deviceDetail.currentItem?.id
          if (newId && newId !== this.id) {
            this.id = newId
            const query = { ...this.$route.query, id: newId }
            delete query.action
            this.$router.push({
              path: this.deviceUrls.detailUrl,
              query: query
            })
          }
          this.detailCurrentMode = 'VIEW'
          this.updateDetailActions()
        } catch (error) {
          // Error đã được handle trong component con
        } finally {
          this.loading = false
        }
      }
    },
    onEditDetail() {
      // Chuyển sang chế độ EDIT
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.onEditDetail) {
        this.$refs.deviceDetail.onEditDetail()
        this.detailCurrentMode = 'EDIT'
      }
    },
    onAddNew() {
      this.detailCurrentMode = 'ADD'
      this.$router.push(this.deviceUrls.detailUrl).then(() => {
        if (this.$refs.deviceDetail) {
          this.$refs.deviceDetail.show({ loaiThietBiId: this.deviceTypeId }, 'ADD')
        }
      })
    },
    async onRefreshDetail() {
      // Làm mới dữ liệu chi tiết hiện tại
      if (this.loading) return

      this.loading = true
      try {
        if (this.$refs.deviceDetail && this.$refs.deviceDetail.onRefreshDetail) {
          await this.$refs.deviceDetail.onRefreshDetail()
        } else if (this.id) {
          // Reload lại chi tiết thiết bị
          await this.$refs.deviceDetail.show({ id: this.id }, 'VIEW')
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi làm mới dữ liệu')
      } finally {
        this.loading = false
      }
    },
    async onCancel() {
      if (!this.$refs.deviceDetail) return

      if (this.$refs.deviceDetail.resetThongTinChungValidation) {
        this.$refs.deviceDetail.resetThongTinChungValidation()
      }

      if (this.detailCurrentMode === 'ADD') {
        const query = { ...this.$route.query }
        delete query.action
        this.$router.push({
          path: this.deviceUrls.listUrl,
          query: query
        })
      } else if (this.detailCurrentMode === 'EDIT') {
        if (this.$refs.deviceDetail.onCancel) {
          await this.$refs.deviceDetail.onCancel()
        } else if (this.id) {
          await this.$refs.deviceDetail.show({ id: this.id }, 'VIEW')
        }
        this.detailCurrentMode = 'VIEW'
      }
    }
  }
}
</script>
