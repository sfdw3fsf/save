<template src="./index.template.html"></template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import { colsCongDieuKhien, PHANLOAI_CONG, LOAICONG_ID, TRANGTHAI_KN } from '../../config.js'

export default {
  name: 'TabCongDieuKhien',
  components: {
    KDataGrid
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    equipmentTypeInfo: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      listCongDieuKhien: [],
      PHANLOAI_CONG,
      LOAICONG_ID,
      TRANGTHAI_KN
    }
  },
  computed: {
    getDataCols() {
      return colsCongDieuKhien(this)
    }
  },
  methods: {
    async loadInitialData() {
      if (this.currentItem && this.currentItem.id) {
        await this.loadListPort()
      }
    },
    async loadListPort() {
      try {
        // TODO: Load port list from API
        this.listCongDieuKhien = []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải danh sách cổng')
      }
    },
    async onGenerateConsolePort() {
      const soCong = Number(this.currentItem.so_cong) || 0
      const batDauTu = Number(this.currentItem.bat_dau_cong) || 1
      const dinhDangTen = (this.currentItem.dinh_dang_ten || '').trim()

      if (!soCong || soCong <= 0) {
        this.$root.toastError('Vui lòng nhập số cổng')
        return
      }
      if (!dinhDangTen) {
        this.$root.toastError('Vui lòng nhập định dạng tên')
        return
      }

      const newPorts = []
      const baseTime = Date.now()

      for (let i = 0; i < soCong; i++) {
        const stt = batDauTu + i
        const tenPort = `${dinhDangTen}_${stt}`

        newPorts.push({
          PORT_ID: `TMP_CK${baseTime}_${i}`,
          SOTHUTU: stt,
          TEN: tenPort,
          TT_PORT_ID: 0,
          TRANGTHAI_PORT: 'Chưa sử dụng',
          TRANGTHAIKN_ID: TRANGTHAI_KN.CHUA_KET_NOI,
          TRANGTHAI_KN: 'Chưa kết nối',
          THIETBIKN_ID: null,
          THIETBIKN: null,
          LOAICONG_ID: LOAICONG_ID.CONG_DIEU_KHIEN,
          LOAICONG: 'Cổng điều khiển'
        })
      }

      this.listCongDieuKhien = newPorts
      this.currentItem.da_su_dung = 0
      this.currentItem.con_trong = soCong
      this.$root.toastSuccess('Sinh cổng điều khiển thành công')
    }
  }
}
</script>
<style scoped>
.w150 {
  min-width: 150px;
}
.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}
.key {
  min-width: 150px;
  padding-right: 10px;
}
.value {
  flex: 1;
}
.red {
  color: red;
}
</style>
