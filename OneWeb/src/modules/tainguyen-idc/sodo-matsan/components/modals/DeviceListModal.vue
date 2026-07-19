<template>
  <b-modal ref="deviceListModal" :title="dialogTitle" size="xl" hide-footer @hidden="closeDialog" modal-class="device-list-modal">
    <div class="device-list-container">
      <!-- Filters -->
      <div class="row mb-3">
        <div class="col-md-4">
          <label class="filter-label">Rack:</label>
          <SelectExt
            v-model="filterRack"
            :options="rackOptions"
            placeholder="Tất cả Rack"
            @change="handleFilterChange"
          />
        </div>
        <div class="col-md-4">
          <label class="filter-label">Trạng thái vận hành:</label>
          <SelectExt
            v-model="filterStatus"
            :options="statusOptions"
            placeholder="Tất cả"
            @change="handleFilterChange"
          />
        </div>
        <div class="col-md-4">
          <label class="filter-label">Tìm kiếm:</label>
          <input
            v-model="searchText"
            type="text"
            class="form-control form-control-sm"
            placeholder="Tìm theo tên thiết bị..."
            @input="handleSearchInput"
          />
        </div>
      </div>

      <!-- Device List Table -->
      <DataGrid
        class="mt-3"
        v-if="deviceColumns.length > 0"
        :columns="deviceColumns"
        :dataSource="filteredDeviceList"
        :allowSorting="true"
        :allowPaging="true"
        :pageSettings="{ pageSize: 10 }"
        :showFilter="false"
        dataKeyField="id"
        :enablePagingServer="false"
      />

      <!-- Summary -->
      <div class="summary-info mt-3">
        <span><strong>Tổng số thiết bị:</strong> {{ filteredDeviceList.length }}</span>
      </div>
    </div>
  </b-modal>
</template>

<script>
import DataGrid from '@/components/Controls/DataGrid'
import SelectExt from '@/components/Controls/SelectExt'

export default {
  name: 'DeviceListModal',

  components: {
    DataGrid,
    SelectExt
  },

  props: {
    roomId: {
      type: [String, Number],
      required: true
    },
    roomName: {
      type: String,
      default: ''
    }
  },

  data () {
    return {
      deviceList: [],
      filteredDeviceList: [],
      filterRack: '-1',
      filterStatus: '-1',
      searchText: '',
      searchDebounceTimeout: null,
      statusList: []
    }
  },

  computed: {
    dialogTitle () {
      return `Danh sách thiết bị - ${this.roomName || 'Phòng'}`
    },

    rackOptions () {
      return [
        { id: '-1', text: 'Tất cả Rack' },
        ...this.uniqueRacks.map(rack => ({ id: rack, text: rack }))
      ]
    },

    statusOptions () {
      return [
        { id: '-1', text: 'Tất cả' },
        ...this.statusList.map(status => ({ id: status.value, text: status.label }))
      ]
    },

    deviceColumns () {
      return [
        { fieldName: 'idc', headerText: 'IDC', width: 150, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'rack', headerText: 'Rack', width: 150, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'deviceName', headerText: 'Tên Thiết bị', width: 300, textAlign: 'Left', allowFiltering: true },
        {
          fieldName: 'status',
          headerText: 'Trạng thái vận hành',
          width: 200,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.colStatusTemplate()
        }
      ]
    },

    uniqueRacks () {
      const racks = [...new Set(this.deviceList.map((device) => device.rack).filter((rack) => rack && rack !== 'N/A'))]
      return racks.sort()
    }
  },

  methods: {
    colStatusTemplate () {
      const parent = this
      return function () {
        return {
          template: {
            template: `<span >{{ data.status }}</span>`,
            methods: {
              
            }
          }
        }
      }
    },

    applyFilters () {
      let list = this.deviceList

      // Filter by rack
      if (this.filterRack && this.filterRack !== '-1') {
        list = list.filter((device) => device.rack === this.filterRack)
      }

      // Filter by status
      if (this.filterStatus && this.filterStatus !== '-1') {
        list = list.filter((device) => device.statusCode == this.filterStatus)
      }

      // Filter by search text
      if (this.searchText) {
        const searchLower = this.searchText.toLowerCase().trim()
        list = list.filter((device) => device.deviceName.toLowerCase().includes(searchLower))
      }

      this.filteredDeviceList = list
    },

    handleFilterChange () {
      this.applyFilters()
    },

    handleSearchInput () {
      if (this.searchDebounceTimeout) {
        clearTimeout(this.searchDebounceTimeout)
      }
      
      this.searchDebounceTimeout = setTimeout(() => {
        this.applyFilters()
      }, 500)
    },

    async openDialog () {
      try {
        this.$root.showLoading(true)
        await Promise.all([
          this.getStatusList(),
          this.getDeviceList()
        ])
        this.$refs.deviceListModal.show()
      } catch (error) {
        console.error('Error opening device list dialog:', error)
        this.$root.toastError('Không thể tải danh sách thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    },

    closeDialog () {
      this.deviceList = []
      this.filteredDeviceList = []
      this.filterRack = '-1'
      this.filterStatus = '-1'
      this.searchText = ''
      if (this.searchDebounceTimeout) {
        clearTimeout(this.searchDebounceTimeout)
        this.searchDebounceTimeout = null
      }
    },

    async getStatusList () {
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc/trangthai-vanhanh/get')
        
        if (rs && rs.data) {
          this.statusList = rs.data.map((item) => ({
            value: item.TTVH_ID,
            label: item.TRANGTHAI_VH
          }))
        }
      } catch (error) {
        console.error('Error fetching status list:', error)
        this.statusList = []
      }
    },

    async getDeviceList () {
      try {
        // Gọi API để lấy danh sách thiết bị theo phòng
        const rs = await this.$root.context.post('/web-ecms/idc/phong-zone/get-thiet-bi-by-phong-zone-id', {
          id: this.roomId
        })

        if (rs && rs.data) {
          this.deviceList = rs.data.map((device) => ({
            id: device.THIETBI_ID,
            idc: device.IDC || 'N/A',
            rack: device.RACK || 'N/A',
            deviceName: device.THIETBI || 'N/A',
            statusCode: device.TTVH_ID,
            status: device.TRANGTHAI_VH || 'N/A'
          }))
          this.applyFilters()
        }
      } catch (error) {
        console.error('Error fetching device list:', error)
        this.$root.toastError('Lỗi khi tải danh sách thiết bị')
        this.deviceList = []
      }
    }
  }
}
</script>

<style scoped>
.col-md-4 {
  padding-left: 8px;
  padding-right: 8px;
}
.device-list-container {
  padding: 15px;
}

.filter-label {
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 5px;
  display: block;
}

.form-control-sm {
  font-size: 13px;
  height: 32px;
}

.form-control-sm:focus {
  border-color: #2196f3;
  box-shadow: 0 0 0 0.2rem rgba(33, 150, 243, 0.25);
}

.summary-info {
  padding: 10px;
  background-color: #f8f9fa;
  border-radius: 4px;
  font-size: 13px;
}
</style>

<style>
.device-list-modal .modal-body {
  padding: 0;
}
</style>
