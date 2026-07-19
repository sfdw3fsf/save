<template src="./DeviceDetail.template.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import thietBiApi from './api/ThietBi.api'
import danhMucApi from './api/DanhMuc.api'
import ThongTinChiTietThietBi from './ThongTinChiTiet/index.vue'
import { NEW_DEFAULT_DEVICE, getDeviceUrls } from './const'

export default {
  name: 'DeviceDetail',
  components: {
    ThongTinChiTietThietBi
  },
  props: {
    deviceTypeId: {
      type: Number,
      required: true
    },
    deviceId: {
      type: Number,
      default: null
    },
    mode: {
      type: String,
      default: 'VIEW' // VIEW, EDIT, ADD
    }
  },
  data() {
    const isAddMode = this.mode === 'ADD' || this.$route.query.action === 'add'
    
    return {
      loading: false,
      isDetail: true,
      detailCurrentMode: isAddMode ? 'ADD' : 'VIEW',
      detailSaveButtonText: 'Lưu',
      deviceTypeList: [],
      defaultDevice: { ...NEW_DEFAULT_DEVICE }
    }
  },
  provide() {
    return {
      setCurrentItemValByProp: this.setCurrentItemValByProp,
      enableControls: this.enableControls,
      setDefaultItem: this.setDefaultItem,
      getDefaultDevice: this.getDefaultDevice,
      onView: this.onView,
      deviceTypeId: this.deviceTypeId
    }
  },
  computed: {
    deviceTypeCode() {
      const deviceType = this.deviceTypeList.find((item) => item.ID == this.deviceTypeId)
      return deviceType?.MA_LOAITBI ? deviceType.MA_LOAITBI.toLowerCase() : null
    },
    deviceUrls() {
      return getDeviceUrls(this.deviceTypeId)
    }
  },
  watch: {
    isDetail(newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.updateDetailActions()
        })
      }
    },
    mode(newMode) {
      if (newMode === 'ADD') {
        this.detailCurrentMode = 'ADD'
        this.setDefaultItem()
        this.$nextTick(() => {
          if (this.$refs.ThongTinChiTietThietBi) {
            this.$refs.ThongTinChiTietThietBi.show(null, null, 'ADD')
          }
        })
      }
    },
    deviceId(newId, oldId) {
      if (newId && newId !== oldId) {
        this.loadDeviceDetail(newId)
      }
    }
  },
  async created() {
    await this.getDeviceTypeApi()
    
    if (this.mode === 'ADD' || this.$route.query.action === 'add') {
      this.detailCurrentMode = 'ADD'
      this.isDetail = true
      this.$nextTick(() => {
        if (this.$refs.ThongTinChiTietThietBi) {
          this.$refs.ThongTinChiTietThietBi.show(null, null, 'ADD')
        }
      })
    } else if (this.deviceId) {
      await this.loadDeviceDetail(this.deviceId)
    }
  },
  methods: {
    async getDeviceTypeApi() {
      try {
        const response = await danhMucApi.getDeviceType(this.$root.context)
        this.deviceTypeList = response
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại thiết bị')
      }
    },
    onView(row) {
      const urls = getDeviceUrls(row.loaithietbi_id)
      if (!urls || !urls.detailUrl) {
        this.$root.toastWarning('Không tìm thấy loại thiết bị')
        return
      }
      this.$router.push(`${urls.detailUrl}?id=${row.id}`)
    },
    onAddNew() {
      this.detailCurrentMode = 'ADD'
      if (!this.deviceUrls || !this.deviceUrls.detailUrl) {
        this.$root.toastWarning('Không tìm thấy URL điều hướng')
        return
      }
      this.$router.push(this.deviceUrls.detailUrl)
    },
    onEditDetail() {
      // Chuyển sang chế độ EDIT
      if (this.$refs.ThongTinChiTietThietBi) {
        this.$refs.ThongTinChiTietThietBi.setMode('EDIT')
      }
    },
    setDefaultItem() {
      // Reset device data to default values
      this.defaultDevice = { ...NEW_DEFAULT_DEVICE }
    },
    getDefaultDevice() {
      return this.defaultDevice || { ...NEW_DEFAULT_DEVICE }
    },
    enableControls(enabled) {
      console.log('Controls enabled:', enabled)
    },
    async onSaveDevice(deviceData) {
      try {
        this.loading = true
        this.$root.showLoading(true)
        // Call API to create/update device
        const result = await thietBiApi.capnhatThietBi(this.$root.context, deviceData)

        if (result) {
          this.$root.toastSuccess('Lưu thiết bị thành công')
          const savedDeviceId = result.id || deviceData.id

          // Emit event để parent component có thể refresh list
          this.$emit('device-saved', savedDeviceId)

          if (this.$route.params.id || savedDeviceId) {
            const deviceId = savedDeviceId || this.$route.params.id
            // Xóa query param action=add khi navigate đến route chi tiết
            const query = { ...this.$route.query }
            delete query.action
            if (!this.deviceUrls || !this.deviceUrls.detailUrl) {
              this.$root.toastWarning('Không tìm thấy URL điều hướng')
              return result
            }
            this.$router.push({
              path: `${this.deviceUrls.detailUrl}/${deviceId}`,
              query: query
            })
            await this.$nextTick()
            await this.loadDeviceDetail(deviceId, 'VIEW')
          } else {
            this.closeDetail()
          }
          return result
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lưu thiết bị')
        return null
      } finally {
        this.loading = false
        this.$root.showLoading(false)
      }
    },
    async onCancel() {
      if (!this.isDetail || !this.$refs.ThongTinChiTietThietBi) return

      const { currentMode, currentItem } = this.$refs.ThongTinChiTietThietBi

      // Reset validation khi cancel
      if (this.$refs.ThongTinChiTietThietBi.resetValidation) {
        this.$refs.ThongTinChiTietThietBi.resetValidation()
      }

      if (currentMode === 'ADD') {
        this.closeDetail()
      } else if (currentMode === 'EDIT') {
        const deviceId = currentItem?.id
        deviceId ? await this.loadDeviceDetail(deviceId, 'VIEW') : this.$refs.ThongTinChiTietThietBi.setMode('VIEW')
      }
    },
    closeDetail() {
      // Đóng form chi tiết và navigate về route không có id và query param action
      const query = { ...this.$route.query }
      delete query.action // Xóa query param action=add

      if (!this.deviceUrls || !this.deviceUrls.listUrl) {
        this.$root.toastWarning('Không tìm thấy URL điều hướng')
        return
      }
      this.$router.push({
        path: this.deviceUrls.listUrl,
        query: query
      })
      this.isDetail = false
      this.detailCurrentMode = 'VIEW' // Reset về VIEW để form không hiển thị khi isDetail = false
      if (this.$refs.ThongTinChiTietThietBi) {
        this.$refs.ThongTinChiTietThietBi.visible = false
      }
      this.$emit('detail-closed')
    },
    async onRefreshDetail() {
      // Làm mới dữ liệu chi tiết hiện tại
      if (this.isDetail && this.$refs.ThongTinChiTietThietBi) {
        const currentId = this.$refs.ThongTinChiTietThietBi.currentItem?.id || 
                         this.deviceId || 
                         this.$route.params.id ||
                         this.$route.query.id
        if (currentId) {
          // Reload lại chi tiết thiết bị
          await this.loadDeviceDetail(currentId)
        }
      }
    },
    onSaveDetail() {
      if (this.$refs.ThongTinChiTietThietBi) {
        this.$refs.ThongTinChiTietThietBi.onSave()
      }
    },
    onBackDetail() {
      if (this.$refs.ThongTinChiTietThietBi) {
        const currentMode = this.$refs.ThongTinChiTietThietBi.currentMode
        if (currentMode === 'VIEW') {
          this.closeDetail()
        } else {
          this.onCancel()
        }
      }
    },
    updateDetailActions() {
      if (this.$refs.ThongTinChiTietThietBi) {
        this.detailCurrentMode = this.$refs.ThongTinChiTietThietBi.currentMode || 'VIEW'
        this.detailSaveButtonText = this.$refs.ThongTinChiTietThietBi.saveButtonText || 'Lưu'
      }
    },
    async loadDeviceDetail(deviceId, mode = null) {
      try {
        this.isDetail = true
        const displayMode = mode || this.$route.query.mode || 'VIEW'
        this.$nextTick(() => {
          if (this.$refs.ThongTinChiTietThietBi) {
            // Chỉ truyền deviceId và deviceTypeCode, component chi tiết sẽ tự gọi API
            this.$refs.ThongTinChiTietThietBi.show(deviceId, this.deviceTypeCode, displayMode)
          }
        })
      } catch (error) {
        console.error('Error loading device detail:', error)
        this.$root.toastError('Có lỗi xảy ra khi tải chi tiết thiết bị')
        if (this.deviceUrls && this.deviceUrls.listUrl) {
          this.$router.push(this.deviceUrls.listUrl)
        }
      }
    },
    // ================= PROVIDE/INJECT ============
    setCurrentItemValByProp(prop, value) {
      // This method is provided to child components via provide/inject
      // The actual currentItem is managed by ThongTinChiTietThietBi component
      if (this.$refs.ThongTinChiTietThietBi && this.$refs.ThongTinChiTietThietBi.currentItem) {
        this.$refs.ThongTinChiTietThietBi.currentItem[prop] = value
      }
    },
    onShowDetail() {
      this.isDetail = true
    },
    onHideDetail() {
      if (this.deviceUrls && this.deviceUrls.listUrl) {
        this.$router.push(this.deviceUrls.listUrl)
      }
    }
  }
}
</script>

