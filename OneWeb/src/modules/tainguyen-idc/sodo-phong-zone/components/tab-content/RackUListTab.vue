<template>
  <div class="rack-u-list-tab">
    <div class="tab-content-area">
      <!-- Filters and Search -->
      <div class="row mb-2">
        <div class="col-md-3">
          <select v-model="filterStatus" class="form-control form-control-sm" @change="handleFilterChange">
            <option value="">Tất cả trạng thái</option>
            <option value="used">Đã sử dụng</option>
            <option value="empty">Còn trống</option>
          </select>
        </div>
        <div class="col-md-3">
          <select v-model="filterDeviceType" class="form-control form-control-sm" @change="handleFilterChange">
            <option value="">Tất cả thiết bị</option>
            <option v-for="item in deviceTypeOptions" :key="item.value" :value="item.value">
              {{ item.text }}
            </option>
          </select>
        </div>
        <div class="col-md-6">
          <input
            v-model="searchText"
            type="text"
            class="form-control form-control-sm"
            placeholder="Tìm kiếm theo U, thiết bị..."
            @input="handleFilterChange"
          />
        </div>
      </div>

      <!-- DataGrid Table -->
      <div class="row">
        <div class="col-md-12">
          <DataGrid
            class="mt-3"
            ref="dataGrid"
            :columns="columns"
            :dataSource="filteredList"
            :allowSorting="true"
            :allowPaging="false"
            :showFilter="false"
            :allowResizing="true"
            :panelDataHeight="'450px'"
            :EmptyRecordText="emptyRecordText"
            @rowClicked="handleRowClicked"
          />

          <!-- Summary -->
          <div class="d-flex justify-content-between align-items-center mt-3 pt-3 border-top">
            <div class="usage-summary">
              <span class="badge badge-secondary">Tổng: {{ getTotalU() }}U</span>
              <span class="badge badge-success ml-2">Đã dùng: {{ getUsedU() }}U</span>
              <span class="badge badge-info ml-2">Trống: {{ getRemainingU() }}U</span>
            </div>
            <div class="text-muted">
              <small>Hiển thị {{ filteredList.length }} / {{ uList.length }} U</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DataGrid from '@/components/Controls/DataGrid'

export default {
  name: 'RackUListTab',

  components: {
    DataGrid
  },

  props: {
    rackId: {
      type: [String, Number],
      required: true
    }
  },

  data () {
    return {
      rackData: {
        totalU: 42,
        uList: []
      },
      filterStatus: '',
      filterDeviceType: '',
      searchText: '',
      deviceTypeOptions: [],
      columns: [
        {
          fieldName: 'positionDisplay',
          headerText: 'Vị trí U',
          width: 80,
          textAlign: 'Center',
          allowFiltering: false
        },
        {
          fieldName: 'frontInfo',
          headerText: 'Mặt trước',
          width: 250,
          allowFiltering: false
        },
        
        {
          fieldName: 'backInfo',
          headerText: 'Mặt sau',
          width: 250,
          allowFiltering: false
        },
        
      ]
    }
  },

  computed: {
    emptyRecordText () {
      if (this.searchText || this.filterStatus || this.filterDeviceType) {
        return 'Không tìm thấy kết quả'
      }
      return 'Chưa có dữ liệu danh sách U'
    },

    hasUData () {
      return this.rackData.uList && this.rackData.uList.length > 0
    },

    uList () {
      if (this.hasUData) {
        // Group by position and combine front/back into single row
        const grouped = {}
        this.rackData.uList.forEach((u) => {
          if (!grouped[u.position]) {
            grouped[u.position] = {
              position: u.position,
              front: null,
              back: null
            }
          }
          if (u.side === 1) {
            grouped[u.position].front = u
          } else {
            grouped[u.position].back = u
          }
        })

        // Sort by position descending (U42 -> U1)
        const sorted = Object.values(grouped).sort((a, b) => b.position - a.position)
        return sorted.map((u) => this.formatUItem(u))
      }

      return []
    },

    filteredList () {
      let filtered = [...this.uList]

      // Filter by status
      if (this.filterStatus) {
        filtered = filtered.filter((u) => {
          const frontMatch = u.front && u.front.status === this.filterStatus
          const backMatch = u.back && u.back.status === this.filterStatus
          return frontMatch || backMatch
        })
      }

      // Filter by device type
      if (this.filterDeviceType) {
        filtered = filtered.filter((u) => {
          const frontMatch = u.front && u.front.deviceTypeId === this.filterDeviceType
          const backMatch = u.back && u.back.deviceTypeId === this.filterDeviceType
          return frontMatch || backMatch
        })
      }

      // Search
      if (this.searchText) {
        const search = this.searchText.toLowerCase().trim()
        filtered = filtered.filter((u) => {
          const posMatch = `u${u.position}`.includes(search)
          const frontMatch =
            u.front &&
            (u.front.deviceName.toLowerCase().includes(search) ||
              (u.front.deviceType && u.front.deviceType.toLowerCase().includes(search)))
          const backMatch =
            u.back &&
            (u.back.deviceName.toLowerCase().includes(search) ||
              (u.back.deviceType && u.back.deviceType.toLowerCase().includes(search)))
          return posMatch || frontMatch || backMatch
        })
      }

      return filtered
    }
  },

  created () {
    this.getDeviceTypes()
    this.getUListData()
  },

  methods: {
    formatUItem (u) {
      const formatSide = (side) => {
        if (!side) return null
        return {
          deviceId: side.deviceId || '',
          deviceName: side.deviceName || '-',
          deviceType: side.deviceType || '',
          deviceTypeId: side.deviceTypeId || '',
          power: side.power || '',
          status: side.status,
          statusText: side.status === 'used' ? 'ĐÃ LẮP ĐẶT' : 'CHƯA LẮP ĐẶT'
        }
      }

      const front = formatSide(u.front)
      const back = formatSide(u.back)

      return {
        position: u.position,
        positionDisplay: `U${u.position}`,
        front: front,
        back: back,
        frontInfo: front
          ? `${front.deviceId ? (front.deviceId + ' - ') : ''} ${front.deviceName}${front.deviceType ? ' (' + front.deviceType + ')' : ''}${
              front.power ? ' - ' + front.power + 'W' : ''
            }`
          : 'Chưa có thiết bị',
        frontStatus: front ? front.statusText : 'CHƯA LẮP ĐẶT',
        backInfo: back
          ? `${back.deviceId ? (back.deviceId + ' - ') : ''} ${back.deviceName}${back.deviceType ? ' (' + back.deviceType + ')' : ''}${
              back.power ? ' - ' + back.power + 'W' : ''
            }`
          : 'Chưa có thiết bị',
        backStatus: back ? back.statusText : 'CHƯA LẮP ĐẶT'
      }
    },

    frontStatusTemplate () {
      return {
        template: {
          template: `<span :class="getStatusClass(data.front)">{{ data.frontStatus }}</span>`,
          methods: {
            getStatusClass (side) {
              if (!side || side.status !== 'used') {
                return 'badge badge-secondary'
              }
              
              return 'badge badge-success'
            }
          }
        }
      }
    },

    backStatusTemplate () {
      return {
        template: {
          template: `<span :class="getStatusClass(data.back)">{{ data.backStatus }}</span>`,
          methods: {
            getStatusClass (side) {
              if (!side || side.status !== 'used') {
                return 'badge badge-secondary'
              }
              return 'badge badge-success'
            }
          }
        }
      }
    },

    handleFilterChange () {
      // Trigger filter update
      this.$forceUpdate()
    },

    handleRowClicked (rowIndex, rowData) {
      this.$emit('rowClicked', rowIndex, rowData)
    },

    getTotalU () {
      return this.uList.length
    },

    getUsedU () {
      let count = 0
      this.uList.forEach((u) => {
        if (u.front && u.front.status === 'used') count++
        if (u.back && u.back.status === 'used') count++
      })
      return count / 2
    },

    getRemainingU () {
      let count = 0
      this.uList.forEach((u) => {
        if (!u.front || u.front.status === 'empty') count++
        if (!u.back || u.back.status === 'empty') count++
      })
      return count / 2
    },

    async getDeviceTypes () {
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-LOAI-TBI'
        })

        if (rs && rs.error === '200' && rs.data) {
          this.deviceTypeOptions = rs.data.map(item => ({
            value: item.ID,
            text: item.TEN
          }))
        }
      } catch (error) {
        console.error('Error loading device types:', error)
      }
    },

    async getUListData () {
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-u-2-mat-theo-rack', {
          rackId: this.rackId
        })

        if (rs && rs.error === '200' && rs.data) {
          // Group by VITRI (position) and MAT_RACKUNIT (side)
          const groupedData = {}

          rs.data.forEach((item) => {
            const position = item.VITRI
            if (!groupedData[position]) {
              groupedData[position] = {
                position: position,
                side1: null,
                side2: null
              }
            }

            const sideData = {
              rackUnitId: item.RACKUNIT_ID,
              deviceId: item.THIETBI_ID,
              deviceName: item.THIETBI,
              deviceType: item.LOAITBI,
              deviceTypeId: item.LOAITBI_ID,
              power: item.CS_DINHDANH,
              status: item.TRANGTHAI_TEXT
            }

            if (item.MAT_RACKUNIT === 1) {
              groupedData[position].side1 = sideData
            } else if (item.MAT_RACKUNIT === 2) {
              groupedData[position].side2 = sideData
            }
          })

          // Convert to uList format - create entries for each side
          const uList = []
          Object.values(groupedData).forEach((item) => {
            // Side 1 (Front)
            if (item.side1) {
              const side1Used = item.side1.deviceId > 0
              uList.push({
                position: item.position,
                side: 1,
                rackUnitId: item.side1.rackUnitId,
                deviceId: side1Used ? item.side1.deviceId : '',
                deviceName: side1Used ? item.side1.deviceName : '',
                deviceType: item.side1.deviceType !== '-' ? item.side1.deviceType : '',
                deviceTypeId: item.side1.deviceTypeId || '',
                power: item.side1.power || '',
                status: side1Used ? 'used' : 'empty',
                statusText: item.side1.status
              })
            }

            // Side 2 (Back)
            if (item.side2) {
              const side2Used = item.side2.deviceId > 0
              uList.push({
                position: item.position,
                side: 2,
                rackUnitId: item.side2.rackUnitId,
                deviceId: side2Used ? item.side2.deviceId : '',
                deviceName: side2Used ? item.side2.deviceName : '',
                deviceType: item.side2.deviceType !== '-' ? item.side2.deviceType : '',
                deviceTypeId: item.side2.deviceTypeId || '',
                power: item.side2.power || '',
                status: side2Used ? 'used' : 'empty',
                statusText: item.side2.status
              })
            }
          })

          this.rackData.uList = uList
          this.rackData.totalU = Object.keys(groupedData).length
        }
      } catch (error) {
        console.error('Error loading U list data:', error)
        this.$root.showError('Không thể tải dữ liệu danh sách U')
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

/* Filters */
.form-control-sm {
  font-size: 13px;
  height: 32px;
}

.form-control-sm:focus {
  border-color: #2196f3;
  box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.25);
}

/* Summary */
.usage-summary .badge {
  font-size: 12px;
  padding: 6px 12px;
}

.badge-success {
  background-color: #4caf50;
}

.badge-info {
  background-color: #2196f3;
}

.badge-secondary {
  background-color: #6c757d;
}

/* Responsive */
@media (max-width: 768px) {
  .col-md-3,
  .col-md-6 {
    margin-bottom: 10px;
  }
}

/* DataGrid Custom Styles */
::v-deep .grid-ext .e-gridcontent {
  font-size: 13px;
}

::v-deep .grid-ext .e-headercell {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #333;
  font-size: 12px;
}

::v-deep .grid-ext .badge {
  font-size: 11px;
  padding: 4px 10px;
  font-weight: 500;
}
</style>
