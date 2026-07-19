<template src="./index.template.html"></template>
<script>
import thietBiApi from '../api/ThietBi.api'
import danhMucApi from '../api/DanhMuc.api'
import {
  ThongTinChung,
  DacTinhThietBi,
  ThongTinLapDat,
  ThongTinKiemKe,
  LichSuThietBi,
  AllocationInfo,
  ThongTinPort,
  DeviceRelationInfo,
  ThongTinKheCam
} from './Components'
import {
  CUSTOMER_TYPES,
  LOAI_THIET_BI,
  NEW_DEFAULT_OBJ,
  NEW_DEFAULT_LINE_CARD,
  PORT_TYPE,
  THIETBI_PHUTRO_ID
} from '../const.js'
import { getButtonText, getCardModuleTitle, getCardModuleDefaults } from './cardModuleConfig.js'
import moment from 'moment'
import ThongTinCard from './Components/ThongTinCard/index.vue'

export default {
  name: 'ThongTinChiTietThietBi',
  components: {
    ThongTinChung,
    DacTinhThietBi,
    ThongTinLapDat,
    ThongTinKiemKe,
    LichSuThietBi,
    DeviceRelationInfo,
    AllocationInfo,
    ThongTinPort,
    ThongTinCard,
    ThongTinKheCam
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      tab_index: 1, // Default active tab
      currentItem: {
        id: null,
        // Thông tin chung
        mathietbi: '',
        ten: '',
        kyhieu: '',
        mathietbi_infra: '',
        thietbicha_id: null,
        loaithietbi_id: null,
        loaikhachhang: 0,
        serial_number: '',
        model: '',
        part_number: '',
        hsm: 0,
        hieu_luc: 1,
        ghi_chu: '',
        loaitudia_id: null,
        chungloaithietbi_id: 0,
        ip_ids: [],
        // Đặc tính thiết bị
        vaitrothietbi_ids: [],
        psu_qty_in: 0,
        psu_qty_out: 0,
        cauhinh: '',
        hangsx_id: null,
        namsx: 0,
        nhacungcap_id: null,
        cs_dinhdanh: 0,
        cs_thucte: 0,
        hedieuhanh_id: null,
        trangthai_sd: 1,
        ngay_lapdat: moment().format('YYYY-MM-DD'),
        ngay_baohanh: moment().format('YYYY-MM-DD'),
        ngay_sudung: moment().format('YYYY-MM-DD'),
        cpu_sokhe: 0,
        cpu_dacam: 0,
        cpu_total: 0,
        hdd_sokhe: 0,
        hdd_dacam: 0,
        hdd_total: 0,
        ram_sokhe: 0,
        ram_dacam: 0,
        ram_total: 0,
        bodieukhien_qty: 0,
        bodieukhien_dau: 0,
        bodieukhien_fname: '',
        khaydia_qty: 0,
        khaydia_dau: 0,
        khaydia_fname: '',
        // Vị trí lắp đặt TB
        idc_id: null,
        building_id: null,
        floor_id: null,
        zone_id: null,
        rack_id: null,
        unit_qty: 0,
        up_unit_id: null,
        down_unit_id: null,
        // Thong tin kiem ke tai san
        project_id: null,
        project_code: '',
        project_name: '',
        so_the_ts: '',
        donviqly_id: null,
        phongbanqly_id: null,
        nguoiqly_id: null,
        // Thong tin cong thiet bi
        // interface
        so_linecard: 0,
        so_port_interface: 0,
        so_interface: 0,
        interface_start: 0,
        interface_fname: '',
        // power
        so_power: 0,
        so_port_power: 0,
        so_powerslot: 0,
        power_start: 0,
        power_fname: '',
        // console
        so_controlcard: 0,
        so_port_console: 0,
        so_console: 0,
        console_start: 0,
        console_fname: '',
        // Nang luc luu tru
        hdd_tong: 0,
        hdd_nangluc: 0,
        hdd_capphat: 0,
        ssd_tong: 0,
        ssd_nangluc: 0,
        ssd_capphat: 0,
        iops_tong: 0,
        iops_nangluc: 0,
        iops_capphat: 0,
        mem_cache: 0,
        flash_cache: 0,
        // Loai thiet bi
        is_interface: null,
        is_power: null,
        is_console: null,
        is_controller: null,
        is_khaydia: null
      },
      // Data lists
      deviceTypeList: [],
      customerTypeList: [],
      chungLoaiThietBiList: [],
      discTypeList: [],
      IPAddressList: [],
      parentDeviceList: [],
      manufacturerList: [],
      supplierList: [],
      usageStatusList: [],
      deviceRoleList: [],
      OSList: [],
      IDCList: [],
      IDCBuildingList: [],
      alterBuildingList: [],
      IDCFloorList: [],
      alterFloorList: [],
      IDCZoneList: [],
      alterZoneList: [],
      rackList: [],
      alterRackList: [],
      UpperUList: [],
      LowerUList: [],
      donViQuanLyList: [],
      phongBanQuanLyList: [],
      nguoiQuanLyList: [],
      deviceRelationHistory: [],
      // Device relation info data
      connectorList: [],
      infraList: [],
      colocationList: [],
      // Allocation info data
      VMList: [],
      NFSList: [],
      controllerList: [],
      shelvesList: [],
      // Port-related data
      portInterfaceList: [],
      portPowerList: [],
      portConsoleList: [],
      portTypeInterface: [],
      portTypePower: [],
      portTypeConsole: [],
      cardList: [],
      loaiCongList: [],
      mucDichList: [],
      bangThongList: [],
      thongtinModuleList: [],
      kheCamList: [],
      // Controls for each tab
      thongTinChungControls: {
        txt_ten: { enabled: false, invalid: false },
        txt_kyhieu: { enabled: false },
        txt_mathietbi_infra: { enabled: false },
        select_loaithietbi_id: { enabled: false, invalid: false },
        select_loaikhachhang: { enabled: false },
        txt_serial_number: { enabled: false },
        checkbox_hsm: { enabled: false },
        checkbox_hieu_luc: { enabled: false },
        txt_ghi_chu: { enabled: false },
        select_loaitudia_id: { enabled: false, invalid: false },
        select_chungloaithietbi_id: { enabled: false, invalid: false },
        select_ip_ids: { enabled: false },
        txt_part_number: { enabled: false },
        txt_model: { enabled: false },
        select_thietbicha_id: { enabled: false, invalid: false }
      },
      dacTinhThietBiControls: {
        select_hangsx_id: { enabled: false },
        txt_namsx: { enabled: false },
        select_nhacungcap_id: { enabled: false },
        txt_cauhinh: { enabled: false },
        select_trangthai_sd: { enabled: false },
        txt_ngay_lapdat: { enabled: false },
        txt_ngay_baohanh: { enabled: false },
        txt_ngay_sudung: { enabled: false },
        txt_cpu_sokhe: { enabled: false },
        txt_cpu_dacam: { enabled: false },
        txt_cpu_total: { enabled: false },
        txt_ram_sokhe: { enabled: false },
        txt_ram_dacam: { enabled: false },
        txt_ram_total: { enabled: false },
        txt_hdd_sokhe: { enabled: false },
        txt_hdd_dacam: { enabled: false },
        txt_hdd_total: { enabled: false },
        txt_psu_qty_in: { enabled: false },
        txt_psu_qty_out: { enabled: false },
        txt_bodieukhien_qty: { enabled: false },
        txt_bodieukhien_dau: { enabled: false },
        txt_khaydia_qty: { enabled: false },
        txt_khaydia_dau: { enabled: false },
        txt_bodieukhien_fname: { enabled: false },
        txt_khaydia_fname: { enabled: false },
        select_vaitrothietbi_ids: { enabled: false },
        txt_cs_dinhdanh: { enabled: false },
        txt_cs_thucte: { enabled: false },
        select_hedieuhanh_id: { enabled: false },
        // Storage capacity controls
        txt_hdd_tong: { enabled: false, invalid: false },
        txt_hdd_nangluc: { enabled: false, invalid: false },
        txt_hdd_capphat: { enabled: false, invalid: false },
        txt_ssd_tong: { enabled: false, invalid: false },
        txt_ssd_nangluc: { enabled: false, invalid: false },
        txt_ssd_capphat: { enabled: false, invalid: false },
        txt_iops_tong: { enabled: false, invalid: false },
        txt_iops_nangluc: { enabled: false, invalid: false },
        txt_iops_capphat: { enabled: false, invalid: false },
        txt_mem_cache: { enabled: false },
        txt_flash_cache: { enabled: false }
      },
      thongTinLapDatControls: {
        select_idc_id: { enabled: false },
        select_building_id: { enabled: false },
        select_floor_id: { enabled: false },
        select_zone_id: { enabled: false },
        select_rack_id: { enabled: false },
        txt_unit_qty: { enabled: false },
        select_up_unit_id: { enabled: false },
        select_down_unit_id: { enabled: false }
      },
      thongTinKiemKeControls: {
        txt_project_code: { enabled: false },
        txt_project_name: { enabled: false },
        txt_so_the_ts: { enabled: false },
        select_project_id: { enabled: false },
        select_donviqly_id: { enabled: false },
        select_phongbanqly_id: { enabled: false },
        select_nguoiqly_id: { enabled: false }
      },
      deviceType: '',
      // Dùng cho thêm card và module
      cardModuleInfo: {
        thietbicha_id: null,
        portType: PORT_TYPE.INTERFACE.NAME,
        isCardModuleMode: false,
        parentDeviceInfo: null
      }
    }
  },
  computed: {
    isSelectedDeviceType() {
      return !!this.deviceType
    },
    isServer() {
      return this.deviceType == LOAI_THIET_BI.MAY_CHU
    },
    isNetwork() {
      return this.deviceType == LOAI_THIET_BI.MANG
    },
    isDiskShelf() {
      return this.deviceType == LOAI_THIET_BI.TU_DIA
    },
    isIDC() {
      return this.deviceType == LOAI_THIET_BI.IDC
    },
    isOther() {
      return this.deviceType == LOAI_THIET_BI.KHAC
    },
    interfaceList() {
      const list = this.cardList.filter((item) => item.thietbicha_id == this.currentItem.id && !!item.is_interface)
      return list
    },
    powerList() {
      const list = this.cardList.filter((item) => item.thietbicha_id == this.currentItem.id && !!item.is_power)
      return list
    },
    consoleList() {
      const list = this.cardList.filter((item) => item.thietbicha_id == this.currentItem.id && !!item.is_console)
      return list
    },
    isCardModuleMode() {
      return this.cardModuleInfo.isCardModuleMode
    },
    cardModuleTitle() {
      if (!this.isCardModuleMode) return ''
      return getCardModuleTitle(this.cardModuleInfo.portType)
    },
    saveButtonText() {
      return getButtonText(this.isCardModuleMode, this.cardModuleInfo.portType, this.currentMode)
    }
  },
  watch: {
    currentMode: function(newVal) {
      this.enableControls(newVal === 'ADD' || newVal === 'EDIT')
    }
  },
  provide() {
    return {
      deletePortTypeItem: this.deletePortTypeItem,
      // Ensure nested components (like PortType) update this component's currentItem
      setCurrentItemValByProp: (prop, value) => {
        if (!this.currentItem) return
        this.$set(this.currentItem, prop, value)
      }
    }
  },
  methods: {
    deletePortTypeItem(type, item) {
      switch (type) {
        case PORT_TYPE.INTERFACE.NAME:
          this.portTypeInterface = this.portTypeInterface.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
          break
        case PORT_TYPE.POWER.NAME:
          this.portTypePower = this.portTypePower.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
          break
        case PORT_TYPE.CONSOLE.NAME:
          this.portTypeConsole = this.portTypeConsole.filter((el) => el.tmpId != item.tmpId || el.id != item.id)
          break
      }
    },
    show: async function(item, mode, cardModuleOptions = null) {
      if (cardModuleOptions && cardModuleOptions.isCardModuleMode) {
        // Chế độ thêm card/module
        this.cardModuleInfo = {
          ...cardModuleOptions,
          isCardModuleMode: true,
          parentDeviceInfo: item
        }
        this.resetCurrentItem()
        this.setupCardModuleDefaults(cardModuleOptions)
        this.currentMode = 'ADD'
      } else if (item) {
        this.currentItem = { ...item }
        this.currentMode = mode
        await this.loadDeviceData(item.id)
      } else {
        this.resetCurrentItem()
        this.currentMode = mode
      }
      if (!this.deviceTypeList.length) {
        await this.loadMasterData()
        this.onDeviceTypeChanged(this.currentItem.loaithietbi_id)
      }
      this.tab_index = 1
      this.$refs.ThongTinChiTietThietBi.show()
    },
    resetCurrentItem() {
      this.currentItem = { ...NEW_DEFAULT_OBJ }
      this.deviceRelationHistory = []
      this.connectorList = []
      this.infraList = []
      this.colocationList = []
      this.VMList = []
      this.NFSList = []
      this.controllerList = []
      this.shelvesList = []
      // Reset port-related data
      this.portInterfaceList = []
      this.portPowerList = []
      this.portConsoleList = []
      this.portTypeInterface = []
      this.portTypePower = []
      this.portTypeConsole = []
      this.cardList = []
      this.kheCamList = []
      this.cardModuleInfo = {
        thietbicha_id: null,
        portType: PORT_TYPE.INTERFACE.NAME,
        isCardModuleMode: false,
        parentDeviceInfo: null
      }
    },
    setupCardModuleDefaults(cardModuleOptions) {
      // Reset currentItem với template cho line card
      this.currentItem = { ...NEW_DEFAULT_LINE_CARD }
      // Set thông tin thiết bị cha
      this.currentItem.thietbicha_id = cardModuleOptions.thietbicha_id
      // Set is_interface, is_power, is_console dựa trên portType sử dụng config
      const defaults = getCardModuleDefaults(cardModuleOptions.portType)
      Object.assign(this.currentItem, defaults)
      // Mặc định thiết bị PHUTRO
      this.currentItem.loaithietbi_id = THIETBI_PHUTRO_ID
      this.deviceType = LOAI_THIET_BI.KHAC

      if (cardModuleOptions.parentDeviceInfo) {
        // Thông tin lắp đặt theo parent
        this.currentItem.idc_id = cardModuleOptions.parentDeviceInfo.idc_id
        this.currentItem.building_id = cardModuleOptions.parentDeviceInfo.building_id
        this.currentItem.floor_id = cardModuleOptions.parentDeviceInfo.floor_id
        this.currentItem.zone_id = cardModuleOptions.parentDeviceInfo.zone_id
        this.currentItem.rack_id = cardModuleOptions.parentDeviceInfo.rack_id
        // thông tin quản lý theo parent
        this.currentItem.donviqly_id = cardModuleOptions.parentDeviceInfo.donviqly_id
        this.currentItem.phongbanqly_id = cardModuleOptions.parentDeviceInfo.phongbanqly_id
        this.currentItem.nguoiqly_id = cardModuleOptions.parentDeviceInfo.nguoiqly_id
      }

      // Reset card module info flag
      this.cardModuleInfo.isCardModuleMode = true
    },
    closeModal() {
      this.resetValidation()
      this.$parent.resetCurrentItem()
      if (this.isCardModuleMode && this.cardModuleInfo.parentDeviceInfo) {
        this.switchToParentDeviceEditMode()
      } else {
        this.cardModuleInfo.isCardModuleMode = false
        this.cardModuleInfo.parentDeviceInfo = null
        this.$refs.ThongTinChiTietThietBi.hide()
      }
    },
    onSave() {
      if (this.validateControls()) {
        if (this.isCardModuleMode) {
          // Xử lý lưu card module
          this.handleSaveCardModule()
        } else {
          // Xử lý lưu thiết bị thông thường
          this.handleSaveDevice()
        }
      }
    },

    handlePortData() {
      // Lấy dữ liệu port types từ PortType component
      let portTypeData = {}
      if (this.$refs.portType) {
        portTypeData = this.$refs.portType.getPortTypeData()
      }

      // Gộp port types vào currentItem trước khi save
      const portKhaiBao = []
      if (!this.currentItem.so_linecard) {
        portKhaiBao.push(...(portTypeData.portTypeInterface || this.portTypeInterface))
      }
      if (!this.currentItem.so_power) {
        portKhaiBao.push(...(portTypeData.portTypePower || this.portTypePower))
      }
      if (!this.currentItem.so_controlcard) {
        portKhaiBao.push(...(portTypeData.portTypeConsole || this.portTypeConsole))
      }

      // Gán vào currentItem
      this.currentItem.khaibao_port = portKhaiBao
      return portKhaiBao
    },
    async handleSaveCardModule() {
      this.handlePortData()
      this.$emit('save-card-module', this.currentItem)
      this.switchToParentDeviceEditMode()
    },
    handleSaveDevice() {
      this.handlePortData()
      this.$emit('save-device', { ...this.currentItem, phanloai: this.deviceType })
    },
    switchToParentDeviceEditMode() {
      if (this.cardModuleInfo.parentDeviceInfo) {
        this.cardModuleInfo.isCardModuleMode = false
        this.show(this.cardModuleInfo.parentDeviceInfo, 'EDIT')
        this.cardModuleInfo.parentDeviceInfo = null
      }
    },
    enableControls(enabled) {
      // Enable/Disable cho form thông tin chung
      Object.keys(this.thongTinChungControls).forEach((key) => {
        if (this.thongTinChungControls[key].hasOwnProperty('enabled')) {
          this.thongTinChungControls[key].enabled = enabled
        }
      })
      // Nếu đang ở chế độ thêm card/module thì luôn disable loại thiết bị và thiết bị cha
      if (this.isCardModuleMode) {
        this.thongTinChungControls.select_loaithietbi_id.enabled = false
        this.thongTinChungControls.select_thietbicha_id.enabled = false
      }
      // Enable/Disable cho form đặc tính thiết bị
      Object.keys(this.dacTinhThietBiControls).forEach((key) => {
        if (this.dacTinhThietBiControls[key].hasOwnProperty('enabled')) {
          this.dacTinhThietBiControls[key].enabled = enabled
        }
      })
      // Mặc định disable thông tin lắp đặt
      Object.keys(this.thongTinLapDatControls).forEach((key) => {
        if (this.thongTinLapDatControls[key].hasOwnProperty('disabled')) {
          this.thongTinLapDatControls[key].enabled = disabled
        }
      })
      // Enable/Disable cho form thông tin quản lý
      Object.keys(this.thongTinKiemKeControls).forEach((key) => {
        if (this.thongTinKiemKeControls[key].hasOwnProperty('enabled')) {
          this.thongTinKiemKeControls[key].enabled = enabled
        }
      })

      // Enable/Disable cho PortType component
      if (this.$refs.portType) {
        this.$refs.portType.enableControls(enabled)
      }
    },
    resetValidation() {
      // Reset validation cho thông tin chung
      if (this.$refs.thongTinChung && this.$refs.thongTinChung.resetValidation) {
        this.$refs.thongTinChung.resetValidation()
      }

      // Reset validation cho đặc tính thiết bị
      if (this.$refs.dacTinhThietBi && this.$refs.dacTinhThietBi.resetValidation) {
        this.$refs.dacTinhThietBi.resetValidation()
      }
    },
    validateControls() {
      let msg = ''

      // Validate thông tin chung
      if (this.$refs.thongTinChung) {
        msg += this.$refs.thongTinChung.validateControls()
      }

      // Validate đặc tính thiết bị
      if (this.$refs.dacTinhThietBi) {
        msg += this.$refs.dacTinhThietBi.validateControls()
      }

      // Validate thông tin lắp đặt
      // if (this.$refs.thongTinLapDat) {
      //   msg += this.$refs.thongTinLapDat.validateControls()
      // }

      // Validate thông tin kiểm kê
      if (this.$refs.thongTinKiemKe) {
        msg += this.$refs.thongTinKiemKe.validateControls()
      }

      if (msg != '') {
        this.$root.toastError(msg)
      }
      return msg == ''
    },
    async loadDeviceData(deviceId) {
      if (!deviceId) return
      try {
        this.$root.showLoading(true)
        await this.getDeviceRelationInfo(deviceId)
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadMasterData() {
      try {
        this.$root.showLoading(true)
        await Promise.all([
          this.getDeviceType(),
          this.getChungLoaiThietBi(),
          this.getDanhMucLoaiTuDia(),
          this.getIDC(),
          this.getIDCBuilding(this.currentItem.idc_id),
          this.getIDCFloor(this.currentItem.building_id),
          this.getIDCZone(this.currentItem.floor_id),
          this.getIDCRack(this.currentItem.zone_id),
          this.getManufacturerList(),
          this.getSupplierList(),
          this.getDeviceRoleList(),
          this.getOSList(),
          this.getUsageStatusList()
        ])
        this.customerTypeList = CUSTOMER_TYPES
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu danh mục')
      } finally {
        this.$root.showLoading(false)
      }
    },
    // Event handlers for component events
    onDeviceTypeChanged(deviceTypeId) {
      if (deviceTypeId) {
        const type = this.deviceTypeList.find((item) => item.ID == deviceTypeId)
        if (type) {
          this.deviceType = type.PHANLOAI
          this.updateParentDeviceList()
        }
      }
    },
    onEquipmentCategoryChanged(chungLoaiId) {
      if (chungLoaiId) {
        const chungloai = this.chungLoaiThietBiList.find((item) => item.ID == Number(chungLoaiId))
        if (chungloai) {
          this.currentItem.hangsx_id = chungloai.HANGSANXUAT_ID
          this.currentItem.cs_dinhdanh = chungloai.CS_DINHDANH
          this.currentItem.so_linecard = chungloai.SO_LINECARD ?? this.currentItem.so_linecard
          this.currentItem.so_interface = chungloai.SO_INTERFACE ?? this.currentItem.so_interface
          this.currentItem.so_port_interface = chungloai.SO_PORT_INTERFACE ?? this.currentItem.so_port_interface
          this.currentItem.so_power = chungloai.SO_POWER ?? this.currentItem.so_power
          this.currentItem.so_port_power = chungloai.SO_PORT_POWER ?? this.currentItem.so_port_power
          this.currentItem.so_controlcard = chungloai.SO_CONTROLCARD ?? this.currentItem.so_controlcard
          this.currentItem.so_console = chungloai.SO_CONSOLE ?? this.currentItem.so_console
          this.currentItem.so_port_console = chungloai.SO_PORT_CONSOLE ?? this.currentItem.so_port_console
        }
      }
    },
    onIDCChanged(idcId) {
      if (idcId) {
        this.getIDCBuilding(idcId)
      }
    },
    onBuildingChanged(buildingId) {
      if (buildingId) {
        this.getIDCFloor(buildingId)
      }
    },
    onFloorChanged(floorId) {
      if (floorId) {
        this.getIDCZone(floorId)
      }
    },
    onZoneChanged(zoneId) {
      if (zoneId) {
        this.getIDCRack(zoneId)
      }
    },
    onDonViQuanLyChanged(donViId) {
      // Load phòng ban quản lý based on đơn vị quản lý
      // Implementation depends on API structure
    },
    onPhongBanQuanLyChanged(phongBanId) {
      // Load người quản lý based on phòng ban quản lý
      // Implementation depends on API structure
    },
    updateParentDeviceList() {
      if (!Array.isArray(this.$parent.dataSource)) {
        this.parentDeviceList = []
        return
      }
      this.parentDeviceList = this.$parent.dataSource.filter(
        (item) =>
          item.id !== this.currentItem?.id &&
          item.thietbicha_id == item.id &&
          // loại trừ thiết bị phụ trợ
          item.loaithietbi_id != THIETBI_PHUTRO_ID
      )
    },
    onChangeTab: function(index) {
      this.tab_index = index
    },
    async getIDC() {
      try {
        const response = await danhMucApi.getIDC(this.$root.context)
        this.IDCList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu IDC')
      }
    },
    async getChungLoaiThietBi() {
      try {
        const response = await danhMucApi.getChungLoaiThietBi(this.$root.context)
        this.chungLoaiThietBiList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu chủng loại thiết bị')
      }
    },
    async getIDCBuilding(idcID = null) {
      try {
        const response = await danhMucApi.getIDCBuilding(this.$root.context, idcID)
        this.IDCBuildingList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu toà nhà IDC')
      }
    },
    async getIDCFloor(idcBuildingID = null) {
      try {
        const response = await danhMucApi.getIDCFloor(this.$root.context, idcBuildingID)
        this.IDCFloorList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu tầng IDC')
      }
    },
    async getIDCZone(idcFloorID = null) {
      try {
        const response = await danhMucApi.getIDCZone(this.$root.context, idcFloorID)
        this.IDCZoneList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu vùng IDC')
      }
    },
    async getIDCRack(idcZoneID = null) {
      try {
        const response = await danhMucApi.getIDCRack(this.$root.context, idcZoneID)
        this.rackList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu rack IDC')
      }
    },
    async getDeviceType() {
      try {
        const response = await danhMucApi.getDeviceType(this.$root.context)
        this.deviceTypeList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại thiết bị')
      }
    },
    async getChungLoaiThietBi() {
      try {
        const response = await danhMucApi.getChungLoaiThietBi(this.$root.context)
        this.chungLoaiThietBiList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu chủng loại thiết bị')
      }
    },
    async getDanhMucLoaiTuDia() {
      try {
        const response = await danhMucApi.getDanhMucLoaiTuDia(this.$root.context)
        this.discTypeList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại tủ đĩa')
      }
    },
    async getManufacturerList() {
      try {
        const response = await danhMucApi.getManufacturerList(this.$root.context)
        this.manufacturerList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu hãng sản xuất')
      }
    },
    async getSupplierList() {
      try {
        const response = await danhMucApi.getSupplierList(this.$root.context)
        this.supplierList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu nhà cung cấp')
      }
    },
    async getDeviceRoleList() {
      try {
        const response = await danhMucApi.getDeviceRoleList(this.$root.context)
        this.deviceRoleList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu vai trò thiết bị')
      }
    },
    async getOSList() {
      try {
        const response = await danhMucApi.getOSList(this.$root.context)
        this.OSList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu hệ điều hành')
      }
    },
    async getUsageStatusList() {
      try {
        const response = await danhMucApi.getUsageStatusList(this.$root.context)
        this.usageStatusList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu trạng thái sử dụng')
      }
    },
    // Device relation info methods
    async getDeviceRelationInfo(deviceId) {
      try {
        // Load basic device relation info
        const [
          thietBiDauNoi,
          thietBiInfra,
          thietBiLichSu,
          kheCamList,
          bangThongList,
          loaiCongList,
          mucDichList,
          thongtinModuleList
        ] = await Promise.all([
          thietBiApi.getThietBiDauNoi(this.$root.context, deviceId),
          thietBiApi.getThietBiInfra(this.$root.context, deviceId),
          thietBiApi.getThietBiLichSu(this.$root.context, deviceId),
          thietBiApi.getDanhSachKheCam(this.$root.context, deviceId),
          danhMucApi.getBangThongCong(this.$root.context),
          danhMucApi.getLoaiCong(this.$root.context),
          danhMucApi.getMucDichSuDung(this.$root.context),
          danhMucApi.getThongtinModule(this.$root.context)
        ])

        this.connectorList = thietBiDauNoi || []
        this.infraList = thietBiInfra || []
        this.deviceRelationHistory = thietBiLichSu || []
        this.kheCamList = kheCamList || []
        this.bangThongList = bangThongList || []
        this.loaiCongList = loaiCongList || []
        this.mucDichList = mucDichList || []
        this.thongtinModuleList = thongtinModuleList || []
        // Load allocation info if it's a disk shelf
        if (this.isDiskShelf) {
          await this.getAllocationInfo(deviceId)
        } else {
          // Load port info for non-disk shelf devices
          await this.getPortInfo(deviceId)
        }
        this.updateParentDeviceList()
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải thông tin thiết bị liên quan')
      }
    },
    async getAllocationInfo(deviceId) {
      try {
        const [khayDia, controller, VM, NFS] = await Promise.all([
          thietBiApi.getKhayDia(this.$root.context, deviceId),
          thietBiApi.getController(this.$root.context, deviceId),
          thietBiApi.getVM(this.$root.context, deviceId),
          thietBiApi.getNFS(this.$root.context, deviceId)
        ])

        this.shelvesList = khayDia || []
        this.controllerList = controller || []
        this.VMList = VM || []
        this.NFSList = NFS || []
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải thông tin phân bổ')
      }
    },
    async getPortInfo(deviceId) {
      try {
        // Load port declaration and port data
        const [portKhaiBao, thietBiPort] = await Promise.all([
          thietBiApi.getThietBiPortKhaiBao(this.$root.context, deviceId),
          thietBiApi.getThietBiPort(this.$root.context, deviceId)
        ])

        const portInterface = []
        const portPower = []
        const portConsole = []
        const portTypeInterface = []
        const portTypePower = []
        const portTypeConsole = []

        // Categorize port declaration data
        portKhaiBao.forEach((item) => {
          if (item.phanloai == PORT_TYPE.INTERFACE.ID) {
            portTypeInterface.push(item)
          } else if (item.phanloai == PORT_TYPE.POWER.ID) {
            portTypePower.push(item)
          } else if (item.phanloai == PORT_TYPE.CONSOLE.ID) {
            portTypeConsole.push(item)
          }
        })

        // Categorize port data
        thietBiPort.forEach((item) => {
          if (item.phanloai == PORT_TYPE.INTERFACE.ID) {
            portInterface.push(item)
          } else if (item.phanloai == PORT_TYPE.POWER.ID) {
            portPower.push(item)
          } else if (item.phanloai == PORT_TYPE.CONSOLE.ID) {
            portConsole.push(item)
          }
        })

        this.portInterfaceList = portInterface
        this.portInterfaceList.forEach((item, index) => {
          item.trangthai_sd_text = this.getTrangThaiText(item.trangthai_sd)
        })
        this.portPowerList = portPower
        this.portConsoleList = portConsole
        this.portTypeInterface = portTypeInterface
        this.portTypePower = portTypePower
        this.portTypeConsole = portTypeConsole

        // Load card/module devices
        await this.getCardList(deviceId)
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải thông tin cổng')
      }
    },
    async getCardList(deviceId) {
      try {
        this.cardList = JSON.parse(JSON.stringify(this.$parent.dataSource))
          .filter((el) => el.thietbicha_id == deviceId && (el.is_interface || el.is_power || el.is_console))
          .map((el, index) => ({ ...el, tempId: index }))
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải danh sách card')
      }
    },
    getTrangThaiText(value) {
      return value == 1 ? 'Đã sử dụng' : 'Chưa sử dụng'
    },
    onViewDeviceDetail(maThietBi) {
      this.show(this.getDetailDeviceById(maThietBi), 'VIEW')
    },
    onViewCardDetail(cardInfo) {
      // Tìm thiết bị card trong danh sách dựa trên mã thiết bị
      const cardDevice = this.getDetailDeviceById(cardInfo.ma_thiet_bi)
      if (cardDevice) {
        this.show(cardDevice, 'VIEW')
      } else {
        this.$root.toastWarning('Không tìm thấy thông tin chi tiết của card')
      }
    },
    onInsertCard(slotData) {
      // Xử lý logic cắm card vào khe
      console.log('Cắm card vào khe:', slotData)
      this.$root.toastInfo(`Đang cắm card vào khe ${slotData.ma_khe}`)
      // TODO: Implement logic cắm card
    },
    onChangeCard(slotData) {
      // Xử lý logic thay đổi card
      console.log('Thay đổi card trong khe:', slotData)
      this.$root.toastInfo(`Đang thay đổi card trong khe ${slotData.ma_khe}`)
      // TODO: Implement logic thay đổi card
    },
    getDetailDeviceById(maThietBi) {
      return this.$parent.dataSource.find((item) => item.mathietbi === maThietBi)
    }
  }
}
</script>
<style scoped>
.close {
  cursor: pointer !important;
}
</style>
