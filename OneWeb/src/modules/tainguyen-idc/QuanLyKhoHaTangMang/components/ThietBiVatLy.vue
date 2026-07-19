<template>
  <div>
    <!-- TAB NAVIGATION -->
    <div class="">
      <div class="nav tab-over tabs" style="margin-top: 20px;">
        <a
          href="#tabDanhSachThietBiVatLy"
          v-on:click="onChangeTab(1)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 1 }"
          >Danh sách thiết bị vật lý</a
        >
        <a
          href="#tabLichSuThayDoi"
          v-on:click="onChangeTab(2)"
          data-toggle="tab"
          class="tab"
          :class="{ active: tab_index == 2 }"
          >Lịch sử thay đổi thiết bị vật lý</a
        >
      </div>
    </div>
    <div class="tab-content" style="margin-bottom: 20px;">
      <div class="tab-pane" id="tabDanhSachThietBiVatLy" :class="{ active: tab_index == 1 }">
        <div class="row d-flex align-items-center">
          <div class="legend-title d-flex justify-content-between align-items-center">
            <span>Danh sách thiết bị</span>
            <div class="btn-action d-flex gap-2">
              <button class="btn btn-sm btn-outline-danger" @click="onDelete()"><span class="icon one-trash"></span> Xoá</button>
              <button class="btn btn-sm btn-outline-primary" @click="onSelectDevice()"><span class="icon one-file-plus"></span> Chọn thiết bị</button>
            </div>
          </div>
        </div>
        <div class="table-content">
          <DataGrid
            v-bind:columns="[
              {
                fieldName: 'maThietBi',
                headerText: 'Mã thiết bị',
                textAlign: 'Left',
                allowFiltering: true,
                width: 200
              },
              { fieldName: 'ten', headerText: 'Tên thiết bị', textAlign: 'Left', allowFiltering: true, width: 150 },
              {
                fieldName: 'kyHieu',
                headerText: 'Ký hiệu',
                textAlign: 'Left',
                allowFiltering: true,
                width: 150
              },
              { fieldName: 'serialNumber', headerText: 'Serial', textAlign: 'Left', allowFiltering: true, width: 120 },
              
              { fieldName: 'soHuu', headerText: 'Sở hữu', textAlign: 'Left', allowFiltering: true, width: 120 },
              { fieldName: 'idcName', headerText: 'IDC', textAlign: 'Left', allowFiltering: true, width: 120 },
              { fieldName: 'toaNhaName', headerText: 'Toà nhà', textAlign: 'Left', allowFiltering: true, width: 120 },
              { fieldName: 'rackName', headerText: 'Rack', textAlign: 'Left', allowFiltering: true, width: 120 },
              {
                fieldName: 'trangThaiVanHanhText',
                headerText: 'Trạng thái vận hành',
                textAlign: 'Left',
                allowFiltering: true,
                width: 120
              },
              {
                fieldName: 'actions',
                headerText: 'Thao tác',
                textAlign: 'Center',
                template: actionColumn(),
                width: 120
              }
            ]"
            :dataSource="thietBiVatLyList"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :showColumnCheckbox="true"
            :allowExcelExport="true"
            :enabledSelectFirstRow="false"
            @selectedItemsChanged="onSelectThietBiCheckbox"
            dataKeyField="id"
            ref="grdItems"
          />
        </div>
      </div>
      <div class="tab-pane" id="tabLichSuThayDoi" :class="{ active: tab_index == 2 }">
        <div class="row d-flex align-items-center">
          <div class="legend-title d-flex justify-content-between align-items-center">
            <span>Log danh sách thiết bị</span>
          </div>
        </div>
        <div class="table-content">
          <DataGrid
            v-bind:columns="[
              { fieldName: 'stt', headerText: 'STT', textAlign: 'Left', allowFiltering: true, width: 90 },
              {
                fieldName: 'maThietBi',
                headerText: 'Mã thiết bị',
                textAlign: 'Left',
                allowFiltering: true,
                width: 150
              },
              {
                fieldName: 'tenThietBi',
                headerText: 'Tên thiết bị',
                textAlign: 'Left',
                allowFiltering: true,
                width: 200
              },
              {
                fieldName: 'hanhDongThucHien',
                headerText: 'Hành động',
                textAlign: 'Left',
                allowFiltering: true,
                width: 120
              },
              {
                fieldName: 'nguoiThucHien',
                headerText: 'Người thực hiện',
                textAlign: 'Left',
                allowFiltering: true,
                width: 150
              },
              {
                fieldName: 'thoiGian',
                headerText: 'Thời gian',
                textAlign: 'Left',
                allowFiltering: true,
                width: 150
              }
            ]"
            :dataSource="lichSuThietBiList"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :showColumnCheckbox="false"
            :allowExcelExport="true"
            :enabledSelectFirstRow="false"
            dataKeyField="ID"
            ref="grdLichSu"
          />
        </div>
      </div>
    </div>

    <!-- POPUP CHỌN THIẾT BỊ -->
    <b-modal
      v-model="showSelectDeviceModal"
      title="Danh sách thiết bị router / switch"
      size="xl"
      hide-footer
      centered
      @hidden="onCloseSelectDevice"
    >
      <div
        class="list-actions-top"
        style="position: sticky; top: 0; background: white; z-index: 10; margin-bottom: 15px;"
        v-if="mode !== 'VIEW'"
      >
        <ul class="list">
          <li>
            <a href="#" @click.prevent="onSaveSelectDevice"> <span class="icon one-save"></span> Ghi lại </a>
          </li>
          <li>
            <a href="#" @click.prevent="showSelectDeviceModal = false">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
            </a>
          </li>
        </ul>
      </div>
      <div class="select-device-modal">
        <div class="table-content">
          <DataGrid
            v-bind:columns="[
              { fieldName: 'maThietBi', headerText: 'Mã TB', textAlign: 'Left', allowFiltering: true, width: 120 },
              {
                fieldName: 'ten',
                headerText: 'Tên Thiết bị',
                textAlign: 'Left',
                allowFiltering: true,
                width: 200
              },
              { fieldName: 'kyHieu', headerText: 'Ký hiệu', textAlign: 'Left', allowFiltering: true, width: 120 },
              { fieldName: 'serialNumber', headerText: 'Serial', textAlign: 'Left', allowFiltering: true, width: 150 },
              { fieldName: 'soHuu', headerText: 'Sở hữu', textAlign: 'Left', allowFiltering: true, width: 120 },
              { fieldName: 'idcName', headerText: 'IDC', textAlign: 'Left', allowFiltering: true, width: 120 },
              { fieldName: 'toaNhaName', headerText: 'Toà nhà', textAlign: 'Left', allowFiltering: true, width: 150 },
              { fieldName: 'rackName', headerText: 'Rack', textAlign: 'Left', allowFiltering: true, width: 120 },
              {
                fieldName: 'trangThaiVanHanhText',
                headerText: 'Trạng thái vận hành',
                textAlign: 'Left',
                allowFiltering: true,
                width: 180
              }
            ]"
            :dataSource="availableDeviceList"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :showColumnCheckbox="true"
            :allowExcelExport="false"
            :enabledSelectFirstRow="false"
            @selectedItemsChanged="onSelectAvailableDeviceCheckbox"
            dataKeyField="id"
            ref="grdAvailableDevices"
          />
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
import api from '../api'

export default {
  name: 'ThietBiVatLy',
  components: {},
  props: {
    itemId: {
      type: Number,
      default: 0
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      tab_index: 1,
      thietBiVatLyList: [],
      lichSuThietBiList: [],
      showSelectDeviceModal: false,
      availableDeviceList: [],
      dsSelectedThietBi: [],
      dsSelectedAvailableDevice: []
    }
  },
  computed: {
    isReadOnly() {
      return this.mode === 'VIEW'
    },
    isEditMode() {
      return this.mode === 'EDIT' || this.mode === 'ADD'
    }
  },
  watch: {
    itemId: {
      immediate: false,
      handler(newVal, oldVal) {
        if (newVal !== oldVal && newVal > 0) {
          this.tab_index = 1
          this.loadData()
        }
      }
    }
  },
  async mounted() {
    if (this.itemId > 0) {
      await this.loadData()
    }
  },
  methods: {
    async loadData() {
      if (!this.itemId || this.itemId === 0) return

      try {
        this.$root.showLoading(true)
        // Load danh sách thiết bị đã gán
        const rs = await api.getThietBiByHtm(this.$root.context, this.itemId)
        if (rs && rs.data) {
          this.thietBiVatLyList = rs.data || []
        }
      } catch (error) {
        this.$root.toastError('Lỗi khi tải danh sách thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadLichSuThietBi() {
      if (!this.itemId || this.itemId === 0) return

      try {
        this.$root.showLoading(true)
        const rs = await api.getLichSuThietBi(this.$root.context, this.itemId)
        if (rs && rs.data) {
          this.lichSuThietBiList = rs.data || []
        }
      } catch (error) {
        this.$root.toastError('Lỗi khi tải lịch sử thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeTab(index) {
      this.tab_index = index
      this.$nextTick(async () => {
        switch (index) {
          case 1:
            await this.loadData()
            break
          case 2:
            await this.loadLichSuThietBi()
            break
        }
      })
    },
    actionColumn() {
      const self = this
      return function() {
        return {
          template: {
            template: `
              <div style="display: flex; gap: 8px; justify-content: center;">
                <button class="btn btn-sm btn-outline-primary ml-1" @click="onView()" title="Xem">
                    <i class="fa fa-eye"></i>
                </button>
              </div>
            `,
            methods: {
              onView() {
                self.$root.toastInfo('Xem chi tiết thiết bị vật lý: ' + this.data.id)
              }
            }
          }
        }
      }
    },
    actionColumnLichSu() {},
    async onSelectDevice() {
      if (!this.itemId || this.itemId === 0) {
        this.$root.toastWarning('Vui lòng lưu thông tin hạ tầng mạng trước')
        return
      }
      await this.loadAvailableDevices()
      this.showSelectDeviceModal = true
    },
    async loadAvailableDevices() {
      if (!this.itemId || this.itemId === 0) return

      try {
        this.$root.showLoading(true)
        const rs = await api.getThietBiAvailable(this.$root.context, this.itemId)
        if (rs && rs.data) {
          this.availableDeviceList = rs.data || []
        }
      } catch (error) {
        this.$root.toastError('Lỗi khi tải danh sách thiết bị khả dụng')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onSelectThietBiCheckbox(selectedItems) {
      // selectedItems là array of IDs vì có dataKeyField="ID"
      this.dsSelectedThietBi = selectedItems || []
    },
    onSelectAvailableDeviceCheckbox(selectedItems) {
      this.dsSelectedAvailableDevice = selectedItems || []
    },
    async onSaveSelectDevice() {
      if (!this.dsSelectedAvailableDevice || this.dsSelectedAvailableDevice.length === 0) {
        this.$root.toastWarning('Vui lòng chọn ít nhất một thiết bị')
        return
      }

      const confirmFlag = await this.$confirm(
        `Bạn có chắc chắn muốn thêm ${this.dsSelectedAvailableDevice.length} thiết bị vào hạ tầng mạng?`,
        'Xác nhận thêm thiết bị',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        }
      )
        .then(() => true)
        .catch(() => false)

      if (!confirmFlag) return

      try {
        this.$root.showLoading(true)
        const thietBiIds = this.dsSelectedAvailableDevice

        const payload = {
          thietBiIds: thietBiIds,
          haTangMangId: this.itemId
        }
        const rs = await api.updateThietBiHaTangMang(this.$root.context, payload)
        this.$root.toastSuccess('Thêm thiết bị thành công')
        this.showSelectDeviceModal = false
        this.dsSelectedAvailableDevice = []
        await this.loadData()
      } catch (error) {
        this.$root.toastError(error?.response?.data?.message || 'Lỗi khi thêm thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onCloseSelectDevice() {
      this.dsSelectedAvailableDevice = []
    },
    async onDelete() {
      if (!this.dsSelectedThietBi || this.dsSelectedThietBi.length === 0) {
        this.$root.toastWarning('Vui lòng chọn ít nhất một thiết bị để xóa')
        return
      }

      const confirmFlag = await this.$confirm(
        'Bạn có chắc chắn muốn gỡ các thiết bị đã chọn khỏi hạ tầng mạng?',
        'Xác nhận gỡ thiết bị',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning',
          dangerouslyUseHTMLString: true
        }
      )
        .then(() => true)
        .catch(() => false)

      if (!confirmFlag) return

      try {
        this.$root.showLoading(true)
        // Lấy danh sách ID thiết bị cần gỡ
        const thietBiIds = this.dsSelectedThietBi

        const payload = {
          thietBiIds: thietBiIds,
          haTangMangId: null // null = gỡ thiết bị
        }
        const rs = await api.updateThietBiHaTangMang(this.$root.context, payload)
        this.$root.toastSuccess('Gỡ thiết bị thành công')
        this.dsSelectedThietBi = []
        await this.loadData()
      } catch (error) {
        this.$root.toastError(error?.response?.data?.message || 'Lỗi khi gỡ thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    }
  }
}
</script>
