<template>
  <b-modal
    id="cageDetailModal"
    ref="cageDetailModal"
    size="lg"
    hide-footer
    @hidden="$emit('close')"
    modal-class="cage-detail-modal"
  >
    <template #modal-header>
      <h5 class="modal-title">
        <strong>THÔNG TIN LỒNG QUÂY - {{ cageDetail ? cageDetail.name : '' }}</strong>
      </h5>
      <button type="button" class="close" @click="closeModal">
        <span aria-hidden="true">&times;</span>
      </button>
    </template>

    <div v-if="cageDetail" class="cage-detail-container">
      <!-- Thông tin cơ bản -->
      <div class="info-section">
        <div class="legend-title">Thông tin cơ bản</div>
        <div class="row">
          <div class="col-md-6">
            <div class="info-item">
              <label>Tên lồng quây:</label>
              <span class="value">{{ cageDetail.name }}</span>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-item">
              <label>Loại:</label>
              <span class="value">{{ cageDetail.type }}</span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6">
            <div class="info-item">
              <label>Màu sắc:</label>
              <span class="value">
                <span class="color-box" :style="{ backgroundColor: cageDetail.color }"></span>
                {{ cageDetail.color }}
              </span>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-item">
              <label>Kích thước:</label>
              <span class="value">{{ cageDetail.width }} x {{ cageDetail.height }} cm</span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6">
            <div class="info-item">
              <label>Vị trí:</label>
              <span class="value">X: {{ cageDetail.x }}, Y: {{ cageDetail.y }}</span>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-item">
              <label>Diện tích:</label>
              <span class="value">{{ cageDetail.area }} m²</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Danh sách rack/equipment trong lồng -->
      <div class="info-section">
         <div class="legend-title">Danh sách thiết bị trong lồng</div>
        <div v-if="cageDetail.children && cageDetail.children.length > 0">
          <DataGrid
            ref="dataGrid"
            :columns="columns"
            :dataSource="formattedChildren"
            :allowSorting="true"
            :allowPaging="false"
            :showFilter="false"
            :allowResizing="true"
            :EmptyRecordText="'Chưa có thiết bị nào trong lồng quây'"
          />
        </div>
        <div v-else class="text-center text-muted py-3">
          <i class="fa fa-inbox fa-2x mb-2"></i>
          <p>Chưa có thiết bị nào trong lồng quây</p>
        </div>
      </div>

      <!-- Polygon info -->
      <div class="info-section">
        <div class="legend-title">Thông tin polygon</div>
        <div class="polygon-info">
          <div class="info-item d-flex align-items-center gap-2">
            <label>Số cạnh:</label>
            <span class="value">{{ cageDetail.polygon ? cageDetail.polygon.length : 0 }}</span>
          </div>
          <div class="polygon-preview">
            <canvas ref="polygonCanvas" width="300" height="200"></canvas>
          </div>
        </div>
      </div>

      <!-- Actions -->
      <div class="modal-actions">
        <button class="btn btn-secondary" @click="closeModal"><i class="fa fa-times"></i> Đóng</button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import DataGrid from '@/components/Controls/DataGrid'

export default {
  name: 'CageDetailModal',
  components: {
    DataGrid
  },
  props: {
    cageDetail: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      columns: [
        {
          fieldName: 'stt',
          headerText: '#',
          width: 50,
          textAlign: 'Center',
          allowFiltering: false
        },
        {
          fieldName: 'name',
          headerText: 'Tên',
          allowFiltering: false
        },
        {
          fieldName: 'typeDisplay',
          headerText: 'Loại',
          width: 120,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.typeTemplate
        },
        {
          fieldName: 'position',
          headerText: 'Vị trí tương đối',
          width: 150,
          allowFiltering: false
        }
      ]
    }
  },
  computed: {
    formattedChildren () {
      if (!this.cageDetail || !this.cageDetail.children) return []

      return this.cageDetail.children.map((child, index) => ({
        ...child,
        stt: index + 1,
        typeDisplay: child.type === 'rack' ? 'Rack' : 'Thiết bị',
        position: `X: ${child.relativeX}, Y: ${child.relativeY}`
      }))
    }
  },
  watch: {
    cageDetail: {
      handler (newVal) {
        if (newVal && newVal.polygon) {
          this.$nextTick(() => {
            this.drawPolygonPreview()
          })
        }
      },
      deep: true
    }
  },
  methods: {
    typeTemplate () {
      return {
        template: {
          template: `<span :class="getBadgeClass(data)">{{ data.typeDisplay }}</span>`,
          methods: {
            getBadgeClass (data) {
              return data.type === 'rack' ? 'badge badge-primary' : 'badge badge-warning'
            }
          }
        }
      }
    },
    show () {
      this.$refs.cageDetailModal.show()
    },
    closeModal () {
      this.$refs.cageDetailModal.hide()
    },
    drawPolygonPreview () {
      const canvas = this.$refs.polygonCanvas
      if (!canvas || !this.cageDetail || !this.cageDetail.polygon) return

      const ctx = canvas.getContext('2d')
      const polygon = this.cageDetail.polygon

      // Clear canvas
      ctx.clearRect(0, 0, canvas.width, canvas.height)

      // Tính bounds
      const xs = polygon.map((p) => p[0])
      const ys = polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Tính scale để fit vào canvas
      const padding = 20
      const scaleX = (canvas.width - padding * 2) / width
      const scaleY = (canvas.height - padding * 2) / height
      const scale = Math.min(scaleX, scaleY)

      // Vẽ polygon
      ctx.beginPath()
      polygon.forEach((point, i) => {
        const x = (point[0] - minX) * scale + padding
        const y = (point[1] - minY) * scale + padding
        if (i === 0) {
          ctx.moveTo(x, y)
        } else {
          ctx.lineTo(x, y)
        }
      })
      ctx.closePath()

      // Fill và stroke
      ctx.fillStyle = this.cageDetail.color ? `${this.cageDetail.color}20` : 'rgba(76, 175, 80, 0.1)'
      ctx.fill()
      ctx.strokeStyle = this.cageDetail.color || '#4CAF50'
      ctx.lineWidth = 2
      ctx.stroke()

      // Vẽ các điểm
      polygon.forEach((point, i) => {
        const x = (point[0] - minX) * scale + padding
        const y = (point[1] - minY) * scale + padding

        ctx.beginPath()
        ctx.arc(x, y, 4, 0, 2 * Math.PI)
        ctx.fillStyle = i === 0 ? '#28a745' : this.cageDetail.color || '#4CAF50'
        ctx.fill()
        ctx.strokeStyle = '#fff'
        ctx.lineWidth = 2
        ctx.stroke()
      })
    }
  }
}
</script>

<style scoped>
.cage-detail-container {
  padding: 10px;
}

.info-section {
  margin-bottom: 25px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #dee2e6;
}

.section-title i {
  color: #4caf50;
  margin-right: 8px;
}

.info-item {
  margin-bottom: 12px;
}

.info-item label {
  font-weight: 600;
  color: #666;
  margin-bottom: 4px;
  display: block;
  font-size: 13px;
}

.info-item .value {
  color: #333;
  font-size: 14px;
  display: flex;
  align-items: center;
}

.color-box {
  display: inline-block;
  width: 20px;
  height: 20px;
  border-radius: 4px;
  border: 1px solid #ddd;
  margin-right: 8px;
}

.polygon-info {
  display: flex;
  flex-direction: column;
}

.polygon-preview {
  display: flex;
  justify-content: center;
  padding: 10px;
  background: white;
  border-radius: 4px;
  border: 1px solid #dee2e6;
}

.polygon-preview canvas {
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #dee2e6;
}

.table {
  margin-bottom: 0;
  background: white;
}

.table thead th {
  background: #f1f3f5;
  font-weight: 600;
  font-size: 13px;
  color: #495057;
  border-bottom: 2px solid #dee2e6;
}

.table tbody td {
  vertical-align: middle;
  font-size: 13px;
}

.badge {
  font-size: 11px;
  padding: 4px 8px;
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
