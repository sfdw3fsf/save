<template>
  <div v-if="roomDetail" class="room-detail-container">
    <div class="row">
      <div class="col-md-2">
        <div class="preview-image-wrapper">
          <img v-if="roomDetail.imageUrl" :src="roomDetail.imageUrl" :alt="roomDetail.name" class="preview-image" />
          <img v-if="!roomDetail.imageUrl"
            :src="'https://t4.ftcdn.net/jpg/05/97/47/95/360_F_597479556_7bbQ7t4Z8k3xbAloHFHVdZIizWK1PdOo.jpg'"
            :alt="'Phong/Zone'" class="preview-image" />
        </div>
      </div>
      <div class="col-md-10">
        <div class="legend-title">Thông tin chung</div>
        <div class="row">
          <div class="col-md-6">
            <div class="info-row">
              <label>ID zone:</label>
              <div class="info-value">{{ roomDetail.zoneId }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>Hiệu lực:</label>
              <div class="info-value">
                <input type="checkbox" :checked="roomDetail.hieuLuc" disabled class="form-check-input" />
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>Tên phòng/zone:</label>
              <div class="info-value">{{ roomDetail.name }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>ID mặt sàn:</label>
              <div class="info-value">{{ roomDetail.floorId }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>IDC:</label>
              <div class="info-value">{{ roomDetail.idc }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>Diện tích zone:</label>
              <div class="info-value">{{ roomDetail.area }} m² ({{ roomDetail.dimensions }})</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>Tòa nhà:</label>
              <div class="info-value">{{ roomDetail.building }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>Mục đích sử dụng:</label>
              <div class="info-value">{{ roomDetail.mucDich }}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-row">
              <label>Ghi chú:</label>
              <div class="info-value">{{ roomDetail.ghiChu }}</div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="action-buttons">
            <button class="btn btn-secondary" @click="viewListDevice()">
              <i class="fa fa-list"></i> Xem danh sách thiết bị
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="">
      <div class="nav tab-over tabs">
        <a href="#tabTaiNguyen" v-on:click="onChangeTab(1)" data-toggle="tab" class="tab" D
          :class="{ active: tab_index == 1 }">
          Năng lực không gian
        </a>
        <a href="#tabRackList" v-on:click="onChangeTab(2)" data-toggle="tab" class="tab"
          :class="{ active: tab_index == 2 }">
          Rack/U
        </a>
      </div>
      <div class="tab-content">
        <div class="tab-pane" :class="{ active: tab_index == 1 }" id="tabTaiNguyen">
          <RoomResourceTab :roomId="modalItemID" />
        </div>
        <div class="tab-pane" :class="{ active: tab_index == 2 }" id="tabRackList">
          <RackListTab :roomId="modalItemID" @view-rack-detail="$emit('view-rack-detail', $event)" ref="rackList" @selectObjectTaiNguyen="$emit('selectObjectTaiNguyen', $event)" />
        </div>
      </div>
    </div>

    <!-- Device List Modal -->
    <DeviceListModal ref="deviceListModal" :roomId="modalItemID" :roomName="roomDetail ? roomDetail.name : ''" />
    <RoomDetailForm ref="roomDetailForm" :roomData="roomDetail" @hidden="getRoomData" />
  </div>
</template>

<script>
import RoomResourceTab from './tab-content/RoomResourceTab.vue'
import RackListTab from './tab-content/RackListTab.vue'
import DeviceListModal from './modals/DeviceListModal.vue'
import RoomDetailForm from './RoomDetailForm.vue'

export default {
  name: 'RoomDetailContent',

  components: {
    RoomResourceTab,
    RackListTab,
    DeviceListModal,
    RoomDetailForm
  },

  props: {
    modalItemID: {
      type: [String, Number],
      required: true
    }
  },

  data() {
    return {
      tab_index: 1,
      roomDetail: null
    }
  },

  created() {
    this.getRoomData()
  },

  computed: {},

  methods: {
    onChangeTab(index) {
      this.tab_index = index
    },
    handleDelete() {
      if (confirm('Bạn có chắc chắn muốn xóa phòng/zone này không?')) {
        this.$emit('delete', this.roomDetail)
      }
    },
    calculateDimensions(khongGian) {
      if (!khongGian) return ' - '

      try {
        const coordinates = JSON.parse(khongGian)
        if (!Array.isArray(coordinates) || coordinates.length < 2) return ' - '

        // Tính độ dài các cạnh (đơn vị CM)
        const lengths = []
        for (let i = 0; i < coordinates.length; i++) {
          const p1 = coordinates[i]
          const p2 = coordinates[(i + 1) % coordinates.length]
          const length = Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2))
          lengths.push(length)
        }

        // Chuyển đổi sang mét và format
        const lengthsInMeters = lengths.map((l) => (l / 100).toFixed(1))

        // Hiển thị tất cả các cạnh
        return lengthsInMeters.join('m x ') + 'm'
      } catch (e) {
        console.error('Error parsing KHONGGIAN:', e)
      }

      return ' - '
    },
    viewListDevice() {
      if (this.$refs.deviceListModal) {
        this.$refs.deviceListModal.openDialog()
      }
    },
    onRefresh() {
      this.getRoomData()
      this.$refs.rackList.getListRacks()
      this.$refs.rackList.getListU()
    },
    async getRoomData() {
      let rs = await this.$root.context.post('/web-ecms/idc/phong-zone/get-by-id', {
        id: this.modalItemID
      })
      if (rs.data[0]) {
        let data = rs.data[0]
        this.roomDetail = {
          zoneId: data.ID,
          idc: data.IDC,
          building: data.TOANHA,
          floorId: data.MATSAN,
          name: data.TENTAT ? data.TEN + ` (${data.TENTAT})` : data.TEN,
          area: data.DIENTICH,
          dimensions: this.calculateDimensions(data.KHONGGIAN),
          taiTrong: ' - ',
          mucDich: data.MDSD,
          congSuatLanhThietKe: ' - ',
          congSuatLanhSuDung: ' - ',
          ghiChu: data.GHI_CHU,
          hieuLuc: data.SUDUNG == 1 ? true : false,
          hinhAnh: data.HINHANH,
          imageUrl: '' // Khởi tạo imageUrl
        }

        // Lấy presigned URL nếu có hình ảnh
        if (this.roomDetail.hinhAnh) {
          await this.getPresignedUrl(this.roomDetail.hinhAnh)
        }
      }
    },
    editDetailRoom() {
      if (this.$refs.roomDetailForm) {
        this.$refs.roomDetailForm.showModal()
      }
    },
    async getPresignedUrl(relativeUrl) {
      try {
        console.log('Getting presigned URL for:', relativeUrl)
        const response = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: relativeUrl
        })

        console.log('Presigned URL response:', response)

        if (response && response.error === '200' && response.data && response.data.result) {
          // Sử dụng $set để đảm bảo Vue reactive
          this.$set(this.roomDetail, 'imageUrl', response.data.result)
          console.log('Presigned URL loaded:', this.roomDetail.imageUrl)
        } else {
          console.error('Failed to get presigned URL:', response)
          this.$set(this.roomDetail, 'imageUrl', '')
        }
      } catch (error) {
        console.error('Error getting presigned URL:', error)
        this.$set(this.roomDetail, 'imageUrl', '')
      }
    },
  }
}
</script>

<style scoped>
/* Section Title */
.section-title {
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
  padding-bottom: 8px;
  border-bottom: 2px solid #e0e0e0;
}

/* Info Rows */
.info-row {
  margin-bottom: 12px;
  display: flex;
  align-items: flex-start;
}

.info-row label {
  font-weight: 600;
  margin-right: 10px;
  margin-bottom: 0;
}

.info-value {
  color: #333;
  flex: 1;
}

/* Checkbox styling */
.form-check-input {
  width: 18px;
  height: 18px;
  cursor: default;
  margin-top: 2px;
  margin-left: 5px;
}

.form-check-input:disabled {
  opacity: 0.6;
}

/* Action Buttons */
.action-buttons {
  display: flex;
  gap: 10px;
  padding: 15px 0;
}

.action-buttons .btn {
  padding: 8px 20px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  gap: 8px;
}

/* Custom Tabs */
.tab-content {
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.tab-pane {
  display: none;
  height: 100%;
}

.tab-pane.active {
  display: flex;
  flex-direction: column;
}

.preview-image-wrapper {
  width: 100%;
  height: auto;
  text-align: center;
  margin-bottom: 15px;
}

.preview-image {
  width: 100%;
}

/* Responsive */
@media (max-width: 768px) {
  .info-row {
    flex-direction: column;
  }

  .info-row label {
    min-width: auto;
    margin-bottom: 5px;
  }

  .action-buttons {
    flex-direction: column;
  }

  .action-buttons .btn {
    width: 100%;
  }

  .tab-over {
    flex-direction: column;
  }

  .tab-over .tab {
    border-bottom: 1px solid #e0e0e0;
    border-left: 3px solid transparent;
  }

  .tab-over .tab.active {
    border-left-color: #1890ff;
    border-bottom-color: #e0e0e0;
  }
}
</style>
