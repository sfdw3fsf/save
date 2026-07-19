<template>
  <div class="rack-general-info-tab">
    <div class="row">
      <!-- Column 1: Thông tin định danh -->
      <div class="col-md-4">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">Thông tin định danh</div>
          <div class="info-item">
            <label>ID Rack:</label>
            <span>{{ rackData.id || rackData.rackId  }}</span>
          </div>
          <div class="info-item">
            <label>Tên Rack:</label>
            <span>{{ rackData.name || rackData.tenRack  }}</span>
          </div>
          <div class="info-item">
            <label>Hãng sản xuất:</label>
            <span>{{ rackData.hangSanXuat || rackData.manufacturer  }}</span>
          </div>
          <div class="info-item">
            <label>Loại Rack:</label>
            <span>{{ rackData.loaiRack || rackData.type  }}</span>
          </div>
          <div class="info-item">
            <label>Kích thước:</label>
            <span>{{ rackData.kichThuoc || rackData.dimensions  }}</span>
          </div>
          <div class="info-item">
            <label>Số U:</label>
            <span>{{ rackData.soU || rackData.totalU  }}</span>
          </div>
        </div>
      </div>

      <!-- Column 2: Thông tin vị trí -->
      <div class="col-md-4">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">Thông tin vị trí</div>
          <div class="info-item">
            <label>IDC:</label>
            <span>{{ rackData.idc  }}</span>
          </div>
          <div class="info-item">
            <label>Tòa nhà:</label>
            <span>{{ rackData.toaNha || rackData.building  }}</span>
          </div>
          <div class="info-item">
            <label>Mặt sàn:</label>
            <span>{{ rackData.matSan || rackData.floor  }}</span>
          </div>
          <div class="info-item">
            <label>Zone:</label>
            <span>{{ rackData.zone || rackData.zoneName  }}</span>
          </div>
          <div class="info-item">
            <label>Vị trí tọa độ:</label>
            <span>{{ rackData.viTriToaDo }}</span>
          </div>
        </div>
      </div>

      <!-- Column 3: Thông tin quản lý -->
      <div class="col-md-4">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">Thông tin quản lý</div>
          <div class="info-item">
            <label>Mục đích sử dụng:</label>
            <span>{{ rackData.mucDichSuDung || rackData.purpose  }}</span>
          </div>
          <div class="info-item">
            <label>Sở hữu Rack:</label>
            <span>{{ rackData.soHuuRack || rackData.owner }}</span>
          </div>
          <div class="info-item">
            <label>Thời điểm sử dụng:</label>
            <span>{{ rackData.thoiDiemSuDung || rackData.usageDate  }}</span>
          </div>
          <div class="info-item">
            <label>Camera giám sát:</label>
            <span>{{ rackData.cameraGiamSat || rackData.hasCamera ? 'Có' : 'Không' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Row 2: Trạng thái -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">Trạng thái</div>
          <div class="row">
            <div class="col-md-3">
              <div class="info-item">
                <label>Quy hoạch:</label>
                <span class="badge badge-primary">{{ getStatusText('quyHoach') }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Vận hành:</label>
                <span class="badge badge-success">{{ getStatusText('vanHanh') }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>CCDV:</label>
                <span class="badge badge-success">{{ getStatusText('ccdv') }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Hiệu lực:</label>
                <span>{{ rackData.hieuLuc || rackData.isActive ? 'Có' : 'Không' }}</span>
              </div>
            </div>
            <div class="col-md-12 mt-2">
              <div class="info-item">
                <label>Ghi chú:</label>
                <span>{{
                  rackData.ghiChu || rackData.note || 'Rack đã được triển khai đầy đủ thiết bị theo quy hoạch.'
                }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Row 3: Thông tin sử dụng -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">Thông tin sử dụng</div>
          <div class="row">
            <div class="col-md-3">
              <div class="info-item">
                <label>Tổng số U:</label>
                <span>{{ rackData.tongSoU || rackData.totalU  }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>U đã sử dụng:</label>
                <span>{{ rackData.uDaSuDung }} ({{ getUsagePercentage() }}%)</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>U còn trống:</label>
                <span>{{ getRemainingU() }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Số thiết bị:</label>
                <span>{{ rackData.soThietBi || rackData.deviceCount }}</span>
              </div>
            </div>
            <div class="col-md-12 mt-2">
              <div class="info-item">
                <label>Loại thiết bị:</label>
                <span>{{ rackData.loaiThietBi || rackData.deviceTypes  }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RackGeneralInfoTab',

  props: {
    rackId: {
      type: [String, Number],
      required: true
    }
  },

  data () {
    return {
      rackData: {
       
      }
    }
  },

  mounted () {
    this.loadRackGeneralInfo()
  },

  methods: {
    // Tính % sử dụng U
    getUsagePercentage () {
      const total = parseInt(this.rackData.tongSoU  )
      const used = parseInt(this.rackData.uDaSuDung )
      return Math.round((used / total) * 100)
    },

    // Tính U còn trống
    getRemainingU () {
      const total = parseInt(this.rackData.tongSoU )
      const used = parseInt(this.rackData.uDaSuDung )
      return total - used + 'U'
    },

    // Lấy text trạng thái
    getStatusText (type) {
      const statusMap = {
        quyHoach: {
          key: 'quyHoach',
          default: 'Đã quy hoạch'
        },
        vanHanh: {
          key: 'vanHanh',
          default: 'Đang hoạt động'
        },
        ccdv: {
          key: 'ccdv',
          default: 'Đang cung cấp dịch vụ'
        }
      }

      const statusConfig = statusMap[type]
      if (!statusConfig) return ''

      return this.rackData[statusConfig.key] || statusConfig.default
    },

    async loadRackGeneralInfo () {
      let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-by-id', {
        rackId: this.rackId
      })

      const data = rs.data

      // Format dimensions if available
      let kichThuoc = ' - '
      if (data.RONG && data.DAI && data.CAO) {
        kichThuoc = `${data.RONG} x ${data.DAI} x ${data.CAO} cm (W x D x H)`
      }

      // Format coordinates
      let viTriToaDo = ' - '
      if (data.TOADO_X && data.TOADO_Y) {
        viTriToaDo = `X: ${data.TOADO_X}, Y: ${data.TOADO_Y}`
      } 

      // Format usage date
      let thoiDiemSuDung = data.NGAY_SD || ' - '

      let dataMap = {
        id: data.ID || data.MA_TS,
        tenRack: data.TEN || data.TENTAT,
        hangSanXuat: data.HANG_SX || ' - ',
        loaiRack: data.LOAI_RACK || ' - ',
        kichThuoc: kichThuoc,
        soU: data.SO_U ? `${data.SO_U}U` : ' - ',
        idc: data.IDC || ' - ',
        toaNha: data.TOANHA || ' - ',
        matSan: data.MATSAN || ' - ',
        zoneName: data.PHONG || ' - ',
        viTriToaDo: viTriToaDo,
        mucDichSuDung: data.MUCDICH_SD || ' - ',
        soHuuRack: data.TS_NOIBO === 1 ? 'VNPT-IT' : 'Khác',
        thoiDiemSuDung: thoiDiemSuDung,
        taiTrongToiDa: ' - ',
        hasCamera: data.CAMERA === 1,
        quyHoach: data.TT_QH || data.QUYHOACH || ' - ',
        vanHanh: data.TRANGTHAI_VH || data.VANHANH || ' - ',
        ccdv: data.TT_CCDV || data.CCDV || ' - ',
        isActive: data.SUDUNG === 1,
        ghiChu: data.GHI_CHU || data.MOTA || ' - ',
        tongSoU: data.SO_U || 42,
        uDaSuDung: data.U_DA_SUDUNG || 0, // This needs to be calculated from equipment data
        soThietBi: data.SO_THIETBI || 0, // This needs to be fetched separately
        loaiThietBi: data.LOAI_THIETBI  || 0 // This needs to be fetched separately
      }
      this.rackData = dataMap
    },

    getMucDichSuDung (id) {
      const mucDichMap = {
        1: 'Dự phòng',
        2: 'Sử dụng nội bộ',
        3: 'Cho thuê',
        4: 'Bảo trì',
        5: 'Cung cấp dịch vụ'
      }
      return mucDichMap[id] || ' - '
    }
  }
}
</script>

<style scoped>
.mt-4 {
  margin-top: 1rem !important;
}
/* Info Section */
.info-section {
  background: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 15px;
  height: 100%;
}

.section-title {
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
  padding-bottom: 8px;
  border-bottom: 2px solid #ddd;
}

/* Info Item */
.info-item {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column;
}

.info-item:last-child {
  margin-bottom: 0;
}

.info-item label {
  font-weight: 600;
  color: black;
  margin-bottom: 4px;
}

.info-item span {
  color: #333;
  word-wrap: break-word;
}

/* Badges */
.badge {
  color: white !important;
  padding: 4px 10px;
  font-weight: 500;
}

.badge-primary {
  background-color: #2196f3;
}

.badge-success {
  background-color: #4caf50;
}

/* Responsive */
@media (max-width: 768px) {
  .info-section {
    margin-bottom: 15px;
  }

  .col-md-4,
  .col-md-3,
  .col-md-12 {
    margin-bottom: 15px;
  }
}
</style>
