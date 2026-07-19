<template>
  <div class="phong-zone">
    <div class="legend-title"> 
      <h5>Danh sách phòng/zone</h5>
    </div>
    <DataGrid
      v-bind:columns="[
        { fieldName: 'ten', headerText: 'Tên Zone', allowFiltering: true, allowSorting: true, width: 120 },
        {
          fieldName: 'mucDichSuDung',
          headerText: 'Mục đích sử dụng',
          allowFiltering: true,
          allowSorting: true,
          width: 120
        },
        { fieldName: 'dienTich', headerText: 'Diện tích (m²)', allowFiltering: true, allowSorting: true, width: 110 },
        {
          fieldName: 'dinhMucThietKeRack',
          headerText: 'Định mức thiết kế rack',
          allowFiltering: true,
          allowSorting: true,
          width: 150
        },
        {
          fieldName: 'soRackDaLapDat',
          headerText: 'Rack đã lắp đặt',
          allowFiltering: true,
          allowSorting: true,
          width: 150
        },
        {
          fieldName: 'tiLeSuDung',
          headerText: 'Tỷ lệ sử dụng',
          textAlign: 'Center',
          template: this.tiLeSuDungTemplate(),
          width: 200
        },
        {
          fieldName: 'ACTIONS',
          headerText: 'Hành động',
          textAlign: 'Center',
          template: this.actionColumn(),
          width: 150
        }
      ]"
      @recordDoubleClick="handlePhongDoubleClicked"
      :dataSource="danhSachPhong"
      :showColumnCheckbox="false"
      :enablePagingServer="false"
      :allowPaging="true"
      :enabledSelectFirstRow="true"
      :showFilter="true"
      ref="dsPhongZone"
    />

    <!-- POPUP CHI TIẾT PHÒNG/ZONE -->
    <ejs-dialog
      ref="dialogPhongZone"
      :visible="showDialog"
      :animationSettings="{ effect: 'Zoom' }"
      :showHeader="false"
      :showCloseIcon="true"
      width="80%"
      height="100%"
      :target="'.main-wrapper'"
      :position="{ X: 'center', Y: 'top' }"
      @close="closePhongZone"
      :isModal="true"
    >
      <div class="popup-box">
        <RoomDetailContent
          v-if="selectedPhongId"
          :key="selectedPhongId"
          :modalItemID="selectedPhongId"
        ></RoomDetailContent>
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
import api from '../api.js'
import RoomDetailContent from '../../sodo-matsan/components/RoomDetailContent.vue'
export default {
  name: 'PhongZone',
  props: {
    matSanId: { type: Number, required: true }
  },
  components: {
    RoomDetailContent
  },
  data() {
    return {
      danhSachPhong: [],
      selectedPhongId: null,
      showDialog: false,
      phongSelected: null
    }
  },
  methods: {
    viewPhongZone(phongId) {
      this.showDialog = true
      this.selectedPhongId = phongId
    },
    closePhongZone() {
      this.showDialog = false
      this.selectedPhongId = null
    },
    async fetchPhongZone() {
      try {
        const response = await api.getPhongZoneByMatSanId(this.$root.context, this.matSanId)
        this.danhSachPhong = response.data || []
      } catch (e) {
        this.$toast.error('Lấy danh sách phòng thất bại: ' + e.response.data.message || '')
      }
    },
    onAddZone() {
      this.$router.push(`/tainguyen-idc/thongtin-phong-zone`)
    },
    actionColumn(parent) {
      const self = this
      return function() {
        return {
          template: {
            template: `
                <div class="action-buttons gap-1">
                  <button
                    class="btn btn-sm btn-outline-primary ml-1"
                    @click="onView()">
                    <i class="fa fa-eye"></i> Xem
                  </button>
                </div>
              `,
            computed: {
              data() {
                return this.$parent.data
              }
            },
            methods: {
              async onView() {
                if (this.data && this.data.phongId) {
                  self.viewPhongZone(this.data.phongId)
                } else {
                  self.$toast.error('Không tìm thấy ID phòng/zone để xem chi tiết.')
                }
              }
            }
          }
        }
      }
    },
    tiLeSuDungTemplate(parent) {
      const self = this
      return function() {
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
            computed: {
              data() {
                return this.$parent.data
              }
            },
            methods: {
              getPercent(data) {
                const dinhMuc = Number(data.dinhMucThietKeRack) || 0
                const daLapDat = Number(data.soRackDaLapDat) || 0
                if (dinhMuc === 0) return 0
                return Math.min(100, Math.round((daLapDat / dinhMuc) * 100))
              }
            }
          }
        }
      }
    },
    handlePhongDoubleClicked(args) {
      this.phongSelected = {
        id: args.rowData.phongId,
        type: 'ZONE'
      }
      this.$root.$emit('selectObjectTaiNguyen', this.phongSelected)
    }
  },

  async mounted() {
    await this.fetchPhongZone()
  },
  watch: {
    matSanId: {
      immediate: true,
      handler() {
        this.fetchPhongZone()
      }
    }
  }
}
</script>

<style scoped>
.phong-zone {
  padding: 15px;
}

/* Toggle Switch */
.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 24px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
}

.slider:before {
  position: absolute;
  content: '';
  height: 18px;
  width: 18px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: 0.4s;
}

input:checked + .slider {
  background-color: #2196f3;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.slider.round {
  border-radius: 24px;
}

.slider.round:before {
  border-radius: 50%;
}

input:disabled + .slider {
  cursor: not-allowed;
  opacity: 0.6;
}
</style>
