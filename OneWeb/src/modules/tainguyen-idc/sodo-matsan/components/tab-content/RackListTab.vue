<template>
  <div class="rack-list-tab">
    <div class="tab-content-area">
      <!-- Danh sách Rack -->
      <div class="legend-title">Danh sách Rack</div>

      <DataGrid v-if="rackColumns.length > 0" :columns="rackColumns" :dataSource="rackList" :allowSorting="true"
        :allowPaging="true" :pageSettings="{ pageSize: 10 }" :showFilter="false" dataKeyField="id" @recordDoubleClick="handleRackRowDoubleClicked"
        :enablePagingServer="false" />

      <!-- Danh sách U theo trang thái CCDV -->
      <div class="legend-title mt-4">Danh sách U theo trạng thái CCDV</div>

      <!-- Filters -->
      <div class="row pb-3">
        <div class="col-md-3">
          <SelectExt
            v-model="filterStatus"
            :options="statusOptions"
            placeholder="Tất cả trạng thái"
            @change="handleFilterChange"
          />
        </div>
        <div class="col-md-3">
          <SelectExt
            v-model="filterRack"
            :options="rackOptions"
            placeholder="Tất cả Rack"
            @change="handleFilterChange"
          />
        </div>
      </div>

      <DataGrid v-if="uColumns.length > 0" :columns="uColumns" :dataSource="filteredUList" :allowSorting="true"
        :allowPaging="true" :pageSettings="{ pageSize: 10 }" :showFilter="false" dataKeyField="id"
        :enablePagingServer="false" />
    </div>
    <RackDetailModal ref="ModalThongTinRack" :rackDetail="rackSelectedId" />
    <b-modal ref="ModalThongTinU" size="xl" hide-footer title="Thông tin U" modal-class="modal-thong-tin-u">
      <UDetailForm :uId="uSelectedId" @close-modal="() => $refs.ModalThongTinU.hide()" />
    </b-modal>
  </div>
</template>

<script>
import DataGrid from '@/components/Controls/DataGrid'
import SelectExt from '@/components/Controls/SelectExt'
import RackDetailModal from '../../../sodo-phong-zone/components/modals/RackDetailModal.vue'
import UDetailForm from '../../../thongtin-u/index.vue'

export default {
  name: 'RackListTab',

  components: {
    DataGrid,
    SelectExt,
    RackDetailModal,
    UDetailForm
  },

  props: {
    roomId: {
      type: [String, Number],
      required: true
    }
  },

  data() {
    return {
      filterStatus: '-1',
      filterRack: '-1',
      rackList: [],
      uList: [],
      rackSelectedId: null,
      uSelectedId: null
    }
  },

  created() {
    this.getListRacks()
    this.getListU()
  },

  computed: {
    statusOptions() {
      return [
        { id: '-1', text: 'Tất cả trạng thái' },
        { id: '0', text: 'Đã sử dụng' },
        { id: '1', text: 'Chưa sử dụng' },
        { id: '2', text: 'Đang lắp đặt' }
      ]
    },
    rackOptions() {
      return [
        { id: '-1', text: 'Tất cả Rack' },
        ...this.rackList.map(rack => ({ id: rack.code, text: rack.code }))
      ]
    },
    rackColumns() {
      return [
        { fieldName: 'code', headerText: 'Rack', width: 120, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'coordinates', headerText: 'Toạ độ', width: 100, textAlign: 'Center', allowFiltering: true },
        { fieldName: 'type', headerText: 'Loại Rack', width: 150, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'totalU', headerText: 'Số U', width: 80, textAlign: 'Center', allowFiltering: false },
        { fieldName: 'usedU', headerText: 'Đã sử dụng', width: 100, textAlign: 'Center', allowFiltering: false },
        {
          fieldName: 'usagePercent',
          headerText: 'Tỷ lệ sử dụng',
          width: 200,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.colUsageTemplate()
        },
        {
          fieldName: 'actions',
          headerText: 'Thao tác',
          width: 100,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.colRackActionTemplate()
        }
      ]
    },

    uColumns() {
      return [
        { fieldName: 'uId', headerText: 'ID U', width: 100, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'uCode', headerText: 'Nhãn U', width: 120, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'rack', headerText: 'Rack', width: 120, textAlign: 'Center', allowFiltering: true },
        { fieldName: 'device', headerText: 'Thiết bị', width: 200, textAlign: 'Left', allowFiltering: true },
        {
          fieldName: 'statusText',
          headerText: 'Trạng thái',
          width: 150,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.colStatusTemplate()
        },
        {
          fieldName: 'actions',
          headerText: 'Thao tác',
          width: 100,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.colUActionTemplate()
        }
      ]
    },

    filteredUList() {
      let list = this.uList

      if (this.filterStatus && this.filterStatus !== '-1') {
        list = list.filter((u) => u.status === this.filterStatus)
      }

      if (this.filterRack && this.filterRack !== '-1') {
        list = list.filter((u) => u.rack === this.filterRack)
      }

      return list
    }
  },

  methods: {
    // Template cho cột Usage với progress bar
    colUsageTemplate() {
      const parent = this
      return function () {
        return {
          template: {
            template: `
              <div style="display: flex; align-items: center; gap: 10px;">
                <span style="min-width: 40px;">{{ getPercent(data) }}%</span>
                <div style="flex: 1; height: 8px; background: #e0e0e0; border-radius: 4px; overflow: hidden;">
                  <div :style="{ height: '100%', width: getPercent(data) + '%', background: '#2196F3', borderRadius: '4px' }"></div>
                </div>
              </div>
            `,
            methods: {
              getPercent(data) {
                return data.usagePercent || 0
              }
            }
          }
        }
      }
    },

    // Template cho cột Action của Rack
    colRackActionTemplate() {
      const parent = this
      return function () {
        return {
          template: {
            template: `<button class="btn btn-sm btn-primary" @click.stop="viewRack()" style="padding: 4px 12px; font-size: 12px;">Xem</button>`,
            methods: {
              viewRack() {
                parent.handleRackRowClicked(this.data)
              }
            }
          }
        }
      }
    },

    // Template cho cột Status
    colStatusTemplate() {
      const parent = this
      return function () {
        return {
          template: {
            template: `<span class="badge" :class="getBadgeClass(data)" style="padding: 4px 10px; font-size: 11px;">{{ data.statusText }}</span>`,
            methods: {
              getBadgeClass(data) {
                return data.status === '0' ? 'badge-primary' : data.status === '1' ? 'badge-secondary' : 'badge-warning'
              }
            }
          }
        }
      }
    },

    // Template cho cột Action của U
    colUActionTemplate() {
      const parent = this
      return function () {
        return {
          template: {
            template: `<button class="btn btn-sm btn-primary" @click="viewU()" style="padding: 4px 12px; font-size: 12px;">Xem</button>`,
            methods: {
              viewU() {
                parent.handleURowClicked(this.data)
              }
            }
          }
        }
      }
    },

    handleFilterChange() {
      this.$forceUpdate()
    },

    handleRackRowClicked(row) {
      this.rackSelectedId = {
        id: row.id,
        name: row.code
      }
      this.$nextTick(() => {
        this.$refs.ModalThongTinRack.show()
      })
    },

    handleURowClicked(row) {
      console.log('Clicked U row:', row)
      this.uSelectedId = row.uId
      this.$nextTick(() => {
        this.$refs.ModalThongTinU.show()
      })
    },

    async getListRacks() {
      let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-by-room', {
        phongId: this.roomId
      })
      this.rackList = rs.data.map((rack) => {
        return {
          id: rack.ID,
          code: rack.TENTAT ?? rack.TEN,
          coordinates: rack.TOADO_X && rack.TOADO_Y ? `${rack.TOADO_X}, ${rack.TOADO_Y}` : '',
          type: rack.LOAIRACK,
          totalU: rack.SO_U,
          usedU: rack.TOTAL_USING,
          usagePercent: Math.round((rack.TOTAL_USING / rack.SO_U) * 100)
        }
      })
    },

    async getListU() {
      let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-u-by-room', {
        phongId: this.roomId
      })
      this.uList = rs.data.map((u) => {
        return {
          id: u.RACKUNIT_ID,
          uId: u.RACKUNIT_ID,
          uCode: u.TEN,
          rack: u.RACK,
          device: u.THIETBI,
          status: u.TRANGTHAI_ID.toString(),
          statusText: u.TRANGTHAI
        }
      })
    },
    handleRackRowDoubleClicked(args) {
      this.rackSelectedId = {
        id: args.rowData.id,
        type: 'RACK'
      }
      this.$emit('selectObjectTaiNguyen', this.rackSelectedId)
    }
  }
}
</script>

<style scoped>
.tab-content-area {
  border-radius: 4px;
  min-height: 150px;
}

/* Section Header */
.section-header {
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #ddd;
}

.section-header h6 {
  font-weight: 600;
  color: #333;
  margin: 0;
  font-size: 15px;
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

::v-deep .grid-ext .badge-primary {
  background-color: #2196f3;
  color: white;
}

::v-deep .grid-ext .badge-success {
  background-color: #4caf50;
  color: white;
}

::v-deep .grid-ext .btn-primary {
  background-color: #2196f3;
  border-color: #2196f3;
  color: white;
  border-radius: 4px;
}

::v-deep .grid-ext .btn-primary:hover {
  background-color: #1976d2;
  border-color: #1976d2;
}

/* Responsive */
@media (max-width: 768px) {
  .col-md-3 {
    margin-bottom: 10px;
  }
}
</style>
<style>
.modal-thong-tin-u .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.modal-thong-tin-u .modal-body {
  padding: 0;
  overflow: hidden;
}</style>
