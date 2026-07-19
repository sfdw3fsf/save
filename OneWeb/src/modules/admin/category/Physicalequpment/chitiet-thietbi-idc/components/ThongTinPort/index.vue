<template src="./index.template.html"></template>
<script>
import TabInterface from '@/modules/tainguyen-idc/components/Tabs/TabInterface/index.vue'

import TabPower from '@/modules/tainguyen-idc/components/Tabs/TabPower/index.vue'
import TabConsole from '@/modules/tainguyen-idc/components/Tabs/TabConsole/index.vue'
import { PHANLOAI_NHOM } from '../../../const.js'
import api from '../../../api.js'

export default {
  name: 'ThongTinPort',
  components: {
    TabInterface,
    TabPower,
    TabConsole
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    loaiCongList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      tab_index: 1,
      selectedPorts: [] // Danh sách port được chọn để xóa
    }
  },
  mounted() {
    this.setDefaultActiveTab()
  },
  computed: {
    allPorts() {
      const list = Array.isArray(this.currentItem?.portList) ? this.currentItem.portList : []
      return list
    },
    interfacePorts() {
      return this.filterPortsByGroup(PHANLOAI_NHOM.INTERFACE)
    },
    powerPorts() {
      return this.filterPortsByGroup(PHANLOAI_NHOM.POWER)
    },
    consolePorts() {
      return this.filterPortsByGroup(PHANLOAI_NHOM.CONSOLE)
    },
    isViewMode() {
      return this.currentMode === 'VIEW'
    },
    hasInterfacePorts() {
      return this.interfacePorts.length > 0
    },
    hasPowerPorts() {
      return this.powerPorts.length > 0
    },
    hasConsolePorts() {
      return this.consolePorts.length > 0
    },
    activeTabIndex() {
      console.log(this.interfacePorts, this.consolePorts, this.powerPorts)

      // Ưu tiên tab có dữ liệu, nếu không có thì chọn tab đầu tiên
      if (this.hasInterfacePorts) return 1
      if (this.hasPowerPorts) return 2
      if (this.hasConsolePorts) return 3
      return 1 // Mặc định tab Interface
    }
  },
  methods: {
    showModalPortThietBi() {
      this.$emit('show-modal-port-thiet-bi')
    },

    editPort(portData) {
      // Đánh dấu port đang được edit
      const portToEdit = { ...portData, isEdit: true }

      // Nếu là port tạm thời, giữ nguyên ID tạm thời
      if (portData.isTemp) {
        portToEdit.isTemp = true
      }
      if (portData.TRANGTHAI_SD == 0) {
        this.$emit('edit-port', portToEdit)
      } else {
        this.$root.toastError('Port đang vận hành không thể chỉnh sửa !')
      }
    },
    // Determine group id of a port item using available fields and loaiCongList metadata
    resolveGroupId(port) {
      if (!port) return null
      // Sử dụng field đúng format với data grid
      const typeId = port.LOAICONG_ID
      if (typeId && Array.isArray(this.loaiCongList) && this.loaiCongList.length) {
        const type = this.loaiCongList.find((x) => x.ID == typeId)
        if (type) {
          return Number(type.THUOCNHOM || 0)
        }
      }
    },
    filterPortsByGroup(groupId) {
      return this.allPorts.filter((p) => this.resolveGroupId(p) === groupId)
    },
    setDefaultActiveTab() {
      // Tự động chọn tab có dữ liệu khi component được mount
      this.tab_index = this.activeTabIndex
    },

    // Xử lý khi port được chọn trong các tab
    onPortSelectionChanged(selectedPorts) {
      this.selectedPorts = selectedPorts || []
    },

    // ============= PORT CRUD OPERATIONS ============= //

    // Thêm port mới
    async onPortSaved(portData) {
      try {
        // Kiểm tra trùng tên port
        const excludeId = portData.isEdit ? portData.ID : null
        if (this.checkDuplicatePortName(portData.TEN, excludeId)) {
          this.$toast.error(`Tên port "${portData.TEN}" đã tồn tại!`)
          return
        }

        // Mapping loaiCong để lấy THUOCNHOM và phân loại port
        let phanLoai = PHANLOAI_NHOM.INTERFACE // Default là interface
        if (portData.LOAICONG_ID && Array.isArray(this.loaiCongList) && this.loaiCongList.length) {
          const loaiCong = this.loaiCongList.find((x) => x.ID == portData.LOAICONG_ID)
          if (loaiCong && loaiCong.THUOCNHOM) {
            phanLoai = Number(loaiCong.THUOCNHOM)
          }
        }

        // Tạo ID tạm thời cho port mới nếu chưa có ID
        if (!portData.ID || portData.ID === 0) {
          portData.ID = this.generateTempPortId()
          portData.isTemp = true // Đánh dấu là port tạm thời
        }

        // Thêm PHANLOAI vào portData
        const portWithPhanLoai = {
          ID: portData.ID,
          LOAICONG_ID: portData.LOAICONG_ID || null,
          TEN: portData.TEN.trim(),
          GIATRI_CONG: portData.GIATRI_CONG,
          PHANLOAI: phanLoai,
          THONGSO_KYTHUAT: portData.THONGSO_KYTHUAT,
          TRANGTHAI_SD: portData.TRANGTHAI_SD,
          TRANGTHAI_DAUNOI: portData.TRANGTHAI_DAUNOI,
          GHI_CHU: portData.GHI_CHU,
          isTemp: portData.isTemp || false,
          isEdit: portData.isEdit || false
        }

        if (portData.isEdit) {
          // Update mode - tìm và cập nhật port hiện có
          const index = this.allPorts.findIndex((port) => port.ID === portData.ID)
          if (index !== -1) {
            // Cập nhật port hiện có
            this.$set(this.currentItem.portList, index, { ...portWithPhanLoai })
            this.$toast.success('Đã cập nhật port thành công')
          } else {
            this.$toast.error('Không tìm thấy port để cập nhật')
          }
        } else {
          // Add mode - thêm port mới
          // Đảm bảo portList luôn là mảng trước khi push
          if (!Array.isArray(this.currentItem.portList)) {
            this.$set(this.currentItem, 'portList', [])
          }
          this.currentItem.portList.push({ ...portWithPhanLoai })
          this.$toast.success('Đã thêm port vào danh sách')
        }

        // Emit event để parent component biết có thay đổi
        this.$emit('port-changed')
      } catch (e) {
        this.$toast.error('Không thể xử lý port: ' + e.message)
      }
    },

    // Helper method để tạo ID tạm thời cho port mới
    generateTempPortId() {
      return 'temp_' + Date.now()
    },

    // Helper method để kiểm tra trùng tên port
    checkDuplicatePortName(portName, excludeId = null) {
      if (!portName || !Array.isArray(this.allPorts)) return false

      return this.allPorts.some(
        (port) => port.TEN && port.TEN.toLowerCase().trim() === portName.toLowerCase().trim() && port.ID !== excludeId
      )
    },

    // Xóa port
    async deletePort() {
      try {
        const ports = [].concat(this.selectedPorts || [])
        if (ports.length === 0) {
          this.$toast.warning('Vui lòng chọn port cần xóa')
          return
        }

        const portNames = ports.map((port) => port.TEN || port.ID).join(', ')
        const confirmFlag = await this.$confirm(
          `Bạn có chắc chắn muốn xoá ${ports.length} port: ${portNames}?`,
          'Xác nhận',
          {
            confirmButtonText: 'Xoá',
            cancelButtonText: 'Huỷ',
            type: 'warning',
            dangerouslyUseHTMLString: true
          }
        )
          .then(() => true)
          .catch(() => false)

        if (confirmFlag) {
          this.$root.showLoading(true)
          const portIdsToDelete = ports.map((port) => port.ID)

          // Phân loại port để xử lý khác nhau
          const tempPorts = ports.filter((port) => port.isTemp)
          const realPorts = ports.filter((port) => !port.isTemp)

          // Nếu có port đã tồn tại trên server, gọi API để xóa từng port một bằng vòng lặp for
          if (realPorts.length > 0) {
            try {
              // Chạy vòng lặp for để gọi API xóa từng port
              for (let i = 0; i < realPorts.length; i++) {
                const port = realPorts[i]
                const result = await api.deletePortThietBiIDC(this.axios, {
                  portId: port.ID
                })

                if (!result || result.error_code !== 'BSS-00000000') {
                  this.$root.toastError(
                    `Không thể xóa port ${port.TEN || port.ID}: ${result?.message_detail || 'Lỗi không xác định'}`
                  )
                  return
                }
              }
            } catch (error) {
              this.$root.toastError(error.message)
              return
            }
          }

          // Xóa port khỏi danh sách local
          if (Array.isArray(this.currentItem.portList)) {
            this.currentItem.portList = this.currentItem.portList.filter((port) => !portIdsToDelete.includes(port.ID))
          }

          // Hiển thị thông báo phù hợp
          let message = `Đã xóa ${ports.length} port thành công!`

          this.$toast.success(message)
          this.selectedPorts = [] // Reset selection

          // Emit event để parent component biết có thay đổi
          this.$emit('port-changed')
        }
      } catch (e) {
        this.$toast.error('Không thể xóa port: ' + e.message)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Submit tất cả port lên server
    async submitPortsToServer() {
      try {
        if (!Array.isArray(this.allPorts) || this.allPorts.length === 0) {
          return true
        }

        // Kiểm tra trùng tên port trước khi submit
        const duplicateNames = []
        const portNames = new Set()

        for (const port of this.allPorts) {
          if (port.TEN && port.TEN.trim()) {
            const normalizedName = port.TEN.toLowerCase().trim()
            if (portNames.has(normalizedName)) {
              duplicateNames.push(port.TEN)
            } else {
              portNames.add(normalizedName)
            }
          }
        }

        if (duplicateNames.length > 0) {
          this.$root.toastError(`Tên port không được trùng nhau: ${duplicateNames.join(', ')}`)
          return false
        }

        // Lọc bỏ port tạm thời khi submit (chỉ submit port đã có ID thật)
        const convertedPorts = this.allPorts.map((port) => {
          if (typeof port.ID === 'string' && port.ID.startsWith('temp_')) {
            return { ...port, ID: null, isTemp: false } // reset ID
          }
          return port
        })

        if (convertedPorts.length > 0) {
          const result = await api.upsertPortThietBiIDC(this.axios, {
            portList: convertedPorts,
            thietBiId: this.currentItem.id
          })

          if (result && result.error_code === 'BSS-00000000') {
            this.$toast.success('Đã lưu danh sách port thành công!')
            return true
          } else {
            this.$root.toastError(result?.message_detail || 'Không thể lưu danh sách port')
            return false
          }
        }
        return true
      } catch (error) {
        this.$root.toastError('Lỗi khi lưu danh sách port: ' + error.message)
        return false
      }
    }
  }
}
</script>
