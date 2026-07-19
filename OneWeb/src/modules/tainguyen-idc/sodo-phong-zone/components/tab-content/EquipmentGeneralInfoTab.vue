<template>
  <div class="equipment-general-info-tab">
    <div class="row">
      <!-- Column 1: Thông tin định danh -->
      <div class="col-md-4">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông tin định danh
          </div>
          <div class="info-item">
            <label>ID Thiết bị:</label>
            <span>{{ equipmentData.id || equipmentData.equipmentId || 'EQ001' }}</span>
          </div>
          <div class="info-item">
            <label>Tên thiết bị:</label>
            <span>{{ equipmentData.name || equipmentData.tenThietBi || 'Thiết bị mạng' }}</span>
          </div>
          <div class="info-item">
            <label>Hãng sản xuất:</label>
            <span>{{ equipmentData.hangSanXuat || equipmentData.manufacturer || 'Cisco' }}</span>
          </div>
          <div class="info-item">
            <label>Loại thiết bị:</label>
            <span>{{ equipmentData.loaiThietBi || equipmentData.type || 'Switch' }}</span>
          </div>
          <div class="info-item">
            <label>Model:</label>
            <span>{{ equipmentData.model || 'Catalyst 9300' }}</span>
          </div>
          <div class="info-item">
            <label>Serial Number:</label>
            <span>{{ equipmentData.serialNumber || 'SN123456789' }}</span>
          </div>
        </div>
      </div>

      <!-- Column 2: Thông tin vị trí -->
      <div class="col-md-4">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông tin vị trí
          </div>
          <div class="info-item">
            <label>IDC:</label>
            <span>{{ equipmentData.idc || 'IDC Hà Nội 1' }}</span>
          </div>
          <div class="info-item">
            <label>Tòa nhà:</label>
            <span>{{ equipmentData.toaNha || equipmentData.building || 'Tòa nhà A' }}</span>
          </div>
          <div class="info-item">
            <label>Mặt sàn:</label>
            <span>{{ equipmentData.matSan || equipmentData.floor || 'Mặt sàn 1' }}</span>
          </div>
          <div class="info-item">
            <label>Zone:</label>
            <span>{{ equipmentData.zone || equipmentData.zoneName || 'Zone Server - A1' }}</span>
          </div>
          <div class="info-item">
            <label>Vị trí tọa độ:</label>
            <span>{{ equipmentData.viTriToaDo || equipmentData.position || 'X: 100, Y: 200' }}</span>
          </div>
        </div>
      </div>

      <!-- Column 3: Thông tin quản lý -->
      <div class="col-md-4">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông tin quản lý
          </div>
          <div class="info-item">
            <label>Mục đích sử dụng:</label>
            <span>{{ equipmentData.mucDichSuDung || equipmentData.purpose || 'Cung cấp dịch vụ' }}</span>
          </div>
          <div class="info-item">
            <label>Sở hữu:</label>
            <span>{{ equipmentData.soHuu || equipmentData.owner || 'VNPT-IT' }}</span>
          </div>
          <div class="info-item">
            <label>Thời điểm lắp đặt:</label>
            <span>{{ equipmentData.thoiDiemLapDat || equipmentData.installDate || '01/06/2021' }}</span>
          </div>
          <div class="info-item">
            <label>Bảo hành đến:</label>
            <span>{{ equipmentData.baoHanhDen || equipmentData.warrantyDate || '01/06/2024' }}</span>
          </div>
          <div class="info-item">
            <label>Người quản lý:</label>
            <span>{{ equipmentData.nguoiQuanLy || equipmentData.manager || 'Nguyễn Văn A' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Row 2: Trạng thái -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Trạng thái
          </div>
          <div class="row">
            <div class="col-md-3">
              <div class="info-item">
                <label>Trạng thái hoạt động:</label>
                <span class="badge badge-success">{{ getStatusText('hoatDong') }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Trạng thái kết nối:</label>
                <span class="badge badge-success">{{ getStatusText('ketNoi') }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Trạng thái nguồn:</label>
                <span class="badge badge-success">{{ getStatusText('nguon') }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Hiệu lực:</label>
                <span>{{ equipmentData.hieuLuc || equipmentData.isActive ? 'Có' : 'Không' }}</span>
              </div>
            </div>
            <div class="col-md-12 mt-2">
              <div class="info-item">
                <label>Ghi chú:</label>
                <span>{{ equipmentData.ghiChu || equipmentData.note || 'Thiết bị đang hoạt động bình thường.' }}</span>
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
  name: 'EquipmentGeneralInfoTab',

  props: {
    equipmentData: {
      type: Object,
      required: true
    }
  },

  methods: {
    getStatusText(type) {
      const statusMap = {
        hoatDong: {
          key: 'trangThaiHoatDong',
          default: 'Đang hoạt động'
        },
        ketNoi: {
          key: 'trangThaiKetNoi',
          default: 'Đã kết nối'
        },
        nguon: {
          key: 'trangThaiNguon',
          default: 'Bình thường'
        }
      }

      const statusConfig = statusMap[type]
      if (!statusConfig) return ''

      return this.equipmentData[statusConfig.key] || statusConfig.default
    }
  }
}
</script>

<style scoped>
.info-section {
  background: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 15px;
  height: 100%;
}

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

.badge {
  color: white !important;
  padding: 4px 10px;
  font-weight: 500;
}

.badge-success {
  background-color: #4CAF50;
}

@media (max-width: 768px) {
  .info-section {
    margin-bottom: 15px;
  }
}
</style>
