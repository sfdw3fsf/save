<template>
  <div class="rack-both-views-tab">
    <div class="tab-content-area">
      <div class="p-0">
        <button class="btn-action btn-add-device mb-2 ms-0" @click="openModalGanThietBi">
          <i class="fa fa-plus"></i>
          Gắn thiết bị
        </button>
      </div>
      <!-- Three Views Container: Power Bars, Front, Back -->
      <div class="three-views-container">
        <!-- Power Bars View -->
        <div class="view-section power-section">
          <div class="view-title view-title-power">
            <i class="fa fa-bolt"></i>
            <span>THANH NGUỒN</span>
          </div>

          <!-- Power Info -->
          <div class="power-info-compact">
            <div class="power-info-header">
              <h6>THÔNG TIN NGUỒN CẤP ĐIỆN</h6>
            </div>
            <div class="power-summary-compact">
              <div class="power-label">Tổng số thanh:</div>
              <div class="power-value">{{ powerBars.length }}</div>
            </div>
            <div class="power-bars-table-compact">
              <table>
                <thead>
                  <tr>
                    <th colspan="2"></th>
                    <th colspan="2">Công suất (W)</th>
                  </tr>
                  <tr>
                    <th>ID</th>
                    <th>Thanh nguồn</th>
                    <th>Tối đa</th>
                    <th>Đã sử dụng</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="bar in powerBars" :key="bar.id">
                    <td>{{ bar.id }}</td>
                    <td>{{ bar.name }}</td>
                    <td>{{ bar.maxPower }}</td>
                    <td>{{ bar.usedPower }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <div class="rack-container" v-if="powerBars.length > 0">
            <div class="rack-frame-with-power">
              <!-- Dynamic Power Bars -->
              <div v-for="(bar, index) in powerBars" :key="bar.id" class="power-bar-container">
                <div class="power-bar">
                  <div class="power-bar-used" :style="{ height: bar.percentage + '%' }"></div>
                  <div class="power-bar-available"></div>
                </div>
                <div class="power-bar-label">
                  <span class="power-bar-number">{{ index + 1 }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Power Legend -->
          <div class="legend">
            <div class="legend-item">
              <span class="legend-color legend-power-used"></span>
              <span class="legend-text">Đã sử dụng</span>
            </div>
            <div class="legend-item">
              <span class="legend-color legend-power-available"></span>
              <span class="legend-text">Còn trống</span>
            </div>
          </div>
        </div>

        <!-- Front View -->
        <div class="view-section">
          <div class="view-title">
            <i class="fa fa-server"></i>
            <span>MẶT TRƯỚC</span>
          </div>

          <!-- Front Usage Info -->
          <div class="view-usage-info">
            {{ getFrontUsedU() }}/{{ getTotalU() }} U đã sử dụng ({{ getFrontUsagePercentage() }}%)
          </div>

          <div class="rack-container">
            <!-- rPDU bên trái -->
            <div class="rpdu-side rpdu-left" v-if="frontLeftRPDU.length > 0">
              <div class="rpdu-title">rPDU Trái</div>
              <div class="rpdu-list">
                <div v-for="rpdu in frontLeftRPDU" :key="rpdu.id" class="rpdu-item" :title="rpdu.tooltip">
                  <div class="rpdu-name">{{ rpdu.name }}</div>
                  <div class="rpdu-info">{{ rpdu.info }}</div>
                </div>
              </div>
            </div>

            <div class="rack-frame-wrapper">
              <!-- Labels Trái/Phải -->
              <div class="rack-direction-labels">
                <div class="direction-label direction-left">◄ TRÁI</div>
                <div class="direction-label direction-right">PHẢI ►</div>
              </div>

              <div class="rack-frame">
                <div class="u-labels u-labels-left">
                  <div v-for="u in frontRackPositions" :key="'front-label-left-' + u.position" class="u-label"
                    :style="{ height: getUHeight(u) + 'px' }">
                    U{{ u.position }}
                  </div>
                </div>

                <div class="device-area">
                  <div v-for="u in frontRackPositions" :key="'front-device-' + u.position" class="rack-slot"
                    :class="[getSlotClass(u), { 'no-border': u.device && !u.showName }]"
                    :data-device-id="u.deviceId"
                    :style="{ height: getUHeight(u) + 'px' }" @click="handleDeviceClick(u)">
                    <div v-if="u.device && u.showName" class="device-info">
                      <div class="device-name">{{ u.device.name }}</div>
                      <div class="device-details">{{ u.device.details }}</div>
                    </div>
                    <div v-else-if="u.device && !u.showName" class="device-continuation"></div>
                    <div v-else class="empty-slot-text">{{ u.emptyText }}</div>
                  </div>
                </div>

                <div class="u-labels u-labels-right">
                  <div v-for="u in frontRackPositions" :key="'front-label-right-' + u.position" class="u-label"
                    :style="{ height: getUHeight(u) + 'px' }">
                    U{{ u.position }}
                  </div>
                </div>
              </div>
            </div>

            <!-- rPDU bên phải -->
            <div class="rpdu-side rpdu-right" v-if="frontRightRPDU.length > 0">
              <div class="rpdu-title">rPDU Phải</div>
              <div class="rpdu-list">
                <div v-for="rpdu in frontRightRPDU" :key="rpdu.id" class="rpdu-item" :title="rpdu.tooltip">
                  <div class="rpdu-name">{{ rpdu.name }}</div>
                  <div class="rpdu-info">{{ rpdu.info }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Back View -->
        <div class="view-section">
          <div class="view-title view-title-back">
            <i class="fa fa-server"></i>
            <span>MẶT SAU</span>
          </div>

          <!-- Back Usage Info -->
          <div class="view-usage-info">
            {{ getBackUsedU() }}/{{ getTotalU() }} U đã sử dụng ({{ getBackUsagePercentage() }}%)
          </div>

          <div class="rack-container">
            <!-- rPDU bên trái -->
            <div class="rpdu-side rpdu-left" v-if="backLeftRPDU.length > 0">
              <div class="rpdu-title">rPDU Trái</div>
              <div class="rpdu-list">
                <div v-for="rpdu in backLeftRPDU" :key="rpdu.id" class="rpdu-item" :title="rpdu.tooltip">
                  <div class="rpdu-name">{{ rpdu.name }}</div>
                  <div class="rpdu-info">{{ rpdu.info }}</div>
                </div>
              </div>
            </div>

            <div class="rack-frame-wrapper">
              <!-- Labels Trái/Phải -->
              <div class="rack-direction-labels">
                <div class="direction-label direction-left">◄ TRÁI</div>
                <div class="direction-label direction-right">PHẢI ►</div>
              </div>

              <div class="rack-frame">
                <div class="u-labels u-labels-left">
                  <div v-for="u in backRackPositions" :key="'back-label-left-' + u.position" class="u-label"
                    :style="{ height: getUHeight(u) + 'px' }">
                    U{{ u.position }}
                  </div>
                </div>

                <div class="device-area">
                  <div v-for="u in backRackPositions" :key="'back-device-' + u.position" class="rack-slot"
                    :class="[getSlotClass(u), { 'no-border': u.device && !u.showName }]"
                    :data-device-id="u.deviceId"
                    :style="{ height: getUHeight(u) + 'px' }" @click="handleDeviceClick(u)">
                    <div v-if="u.device && u.showName" class="device-info">
                      <div class="device-name">{{ u.device.name }}</div>
                      <div class="device-details">{{ u.device.details }}</div>
                    </div>
                    <div v-else-if="u.device && !u.showName" class="device-continuation"></div>
                    <div v-else class="empty-slot-text">{{ u.emptyText }}</div>
                  </div>
                </div>

                <div class="u-labels u-labels-right">
                  <div v-for="u in backRackPositions" :key="'back-label-right-' + u.position" class="u-label"
                    :style="{ height: getUHeight(u) + 'px' }">
                    U{{ u.position }}
                  </div>
                </div>
              </div>
            </div>

            <!-- rPDU bên phải -->
            <div class="rpdu-side rpdu-right" v-if="backRightRPDU.length > 0">
              <div class="rpdu-title">rPDU Phải</div>
              <div class="rpdu-list">
                <div v-for="rpdu in backRightRPDU" :key="rpdu.id" class="rpdu-item" :title="rpdu.tooltip">
                  <div class="rpdu-name">{{ rpdu.name }}</div>
                  <div class="rpdu-info">{{ rpdu.info }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Device Detail Modal Component -->
    <ModalChiTietThietBi :thietBiId="selectedThietbiId" @close="closeDeviceModal" ref="modalChiTietThietBi" />

    <!-- Modal Gắn Thiết Bị Vào Rack -->
    <ModalGanThietBiVaoRack :rackId="rackId" @saved="handleDevicesSaved" ref="modalGanThietBi" />
  </div>
</template>

<script>
import ModalChiTietThietBi from '../chitiet-thietbi/index.vue'
import ModalGanThietBiVaoRack from '../modals/ModalGanThietBiVaoRack.vue'

export default {
  name: 'RackBothViewsTab',

  components: {
    ModalChiTietThietBi,
    ModalGanThietBiVaoRack
  },

  props: {
    rackId: {
      type: [String, Number],
      required: true
    }
  },

  data() {
    return {
      loading: false,
      rackData: {
        totalU: 42,
        usedU: 0
      },
      powerBars: [],
      frontDevices: [],
      backDevices: [],
      frontLeftRPDU: [],
      frontRightRPDU: [],
      backLeftRPDU: [],
      backRightRPDU: [],
      apiData: [],
      showDeviceModal: false,
      selectedThietbiId: null
    }
  },

  mounted() {
    this.fetchRackData()
  },

  watch: {
    rackId() {
      this.fetchRackData()
    }
  },

  computed: {
    frontRackPositions() {
      return this.buildRackPositions(this.frontDevices)
    },

    backRackPositions() {
      return this.buildRackPositions(this.backDevices)
    }
  },

  created() {
    this.fetchRackData()
    this.getThanhNguonData()
    this.getRPDUData()
  },

  methods: {
    async fetchRackData() {
      if (!this.rackId) {
        console.log('No rackId provided')
        return
      }

      console.log('Fetching rack data for rackId:', this.rackId)
      this.loading = true
      try {
        // Gọi API lấy thông tin rack (tổng số U)
        const rackInfoResponse = await this.$root.context.post('/web-ecms/thongtin-rack/get-by-id', {
          rackId: this.rackId
        })

        console.log('Rack Info Response:', rackInfoResponse)

        if (rackInfoResponse && rackInfoResponse.data) {
          const rackInfo = rackInfoResponse.data
          this.rackData.totalU = rackInfo.SO_U || 42
          console.log('Total U from rack info:', this.rackData.totalU)
        }

        // Gọi API lấy danh sách U đã cấp phát và thiết bị
        const response = await this.$root.context.post('/web-ecms/thongtin-rack/get-u-2-mat-theo-rack', {
          rackId: this.rackId
        })

        console.log('API Response:', response)

        if (response && response.data) {
          console.log('Processing data:', response.data)
          this.apiData = response.data
          this.processRackData(response.data)
        } else {
          console.log('No data in response')
        }
      } catch (error) {
        console.error('Error fetching rack data:', error)
      } finally {
        this.loading = false
      }
    },

    processRackData(data) {
      console.log('processRackData called with:', data)

      // Nhóm dữ liệu theo mặt (MAT_RACKUNIT: 1 = mặt trước, 2 = mặt sau)
      const frontData = data.filter((item) => item.MAT_RACKUNIT === 1)
      const backData = data.filter((item) => item.MAT_RACKUNIT === 2)

      console.log('Front data count:', frontData.length)
      console.log('Back data count:', backData.length)

      // Xử lý thiết bị cho mặt trước
      this.frontDevices = this.groupDevicesByPosition(frontData)
      console.log('Front devices:', this.frontDevices)

      // Xử lý thiết bị cho mặt sau
      this.backDevices = this.groupDevicesByPosition(backData)
      console.log('Back devices:', this.backDevices)
    },

    groupDevicesByPosition(data) {
      const devices = []
      const processedDevices = new Map()

      // Sắp xếp theo vị trí
      const sortedData = [...data].sort((a, b) => a.VITRI - b.VITRI)

      // Nhóm theo THIETBI_ID để tìm các vị trí liên tiếp (bỏ qua THIETBI_ID = 0)
      sortedData.forEach((item) => {
        // Bỏ qua nếu THIETBI_ID = 0 (không có thiết bị)
        if (item.THIETBI_ID === 0) {
          return
        }
        const key = item.THIETBI_ID
        if (!processedDevices.has(key)) {
          processedDevices.set(key, [])
        }
        processedDevices.get(key).push(item.VITRI)
      })

      // Tạo danh sách thiết bị với số U thực tế
      processedDevices.forEach((positions, thietbiId) => {
        const firstItem = sortedData.find((item) => item.THIETBI_ID === thietbiId)
        if (!firstItem) return

        // Sắp xếp các vị trí và tìm các nhóm liên tiếp
        const sortedPositions = positions.sort((a, b) => a - b)
        let startU = sortedPositions[0]
        let currentGroup = [startU]

        for (let i = 1; i < sortedPositions.length; i++) {
          if (sortedPositions[i] === sortedPositions[i - 1] + 1) {
            // Vị trí liên tiếp
            currentGroup.push(sortedPositions[i])
          } else {
            // Vị trí không liên tiếp, tạo thiết bị cho nhóm hiện tại
            const uCount = currentGroup.length
            const congSuat = firstItem.CS_DINHDANH ? `${firstItem.CS_DINHDANH}W` : ''
            const detailParts = [`${uCount}U (${startU}-${startU + uCount - 1})`, firstItem.LOAITBI, congSuat].filter(
              Boolean
            )

            devices.push({
              startU: startU,
              uCount: uCount,
              name: firstItem.THIETBI,
              details: detailParts.join(' | '),
              type: this.getDeviceType(firstItem.LOAITBI, firstItem.THIETBI_ID),
              thietbiId: firstItem.THIETBI_ID,
              loaiTbi: firstItem.LOAITBI,
              trangThai: firstItem.TRANGTHAI_TEXT,
              congSuat: firstItem.CS_DINHDANH
            })
            // Bắt đầu nhóm mới
            startU = sortedPositions[i]
            currentGroup = [startU]
          }
        }

        // Thêm nhóm cuối cùng
        const uCount = currentGroup.length
        const congSuat = firstItem.CS_DINHDANH ? `${firstItem.CS_DINHDANH}W` : ''
        const detailParts = [`${uCount}U (${startU}-${startU + uCount - 1})`, firstItem.LOAITBI, congSuat].filter(
          Boolean
        )

        devices.push({
          startU: startU,
          uCount: uCount,
          name: firstItem.THIETBI,
          details: detailParts.join(' | '),
          type: this.getDeviceType(firstItem.LOAITBI, firstItem.THIETBI_ID),
          thietbiId: firstItem.THIETBI_ID,
          loaiTbi: firstItem.LOAITBI,
          trangThai: firstItem.TRANGTHAI_TEXT,
          congSuat: firstItem.CS_DINHDANH
        })
      })

      console.log('Grouped devices:', devices)
      return devices
    },

    getDeviceType(loaiTbi, thietbiId) {
      if (thietbiId === 0) return 'empty'

      const type = loaiTbi.toLowerCase()
      if (type.includes('máy chủ') || type.includes('server')) return 'server'
      if (type.includes('switch') || type.includes('router')) return 'network'
      if (type.includes('storage') || type.includes('lưu trữ')) return 'storage'

      return 'server'
    },

    buildRackPositions(devices) {
      const totalU = this.getTotalU()
      const positions = []

      for (let i = totalU; i >= 1; i--) {
        const device = this.getDeviceAtU(i, devices)
        const isFirstU = device && i === device.startU
        const isLastU = device && i === (device.startU + device.uCount - 1)

        positions.push({
          position: i,
          uCount: 1,
          device: device,
          type: device ? device.type : 'empty',
          emptyText: device ? '' : '',
          isFirstU: isFirstU,
          isLastU: isLastU,
          showName: isFirstU,
          deviceId: device ? device.thietbiId : null
        })
      }

      return positions
    },

    getTotalU() {
      return this.rackData.totalU || 42
    },

    getUsedU() {
      return this.rackData.usedU || 0
    },

    getUsagePercentage() {
      const total = this.getTotalU()
      const used = this.getUsedU()
      return Math.round((used / total) * 100 * 10) / 10
    },

    getFrontUsedU() {
      let usedU = 0
      this.frontDevices.forEach((device) => {
        if (device.type !== 'empty' && device.thietbiId !== 0) {
          usedU += device.uCount
        }
      })
      return usedU
    },

    getFrontUsagePercentage() {
      const total = this.getTotalU()
      const used = this.getFrontUsedU()
      return Math.round((used / total) * 100 * 10) / 10
    },

    getBackUsedU() {
      let usedU = 0
      this.backDevices.forEach((device) => {
        if (device.type !== 'empty' && device.thietbiId !== 0) {
          usedU += device.uCount
        }
      })
      return usedU
    },

    getBackUsagePercentage() {
      const total = this.getTotalU()
      const used = this.getBackUsedU()
      return Math.round((used / total) * 100 * 10) / 10
    },

    getDeviceAtU(uPosition, devices) {
      return devices.find((device) => {
        const endU = device.startU + device.uCount - 1
        return uPosition >= device.startU && uPosition <= endU
      })
    },

    getUHeight(u) {
      return (u.uCount || 1) * 20
    },

    getSlotClass(u) {
      if (!u.device) {
        return 'slot-empty'
      }
      return 'slot-' + u.type
    },

    async getThanhNguonData() {
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-front-back-info', {
          rackId: this.rackId
        })
        console.log(rs, 'data thanh nguon')

        if (rs && rs.data && rs.data && rs.data.DANH_SACH_THANH_NGUON) {
          const thanhNguonList = JSON.parse(rs.data.DANH_SACH_THANH_NGUON)

          // Map dữ liệu từ API vào powerBars
          this.powerBars = thanhNguonList.map((item) => {
            // Chuyển đổi công suất (giả sử CONGSUAT_TOIDA là kW, nhân 1000 để ra W)
            const maxPower = parseFloat(item.CONGSUAT_TOIDA) || 0
            const usedPower = parseFloat(item.CONGSUAT_DASD) || 0
            const percentage = maxPower > 0 ? Math.round((usedPower / maxPower) * 100) : 0

            return {
              id: item.ID,
              name: item.TEN,
              maxPower: maxPower,
              usedPower: usedPower,
              percentage: percentage
            }
          })

          console.log('Power bars updated:', this.powerBars)
        }
      } catch (error) {
        console.error('Error fetching thanh nguon data:', error)
      }
    },

    handleDeviceClick(u) {
      // Chỉ hiển thị modal nếu có thiết bị và không phải slot trống
      if (u.device && u.device.thietbiId !== 0 && u.device.type !== 'empty') {
        this.selectedThietbiId = u.device.thietbiId
        // Đợi Vue update prop trước khi show modal
        this.$nextTick(() => {
          this.$refs.modalChiTietThietBi.show(null, 'VIEW')
        })
      }
    },

    closeDeviceModal() {
      this.$refs.modalChiTietThietBi.hide()
      this.selectedThietbiId = null
    },

    openModalGanThietBi() {
      this.$refs.modalGanThietBi.show()
    },

    handleDevicesSaved() {
      // Reload dữ liệu sau khi gắn thiết bị thành công
      this.fetchRackData()
    },

    async getRPDUData() {
      try {
        return;
        // Gọi API để lấy dữ liệu rPDU gắn vào rack
        const response = await this.$root.context.post('/web-ecms/idc/thanhnguon-trenrack/by-rack', {
          id: this.rackId
        })

        console.log('rPDU data:', response)

        if (response && response.data) {
          const rpduList = response.data

          // Phân loại rPDU theo vị trí (mặt và bên)
          // MAT_RACKUNIT: 1 = mặt trước, 2 = mặt sau
          // VI_TRI_GAN: 1 = trái, 2 = phải
          this.frontLeftRPDU = rpduList
            .filter((item) => item.MAT_RACKUNIT === 1 && item.VI_TRI_GAN === 1)
            .map((item) => this.formatRPDUItem(item))

          this.frontRightRPDU = rpduList
            .filter((item) => item.MAT_RACKUNIT === 1 && item.VI_TRI_GAN === 2)
            .map((item) => this.formatRPDUItem(item))

          this.backLeftRPDU = rpduList
            .filter((item) => item.MAT_RACKUNIT === 2 && item.VI_TRI_GAN === 1)
            .map((item) => this.formatRPDUItem(item))

          this.backRightRPDU = rpduList
            .filter((item) => item.MAT_RACKUNIT === 2 && item.VI_TRI_GAN === 2)
            .map((item) => this.formatRPDUItem(item))

          console.log('Front Left rPDU:', this.frontLeftRPDU)
          console.log('Front Right rPDU:', this.frontRightRPDU)
          console.log('Back Left rPDU:', this.backLeftRPDU)
          console.log('Back Right rPDU:', this.backRightRPDU)
        }
      } catch (error) {
        console.error('Error fetching rPDU data:', error)
      }
    },

    formatRPDUItem(item) {
      const soLuongU = item.SOLUONG_U || 1 // Default là 1 nếu null
      return {
        id: item.ID || item.RPDU_ID,
        name: item.TEN || item.TEN_RPDU || 'rPDU',
        info: item.SO_CONG ? `${item.SO_CONG} cổng` : '',
        tooltip: `${item.TEN || 'rPDU'} - ${item.SO_CONG || 0} cổng - ${item.CONGSUAT_TOIDA || 0}W`,
        soLuongU: soLuongU
      }
    }
  }
}
</script>

<style scoped>
.tab-content-area {
  min-height: 300px;
  border-radius: 6px;
}

.view-usage-info {
  background: #e8f4f8;
  padding: 8px 12px;
  border-radius: 4px;
  text-align: center;
  font-weight: 600;
  margin-bottom: 15px;
  font-size: 13px;
  color: #2196f3;
}

.view-actions {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.btn-action {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.2s;
  font-size: 13px;
}

.three-views-container {
  display: grid;
  grid-template-columns: 300px 1fr 1fr;
  gap: 20px;
}

.view-section {
  display: flex;
  flex-direction: column;
}

.view-title {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 15px;
  padding: 10px;
  background: #e8f4f8;
  border-radius: 4px;
}

.view-title i {
  color: #3498db;
}

.view-title-power {
  background: #fff3cd;
}

.view-title-power i {
  color: #f39c12;
}

.view-title-back {
  background: #fee;
}

.view-title-back i {
  color: #e74c3c;
}

.rack-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  margin-bottom: 15px;
  gap: 15px;
}

/* rPDU Side Panels */
.rpdu-side {
  display: flex;
  flex-direction: column;
  width: 80px;
  background: white;
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.rpdu-title {
  font-size: 11px;
  font-weight: 700;
  text-align: center;
  color: #666;
  margin-bottom: 10px;
  padding-bottom: 8px;
  border-bottom: 2px solid #e0e0e0;
}

.rpdu-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.rpdu-item {
  background: linear-gradient(135deg, #6c757d 0%, #868e96 100%);
  color: white;
  padding: 8px 6px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.rpdu-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.3);
}

.rpdu-name {
  font-size: 10px;
  font-weight: 700;
  text-align: center;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.rpdu-info {
  font-size: 9px;
  text-align: center;
  opacity: 0.9;
}

.rpdu-left {
  border-left: 3px solid #6c757d;
}

.rpdu-right {
  border-right: 3px solid #6c757d;
}

/* Rack Frame Wrapper */
.rack-frame-wrapper {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 450px;
}

/* Direction Labels */
.rack-direction-labels {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  padding: 0 15px;
}

.direction-label {
  font-size: 12px;
  font-weight: 700;
  color: #555;
  padding: 4px 12px;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 4px;
}

.direction-left {
  color: #0056b3;
}

.direction-right {
  color: #dc3545;
}


.rack-frame {
  display: flex;
  gap: 0;
  background: #e0e0e0;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  width: 100%;
}

.u-labels {
  border: 2px solid #ccc;
  display: flex;
  flex-direction: column;
  width: 35px;
  flex-shrink: 0;
}

.u-labels-left {
  padding-right: 5px;
}

.u-labels-right {
  padding-left: 5px;
}

.u-label {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
  font-weight: 600;
  font-size: 11px;
  background: #f5f5f5;
  border-bottom: 1px solid #ddd;
}

.device-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: white;
  border: 2px solid #999;
  border-radius: 4px;
  overflow: hidden;
}

.rack-slot {
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  padding: 5px 8px;
  transition: all 0.2s;
  cursor: pointer;
  font-size: 11px;
}

body:has(.rack-slot[data-device-id="1"]:hover) .rack-slot[data-device-id="1"],
body:has(.rack-slot[data-device-id="2"]:hover) .rack-slot[data-device-id="2"],
body:has(.rack-slot[data-device-id="3"]:hover) .rack-slot[data-device-id="3"],
body:has(.rack-slot[data-device-id="4"]:hover) .rack-slot[data-device-id="4"],
body:has(.rack-slot[data-device-id="5"]:hover) .rack-slot[data-device-id="5"],
body:has(.rack-slot[data-device-id="6"]:hover) .rack-slot[data-device-id="6"],
body:has(.rack-slot[data-device-id="7"]:hover) .rack-slot[data-device-id="7"],
body:has(.rack-slot[data-device-id="8"]:hover) .rack-slot[data-device-id="8"],
body:has(.rack-slot[data-device-id="9"]:hover) .rack-slot[data-device-id="9"],
body:has(.rack-slot[data-device-id="10"]:hover) .rack-slot[data-device-id="10"] {
  opacity: 0.85;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.2);
}

.rack-slot:not([data-device-id]):hover {
  opacity: 0.85;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.2);
}

.rack-slot.no-border {
  border-bottom: none;
}

.slot-server {
  background: linear-gradient(135deg, #3498db 0%, #5dade2 100%);
  color: white;
}

.slot-network {
  background: linear-gradient(135deg, #f39c12 0%, #f8b739 100%);
  color: white;
}

.slot-storage {
  background: linear-gradient(135deg, #9b59b6 0%, #bb8fce 100%);
  color: white;
}

.slot-empty {
  background: linear-gradient(135deg, #2ecc71 0%, #58d68d 100%);
  color: white;
}

.device-info {
  text-align: center;
  width: 100%;
  display: flex;
  gap: 2px;
}

.device-name {
  font-weight: 700;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.device-details {
  opacity: 0.9;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 10px;
}

.empty-slot-text {
  opacity: 0.8;
}

.legend {
  display: flex;
  justify-content: center;
  gap: 15px;
  flex-wrap: wrap;
  padding: 10px;
  background: white;
  border-radius: 6px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
}

.legend-color {
  width: 20px;
  height: 14px;
  border-radius: 3px;
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.legend-server {
  background: linear-gradient(135deg, #3498db 0%, #5dade2 100%);
}

.legend-network {
  background: linear-gradient(135deg, #f39c12 0%, #f8b739 100%);
}

.legend-storage {
  background: linear-gradient(135deg, #9b59b6 0%, #bb8fce 100%);
}

.legend-empty {
  background: linear-gradient(135deg, #2ecc71 0%, #58d68d 100%);
}

.legend-power-used {
  background: #dc3545;
}

.legend-power-available {
  background: #28a745;
}

.legend-text {
  color: #666;
  font-weight: 500;
}

/* Power Section */
.power-section {
  background: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
}

/* Power Info Compact */
.power-info-compact {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 12px;
  margin-bottom: 15px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}

.power-info-compact .power-info-header h6 {
  margin: 0;
  font-size: 13px;
  font-weight: 700;
  color: #333;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 2px solid #e0e0e0;
}

.power-summary-compact {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  margin: 10px 0;
  font-size: 12px;
}

.power-summary-compact .power-label {
  font-weight: 600;
  color: #555;
}

.power-summary-compact .power-value {
  font-weight: 700;
  font-size: 16px;
  color: #2196f3;
}

.power-bars-table-compact {
  overflow-x: auto;
}

.power-bars-table-compact table {
  width: 100%;
  margin: 0;
  font-size: 11px;
  border-collapse: collapse;
}

.power-bars-table-compact th {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
  padding: 6px 4px;
  text-align: center;
  border: 1px solid #dee2e6;
}

.power-bars-table-compact td {
  padding: 6px 4px;
  text-align: center;
  border: 1px solid #dee2e6;
  font-size: 10px;
}

/* Power Bar Visualization */
.rack-frame-with-power {
  display: flex;
  gap: 20px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  justify-content: center;
  align-items: flex-end;
}

.power-bar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.power-bar {
  width: 120px;
  height: 400px;
  background: white;
  border: 3px solid #999;
  border-radius: 8px;
  display: flex;
  flex-direction: column-reverse;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.power-bar-used {
  background: #dc3545;
  transition: height 0.3s ease;
}

.power-bar-available {
  flex: 1;
  background: #28a745;
}

.power-bar-label {
  display: flex;
  align-items: center;
  justify-content: center;
}

.power-bar-number {
  width: 35px;
  height: 35px;
  background: #dc3545;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 18px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

@media (max-width: 1400px) {
  .three-views-container {
    grid-template-columns: 1fr;
  }

  .rack-frame {
    max-width: 500px;
  }
}
</style>
