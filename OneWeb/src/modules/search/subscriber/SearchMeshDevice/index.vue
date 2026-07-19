<template src="./template.html"></template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import DataGridCustom from '@/modules/contract/setup/DeclareLandline/components/DataGridCustom'
export default {
  data() {
    return {
      collums: [
        { fieldName: 'ma_tinh', headerText: 'Tỉnh' },
        { fieldName: 'ttvt', headerText: 'Trung tâm viễn thông' },
        { fieldName: 'serial', headerText: 'Serial thiết bị' },
        { fieldName: 'ncc', headerText: 'Nhà cung cấp' },
        { fieldName: 'chungloai', headerText: 'Chủng loại' },
        { fieldName: 'loai_tbi', headerText: 'Mã vật tư' },
        { fieldName: 'loai_ct', headerText: 'Loại chứng từ' },
        { fieldName: 'khoxuat', headerText: 'Kho xuất' }
      ],
      title_in: 'Tra cứu thông tin thiết bị Mesh',
    }
  },
  components: {
    breadcrumb,
    DataGridCustom
  },
  mounted() {
      const routePath = this.$route.path;
      console.log(routePath)
      if(routePath.includes("LockAndUnlockMeshDevice")) {
        this.title_in = 'Tra cứu thông tin thiết bị Mesh (Khoá/Mở)'
        this.$refs.btnKhoa.style.display = null
        this.$refs.btnMo.style.display = null
      }
    },
  methods: {
    clear() {
      this.$refs.txtMaTB_nhap.value = ''
      this.$refs.txtSerialTT.value = ''
      this.$refs.txtTenTB.value = ''
      this.$refs.txtDiaChiLD.value = ''
      this.$refs.txtTrangThaiTB.value = ''
      this.$refs.txtNgaySD.value = ''
      this.$refs.txtTenVattu.value = ''
      this.$refs.dsGrid.dataSource = []
      this.$refs.txtSerialNumber.value = ''
      this.$refs.txtDeviceName.value = ''
      this.$refs.txtDeviceType.value = ''
      this.$refs.txtModelNumber.value = ''
      this.$refs.txtFirmwareVersion.value = ''
      this.$refs.txOperationMode.value = ''
      this.$refs.txtServiceActiveStatus.value = ''
      this.$refs.txtServiceActiveReason.value = ''
    },
    async lockDevice() {
        if (!this.$refs.txtSerialTT.value) {
          this.$toast.error('Vui lòng tra cứu serial!')
          return
        }
        this.$confirm('Bạn có chắc chắn muốn khóa thiết bị này không?', 'Xác nhận', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy',
          type: 'warning'
        }).then(async () => {
          await this.axios.post('tichhop/onemesh/lock', {
            serialNumber: this.$refs.txtSerialTT.value
          })
            this.$toast.success('Khóa thiết bị thành công!')
        }).catch(() => {
        })
    },
    async unlockDevice() {
        if (!this.$refs.txtSerialTT.value) {
          this.$toast.error('Vui lòng tra cứu serial!')
          return
        }
        this.$confirm('Bạn có chắc chắn muốn mở khóa thiết bị này không?', 'Xác nhận', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy',
          type: 'warning'
        }).then(async () => {
          await this.axios.post('tichhop/onemesh/unlock', {
            serialNumber: this.$refs.txtSerialTT.value
          })
            this.$toast.success('Mở khóa thiết bị thành công!')
        }).catch(() => {
        })
    },
    async onSearchMeshDevice() {
      if(!this.$refs.txtMaTB.value && !this.$refs.txtSerial.value) {
        this.$toast.error('Vui lòng nhập mã thuê bao hoặc serial!')
        return
      }
      this.clear()
      this.loading(true)
      try {
        let rs = await this.axios.post('web-tracuu/tracuu/sp_lay_thongtin_theo_ma_tb_mesh', {
          ma_tb: this.$refs.txtMaTB.value,
          serial: this.$refs.txtSerial.value
        })

        if (rs.data && rs.data.data) {
          let formData = rs.data.data.datas
          if (formData && formData.length > 0) {
            let info = formData[0]
            this.$refs.txtMaTB_nhap.value = info.ma_tb
            this.$refs.txtSerialTT.value = info.serial
            this.$refs.txtTenTB.value = info.ten_khach_hang
            this.$refs.txtDiaChiLD.value = info.dia_chi
            this.$refs.txtTrangThaiTB.value = info.trangthai_tb
            this.$refs.txtNgaySD.value = info.ngay_sd
            this.$refs.txtTenVattu.value = info.ten_vt
            this.$refs.dsGrid.dataSource = rs.data.data.datas2
            if (info.serial) {
              rs = await this.axios.get('/tichhop/onemesh/getDeviceServiceActive?serialNumber=' + info.serial)
              if (rs.data && rs.data.data) {
                let deviceData = rs.data.data
                this.$refs.txtSerialNumber.value = deviceData.serialNumber
                this.$refs.txtDeviceName.value = deviceData.deviceName
                this.$refs.txtDeviceType.value = deviceData.deviceType
                this.$refs.txtModelNumber.value = deviceData.modelNumber
                this.$refs.txtFirmwareVersion.value = deviceData.firmwareVersion
                this.$refs.txOperationMode.value = deviceData.operationMode
                this.$refs.txtServiceActiveStatus.value = deviceData.serviceActiveStatus
                this.$refs.txtServiceActiveReason.value = deviceData.serviceActiveReason
              }
            }
          } else {
            this.$toast.error('Không tìm thấy!')
          }
        } else {
          this.$toast.error('Không tìm thấy!')
        }
      } catch (e) {
        console.log(e)
      }
      this.loading(false)
    }
  }
}
</script>

<style>
</style>