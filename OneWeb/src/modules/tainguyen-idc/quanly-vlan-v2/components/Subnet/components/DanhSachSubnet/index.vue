<template>
  <div class="popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" class="pointer" @click="onClickSearch">
            <span class="icon one-search"></span>Tìm kiếm
          </a>
        </li>
        <li>
          <b-link
            href="javascript:void(0)"
            @click="onConfirmSelection"
            :class="{ disabled: grid.selectedItems.length === 0 }"
          >
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chọn
          </b-link>
        </li>
      </ul>
    </div>
    <div class="modal-content p-2">
      <!-- Filter Tìm kiếm -->
      <div class="box-form">
        <div class="row">
          <div class="legend-title">Trường tìm kiếm</div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="col-sm-4 key">Tên Subnet</div>
                  <div class="col-sm-8 value">
                    <input type="text" class="form-control" v-model="searchParams.ten" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="col-sm-4 key">IDC</div>
                  <div class="col-sm-8 value">
                    <SelectExt
                      :dataSource="dropdowns.idcs"
                      :allowFiltering="true"
                      v-model="searchParams.idc_id"
                      dataValueField="id"
                      dataTextField="ten"
                      @change="onIdcChange"
                    ></SelectExt>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="col-sm-4 key">Hạ tầng mạng</div>
                  <div class="col-sm-8 value">
                    <SelectExt
                      :dataSource="dropdowns.htm"
                      :allowFiltering="true"
                      v-model="searchParams.htm_id"
                      dataValueField="id"
                      dataTextField="ten"
                      :placeholder="searchParams.idc_id ? '' : 'Chọn IDC trước'"
                      :disabled="!searchParams.idc_id"
                      @change="onHtmChange"
                    ></SelectExt>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="col-sm-4 key">Thiết bị mạng</div>
                  <div class="col-sm-8 value">
                    <SelectExt
                      :dataSource="dropdowns.devices"
                      :allowFiltering="true"
                      v-model="searchParams.thietbi_id"
                      dataValueField="id"
                      dataTextField="ten"
                      :placeholder="searchParams.htm_id ? '' : 'Chọn Hạ tầng mạng trước'"
                      :disabled="!searchParams.htm_id"
                    >
                    </SelectExt>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="col-sm-4 key">Phân loại IP</div>
                  <div class="col-sm-8 value">
                    <SelectExt
                      :dataSource="dropdowns.phanloai"
                      :allowFiltering="true"
                      v-model="searchParams.phanloaiip_id"
                      dataValueField="id"
                      dataTextField="ten"
                    >
                    </SelectExt>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="col-sm-4 key">Loại IP</div>
                  <div class="col-sm-8 value">
                    <SelectExt
                      :dataSource="dropdowns.loai"
                      :allowFiltering="true"
                      v-model="searchParams.loaiip_id"
                      dataValueField="id"
                      dataTextField="ten"
                    >
                    </SelectExt>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <DataGrid
        ref="gridItems"
        :allowPaging="true"
        :enablePagingServer="false"
        :showColumnCheckbox="true"
        :selectionSettings="{ type: 'Multiple' }"
        v-bind:columns="grid.columns"
        :dataSource="grid.dataSource"
        :enabledSelectFirstRow="false"
        @selectedItemsChanged="onSelectedRowChanged"
        @dataBound="onDataBound"
      >
      </DataGrid>
    </div>
  </div>
</template>
<script>
import vlan from '../../../../api/vlan'
import danhmuc from '../../../../api/danhmuc'
export default {
  name: 'DanhSachSubnet',
  props: {
    vlanId: {
      type: [Number, String],
      required: false,
      default: null
    },
    selectedItems: {
      type: Array,
      required: false,
      default: () => []
    }
  },
  data() {
    return {
      animationSettings: { effect: 'None' },
      searchParams: {
        ten: '',
        idc_id: null,
        vlan_id: null,
        htm_id: null,
        thietbi_id: null,
        phanloaiip_id: null,
        loaiip_id: null
      },
      dropdowns: {
        idcs: [],
        htm: [],
        devices: [],
        phanloai: [],
        loai: []
      },
      grid: {
        selectedItems: [],
        dataSource: [],
        columns: [
          { fieldName: 'subnet_id', headerText: 'ID Subnet', textAlign: 'Left', width: '100px' },
          { fieldName: 'ten_subnet', headerText: 'Tên Subnet', textAlign: 'Left', width: '180px' },
          { fieldName: 'ten_loai_ip', headerText: 'Loại IP', textAlign: 'Left', width: '100px' },
          { fieldName: 'ten_phan_loai_ip', headerText: 'Phân loại IP', textAlign: 'Left', width: '120px' },
          { fieldName: 'subnet', headerText: 'Subnet', textAlign: 'Left', width: '100px' },
          { fieldName: 'subnetmask', headerText: 'Subnet mask', textAlign: 'Left', width: '120px' },
          { fieldName: 'gateway', headerText: 'Gateway', textAlign: 'Left', width: '100px' },
          { fieldName: 'trangthai_id', headerText: 'Trạng thái CCDV', textAlign: 'Left', width: '150px' },
          { fieldName: 'hieu_luc', headerText: 'Hiệu lực', textAlign: 'Left', width: '100px' }
        ]
      }
    }
  },
  watch: {
    selectedItems: {
      deep: true,
      handler(newVal) {
        this.grid.selectedItems = Array.isArray(newVal) ? [...newVal] : []
        this.$nextTick(() => this.selectRowsFromSelectedItems())
      }
    }
  },
  async mounted() {
    this.searchParams.vlan_id = this.vlanId
    this.grid.selectedItems = Array.isArray(this.selectedItems) ? [...this.selectedItems] : []

    // Tải dữ liệu
    await this.searchSubnet()
    await this.loadDanhMucMaster()
  },
  methods: {
    /**
     *   Load dropdown filter tìm kiếm
     */

    async loadDanhMucMaster() {
      this.$root.showLoading(true)
      try {
        const promise = []

        promise.push(this.fetchIdcOptions())
        promise.push(this.fetchLoaiIpOptions())
        promise.push(this.fetchPhanLoaiIpOptions())

        if (this.searchParams?.idc_id) {
          promise.push(this.fetchHtmOptions())
        }
        if (this.searchParams?.htm_id) {
          promise.push(this.fetchDeviceOptions())
        }

        await Promise.all(promise)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải dữ liệu ban đầu')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async fetchIdcOptions() {
      try {
        // Lấy phân vùng của user hiện tại
        const raw = await danhmuc.getIdcOptions(this.$root.context)
        this.dropdowns.idcs = this.normalizeOptions(raw)
        this.dropdowns.idcs.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách IDC')
      }
    },
    // hạ tầng mạng -> phụ thuộc IDC
    async fetchHtmOptions() {
      try {
        if (!this.searchParams.idc_id) {
          this.dropdowns.htm = []
          return
        }
        const raw = await danhmuc.getInfrastructureByIdc(this.$root.context, this.searchParams.idc_id)
        this.dropdowns.htm = this.normalizeOptions(raw)
        this.dropdowns.htm.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Hạ tầng mạng')
      }
    },
    // thiết bị mạng -> phụ thuộc Hạ tầng mạng
    async fetchDeviceOptions() {
      try {
        if (!this.searchParams.htm_id) {
          this.dropdowns.devices = []
          return
        }
        const raw = await danhmuc.getNetworkDevicesByHatang(this.$root.context, this.searchParams.htm_id)
        this.dropdowns.devices = this.normalizeOptions(raw)
        this.dropdowns.devices.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Thiết bị mạng')
      }
    },
    // loại IP -> không phụ thuộc
    async fetchLoaiIpOptions() {
      try {
        const raw = await danhmuc.getLoaiIp(this.$root.context)
        this.dropdowns.loai = this.normalizeOptions(raw)
        this.dropdowns.loai.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Loại IP')
      }
    },
    // phân loại IP -> không phụ thuộc
    async fetchPhanLoaiIpOptions() {
      try {
        const raw = await danhmuc.getPhanLoaiIp(this.$root.context)
        this.dropdowns.phanloai = this.normalizeOptions(raw)
        this.dropdowns.phanloai.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Phân loại IP')
      }
    },
    normalizeOptions(list) {
      if (!Array.isArray(list)) return []

      const res = list.map((item) =>
        Object.fromEntries(Object.entries(item).map(([key, value]) => [key.toLowerCase(), value]))
      )
      return res
    },

    /**
     *   Tìm kiếm Subnet
     */

    async searchSubnet() {
      this.$root.showLoading(true)
      try {
        const params = this.preparePayload()
        const data = await vlan.searchSubnet(this.$root.context, params)

        this.grid.dataSource = data
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi lấy danh sách Subnet')
      } finally {
        this.$root.showLoading(false)
      }
    },
    preparePayload() {
      return {
        ten: this.searchParams.ten,
        idc_id: this.searchParams.idc_id,
        vlan_id: this.searchParams.vlan_id,
        htm_id: this.searchParams.htm_id,
        thietbi_id: this.searchParams.thietbi_id,
        phanloaiip_id: this.searchParams.phanloaiip_id,
        loaiip_id: this.searchParams.loaiip_id,
        phuong_thuc: 'vlan_not_used'
      }
    },

    /**
     *   Event handlers
     */

    async onIdcChange() {
      // Reset HTM và TBM
      this.searchParams.htm_id = null
      this.searchParams.thietbi_id = null

      // Fetch lại HTM
      await this.fetchHtmOptions()

      // Clear TBM dropdown
      this.dropdowns.devices = []
    },

    async onHtmChange() {
      // Reset TBM
      this.searchParams.thietbi_id = null

      // Fetch lại TBM
      await this.fetchDeviceOptions()
    },

    resetSelection() {
      this.grid.selectedItems = []
      this.$emit('item-selected', [])
    },
    onClickSearch() {
      this.searchSubnet()
    },
    async onConfirmSelection() {
      if (this.grid.selectedItems) {
        // Trường hợp đã có VLAN: hỏi xác nhận để thêm ngay
        if (this.vlanId) {
          const confirmed = await this.$confirm(
            `Bạn có chắc chắn muốn thêm những Subnet này vào VLAN?`,
            'Xác nhận thêm Subnet',
            { confirmButtonText: 'Thêm', cancelButtonText: 'Hủy', type: 'warning' }
          )
          if (confirmed) {
            this.$emit('item-selected', this.grid.selectedItems)
          }
        }
        // Trường hợp không có VLAN, không cần xác nhận vì mới chỉ lưu tạm
        else {
          this.$emit('item-selected', this.grid.selectedItems)
        }
      }
    },
    onSelectedRowChanged() {
      console.log('onSelectedRowChanged')
      this.grid.selectedItems = this.$refs.gridItems.$refs.grid.getSelectedRecords()
    },
    onDataBound() {
      this.selectRowsFromSelectedItems()
    },
    selectRowsFromSelectedItems() {
      const grid = this.$refs.gridItems

      if (!this.grid.selectedItems || this.grid.selectedItems.length === 0) {
        return
      }

      try {
        const indexes = this.grid.selectedItems
          .map((selectedItem) => {
            const selId = selectedItem?.subnet_id
            if (selId === undefined || selId === null) return -1
            // Tìm index trong dataSource
            const index = this.grid.dataSource.findIndex((row) => {
              const rowId = row?.subnet_id
              return rowId == selId
            })
            return index
          })
          .filter((idx) => idx !== -1 && idx >= 0)

        // Select các rows theo indexes
        if (indexes.length > 0) {
          grid.selectRows(indexes)
        }
      } catch (error) {
        console.error('Error selecting rows:', error)
      }
    }
  }
}
</script>

<style scoped>
.item-selector-wrapper {
  width: 100%;
}
</style>
