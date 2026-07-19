<template>
  <div v-if="visible" class="modal-overlay" @click.self="handleClose">
    <div class="modal-container">
      <div class="modal-header">
        <h4>
          Gắn thiết bị vào Rack
        </h4>
        <button class="btn-close" @click="handleClose">
        </button>
      </div>

      <div class="modal-body">
        <!-- Panel bên trái: Danh sách thiết bị -->
        <div class="left-panel">
          <div class="panel-header">
            <h5>
              Danh sách thiết bị
            </h5>
            <div class="search-box">
              <input
                type="text"
                v-model="searchKeyword"
                placeholder="Tìm kiếm thiết bị..."
                class="form-control form-control-sm"
              />
            </div>
          </div>

          <div class="device-list">
            <div
              v-for="device in filteredDevices"
              :key="device.id"
              class="device-item"
              :class="{ 'device-installing': device.isInstalling }"
              draggable="true"
              @dragstart="handleDragStart($event, device)"
              @dragend="handleDragEnd"
            >
              <div class="device-icon">
                <i :class="getDeviceIcon(device.loaiTbi)"></i>
              </div>
              <div class="device-info">
                <div class="device-name" :title="device.tenThietBi">
                  {{ device.tenThietBi }}
                </div>
                <div class="device-specs">
                  <span class="spec-item">
                    <i class="fa fa-arrows-v"></i> {{ device.soU }}U
                  </span>
                  <span class="spec-item">
                    <i class="fa fa-bolt"></i> {{ device.congSuat }}W
                  </span>
                  <span v-if="!device.isRPDU" class="spec-item">
                    <i class="fa fa-eye"></i>
                    {{ getMatRackText(device.matRack) }}
                  </span>
                  <span v-if="device.isRPDU" class="spec-item spec-rpdu">
                    <i class="fa fa-plug"></i>
                    rPDU (Cạnh rack)
                  </span>
                </div>
                <div class="device-type">
                  {{ device.loaiTbi }}
                </div>
              </div>
              <div class="device-drag-hint">
                <i class="fa fa-hand-rock-o"></i>
              </div>
            </div>

            <div v-if="filteredDevices.length === 0" class="no-devices">
              <i class="fa fa-inbox"></i>
              <p>Không tìm thấy thiết bị</p>
            </div>
          </div>
        </div>

        <!-- Panel bên phải: Đồ họa rack + rPDU -->
        <div class="right-panel">
          <div class="rack-views-container">
            <!-- Mặt trước với rPDU 2 bên -->
            <div class="rack-view-section">
              <div class="rack-view-header">
                <h5>
                  <i class="fa fa-server"></i>
                  Mặt trước
                </h5>
                <div class="usage-info">
                  {{ getFrontUsedU() }}/{{ totalU }} U ({{ getFrontUsagePercentage() }}%)
                </div>
              </div>

              <div class="rack-graphic">
                <!-- U Labels bên trái -->
                <div class="u-labels u-labels-left">
                  <div
                    v-for="u in totalU"
                    :key="'front-label-left-' + u"
                    class="u-label"
                    :style="{ height: uHeight + 'px' }"
                  >
                    U{{ totalU - u + 1 }}
                  </div>
                </div>

                <!-- rPDU cạnh trái (mặt trước) -->
                <div class="rpdu-side-column">
                  <div class="rpdu-column-header">Trái</div>
                  <div class="rpdu-slots">
                    <div
                      v-for="u in totalU"
                      :key="'front-rpdu-left-' + u"
                      class="rpdu-slot"
                      :class="getRPDUSlotClass('front', 'left', totalU - u + 1)"
                      :style="{ height: uHeight + 'px' }"
                      @dragover.prevent="handleRPDUDragOver($event, 'front', 'left', totalU - u + 1)"
                      @dragleave="handleDragLeave"
                      @drop="handleRPDUDrop($event, 'front', 'left', totalU - u + 1)"
                    >
                      <div v-if="getRPDUAt('front', 'left', totalU - u + 1)" class="rpdu-device">
                        <button
                          v-if="getRPDUAt('front', 'left', totalU - u + 1).isFirstU"
                          class="btn-remove-device"
                          @click="removeRPDU('front', 'left', totalU - u + 1)"
                        >
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                      <!-- Tên rPDU overlay cho toàn bộ khối -->
                      <div 
                        v-if="getRPDUAt('front', 'left', totalU - u + 1) && getRPDUAt('front', 'left', totalU - u + 1).isFirstU" 
                        class="rpdu-label-overlay"
                        :style="{ height: (getRPDUAt('front', 'left', totalU - u + 1).soU * uHeight) + 'px' }"
                        :title="getRPDUAt('front', 'left', totalU - u + 1).tenThietBi"
                      >
                        <span class="rpdu-name">
                          {{ getRPDUAt('front', 'left', totalU - u + 1).tenThietBi }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Rack slots mặt trước -->
                <div class="rack-slots">
                  <div
                    v-for="u in totalU"
                    :key="'front-' + u"
                    class="rack-u-row"
                    :style="{ height: uHeight + 'px' }"
                  >
                    <div
                      class="rack-full-slot"
                      :class="getSlotClass('front', totalU - u + 1)"
                      @dragover.prevent="handleDragOver($event, 'front', totalU - u + 1)"
                      @dragleave="handleDragLeave"
                      @drop="handleDrop($event, 'front', totalU - u + 1)"
                    >
                      <div v-if="getDeviceAt('front', totalU - u + 1)" class="slot-device">
                        <span class="device-label">
                          {{ getDeviceAt('front', totalU - u + 1).tenThietBi }}
                        </span>
                        <button
                          class="btn-remove-device"
                          @click="removeDevice('front', totalU - u + 1)"
                        >
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- rPDU cạnh phải (mặt trước) -->
                <div class="rpdu-side-column">
                  <div class="rpdu-column-header">Phải</div>
                  <div class="rpdu-slots">
                    <div
                      v-for="u in totalU"
                      :key="'front-rpdu-right-' + u"
                      class="rpdu-slot"
                      :class="getRPDUSlotClass('front', 'right', totalU - u + 1)"
                      :style="{ height: uHeight + 'px' }"
                      @dragover.prevent="handleRPDUDragOver($event, 'front', 'right', totalU - u + 1)"
                      @dragleave="handleDragLeave"
                      @drop="handleRPDUDrop($event, 'front', 'right', totalU - u + 1)"
                    >
                      <div v-if="getRPDUAt('front', 'right', totalU - u + 1)" class="rpdu-device">
                        <button
                          v-if="getRPDUAt('front', 'right', totalU - u + 1).isFirstU"
                          class="btn-remove-device"
                          @click="removeRPDU('front', 'right', totalU - u + 1)"
                        >
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                      <!-- Tên rPDU overlay cho toàn bộ khối -->
                      <div 
                        v-if="getRPDUAt('front', 'right', totalU - u + 1) && getRPDUAt('front', 'right', totalU - u + 1).isFirstU" 
                        class="rpdu-label-overlay"
                        :style="{ height: (getRPDUAt('front', 'right', totalU - u + 1).soU * uHeight) + 'px' }"
                        :title="getRPDUAt('front', 'right', totalU - u + 1).tenThietBi"
                      >
                        <span class="rpdu-name">
                          {{ getRPDUAt('front', 'right', totalU - u + 1).tenThietBi }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- U Labels bên phải -->
                <div class="u-labels u-labels-right">
                  <div
                    v-for="u in totalU"
                    :key="'front-label-right-' + u"
                    class="u-label"
                    :style="{ height: uHeight + 'px' }"
                  >
                    U{{ totalU - u + 1 }}
                  </div>
                </div>
              </div>
            </div>

            <!-- Mặt sau với rPDU 2 bên -->
            <div class="rack-view-section">
              <div class="rack-view-header">
                <h5>
                  <i class="fa fa-server"></i>
                  Mặt sau
                </h5>
                <div class="usage-info">
                  {{ getBackUsedU() }}/{{ totalU }} U ({{ getBackUsagePercentage() }}%)
                </div>
              </div>

              <div class="rack-graphic">
                <!-- U Labels bên trái -->
                <div class="u-labels u-labels-left">
                  <div
                    v-for="u in totalU"
                    :key="'back-label-left-' + u"
                    class="u-label"
                    :style="{ height: uHeight + 'px' }"
                  >
                    U{{ totalU - u + 1 }}
                  </div>
                </div>

                <!-- rPDU cạnh trái (mặt sau) -->
                <div class="rpdu-side-column">
                  <div class="rpdu-column-header">Trái</div>
                  <div class="rpdu-slots">
                    <div
                      v-for="u in totalU"
                      :key="'back-rpdu-left-' + u"
                      class="rpdu-slot"
                      :class="getRPDUSlotClass('back', 'left', totalU - u + 1)"
                      :style="{ height: uHeight + 'px' }"
                      @dragover.prevent="handleRPDUDragOver($event, 'back', 'left', totalU - u + 1)"
                      @dragleave="handleDragLeave"
                      @drop="handleRPDUDrop($event, 'back', 'left', totalU - u + 1)"
                    >
                      <div v-if="getRPDUAt('back', 'left', totalU - u + 1)" class="rpdu-device">
                        <button
                          v-if="getRPDUAt('back', 'left', totalU - u + 1).isFirstU"
                          class="btn-remove-device"
                          @click="removeRPDU('back', 'left', totalU - u + 1)"
                        >
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                      <!-- Tên rPDU overlay cho toàn bộ khối -->
                      <div 
                        v-if="getRPDUAt('back', 'left', totalU - u + 1) && getRPDUAt('back', 'left', totalU - u + 1).isFirstU" 
                        class="rpdu-label-overlay"
                        :style="{ height: (getRPDUAt('back', 'left', totalU - u + 1).soU * uHeight) + 'px' }"
                        :title="getRPDUAt('back', 'left', totalU - u + 1).tenThietBi"
                      >
                        <span class="rpdu-name">
                          {{ getRPDUAt('back', 'left', totalU - u + 1).tenThietBi }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Rack slots mặt sau -->
                <div class="rack-slots">
                  <div
                    v-for="u in totalU"
                    :key="'back-' + u"
                    class="rack-u-row"
                    :style="{ height: uHeight + 'px' }"
                  >
                    <div
                      class="rack-full-slot"
                      :class="getSlotClass('back', totalU - u + 1)"
                      @dragover.prevent="handleDragOver($event, 'back', totalU - u + 1)"
                      @dragleave="handleDragLeave"
                      @drop="handleDrop($event, 'back', totalU - u + 1)"
                    >
                      <div v-if="getDeviceAt('back', totalU - u + 1)" class="slot-device">
                        <span class="device-label">
                          {{ getDeviceAt('back', totalU - u + 1).tenThietBi }}
                        </span>
                        <button
                          class="btn-remove-device"
                          @click="removeDevice('back', totalU - u + 1)"
                        >
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- rPDU cạnh phải (mặt sau) -->
                <div class="rpdu-side-column">
                  <div class="rpdu-column-header">Phải</div>
                  <div class="rpdu-slots">
                    <div
                      v-for="u in totalU"
                      :key="'back-rpdu-right-' + u"
                      class="rpdu-slot"
                      :class="getRPDUSlotClass('back', 'right', totalU - u + 1)"
                      :style="{ height: uHeight + 'px' }"
                      @dragover.prevent="handleRPDUDragOver($event, 'back', 'right', totalU - u + 1)"
                      @dragleave="handleDragLeave"
                      @drop="handleRPDUDrop($event, 'back', 'right', totalU - u + 1)"
                    >
                      <div v-if="getRPDUAt('back', 'right', totalU - u + 1)" class="rpdu-device">
                        <button
                          v-if="getRPDUAt('back', 'right', totalU - u + 1).isFirstU"
                          class="btn-remove-device"
                          @click="removeRPDU('back', 'right', totalU - u + 1)"
                        >
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                      <!-- Tên rPDU overlay cho toàn bộ khối -->
                      <div 
                        v-if="getRPDUAt('back', 'right', totalU - u + 1) && getRPDUAt('back', 'right', totalU - u + 1).isFirstU" 
                        class="rpdu-label-overlay"
                        :style="{ height: (getRPDUAt('back', 'right', totalU - u + 1).soU * uHeight) + 'px' }"
                        :title="getRPDUAt('back', 'right', totalU - u + 1).tenThietBi"
                      >
                        <span class="rpdu-name">
                          {{ getRPDUAt('back', 'right', totalU - u + 1).tenThietBi }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- U Labels bên phải -->
                <div class="u-labels u-labels-right">
                  <div
                    v-for="u in totalU"
                    :key="'back-label-right-' + u"
                    class="u-label"
                    :style="{ height: uHeight + 'px' }"
                  >
                    U{{ totalU - u + 1 }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-secondary" @click="handleClose">
          <i class="fa fa-times"></i>
          Đóng
        </button>
        <button class="btn btn-primary" @click="handleSave">
          <i class="fa fa-save"></i>
          Lưu lại
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModalGanThietBiVaoRack',

  props: {
    rackId: {
      type: [String, Number],
      required: true
    }
  },

  data () {
    return {
      visible: false,
      searchKeyword: '',
      totalU: 42,
      uHeight: 20, // Chiều cao mỗi U (px)
      devices: [], // Danh sách thiết bị có thể gắn
      installedDevices: [], // Danh sách thiết bị đã gắn vào rack (không bao gồm rPDU)
      installedRPDUs: [], // Danh sách rPDU đã gắn bên trái/phải rack
      draggedDevice: null,
      dragOverSlot: null
    }
  },

  computed: {
    filteredDevices () {
      if (!this.searchKeyword) {
        return this.devices
      }
      const keyword = this.searchKeyword.toLowerCase()
      return this.devices.filter(
        (device) =>
          device.tenThietBi.toLowerCase().includes(keyword) ||
          device.loaiTbi.toLowerCase().includes(keyword)
      )
    }
  },

  methods: {
    async show () {
      this.visible = true
      await this.fetchRackInfo()
      await this.fetchAvailableDevices()
      await this.fetchInstalledDevices()
    },

    hide () {
      this.visible = false
      this.resetData()
    },

    handleClose () {
      this.hide()
    },

    resetData () {
      this.searchKeyword = ''
      this.devices = []
      this.installedDevices = []
      this.installedRPDUs = []
      this.draggedDevice = null
      this.dragOverSlot = null
    },

    async fetchRackInfo () {
      try {
        const response = await this.$root.context.post('/web-ecms/thongtin-rack/get-by-id', {
          id: this.rackId
        })
        if (response && response.data && response.data.data) {
          this.totalU = response.data.data.SO_U || 42
        }
      } catch (error) {
        console.error('Error fetching rack info:', error)
      }
    },

    async fetchAvailableDevices () {
      try {
        // API lấy danh sách thiết bị chưa lắp vào rack
        const response = await this.$root.context.post('/web-ecms/thiet-bi/get-danh-sach-chua-gan-rack', {
          // Các filter nếu cần
        })
        if (response && response.data) {
          this.devices = response.data.map((item) => ({
            id: item.ID,
            tenThietBi: item.TEN_THIETBI,
            loaiTbi: item.LOAI_TBI,
            soU: item.SO_U || 1, // Số U thiết bị chiếm
            congSuat: item.CONG_SUAT || 0,
            matRack: item.MAT_RACK || 3, // 1: Front, 2: Back, 3: Both
            isRPDU: item.IS_RPDU || false, // true nếu là rPDU
            isInstalling: false
          }))
        }
      } catch (error) {
        console.error('Error fetching devices:', error)
        // Mock data cho demo
        this.devices = this.getMockDevices()
      }
    },

    getMockDevices () {
      return [
        {
          id: 1,
          tenThietBi: 'Server Dell PowerEdge R740',
          loaiTbi: 'Máy chủ',
          soU: 2,
          congSuat: 750,
          matRack: 3, // Both
          isRPDU: false,
          isInstalling: false
        },
        {
          id: 2,
          tenThietBi: 'Switch Cisco Catalyst 9300',
          loaiTbi: 'Switch',
          soU: 1,
          congSuat: 350,
          matRack: 1, // Front only
          isRPDU: false,
          isInstalling: false
        },
        {
          id: 3,
          tenThietBi: 'Storage NetApp FAS2750',
          loaiTbi: 'Storage',
          soU: 4,
          congSuat: 1200,
          matRack: 3, // Both
          isRPDU: false,
          isInstalling: false
        },
        {
          id: 4,
          tenThietBi: 'Router Juniper MX240',
          loaiTbi: 'Router',
          soU: 3,
          congSuat: 900,
          matRack: 1, // Front only
          isRPDU: false,
          isInstalling: false
        },
        {
          id: 5,
          tenThietBi: 'UPS APC Smart-UPS',
          loaiTbi: 'UPS',
          soU: 3,
          congSuat: 2000,
          matRack: 2, // Back only
          isRPDU: false,
          isInstalling: false
        },
        {
          id: 6,
          tenThietBi: 'APC PDU AP8941',
          loaiTbi: 'rPDU',
          soU: 5, // rPDU chiếm 5U dọc bên cạnh rack
          congSuat: 8000,
          matRack: 0, // 0 = Side (left/right)
          isRPDU: true,
          isInstalling: false
        },
        {
          id: 7,
          tenThietBi: 'Schneider Electric PDU',
          loaiTbi: 'rPDU',
          soU: 3, // rPDU chiếm 3U dọc bên cạnh rack
          congSuat: 10000,
          matRack: 0,
          isRPDU: true,
          isInstalling: false
        }
      ]
    },

    async fetchInstalledDevices () {
      try {
        const response = await this.$root.context.post('/web-ecms/thongtin-rack/get-u-2-mat-theo-rack', {
          rackId: this.rackId
        })
        if (response && response.data) {
          // Parse dữ liệu đã lắp - phân loại thiết bị thường và rPDU
          response.data.forEach((item) => {
            if (item.IS_RPDU) {
              // rPDU
              this.installedRPDUs.push({
                id: item.ID,
                thietBiId: item.THIETBI_ID,
                tenThietBi: item.THIETBI,
                loaiTbi: item.LOAITBI,
                mat: item.MAT, // 'front' hoặc 'back'
                side: item.SIDE, // 'left' hoặc 'right'
                viTri: item.VITRI, // Vị trí U
                congSuat: item.CS_DINHDANH
              })
            } else {
              // Thiết bị thường
              if (item.THIETBI_ID) {
                this.installedDevices.push({
                id: item.ID,
                thietBiId: item.THIETBI_ID,
                tenThietBi: item.THIETBI,
                loaiTbi: item.LOAITBI,
                mat: item.MAT_RACKUNIT, // 1: Front, 2: Back
                viTri: item.VITRI, // Vị trí U
                soU: item.SO_U || 1,
                congSuat: item.CS_DINHDANH
              })
              }
            }
          })
        }
      } catch (error) {
        console.error('Error fetching installed devices:', error)
      }
    },

    handleDragStart (event, device) {
      this.draggedDevice = device
      device.isInstalling = true
      event.dataTransfer.effectAllowed = 'move'
      event.dataTransfer.setData('text/html', event.target.innerHTML)
    },

    handleDragEnd () {
      if (this.draggedDevice) {
        this.draggedDevice.isInstalling = false
        this.draggedDevice = null
      }
      this.dragOverSlot = null
    },

    handleDragOver (event, mat, uPosition) {
      if (!this.draggedDevice || this.draggedDevice.isRPDU) return

      event.preventDefault()
      this.dragOverSlot = { mat, uPosition }
      event.currentTarget.classList.add('drag-over')
    },

    handleDragLeave (event) {
      event.currentTarget.classList.remove('drag-over')
    },

    handleDrop (event, mat, uPosition) {
      event.preventDefault()
      event.currentTarget.classList.remove('drag-over')

      if (!this.draggedDevice || this.draggedDevice.isRPDU) return

      const device = this.draggedDevice

      // Kiểm tra xem có thể đặt thiết bị tại vị trí này không
      const canInstall = this.canInstallDevice(device, mat, uPosition)

      if (canInstall.ok) {
        this.installDevice(device, mat, uPosition)
        this.$toast.success(`Đã gắn thiết bị ${device.tenThietBi} vào vị trí U${uPosition}`)
      } else {
        this.$toast.error(canInstall.message)
      }

      this.handleDragEnd()
    },

    handleRPDUDragOver (event, mat, side, uPosition) {
      if (!this.draggedDevice || !this.draggedDevice.isRPDU) return

      event.preventDefault()
      this.dragOverSlot = { mat, side, uPosition }
      event.currentTarget.classList.add('drag-over')
    },

    handleRPDUDrop (event, mat, side, uPosition) {
      event.preventDefault()
      event.currentTarget.classList.remove('drag-over')

      if (!this.draggedDevice || !this.draggedDevice.isRPDU) return

      const device = this.draggedDevice

      // Kiểm tra xem có thể đặt rPDU tại vị trí này không
      const canInstall = this.canInstallRPDU(device, mat, side, uPosition)

      if (canInstall.ok) {
        this.installRPDU(device, mat, side, uPosition)
        this.$toast.success(`Đã gắn rPDU ${device.tenThietBi} vào ${mat === 'front' ? 'mặt trước' : 'mặt sau'} - ${side === 'left' ? 'cạnh trái' : 'cạnh phải'}`)
      } else {
        this.$toast.error(canInstall.message)
      }

      this.handleDragEnd()
    },

    canInstallDevice (device, mat, startU) {
      // Kiểm tra số U còn trống
      const requiredUs = []
      for (let i = 0; i < device.soU; i++) {
        const u = startU + i
        if (u > this.totalU) {
          return { ok: false, message: `Không đủ U để lắp thiết bị (cần ${device.soU}U)` }
        }
        requiredUs.push(u)
      }

      // Kiểm tra các U có bị chiếm chưa
      for (const u of requiredUs) {
        const matCode = mat === 'front' ? 1 : 2
        const existingDevice = this.getDeviceAt(mat, u)
        if (existingDevice) {
          return {
            ok: false,
            message: `Vị trí U${u} đã có thiết bị`
          }
        }
      }

      return { ok: true }
    },

    canInstallRPDU (device, mat, side, uPosition) {
      const soU = device.soU || 1
      
      // Kiểm tra uPosition hợp lệ
      if (uPosition < 1 || uPosition > this.totalU) {
        return { ok: false, message: `Vị trí U không hợp lệ` }
      }
      
      // Kiểm tra có đủ không gian cho số U cần thiết
      if (uPosition + soU - 1 > this.totalU) {
        return { ok: false, message: `Không đủ không gian (cần ${soU}U)` }
      }

      // Kiểm tra tất cả các vị trí U trong khoảng [uPosition, uPosition + soU - 1] đều trống
      for (let u = uPosition; u < uPosition + soU; u++) {
        const existing = this.getRPDUAt(mat, side, u)
        if (existing) {
          return { ok: false, message: `Vị trí U${u} đã có rPDU` }
        }
      }

      return { ok: true }
    },

    installDevice (device, mat, startU) {
      const matCode = mat === 'front' ? 1 : 2

      // Nếu thiết bị chiếm cả 2 mặt
      if (device.matRack === 3) {
        // Gắn vào cả front và back
        this.addInstalledDevice(device, 1, startU)
        this.addInstalledDevice(device, 2, startU)
      } else {
        // Gắn vào 1 mặt
        this.addInstalledDevice(device, matCode, startU)
      }

      // Xóa thiết bị khỏi danh sách có thể gắn
      const index = this.devices.findIndex((d) => d.id === device.id)
      if (index > -1) {
        this.devices.splice(index, 1)
      }
    },

    installRPDU (device, mat, side, uPosition) {
      const soU = device.soU || 1
      
      // Thêm rPDU vào danh sách đã lắp cho tất cả các U mà nó chiếm
      for (let i = 0; i < soU; i++) {
        this.installedRPDUs.push({
          id: `temp-rpdu-${Date.now()}-${mat}-${side}-${uPosition + i}`,
          thietBiId: device.id,
          tenThietBi: device.tenThietBi,
          loaiTbi: device.loaiTbi,
          mat: mat, // 'front' hoặc 'back'
          side: side, // 'left' hoặc 'right'
          viTri: uPosition + i,
          soU: soU,
          congSuat: device.congSuat,
          isFirstU: i === 0 // Đánh dấu U đầu tiên để hiển thị tên đầy đủ
        })
      }

      // Xóa thiết bị khỏi danh sách có thể gắn
      const index = this.devices.findIndex((d) => d.id === device.id)
      if (index > -1) {
        this.devices.splice(index, 1)
      }
    },

    addInstalledDevice (device, matCode, startU) {
      // Thêm các U mà thiết bị chiếm
      for (let i = 0; i < device.soU; i++) {
        this.installedDevices.push({
          id: `temp-${Date.now()}-${matCode}-${startU + i}`,
          thietBiId: device.id,
          tenThietBi: device.tenThietBi,
          loaiTbi: device.loaiTbi,
          mat: matCode,
          viTri: startU + i,
          soU: device.soU,
          congSuat: device.congSuat,
          isFirstU: i === 0 // Đánh dấu U đầu tiên để hiển thị tên
        })
      }
    },

    removeDevice (mat, uPosition) {
      const matCode = mat === 'front' ? 1 : 2

      // Tìm thiết bị tại vị trí này
      const device = this.installedDevices.find(
        (d) => d.mat === matCode && d.viTri === uPosition
      )

      if (!device) return

      // Xóa tất cả các U của thiết bị này
      const thietBiId = device.thietBiId

      // Xóa tất cả U của thiết bị này trên tất cả các mặt (nếu chiếm cả 2 mặt)
      this.installedDevices = this.installedDevices.filter(
        (d) => d.thietBiId !== thietBiId
      )

      // Thêm thiết bị trở lại danh sách có thể gắn
      this.devices.push({
        id: device.thietBiId,
        tenThietBi: device.tenThietBi,
        loaiTbi: device.loaiTbi,
        soU: device.soU,
        congSuat: device.congSuat,
        matRack: 3, // Giả định là Both
        isRPDU: false,
        isInstalling: false
      })

      this.$toast.success('Đã gỡ thiết bị khỏi rack')
    },

    removeRPDU (mat, side, uPosition) {
      // Tìm rPDU tại vị trí này
      const rpdu = this.installedRPDUs.find(
        (r) => r.mat === mat && r.side === side && r.viTri === uPosition
      )

      if (!rpdu) return

      const thietBiId = rpdu.thietBiId
      const soU = rpdu.soU || 1

      // Xóa tất cả các U của rPDU này
      this.installedRPDUs = this.installedRPDUs.filter(
        (r) => r.thietBiId !== thietBiId || r.mat !== mat || r.side !== side
      )

      // Thêm rPDU trở lại danh sách có thể gắn (chỉ thêm 1 lần)
      if (!this.devices.find(d => d.id === thietBiId)) {
        this.devices.push({
          id: thietBiId,
          tenThietBi: rpdu.tenThietBi,
          loaiTbi: rpdu.loaiTbi,
          soU: soU,
          congSuat: rpdu.congSuat,
          matRack: 0,
          isRPDU: true,
        })
      }

      this.$toast.success('Đã gỡ rPDU khỏi rack')
    },

    getDeviceAt (mat, uPosition) {
      const matCode = mat === 'front' ? 1 : 2
      return this.installedDevices.find((d) => {
        return d.mat === matCode && d.viTri === uPosition
      })
    },

    getRPDUAt (mat, side, uPosition) {
      return this.installedRPDUs.find((r) => {
        return r.mat === mat && r.side === side && r.viTri === uPosition
      })
    },

    getSlotClass (mat, uPosition) {
      const device = this.getDeviceAt(mat, uPosition)
      if (!device) return 'slot-empty'

      const classes = ['slot-occupied']

      // Thêm class theo loại thiết bị
      if (device.loaiTbi) {
        const type = device.loaiTbi.toLowerCase()
        if (type.includes('máy chủ') || type.includes('server')) classes.push('slot-server')
        else if (type.includes('switch') || type.includes('router')) classes.push('slot-network')
        else if (type.includes('storage')) classes.push('slot-storage')
        else classes.push('slot-other')
      }

      // Nếu không phải U đầu tiên, thêm class continuation
      if (!device.isFirstU) {
        classes.push('slot-continuation')
      }

      return classes.join(' ')
    },

    getRPDUSlotClass (mat, side, uPosition) {
      const rpdu = this.getRPDUAt(mat, side, uPosition)
      if (!rpdu) return 'rpdu-slot-empty'
      
      const classes = ['rpdu-slot-occupied']
      
      // Kiểm tra xem đây có phải là U đầu tiên của rPDU không (U nhỏ nhất)
      if (rpdu.isFirstU) {
        classes.push('rpdu-first-u')
      }
      
      // Vì hiển thị từ dưới lên (U1 ở dưới), nên U đầu tiên (U nhỏ nhất) sẽ có border-bottom
      // U cuối cùng (U lớn nhất) sẽ có border-top
      
      // Kiểm tra xem đây có phải là U cuối cùng của rPDU không (U lớn nhất)
      const nextU = this.getRPDUAt(mat, side, uPosition + 1)
      if (!nextU || nextU.thietBiId !== rpdu.thietBiId) {
        classes.push('rpdu-last-u')
      }
      
      return classes.join(' ')
    },

    getFrontUsedU () {
      const usedUs = new Set()
      this.installedDevices
        .filter((d) => d.mat === 1)
        .forEach((d) => {
          usedUs.add(d.viTri)
        })
      return usedUs.size
    },

    getBackUsedU () {
      const usedUs = new Set()
      this.installedDevices
        .filter((d) => d.mat === 2)
        .forEach((d) => {
          usedUs.add(d.viTri)
        })
      return usedUs.size
    },

    getFrontUsagePercentage () {
      return Math.round((this.getFrontUsedU() / this.totalU) * 100 * 10) / 10
    },

    getBackUsagePercentage () {
      return Math.round((this.getBackUsedU() / this.totalU) * 100 * 10) / 10
    },

    getDeviceIcon (loaiTbi) {
      const type = loaiTbi.toLowerCase()
      if (type.includes('máy chủ') || type.includes('server')) return 'fa fa-server'
      if (type.includes('switch')) return 'fa fa-sitemap'
      if (type.includes('router')) return 'fa fa-random'
      if (type.includes('storage')) return 'fa fa-database'
      if (type.includes('ups')) return 'fa fa-battery-full'
      if (type.includes('pdu') || type.includes('rpdu')) return 'fa fa-plug'
      return 'fa fa-cube'
    },

    getMatRackText (matRack) {
      switch (matRack) {
        case 1:
          return 'Mặt trước'
        case 2:
          return 'Mặt sau'
        case 3:
          return 'Cả 2 mặt'
        default:
          return ''
      }
    },

    async handleSave () {
      try {
        // Chuẩn bị dữ liệu thiết bị thường để lưu
        const devicesToSave = this.installedDevices.map((d) => ({
          THIETBI_ID: d.thietBiId,
          RACK_ID: this.rackId,
          MAT_RACKUNIT: d.mat,
          VITRI: d.viTri,
          IS_RPDU: false
        }))

        // Chuẩn bị dữ liệu rPDU để lưu
        const rpdusToSave = this.installedRPDUs.map((r) => ({
          THIETBI_ID: r.thietBiId,
          RACK_ID: this.rackId,
          MAT: r.mat, // 'front' hoặc 'back'
          SIDE: r.side, // 'left' hoặc 'right'
          VITRI: r.viTri,
          IS_RPDU: true
        }))

        // Gộp tất cả thiết bị
        const allData = [...devicesToSave, ...rpdusToSave]

        // Gọi API lưu
        const response = await this.$root.context.post('/web-ecms/thongtin-rack/gan-thiet-bi-vao-rack', {
          rackId: this.rackId,
          devices: allData
        })

        if (response && response.data) {
          this.$toast.success('Lưu thành công!')
          this.$emit('saved')
          this.hide()
        }
      } catch (error) {
        console.error('Error saving devices:', error)
        this.$toast.error('Lỗi khi lưu dữ liệu!')
      }
    }
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  padding: 20px;
}

.modal-container {
  background: white;
  border-radius: 8px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
  width: 95%;
  max-width: 1800px;
  max-height: 90vh;
  display: flex;
  flex-direction: column;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
}

.modal-header h4 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 10px;
}

.modal-body {
  display: grid;
  grid-template-columns: 350px 1fr;
  gap: 20px;
  padding: 20px;
  overflow: hidden;
  flex: 1;
}

/* Left Panel */
.left-panel {
  display: flex;
  flex-direction: column;
  background: #f8f9fa;
  border-radius: 6px;
  overflow: hidden;
}

.panel-header {
  background: white;
  border-bottom: 2px solid #e0e0e0;
  padding-bottom: 10px;
}

.panel-header h5 {
  margin: 0 0 10px 0;
  font-size: 15px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  color: #333;
}

.search-box input {
  width: 100%;
}

.device-list {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
}

.device-item {
  background: white;
  border: 2px solid #e0e0e0;
  border-radius: 6px;
  padding: 12px;
  margin-bottom: 10px;
  cursor: move;
  display: flex;
  gap: 12px;
  transition: all 0.2s;
  position: relative;
}

.device-item:hover {
  border-color: #667eea;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2);
  transform: translateY(-2px);
}

.device-item.device-installing {
  opacity: 0.5;
  cursor: grabbing;
}

.device-icon {
  width: 40px;
  height: 40px;
  background: orange;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
  flex-shrink: 0;
}

.device-info {
  flex: 1;
  min-width: 0;
}

.device-name {
  font-weight: 600;
  font-size: 13px;
  color: #333;
  margin-bottom: 6px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.device-specs {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 4px;
}

.spec-item {
  font-size: 11px;
  color: #666;
  display: flex;
  align-items: center;
  gap: 4px;
  background: #f0f0f0;
  padding: 2px 6px;
  border-radius: 3px;
}

.spec-item.spec-rpdu {
  background: #fff3cd;
  color: #f39c12;
  font-weight: 600;
}

.spec-item i {
  font-size: 10px;
}

.device-type {
  font-size: 11px;
  color: #999;
  font-style: italic;
}

.device-drag-hint {
  position: absolute;
  top: 8px;
  right: 8px;
  color: #ccc;
  font-size: 16px;
}

.no-devices {
  text-align: center;
  padding: 40px 20px;
  color: #999;
}

.no-devices i {
  font-size: 48px;
  margin-bottom: 10px;
  display: block;
}

/* Right Panel */
.right-panel {
  overflow-y: auto;
  padding: 10px;
  background: #f8f9fa;
  border-radius: 6px;
}

.rack-views-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

/* rPDU Side Columns - nằm trong rack graphic */
.rpdu-side-column {
  width: 40px;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
}

.rpdu-column-header {
  margin-top: -22px;
  font-size: 10px;
  font-weight: 600;
  color: #f39c12;
  text-align: center;
  padding: 4px 2px;
  background: #fff3cd;
  border-radius: 3px 3px 0 0;
  writing-mode: horizontal-tb;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.rpdu-slots {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.rpdu-slot {
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  position: relative;
  background: #f9f9f9;
  border-left: 1px solid #e0e0e0;
  border-right: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
}

.rpdu-slot:first-child {
  border-top: 1px solid #e0e0e0;
}

.rpdu-slot.rpdu-slot-empty:hover {
  background: #fff3cd;
  border-color: #f39c12;
}

.rpdu-slot.rpdu-slot-occupied {
  background: linear-gradient(135deg, #f39c12 0%, #f8b739 100%);
  border-left: 2px solid #e67e22;
  border-right: 2px solid #e67e22;
  border-top: none;
  border-bottom: none;
}

/* U đầu tiên (U nhỏ nhất) - ở dưới cùng khi hiển thị */
.rpdu-slot.rpdu-slot-occupied.rpdu-first-u {
  border-bottom: 2px solid #e67e22;
  border-bottom-left-radius: 3px;
  border-bottom-right-radius: 3px;
}

/* U cuối cùng (U lớn nhất) - ở trên cùng khi hiển thị */
.rpdu-slot.rpdu-slot-occupied.rpdu-last-u {
  border-top: 2px solid #e67e22;
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
}

.rpdu-slot.drag-over {
  background: #c8e6c9 !important;
  border: 2px dashed #4caf50;
}

.rpdu-device {
  width: 100%;
  height: 100%;
  position: relative;
}

.rpdu-label-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  pointer-events: auto;
  z-index: 1;
  overflow: hidden;
  cursor: pointer;
}

.rpdu-name {
  font-size: 10px;
  font-weight: 600;
  color: #fff;
  text-align: center;
  writing-mode: vertical-lr;
  text-orientation: mixed;
  white-space: nowrap;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
  transform: rotate(180deg);
  overflow: hidden;
  text-overflow: ellipsis;
  max-height: 100%;
  display: block;
}

.rpdu-device .btn-remove-device {
  pointer-events: auto;
  z-index: 2;
}

/* Rack View Sections */
.rack-view-section {
  background: white;
  border-radius: 6px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.rack-view-header {
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #e0e0e0;
}

.rack-view-header h5 {
  margin: 0 0 8px 0;
  font-size: 15px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  color: #333;
}

.usage-info {
  background: #e8f4f8;
  padding: 6px 10px;
  border-radius: 4px;
  text-align: center;
  font-size: 12px;
  font-weight: 600;
  color: #2196f3;
}

.rack-graphic {
  display: flex;
  gap: 0;
  background: #e0e0e0;
  padding: 10px;
  border-radius: 6px;
}

.u-labels {
  width: 30px;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
}

.u-labels-left {
  padding-right: 4px;
}

.u-labels-right {
  padding-left: 4px;
}

.u-label {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  font-weight: 600;
  color: #666;
  background: #f5f5f5;
  border-bottom: 1px solid #ddd;
}

.rack-slots {
  flex: 1;
  background: white;
  border: 2px solid #999;
  border-radius: 4px;
  overflow: hidden;
}

.rack-u-row {
  display: flex;
  border-bottom: 1px solid #e0e0e0;
}

.rack-full-slot {
  flex: 1;
  position: relative;
  transition: all 0.2s;
  min-height: 20px;
}

.rack-full-slot.slot-empty {
  background: #f9f9f9;
}

.rack-full-slot.slot-empty:hover {
  background: #e8f4f8;
}

.rack-full-slot.drag-over {
  background: #c8e6c9 !important;
  border: 2px dashed #4caf50;
}

.rack-full-slot.slot-occupied {
  background: linear-gradient(135deg, #3498db 0%, #5dade2 100%);
}

.rack-full-slot.slot-server {
  background: linear-gradient(135deg, #3498db 0%, #5dade2 100%);
}

.rack-full-slot.slot-network {
  background: linear-gradient(135deg, #f39c12 0%, #f8b739 100%);
}

.rack-full-slot.slot-storage {
  background: linear-gradient(135deg, #9b59b6 0%, #bb8fce 100%);
}

.rack-full-slot.slot-other {
  background: linear-gradient(135deg, #34495e 0%, #5d6d7e 100%);
}

.rack-full-slot.slot-continuation {
  opacity: 0.7;
}

.slot-device {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2px 4px;
  position: relative;
}

.device-label {
  color: white;
  font-size: 10px;
  font-weight: 600;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 100%;
}

.btn-remove-device {
  position: absolute;
  top: 2px;
  right: 2px;
  width: 16px;
  height: 16px;
  background: rgba(255, 0, 0, 0.8);
  border: none;
  border-radius: 50%;
  color: white;
  font-size: 10px;
  cursor: pointer;
  display: none;
  align-items: center;
  justify-content: center;
  padding: 0;
  transition: all 0.2s;
}

.rack-full-slot:hover .btn-remove-device,
.rpdu-slot.rpdu-first-u:hover .btn-remove-device {
  display: flex;
}

.btn-remove-device:hover {
  background: rgba(255, 0, 0, 1);
  transform: scale(1.1);
}

/* Modal Footer */
.modal-footer {
  padding: 15px 20px;
  border-top: 2px solid #e0e0e0;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  background: #f8f9fa;
  border-radius: 0 0 8px 8px;
}

.btn {
  padding: 8px 20px;
  border-radius: 4px;
  border: none;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.2s;
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background: #5a6268;
}


/* Scrollbar */
.device-list::-webkit-scrollbar,
.right-panel::-webkit-scrollbar {
  width: 8px;
}

.device-list::-webkit-scrollbar-track,
.right-panel::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.device-list::-webkit-scrollbar-thumb,
.right-panel::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.device-list::-webkit-scrollbar-thumb:hover,
.right-panel::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
