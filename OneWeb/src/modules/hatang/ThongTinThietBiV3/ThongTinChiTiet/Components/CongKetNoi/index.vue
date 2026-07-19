<template src="./index.template.html"></template>
<script>
import { validationMixin, ValidationRulesFactory } from '../../validation.js'
// import ThongTinCongDuLieu from './components/thongtin-cong-dulieu/index.vue'
import TabCongDulieu from './components/TabCongDulieu/index.vue'
import TabCongNguon from './components/TabCongNguon/index.vue'
import TabCongDieuKhien from './components/TabCongDieuKhien/index.vue'
import api from './api'
import { PHANLOAI_CONG, TRANGTHAI_KN, LOAI_THIETBI } from './config.js'
export default {
  name: 'CongKetNoi',
  components: {
    TabCongDulieu,
    TabCongNguon,
    TabCongDieuKhien
  },
  mixins: [validationMixin],
  props: {
    deviceTypeList: {
      type: Array,
      default: () => []
    },
    customerTypeList: {
      type: Array,
      default: () => []
    },
    parentDeviceList: {
      type: Array,
      default: () => []
    },
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    deviceTypeInfo: {
      type: Object,
      default: () => ({
        phanLoai: null,
        ten: null,
        id: null,
        isServer: false,
        isNetwork: false,
        isServerBlade: false,
        isBlade: false,
        isTuDia: false,
        isBoDieuKhien: false,
        isKhayDia: false,
        isLinecard: false,
        isPowercard: false,
        isConsolecard: false
      })
    }
  },
  data() {
    return {
      validationRules: {
        txt_ten: ValidationRulesFactory.createTextRule('Tên thiết bị không được để trống'),
        txt_mathietbi_infra: ValidationRulesFactory.createTextRule('Mã thiết bị không được để trống'),
        select_loaithietbi_id: ValidationRulesFactory.createSelectRule('Loại thiết bị không được để trống')
      },
      currentTab: 1,
      isUsedPort: false,
      phanLoai: 1
    }
  },
  provide() {
    return {
      isNumber: this.isNumber,
      handlePasteNumber: this.handlePasteNumber,
      checkPortStatus: this.checkPortStatus
    }
  },
  computed: {
    isCard() {
      // Kiểm tra xem có phải thiết bị card không
      return this.currentItem.loaithietbi_id === LOAI_THIETBI.CARD
    },
    displayTab1() {
      // Nếu là khay đĩa → không hiển thị tab 1
      if (this.deviceTypeInfo.isKhayDia) return false
      if (this.isCard) {
        // Chưa có phan_loai_tb_id → hiển thị tất cả tabs
        if (!this.currentItem.phan_loai_tb_id) return true
        // Đã có phan_loai_tb_id → chỉ hiển thị tab tương ứng (dùng == để xử lý cả string và number)
        return this.currentItem.phan_loai_tb_id == LOAI_THIETBI.LINECARD
      }
      // Thiết bị khác → hiển thị tab 1 mặc định
      return true
    },
    displayTab2() {
      // Nếu là khay đĩa → luôn hiển thị tab 2
      if (this.deviceTypeInfo.isKhayDia) return true
      if (this.isCard) {
        if (!this.currentItem.phan_loai_tb_id) return true
        return this.currentItem.phan_loai_tb_id == LOAI_THIETBI.POWERCARD
      }
      return true
    },
    displayTab3() {
      if (this.deviceTypeInfo.isKhayDia) return false
      if (this.isCard) {
        if (!this.currentItem.phan_loai_tb_id) return true
        return this.currentItem.phan_loai_tb_id == LOAI_THIETBI.CONSOLECARD
      }
      return true
    },
    defaultTab() {
      if (this.displayTab1) return 1
      if (this.displayTab2) return 2
      if (this.displayTab3) return 3
      return 1
    },
    defaultPhanLoai() {
      if (this.defaultTab === 1) return PHANLOAI_CONG.CONG_DU_LIEU
      if (this.defaultTab === 2) return PHANLOAI_CONG.CONG_NGUON
      if (this.defaultTab === 3) return PHANLOAI_CONG.CONG_DIEU_KHIEN
      return PHANLOAI_CONG.CONG_DU_LIEU
    }
  },
  mounted() {
    this.currentTab = this.defaultTab
    this.phanLoai = this.defaultPhanLoai
  },
  methods: {
    loadData(deviceId) {
      if (deviceId) {
        if (this.deviceTypeInfo.isNetwork) {
          this.loadListKheCam(deviceId)
        }
        this.loadListPort(deviceId)
      }
    },
    getValueForControl(controlKey) {
      const valueMap = {
        txt_ten: this.currentItem.ten,
        txt_mathietbi_infra: this.currentItem.mathietbi_infra,
        select_loaithietbi_id: this.currentItem.loaithietbi_id
      }

      return valueMap[controlKey]
    },
    normalizer(node) {
      return {
        id: node.ID,
        label: node.IP
      }
    },
    // onGenerateDataPort() {
    //   // this.$emit('generate-data-port')
    // },
    showThongTinCong() {
      this.$refs.ThongTinCongDuLieu.showDialog()
    },
    //danh sách khe cắm trên thiết bị
    loadListKheCam: async function(deviceId = null) {
      try {
        const idLoadData = deviceId || this.currentItem.id
        const data = {
          thietBiId: idLoadData,
          phanLoai: this.phanLoai,
          kheCam: 1
        }
        let result = []
        if (idLoadData != null) {
          result = await this.getListPort(data)
        }
        if (this.currentTab === 1) {
          this.$refs.refCongDulieu.listLineCard =
            this.currentItem.arr_line_card?.ports?.length > 0 ? this.currentItem.arr_line_card.ports : result
          this.currentItem.so_khe_cam = this.$refs.refCongDulieu.listLineCard.length || null
          this.currentItem.khe_cam_dinh_dang_ten =
            this.currentItem.listLineCard?.length > 0
              ? this.currentItem.listLineCard[0]?.TEN_FORMAT || ''
              : result[0]?.TEN_FORMAT || ''
          this.currentItem.khe_cam_da_dung = result[0]?.PORT_DA_SD || 0
          this.currentItem.khe_cam_con_lai =
            this.$refs.refCongDulieu.listLineCard?.length - this.currentItem.khe_cam_da_dung || 0
          this.currentItem.khe_cam_bat_dau =
            this.$refs.refCongDulieu.listLineCard?.length > 0
              ? this.$refs.refCongDulieu.listLineCard[0]?.SOTHUTU || null
              : result[0]?.SOTHUTU || null
          this.isUsedPort = this.$refs.refCongDulieu.listLineCard.some(
            (port) => port.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI
          )
        } else if (this.currentTab === 2) {
          this.$refs.refCongNguon.listPowerCard =
            this.currentItem.arr_power_card?.ports?.length > 0 ? this.currentItem.arr_power_card.ports : result
          this.currentItem.so_khe_cam = this.$refs.refCongNguon.listPowerCard.length || null
          this.currentItem.khe_cam_dinh_dang_ten =
            this.$refs.refCongNguon.listPowerCard?.length > 0
              ? this.$refs.refCongNguon.listPowerCard[0]?.TEN_FORMAT || ''
              : result[0]?.TEN_FORMAT || ''
          this.currentItem.khe_cam_da_dung = result[0]?.PORT_DA_SD || 0
          this.currentItem.khe_cam_con_lai =
            this.$refs.refCongNguon.listPowerCard?.length - this.currentItem.khe_cam_da_dung || 0
          this.currentItem.khe_cam_bat_dau =
            this.$refs.refCongNguon.listPowerCard?.length > 0
              ? this.$refs.refCongNguon.listPowerCard[0]?.SOTHUTU || null
              : result[0]?.SOTHUTU || null
          this.isUsedPort = this.$refs.refCongNguon.listPowerCard.some(
            (port) => port.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI
          )
        } else if (this.currentTab === 3) {
          this.$refs.refCongDieuKhien.listControlCard =
            this.currentItem.arr_control_card?.ports?.length > 0 ? this.currentItem.arr_control_card.ports : result
          this.currentItem.so_khe_cam = this.$refs.refCongDieuKhien.listControlCard.length || null
          this.currentItem.khe_cam_dinh_dang_ten =
            this.$refs.refCongDieuKhien.listControlCard?.length > 0
              ? this.$refs.refCongDieuKhien.listControlCard[0]?.TEN_FORMAT || ''
              : result[0]?.TEN_FORMAT || ''
          this.currentItem.khe_cam_da_dung = result[0]?.PORT_DA_SD || 0
          this.currentItem.khe_cam_con_lai =
            this.$refs.refCongDieuKhien.listControlCard?.length - this.currentItem.khe_cam_da_dung || 0
          this.currentItem.khe_cam_bat_dau =
            this.$refs.refCongDieuKhien.listControlCard?.length > 0
              ? this.$refs.refCongDieuKhien.listControlCard[0]?.SOTHUTU || null
              : result[0]?.SOTHUTU || null
          this.isUsedPort = this.$refs.refCongDieuKhien.listControlCard.some(
            (port) => port.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI
          )
        }
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.listCongDuLieu = []
          this.listCongDieuKhien = []
          this.listCongNguon = []
          this.listLineCard = []
          this.listPowerCard = []
          this.listControlCard = []
          this.$root.toastError(e.response.data.message_detail)
        }
      }
    },
    //danh sách port trên thiết bị
    loadListPort: async function(deviceId = null) {
      try {
        const idLoadData = deviceId || this.currentItem.id
        const data = {
          thietBiId: idLoadData,
          phanLoai: this.phanLoai,
          kheCam: 0
        }
        let result = []
        if (idLoadData != null) {
          result = await this.getListPort(data)
        }
        if (this.currentTab === 1) {
          this.$refs.refCongDulieu.listCongDuLieu =
            this.currentItem.arr_cong_dulieu?.ports?.length > 0 ? this.currentItem.arr_cong_dulieu.ports : result
          this.isUsedPort = this.$refs.refCongDulieu.listCongDuLieu.some(
            (port) => port.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI
          )
          this.currentItem.so_cong = this.$refs.refCongDulieu.listCongDuLieu.length || null
          this.currentItem.dinh_dang_ten =
            this.$refs.refCongDulieu.listCongDuLieu.length > 0
              ? this.$refs.refCongDulieu.listCongDuLieu[0]?.TEN_FORMAT || ''
              : result[0]?.TEN_FORMAT || ''
          this.currentItem.da_su_dung = result[0]?.PORT_DA_SD || 0
          this.currentItem.con_trong = this.$refs.refCongDulieu.listCongDuLieu.length - this.currentItem.da_su_dung || 0
          this.currentItem.bat_dau_cong =
            this.$refs.refCongDulieu.listCongDuLieu.length > 0
              ? this.$refs.refCongDulieu.listCongDuLieu[0]?.SOTHUTU || null
              : result[0]?.SOTHUTU || null
          this.$refs.refCongDulieu.allPorts = this.$refs.refCongDulieu.listCongDuLieu
        } else if (this.currentTab === 2) {
          this.$refs.refCongNguon.listCongNguon =
            this.currentItem.arr_cong_nguon?.ports?.length > 0 ? this.currentItem.arr_cong_nguon.ports : result
          this.isUsedPort = this.$refs.refCongNguon.listCongNguon.some(
            (port) => port.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI
          )
          this.currentItem.so_cong = this.$refs.refCongNguon.listCongNguon.length || null
          this.currentItem.dinh_dang_ten =
            this.$refs.refCongNguon.listCongNguon.length > 0
              ? this.$refs.refCongNguon.listCongNguon[0]?.TEN_FORMAT || ''
              : result[0]?.TEN_FORMAT || ''
          this.currentItem.da_su_dung = result[0]?.PORT_DA_SD || 0
          this.currentItem.con_trong = this.$refs.refCongNguon.listCongNguon.length - this.currentItem.da_su_dung || 0
          this.currentItem.bat_dau_cong =
            this.$refs.refCongNguon.listCongNguon.length > 0
              ? this.$refs.refCongNguon.listCongNguon[0]?.SOTHUTU || null
              : result[0]?.SOTHUTU || null
          this.$refs.refCongNguon.allPorts = this.$refs.refCongNguon.listCongNguon
        } else if (this.currentTab === 3) {
          this.$refs.refCongDieuKhien.listCongDieuKhien =
            this.currentItem.arr_cong_dieukhien?.ports?.length > 0 ? this.currentItem.arr_cong_dieukhien.ports : result
          this.currentItem.so_cong = this.$refs.refCongDieuKhien.listCongDieuKhien.length || null
          this.currentItem.dinh_dang_ten =
            this.$refs.refCongDieuKhien.listCongDieuKhien.length > 0
              ? this.$refs.refCongDieuKhien.listCongDieuKhien[0]?.TEN_FORMAT || ''
              : result[0]?.TEN_FORMAT || ''
          this.currentItem.da_su_dung = result[0]?.PORT_DA_SD || 0
          this.currentItem.con_trong =
            this.$refs.refCongDieuKhien.listCongDieuKhien.length - this.currentItem.da_su_dung || 0
          this.currentItem.bat_dau_cong =
            this.$refs.refCongDieuKhien.listCongDieuKhien.length > 0
              ? this.$refs.refCongDieuKhien.listCongDieuKhien[0]?.SOTHUTU || null
              : result[0]?.SOTHUTU || null
          this.isUsedPort = this.$refs.refCongDieuKhien.listCongDieuKhien.some(
            (port) => port.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI
          )
          this.$refs.refCongDieuKhien.allPorts = this.$refs.refCongDieuKhien.listCongDieuKhien
        }
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.listCongDuLieu = []
          this.$root.toastError(e.response.data.message_detail)
        }
      }
    },
    getListPort: async function(data) {
      let result = []
      try {
        this.loading(true)
        const response = await api.getPortList(this.$root.context, data)
        result = response.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    onChangeTab(tabIndex) {
      if (this.currentTab === 1 && this.$refs.refCongDulieu) {
        if (!this.currentItem.arr_cong_dulieu) this.$set(this.currentItem, 'arr_cong_dulieu', {})
        this.currentItem.arr_cong_dulieu.ports = this.$refs.refCongDulieu.listCongDuLieu

        if (!this.currentItem.arr_line_card) this.$set(this.currentItem, 'arr_line_card', {})
        this.currentItem.arr_line_card.ports = this.$refs.refCongDulieu.listLineCard
      } else if (this.currentTab === 2 && this.$refs.refCongNguon) {
        if (!this.currentItem.arr_cong_nguon) this.$set(this.currentItem, 'arr_cong_nguon', {})
        this.currentItem.arr_cong_nguon.ports = this.$refs.refCongNguon.listCongNguon

        if (!this.currentItem.arr_power_card) this.$set(this.currentItem, 'arr_power_card', {})
        this.currentItem.arr_power_card.ports = this.$refs.refCongNguon.listPowerCard
      } else if (this.currentTab === 3 && this.$refs.refCongDieuKhien) {
        if (!this.currentItem.arr_cong_dieukhien) this.$set(this.currentItem, 'arr_cong_dieukhien', {})
        this.currentItem.arr_cong_dieukhien.ports = this.$refs.refCongDieuKhien.listCongDieuKhien

        if (!this.currentItem.arr_control_card) this.$set(this.currentItem, 'arr_control_card', {})
        this.currentItem.arr_control_card.ports = this.$refs.refCongDieuKhien.listControlCard
      }

      this.currentTab = tabIndex
      this.phanLoai =
        this.currentTab === 1
          ? PHANLOAI_CONG.CONG_DU_LIEU
          : this.currentTab === 2
          ? PHANLOAI_CONG.CONG_NGUON
          : PHANLOAI_CONG.CONG_DIEU_KHIEN
      this.currentItem.so_cong = null
      this.currentItem.dinh_dang_ten = ''
      this.currentItem.bat_dau_cong = null
      this.isUsedPort = false
      if (this.deviceTypeInfo.isNetwork) {
        this.loadListKheCam()
      }
      this.loadListPort()
    },
    validateControlsCustom() {
      if (this.deviceTypeInfo.isNetwork) {
        return this.validatePortMessage(true) && this.validatePortMessage(false)
      } else {
        return this.validatePortMessage(true)
      }
    },
    validatePort(isPort) {
      const msg = this.validatePortMessage(isPort)
      if (!msg) return true
      this.$root.toastError(msg)
      return false
    },
    validatePortMessage(isPort) {
      let msg = ''
      if (isPort) {
        if (this.currentItem.so_cong == null) {
          msg = 'Số cổng không được để trống.'
        } else if (isNaN(this.currentItem.so_cong) || Number(this.currentItem.so_cong) <= 0) {
          msg = 'Số cổng phải là số lớn hơn 0.'
        } else if (this.currentItem.bat_dau_cong == null) {
          msg = 'Bắt đầu từ không được để trống.'
        } else if (isNaN(this.currentItem.bat_dau_cong) || Number(this.currentItem.bat_dau_cong) < 0) {
          msg = 'Bắt đầu từ phải là số lớn hơn hoặc bằng 0.'
        } else if (!this.currentItem.dinh_dang_ten || this.currentItem.dinh_dang_ten.trim() == '') {
          msg = 'Định dạng tên không được để trống.'
        }
      } else {
        if (this.currentItem.so_khe_cam == null) {
          msg = 'Tổng số không được để trống.'
        } else if (isNaN(this.currentItem.so_khe_cam) || Number(this.currentItem.so_khe_cam) <= 0) {
          msg = 'Tổng số phải là số lớn hơn 0.'
        } else if (this.currentItem.khe_cam_bat_dau == null) {
          msg = 'Bắt đầu từ không được để trống.'
        } else if (isNaN(this.currentItem.khe_cam_bat_dau) || Number(this.currentItem.khe_cam_bat_dau) < 0) {
          msg = 'Bắt đầu từ phải là số lớn hơn hoặc bằng 0.'
        } else if (!this.currentItem.khe_cam_dinh_dang_ten || this.currentItem.khe_cam_dinh_dang_ten.trim() == '') {
          msg = 'Định dạng tên không được để trống.'
        }
      }
      return msg
    },
    //Định dạng tên cổng
    formatPortName(pattern, index) {
      if (!pattern) {
        return String(index)
      }
      return `${pattern}_${index}`
    },
    async onSavePort() {
      try {
        this.$root.showLoading(true)
        let params = {} // Changed to object
        let rs = null
        // const result = [] // This variable was unused
        // this.currentItem.id = 988 //fake để test - Removed fake ID

        // Data Port and Line Card
        if (
          this.$refs.refCongDulieu?.listCongDuLieu?.length > 0 ||
          this.$refs.refCongDulieu?.listLineCard?.length > 0
        ) {
          const portsDuLieu = this.getDirtyItems(this.$refs.refCongDulieu.listCongDuLieu || [])
          const portsLineCard = this.getDirtyItems(this.$refs.refCongDulieu.listLineCard || [])
          params = {
            thietBiId: this.currentItem.id,
            portsDto:
              portsDuLieu.length > 0
                ? {
                    thietBiId: this.currentItem.id,
                    phanLoai: PHANLOAI_CONG.CONG_DU_LIEU,
                    soCong: this.currentItem.so_cong,
                    batDauTu: this.currentItem.bat_dau_cong,
                    dinhDangTen: this.currentItem.dinh_dang_ten,
                    ports: portsDuLieu
                  }
                : null,
            lineCardsDto:
              portsLineCard.length > 0
                ? {
                    thietBiId: this.currentItem.id,
                    phanLoai: PHANLOAI_CONG.CONG_DU_LIEU,
                    soCong: this.currentItem.so_khe_cam,
                    batDauTu: this.currentItem.khe_cam_bat_dau,
                    dinhDangTen: this.currentItem.khe_cam_dinh_dang_ten,
                    ports: portsLineCard
                  }
                : null
          }
          rs = await api.upSertPort(this.$root.context, params)
        }
        // Power Port and Power Card
        if (this.$refs.refCongNguon?.listCongNguon?.length > 0 || this.$refs.refCongNguon?.listPowerCard?.length > 0) {
          const portsNguon = this.getDirtyItems(this.$refs.refCongNguon.listCongNguon || [])
          const portsPowerCard = this.getDirtyItems(this.$refs.refCongNguon.listPowerCard || [])
          params = {
            thietBiId: this.currentItem.id,
            portsDto:
              portsNguon.length > 0
                ? {
                    thietBiId: this.currentItem.id,
                    phanLoai: PHANLOAI_CONG.CONG_NGUON,
                    soCong: this.currentItem.so_cong,
                    batDauTu: this.currentItem.bat_dau_cong,
                    dinhDangTen: this.currentItem.dinh_dang_ten,
                    ports: portsNguon
                  }
                : null,
            lineCardsDto:
              portsPowerCard.length > 0
                ? {
                    thietBiId: this.currentItem.id,
                    phanLoai: PHANLOAI_CONG.CONG_NGUON,
                    soCong: this.currentItem.so_khe_cam,
                    batDauTu: this.currentItem.khe_cam_bat_dau,
                    dinhDangTen: this.currentItem.khe_cam_dinh_dang_ten,
                    ports: portsPowerCard
                  }
                : null
          }
          rs = await api.upSertPort(this.$root.context, params)
        }
        // Control Port and Control Card
        if (
          this.$refs.refCongDieuKhien?.listCongDieuKhien?.length > 0 ||
          this.$refs.refCongDieuKhien?.listControlCard?.length > 0
        ) {
          const portsDieuKhien = this.getDirtyItems(this.$refs.refCongDieuKhien.listCongDieuKhien || [])
          const portsControlCard = this.getDirtyItems(this.$refs.refCongDieuKhien.listControlCard || [])
          params = {
            thietBiId: this.currentItem.id,
            portsDto:
              portsDieuKhien.length > 0
                ? {
                    thietBiId: this.currentItem.id,
                    phanLoai: PHANLOAI_CONG.CONG_DIEU_KHIEN,
                    soCong: this.currentItem.so_cong,
                    batDauTu: this.currentItem.bat_dau_cong,
                    dinhDangTen: this.currentItem.dinh_dang_ten,
                    ports: portsDieuKhien
                  }
                : null,
            lineCardsDto:
              portsControlCard.length > 0
                ? {
                    thietBiId: this.currentItem.id,
                    phanLoai: PHANLOAI_CONG.CONG_DIEU_KHIEN,
                    soCong: this.currentItem.so_khe_cam,
                    batDauTu: this.currentItem.khe_cam_bat_dau,
                    dinhDangTen: this.currentItem.khe_cam_dinh_dang_ten,
                    ports: portsControlCard
                  }
                : null
          }
          rs = await api.upSertPort(this.$root.context, params)
        }

        if (rs && rs.error_code === 'BSS-00000000') {
          if (this.currentItem.arr_cong_dulieu) {
            this.currentItem.arr_cong_dulieu.ports = null
          }
          if (this.currentItem.arr_cong_nguon) {
            this.currentItem.arr_cong_nguon.ports = null
          }
          if (this.currentItem.arr_cong_dieukhien) {
            this.currentItem.arr_cong_dieukhien.ports = null
          }
          if (this.currentItem.arr_line_card) {
            this.currentItem.arr_line_card.ports = null
          }
          if (this.currentItem.arr_control_card) {
            this.currentItem.arr_control_card.ports = null
          }
          if (this.currentItem.arr_power_card) {
            this.currentItem.arr_power_card.ports = null
          }
          this.$toast.success('Lưu cấu hình cổng kết nối thành công!')
          if (this.deviceTypeInfo.isNetwork) {
            await this.loadListPort()
            await this.loadListKheCam()
          } else {
            await this.loadListPort()
          }
        } else {
          this.$toast.error(rs.message_detail || 'Có lỗi xảy ra')
        }
      } catch (err) {
        console.error(err)
        this.$toast.error('Lỗi khi lưu dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDirtyItems(list) {
      if (!Array.isArray(list)) return []
      // const dirtyItems = list
      //   .filter(
      //     (x) =>
      //       x.isEdited === true ||
      //       (x.PORT_ID && x.PORT_ID.toString().startsWith('TMP_')) ||
      //       (x.ID && x.ID.toString().startsWith('TMP_'))
      //   )
      //   .map((port) => this.mapPortToApiFormat(port, type))
      const dirtyItems = list.map((port) => this.mapPortToApiFormat(port))
      return dirtyItems
    },

    mapPortToApiFormat(port) {
      let portId = port.PORT_ID || port.ID
      if (portId && portId.toString().startsWith('TMP_')) {
        portId = null
      }
      return {
        portId: portId,
        soThuTu: port.SOTHUTU || null,
        ten: port.TEN || null,
        chuanDnId: port.CHUANDN_ID || null,
        ttPortId: port.TT_PORT_ID || null,
        trangThaiKnId: port.TRANGTHAIKN_ID || null,
        thietBiKnId: port.THIETBIKN_ID || null,
        diaChiIp: port.DIACHI_IP_ID || null,
        diaChiIpTen: port.DIACHI_IP || null,
        loaiNdienId: port.LOAI_NDIEN_ID || null,
        daDinhDanh: port.DA_DINHDANH || null,
        ddDinhDanh: port.DD_DINHDANH || null,
        mucDichSdId: port.MUCDICHSD_ID || null,
        loaiCongId: port.LOAICONG_ID || null,
        moduleId: port.MODULE_ID || null,
        bangThong: port.BANGTHONG_TN || null,
        thietBiId: port.THIETBI_ID || null
      }
    },
    isNumber(evt) {
      const charCode = evt.which ? evt.which : evt.keyCode
      const allowedKeys = [8, 9, 13, 37, 39, 46] // Backspace, Tab, Enter, ←, →, Delete
      if (allowedKeys.includes(charCode)) return
      // Chỉ cho phép số (0–9)
      if (charCode < 48 || charCode > 57) {
        evt.preventDefault()
      }
    },
    // Khi dán, chỉ giữ lại chữ số
    /**
     * Dùng chung cho tất cả input số (so_cong, bat_dau_cong, v.v.)
     * @param {Object} obj   : object model (currentItem, dataPortCurrent, v.v.)
     * @param {String} field : tên field trong obj
     * @param {Event} e      : sự kiện paste
     * @param {Number} min   : giá trị nhỏ nhất (mặc định 0)
     * @param {Number|null} max : giá trị lớn nhất (nếu cần)
     */
    handlePasteNumber(obj, field, e, min = 0, max = null) {
      if (!obj || !field) return
      e.preventDefault() // chặn dán mặc định vào input
      const text = (e.clipboardData || window.clipboardData)?.getData('text') || ''
      const digits = text.replace(/\D/g, '') // chỉ giữ lại chữ số
      if (!digits) {
        // nếu không có số nào -> không set gì
        return
      }
      let num = parseInt(digits, 10)
      if (!Number.isFinite(num)) {
        return
      }
      // ép min
      if (num < min) num = min
      // ép max nếu có
      if (max !== null && num > max) num = max
      // cập nhật model (giữ reactivity)
      this.$set(obj, field, num)
    },
    checkPortStatus(portId, statusId, connectionId) {
      // Nếu port có ID chứa TMP thì TT_PORT_ID bắt buộc phải = 0
      if (portId && portId.toString().startsWith('TMP_')) {
        if (Number(statusId) !== 0) {
          return {
            isValid: false,
            message: 'Cổng tạm chưa được lưu xuống DB không thể chuyển trạng thái "Đang sử dụng".'
          }
        }
      } else {
        // Nếu ID port đã có dưới BE
        // nếu chuyển từ trạng thái khác sang = 1 (Đang sử dụng)
        // thì thông báo Thiết bị chưa được kết nối điện/mạng không thể chuyển sang trạng thái "Đang sử dụng".
        // Logic: Nếu đang chọn trạng thái "Đang sử dụng" (1) mà chưa kết nối (TRANGTHAIKN_ID == CHUA_KET_NOI) -> lỗi
        if (Number(statusId) === 1) {
          if (Number(connectionId) === TRANGTHAI_KN.CHUA_KET_NOI) {
            return {
              isValid: false,
              message: 'Thiết bị chưa được kết nối điện/mạng không thể chuyển sang trạng thái "Đang sử dụng".'
            }
          }
        } else if (Number(statusId) === 2 || Number(statusId) === 0) {
          if (Number(connectionId) === TRANGTHAI_KN.DA_KET_NOI) {
            return {
              isValid: false,
              message: 'Đã tồn tại kết nối điện/mạng. Vui lòng gỡ kết nối trước khi chuyển sang trạng thái khác.'
            }
          }
        }
      }
      return { isValid: true, message: '' }
    },
    checkSoCong(val, listPort, isKheCam = false) {
      if (listPort && listPort.length > 0) {
        const hasConnected = listPort.some((p) => p.TRANGTHAIKN_ID === TRANGTHAI_KN.DA_KET_NOI)
        const msg = isKheCam
          ? 'Đã tồn tại khe cắm có trạng thái kết nối. Không thể nhập tổng số nhỏ hơn tổng số khe cắm đã tồn tại.'
          : 'Đã tồn tại cổng có trạng thái kết nối. Không thể nhập số cổng nhỏ hơn hoặc bằng số cổng đã tồn tại.'
        if (hasConnected) {
          if (Number(val) <= listPort.length) {
            return {
              isValid: false,
              message: msg
            }
          }
        }
      }
      return { isValid: true, message: '' }
    }
  }
}
</script>
<style scoped>
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
