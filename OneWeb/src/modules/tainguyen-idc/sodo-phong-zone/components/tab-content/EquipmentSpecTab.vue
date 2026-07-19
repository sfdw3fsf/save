<template>
  <div class="equipment-spec-tab">
    <div class="row">
      <!-- Column 1: Thông số vật lý -->
      <div class="col-md-6">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông số vật lý
          </div>
          <div class="info-item">
            <label>Kích thước (W x D x H):</label>
            <span>{{ equipmentData.kichThuoc || equipmentData.dimensions || '440 x 400 x 44 mm' }}</span>
          </div>
          <div class="info-item">
            <label>Trọng lượng:</label>
            <span>{{ equipmentData.trongLuong || equipmentData.weight || '5.2' }} kg</span>
          </div>
          <div class="info-item">
            <label>Số U chiếm dụng:</label>
            <span>{{ equipmentData.soU || equipmentData.rackUnits || '1U' }}</span>
          </div>
          <div class="info-item">
            <label>Màu sắc:</label>
            <span>{{ equipmentData.mauSac || equipmentData.color || 'Đen' }}</span>
          </div>
        </div>
      </div>

      <!-- Column 2: Thông số điện -->
      <div class="col-md-6">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông số điện
          </div>
          <div class="info-item">
            <label>Điện áp đầu vào:</label>
            <span>{{ equipmentData.dienApDauVao || equipmentData.inputVoltage || '100-240V AC' }}</span>
          </div>
          <div class="info-item">
            <label>Công suất tiêu thụ:</label>
            <span>{{ equipmentData.congSuat || equipmentData.powerConsumption || '350' }} W</span>
          </div>
          <div class="info-item">
            <label>Công suất tối đa:</label>
            <span>{{ equipmentData.congSuatToiDa || equipmentData.maxPower || '450' }} W</span>
          </div>
          <div class="info-item">
            <label>Số nguồn:</label>
            <span>{{ equipmentData.soNguon || equipmentData.powerSupplyCount || '2 (Redundant)' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Row 2: Thông số môi trường -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông số môi trường
          </div>
          <div class="row">
            <div class="col-md-3">
              <div class="info-item">
                <label>Nhiệt độ hoạt động:</label>
                <span>{{ equipmentData.nhietDoHoatDong || equipmentData.operatingTemp || '0-40°C' }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Độ ẩm hoạt động:</label>
                <span>{{ equipmentData.doAmHoatDong || equipmentData.operatingHumidity || '10-90%' }}</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Độ ồn:</label>
                <span>{{ equipmentData.doOn || equipmentData.noiseLevel || '45' }} dB</span>
              </div>
            </div>
            <div class="col-md-3">
              <div class="info-item">
                <label>Tản nhiệt:</label>
                <span>{{ equipmentData.tanNhiet || equipmentData.cooling || 'Fan tích hợp' }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Row 3: Thông số kỹ thuật -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông số kỹ thuật
          </div>
          <div class="row">
            <div class="col-md-4">
              <div class="info-item">
                <label>CPU:</label>
                <span>{{ equipmentData.cpu || 'Intel Xeon E5-2680 v4' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>RAM:</label>
                <span>{{ equipmentData.ram || '64GB DDR4' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>Storage:</label>
                <span>{{ equipmentData.storage || '2TB SSD' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>Network Ports:</label>
                <span>{{ equipmentData.networkPorts || '48 x 1GbE, 4 x 10GbE SFP+' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>Management Port:</label>
                <span>{{ equipmentData.managementPort || '1 x RJ45' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>Console Port:</label>
                <span>{{ equipmentData.consolePort || '1 x RJ45' }}</span>
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
  name: 'EquipmentSpecTab',

  props: {
    equipmentData: {
      type: Object,
      required: true
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

@media (max-width: 768px) {
  .info-section {
    margin-bottom: 15px;
  }
}
</style>
