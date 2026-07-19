<template src="./index.template.html"></template>
<script>
import thietBiApi from '../api/ThietBi.api'
import danhMucApi from '../api/DanhMuc.api'
import {
  ThongTinChung,
  ThongTinLapDat,
  ThongTinKiemKe,
  LichSuThietBi,
  ThongTinKhachHang,
  NghiepVuIdc,
  HeThongDichVu,
  CauHinhPhanCung,
  CongKetNoi,
  VungDuLieu,
  LuuTruTaiNguyen,
  HaTangAoHoa,
  NangLucLuuTru
} from './Components'
import { CUSTOMER_TYPES, NEW_DEFAULT_OBJ, getDeviceTypeFlags, getDeviceUrls } from '../const.js'
import { TAB_INDICES, checkTabVisibility, TAB_CONFIG } from './tabVisibility.js'
import { createTabActions } from './tabActions.js'

export default {
  name: 'ThongTinChiTietThietBi',
  inject: ['deviceTypeId'],
  components: {
    ThongTinChung,
    ThongTinLapDat,
    ThongTinKiemKe,
    LichSuThietBi,
    ThongTinKhachHang,
    NghiepVuIdc,
    CongKetNoi,
    HeThongDichVu,
    CauHinhPhanCung,
    VungDuLieu,
    LuuTruTaiNguyen,
    HaTangAoHoa,
    NangLucLuuTru
  },
  data() {
    return {
      visible: false,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      currentTabIndex: TAB_INDICES.THONG_TIN_CHUNG,
      TAB_INDICES: TAB_INDICES,
      currentItem: {
        id: null,
        // Thông tin tiêu chuẩn hệ thống
        mang_thiet_bi_id: null,
        he_thong_id: null,
        loaithietbi_id: null,
        chung_loai_id: null,
        phan_loai_tb_id: null,
        so_huu_thiet_bi_id: null,
        // Thông tin cơ bản
        mathietbi: '',
        ten: '',
        kyhieu: '',
        mathietbi_infra: '',
        thietbicha_id: null,
        loaikhachhang: 0,
        serial_number: '',
        model: '',
        part_number: '',
        hsm: 0,
        hieu_luc: 1,
        full_depth: 1,
        ghi_chu: '',
        loaitudia_id: null,
        chungloaithietbi_id: 0,
        ip_id: null,
        trang_thai_vh_id: null,
        // Đặc tính thiết bị
        vaitrothietbi_ids: [],
        psu_qty_in: 0,
        psu_qty_out: 0,
        cauhinh: '',
        hangsx_id: null,
        namsx: 0,
        nam_san_xuat: '',
        nhacungcap_id: null,
        cs_dinhdanh: 0,
        cs_thucte: 0,
        hedieuhanh_id: null,
        khong_gian_lap_dat: 0,
        vaitrothietbi_id: null,
        trangthai_sd: 1,
        // ngay_lapdat: moment().format('YYYY-MM-DD'),
        // ngay_baohanh: moment().format('YYYY-MM-DD'),
        // ngay_sudung: moment().format('YYYY-MM-DD'),
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
        ma_da: null,
        ten_da: null,
        // project_name: '',
        so_the_ts: null,
        taisan_id: null,
        ma_vtu: null,
        ngay_kh: null,
        ngay_baohanh: null,
        ngay_lapdat: null,
        ngay_sudung: null,
        email_sdt: '',
        donviqly_id: null,
        phongbanqly_id: null,
        nguoiqly_id: [],

        //Nang Luc Luu Tru
        mem_cache: null,
        flash_cache: null,
        raid_level: null,

        // trước RAID
        tong_hdd_truoc_raid: null,
        tong_ssd_truoc_raid: null,
        tong_nvme_truoc_raid: null,
        tong_iops_truoc_raid: null,
        tong_hdd_tho_truoc_raid: null,

        hdd_kha_nang_cap_truoc_raid: null,
        ssd_kha_nang_cap_truoc_raid: null,
        nvme_kha_nang_cap_truoc_raid: null,

        hdd_da_cap_truoc_raid: null,
        ssd_da_cap_truoc_raid: null,
        nvme_da_cap_truoc_raid: null,

        hdd_con_trong_truoc_raid: null,
        ssd_con_trong_truoc_raid: null,
        nvme_con_trong_truoc_raid: null,

        hdd_phan_tram_truoc_raid: null,
        ssd_phan_tram_truoc_raid: null,
        nvme_phan_tram_truoc_raid: null,

        // sau RAID
        hdd_tong_sau_raid: null,
        ssd_tong_sau_raid: null,
        nvme_tong_sau_raid: null,
        iops_tong_sau_raid: null,
        tong_hdd_tho_sau_raid: null,

        hdd_kha_nang_cap_sau_raid: null,
        ssd_kha_nang_cap_sau_raid: null,
        nvme_kha_nang_cap_sau_raid: null,
        iops_kha_nang_cap_sau_raid: null,

        hdd_da_cap_sau_raid: null,
        ssd_da_cap_sau_raid: null,
        nvme_da_cap_sau_raid: null,
        iops_da_cap_sau_raid: null,

        hdd_con_trong_sau_raid: null,
        ssd_con_trong_sau_raid: null,
        nvme_con_trong_sau_raid: null,
        iops_con_trong_sau_raid: null,

        hdd_phan_tram_sau_raid: null,
        ssd_phan_tram_sau_raid: null,
        nvme_phan_tram_sau_raid: null,
        iops_phan_tram_sau_raid: null,
        // Cổng kết nối
        arr_cong_dulieu: [],
        arr_cong_nguon: [],
        arr_cong_dieukhien: [],
        arr_line_card: [],
        arr_power_card: [],
        arr_control_card: [],
        so_cong: 0,
        dinh_dang_ten: '',
        bat_dau_cong: 0,
        da_su_dung: 0,
        con_trong: 0,
        so_line_card: 0,
        line_card_dinh_dang_ten: '',
        line_card_bat_dau: 0,
        line_card_da_dung: 0,
        so_power_card: 0,
        power_card_dinh_dang_ten: '',
        power_card_bat_dau: 0,
        power_card_da_dung: 0,
        so_control_card: 0,
        control_card_dinh_dang_ten: '',
        control_card_bat_dau: 0,
        control_card_da_dung: 0,
        so_khe_cam: 0,
        khe_cam_dinh_dang_ten: '',
        khe_cam_bat_dau: 0,
        khe_cam_da_dung: 0,
        khe_cam_con_lai: 0
      },
      // Data lists
      deviceTypeList: [],
      customerTypeList: [],
      parentDeviceList: [],
      deviceRelationHistory: [],
      // Controls for each tab
      thongTinChungControls: {
        // Thông tin tiêu chuẩn hệ thống
        select_mang_thiet_bi_id: { enabled: false, invalid: false },
        select_he_thong_id: { enabled: false },
        select_loaithietbi_id: { enabled: false, invalid: false },
        select_chung_loai_id: { enabled: false },
        select_phan_loai_tb_id: { enabled: false, invalid: false },
        select_so_huu_thiet_bi_id: { enabled: false, invalid: false },
        // Thông tin cơ bản
        txt_ten: { enabled: false, invalid: false },
        txt_kyhieu: { enabled: false, invalid: false },
        txt_mathietbi_infra: { enabled: false },
        select_loaikhachhang: { enabled: false },
        txt_serial_number: { enabled: false, invalid: false },
        checkbox_hsm: { enabled: false },
        checkbox_hieu_luc: { enabled: false },
        checkbox_full_depth: { enabled: false },
        txt_ghi_chu: { enabled: false },
        select_loaitudia_id: { enabled: false, invalid: false },
        select_loai_card: { enabled: false, invalid: false },
        select_chungloaithietbi_id: { enabled: false, invalid: false },
        select_ip_id: { enabled: false },
        txt_part_number: { enabled: false, invalid: false },
        txt_model: { enabled: false, invalid: false },
        select_trang_thai_vh_id: { enabled: false, invalid: false },
        select_thietbicha_id: { enabled: false, invalid: false },
        // Đặc tính thiết bị
        select_hangsx_id: { enabled: false, invalid: false },
        txt_nam_san_xuat: { enabled: false },
        txt_cs_dinhdanh: { enabled: false, invalid: false },
        txt_cs_thucte: { enabled: false },
        select_vaitrothietbi_id: { enabled: false },
        select_nhacungcap_id: { enabled: false, invalid: false },
        select_hedieuhanh_id: { enabled: false },
        txt_khong_gian_lap_dat: { enabled: false, invalid: false }
      },
      thongTinLapDatControls: {
        select_donvi_idc_id: { enabled: false, invalid: false },
        select_idc_id: { enabled: false, invalid: false },
        select_building_id: { enabled: false, invalid: false },
        select_floor_id: { enabled: false, invalid: false },
        select_zone_id: { enabled: false, invalid: false },
        select_rack_id: { enabled: false, invalid: false },
        select_up_unit_id: { enabled: false, invalid: false },
        select_down_unit_id: { enabled: false, invalid: false },
        select_rack_pos_front: { enabled: false },
        select_rack_pos_back: { enabled: false },
        select_rack_pos_left: { enabled: false },
        select_rack_pos_right: { enabled: false }
      },
      thongTinKiemKeControls: {
        ma_da: { enabled: false },
        ten_da: { enabled: false },
        so_the_ts: { enabled: false },
        taisan_id: { enabled: false },
        ma_vtu: { enabled: false },
        ngay_kh: { enabled: false },
        ngay_baohanh: { enabled: false },
        ngay_lapdat: { enabled: false },
        ngay_sudung: { enabled: false },
        email_sdt: { enabled: false },
        select_ma_da: { enabled: false },
        select_donviqly_id: { enabled: false },
        select_phongbanqly_id: { enabled: false },
        select_nguoiqly_id: { enabled: false }
      },
      congKetNoiControls: {
        txt_so_cong: { enabled: false },
        txt_dinh_dang_ten: { enabled: false },
        txt_bat_dau_cong: { enabled: false },
        txt_so_line_card: { enabled: false },
        txt_line_card_dinh_dang_ten: { enabled: false },
        txt_line_card_bat_dau: { enabled: false },
        txt_so_power_card: { enabled: false },
        txt_power_card_dinh_dang_ten: { enabled: false },
        txt_power_card_bat_dau: { enabled: false },
        txt_so_control_card: { enabled: false },
        txt_control_card_dinh_dang_ten: { enabled: false },
        txt_control_card_bat_dau: { enabled: false }
      },
      nangLucLuuTruControls: {
        mem_cache: { enabled: false, invalid: false },
        flash_cache: { enabled: false, invalid: false },
        raid_level: { enabled: false, invalid: false },

        tong_hdd_tho_truoc_raid: { enabled: false },
        tong_hdd_truoc_raid: { enabled: false, invalid: false },
        tong_ssd_truoc_raid: { enabled: false, invalid: false },
        tong_nvme_truoc_raid: { enabled: false, invalid: false },
        tong_iops_truoc_raid: { enabled: false, invalid: false },

        hdd_kha_nang_cap_truoc_raid: { enabled: false },
        ssd_kha_nang_cap_truoc_raid: { enabled: false },
        nvme_kha_nang_cap_truoc_raid: { enabled: false },
        iops_kha_nang_cap_truoc_raid: { enabled: false },

        hdd_da_cap_truoc_raid: { enabled: false },
        ssd_da_cap_truoc_raid: { enabled: false },
        nvme_da_cap_truoc_raid: { enabled: false },
        iops_da_cap_truoc_raid: { enabled: false },

        hdd_con_trong_truoc_raid: { enabled: false },
        ssd_con_trong_truoc_raid: { enabled: false },
        nvme_con_trong_truoc_raid: { enabled: false },
        iops_con_trong_truoc_raid: { enabled: false },

        hdd_phan_tram_truoc_raid: { enabled: false },
        ssd_phan_tram_truoc_raid: { enabled: false },
        nvme_phan_tram_truoc_raid: { enabled: false },
        iops_phan_tram_truoc_raid: { enabled: false },

        tong_da_cap: { enabled: false },
        tong_sau_raid: { enabled: false },

        tong_hdd_tho_sau_raid: { enabled: false, invalid: false },
        hdd_tong_sau_raid: { enabled: false, invalid: false },
        ssd_tong_sau_raid: { enabled: false, invalid: false },
        nvme_tong_sau_raid: { enabled: false, invalid: false },
        iops_tong_sau_raid: { enabled: false, invalid: false },

        hdd_kha_nang_cap_sau_raid: { enabled: false, invalid: false },
        ssd_kha_nang_cap_sau_raid: { enabled: false, invalid: false },
        nvme_kha_nang_cap_sau_raid: { enabled: false, invalid: false },
        iops_kha_nang_cap_sau_raid: { enabled: false, invalid: false },

        hdd_da_cap_sau_raid: { enabled: false },
        ssd_da_cap_sau_raid: { enabled: false },
        nvme_da_cap_sau_raid: { enabled: false },
        iops_da_cap_sau_raid: { enabled: false },

        hdd_con_trong_sau_raid: { enabled: false },
        ssd_con_trong_sau_raid: { enabled: false },
        nvme_con_trong_sau_raid: { enabled: false },
        iops_con_trong_sau_raid: { enabled: false },

        hdd_phan_tram_sau_raid: { enabled: false },
        ssd_phan_tram_sau_raid: { enabled: false },
        nvme_phan_tram_sau_raid: { enabled: false },
        iops_phan_tram_sau_raid: { enabled: false }
      },

      deviceType: ''
    }
  },
  computed: {
    getDeviceType() {
      if (!this.deviceTypeId) {
        return null
      }
      const deviceType = this.deviceTypeList.find((item) => item.ID == this.deviceTypeId)
      return deviceType ? deviceType.MA_LOAITBI : null
    },
    getDeviceTypeInfo() {
      const phanLoai = this.getDeviceType
      if (!phanLoai || !this.deviceTypeId) {
        return {
          phanLoai: null,
          ten: null,
          id: null,
          ...getDeviceTypeFlags(null)
        }
      }
      return {
        phanLoai: phanLoai,
        ten: this.getDeviceType,
        id: this.deviceTypeId,
        ...getDeviceTypeFlags(phanLoai)
      }
    },
    isSelectedDeviceType() {
      return !!this.getDeviceType
    },
    saveButtonText() {
      return this.currentMode === 'VIEW' ? 'Ghi lại' : 'Lưu'
    },
    visibleTabs() {
      return TAB_CONFIG.filter((tab) => {
        return this.isTabVisible(tab.id)
      })
    }
  },
  watch: {
    currentMode: function(newVal) {
      this.enableControls(newVal === 'ADD' || newVal === 'EDIT')
      // Notify parent to update actions
      this.$nextTick(() => {
        if (this.$parent && this.$parent.updateDetailActions) {
          this.$parent.updateDetailActions()
        }
      })
    }
  },
  provide() {
    return {
      // Ensure nested components update this component's currentItem
      setCurrentItemValByProp: (prop, value) => {
        if (!this.currentItem) return
        this.$set(this.currentItem, prop, value)
      },
      mode: () => this.currentMode
    }
  },
  methods: {
    isTabVisible(tabIndex) {
      return checkTabVisibility(this.getDeviceTypeInfo, tabIndex)
    },
    show: async function(deviceIdOrItem, deviceTypeCode = null, mode = 'VIEW') {
      try {
        this.$root.showLoading(true)
        if (mode === 'ADD' || !deviceIdOrItem) {
          this.resetCurrentItem()
          this.currentMode = mode
          this.visible = true
          this.currentTabIndex = TAB_INDICES.THONG_TIN_CHUNG
          if (!this.deviceTypeList.length) {
            await this.loadMasterData()
          }
          if (this.deviceTypeId && this.deviceTypeList.length > 0 && mode === 'ADD') {
            const deviceType = this.deviceTypeList.find((item) => item.ID == this.deviceTypeId)
            if (deviceType) {
              this.currentItem.loaithietbi_id = this.deviceTypeId
              const mangThietBiId = deviceType.NHOMTBI_ID ? deviceType.NHOMTBI_ID : null
              if (mangThietBiId !== null) {
                this.currentItem.mang_thiet_bi_id = mangThietBiId
              }
              await this.updateParentDeviceList()
            }
          }
          this.$emit('show-detail')
          this.$nextTick(async () => {
            Promise.all([
              this.$refs.thongTinChung.getHeThongByMangThietBi(this.currentItem.mang_thiet_bi_id),
              this.$refs.thongTinChung.getPhanLoaiTB(this.currentItem.loaithietbi_id)
            ])
            if (this.$parent && this.$parent.updateDetailActions) {
              this.$parent.updateDetailActions()
            }
          })
          return
        }

        const deviceId = typeof deviceIdOrItem === 'object' ? deviceIdOrItem.id : deviceIdOrItem
        this.currentMode = mode
        this.currentTabIndex = TAB_INDICES.THONG_TIN_CHUNG
        this.visible = true

        // Load master data nếu chưa có
        if (!this.deviceTypeList.length) {
          await this.loadMasterData()
        }
        // Load device data by tabs
        await this.loadDeviceDataByTabs(deviceId)

        // load danh mục cần item data
        if(this.$refs.thongTinLapDat) {
          await this.$refs.thongTinLapDat.loadData()
        }
        if(this.$refs.thongTinKiemKe) {
          await this.$refs.thongTinKiemKe.loadDanhMuc()
        }
        if(this.$refs.cauHinhPhanCung) {
          await this.$refs.cauHinhPhanCung.loadData()
        }	
        this.$emit('show-detail')

        this.$nextTick(() => {
          if (this.$parent && this.$parent.updateDetailActions) {
            this.$parent.updateDetailActions()
          }
        })
      } catch (error) {
        console.error('Error loading device detail:', error)
        this.$root.toastError('Có lỗi xảy ra khi tải chi tiết thiết bị')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadDeviceDataByTabs(deviceId) {
      if (!deviceId) return

      try {
        const enabledTabs = this.getEnabledTabs()
        const apiPromises = enabledTabs.map((tab) => {
          return tab.fetchFn?.(deviceId)
        })
        const response = await Promise.all(apiPromises)
        response.forEach((item, index) => {
          const tab = enabledTabs[index]
          if (!item) return
          console.log(tab, 'tab')
          if (tab.type === 'object') {
            Object.assign(this.currentItem, item)
          }

          if (tab.type === 'array') {
            item.forEach((i) => {
              Object.assign(this.currentItem, i || {})
            })
          }
        })
        this.deviceRelationHistory = await thietBiApi.getLichSuTacDong(this.$root.context, this.currentItem.id)

        // Chỉ update parent device list khi EDIT/ADD mode (cần để chọn thiết bị cha)
        if (this.currentMode === 'EDIT' || this.currentMode === 'ADD') {
          await this.updateParentDeviceList()
        }
      } catch (error) {
        console.error('Error loading device data by tabs:', error)
        throw error
      } finally {
        this.$root.showLoading(false)
      }
    },
    resetCurrentItem() {
      this.currentItem = { ...NEW_DEFAULT_OBJ }
      this.deviceRelationHistory = []
    },
    setMode(mode) {
      this.currentMode = mode
      this.$nextTick(() => {
        if (this.$parent && this.$parent.updateDetailActions) {
          this.$parent.updateDetailActions()
        }
      })
    },
    onSave() {
      if (this.validateControls()) {
        this.handleSaveDevice()
      }
    },
    async handleSaveDevice() {
      try {
        this.$root.showLoading(true)
        const enabledTabs = this.getEnabledTabs()
        
        // Tách tab thông tin chung ra để lưu trước
        const thongTinChungTab = enabledTabs.find(
          (tab) => tab.id === TAB_INDICES.THONG_TIN_CHUNG
        )
        const otherTabs = enabledTabs.filter(
          (tab) => tab.id !== TAB_INDICES.THONG_TIN_CHUNG
        )
        if (thongTinChungTab && thongTinChungTab.saveFn) {
          try {
            const response = await thongTinChungTab.saveFn()
            let newId = response ? response : null
            if (newId && (!this.currentItem.id || this.currentItem.id !== newId)) {
              this.$set(this.currentItem, 'id', newId)
              console.log('Đã cập nhật ID từ thông tin chung:', newId)
            }
          } catch (error) {
            console.error(`Error saving tab ${thongTinChungTab.label}:`, error)
            throw error
          }
        }
        if (otherTabs.length > 0) {
          const apiPromises = otherTabs.map((tab) => {
            try {
              return tab.saveFn?.()
            } catch (error) {
              console.error(`Error saving tab ${tab.label}:`, error)
              throw new Error(`${tab.label}: ${error.message}`)
            }
          })

          await Promise.all(apiPromises)
        }

        this.setMode('VIEW')
        this.$root.toastSuccess('Lưu thiết bị thành công')

        // Chuyển route đến trang chi tiết với ID vừa lưu
        if (this.currentItem.id && this.deviceTypeId) {
          const deviceUrls = getDeviceUrls(this.deviceTypeId)
          if (deviceUrls && deviceUrls.detailUrl) {
            this.$router.push({
              path: deviceUrls.detailUrl,
              query: { id: this.currentItem.id }
            })
          }
        }

        this.$nextTick(() => {
          if (this.$parent && this.$parent.updateDetailActions) {
            this.$parent.updateDetailActions()
          }
        })
      } catch (error) {
        console.error('Error saving device:', error)
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lưu thiết bị')
      } finally {
        await this.loadDeviceDataByTabs(this.currentItem.id)
        this.$root.showLoading(false)
      }
    },
    enableControls(enabled) {
      // Enable/Disable cho form thông tin chung
      Object.keys(this.thongTinChungControls).forEach((key) => {
        if (this.thongTinChungControls[key].hasOwnProperty('enabled')) {
          if (key === 'select_mang_thiet_bi_id' || key === 'select_loaithietbi_id') {
            this.thongTinChungControls[key].enabled = false
          } else {
            this.thongTinChungControls[key].enabled = enabled
          }
        }
      })
      // Mặc định disable thông tin lắp đặt
      Object.keys(this.thongTinLapDatControls).forEach((key) => {
        if (this.thongTinLapDatControls[key].hasOwnProperty('enabled')) {
          this.thongTinLapDatControls[key].enabled = enabled
        }
      })
      // Enable/Disable cho form thông tin quản lý
      Object.keys(this.thongTinKiemKeControls).forEach((key) => {
        if (this.thongTinKiemKeControls[key].hasOwnProperty('enabled')) {
          this.thongTinKiemKeControls[key].enabled = enabled
        }
      })

      // Enable/Disable cho form năng lực lưu trữ
      Object.keys(this.nangLucLuuTruControls).forEach((key) => {
        if (this.nangLucLuuTruControls[key].hasOwnProperty('enabled')) {
          this.nangLucLuuTruControls[key].enabled = enabled
        }
      })
    },
    resetValidation() {
      // Reset validation cho thông tin chung
      if (this.$refs.thongTinChung && this.$refs.thongTinChung.resetValidation) {
        this.$refs.thongTinChung.resetValidation()
      }
    },
    getEnabledTabs() {
      const allTabs = createTabActions({
        $refs: this.$refs,
        currentItem: this.currentItem,
        $root: this.$root
      })
      return allTabs.filter((tab) => this.isTabVisible(tab.id))
    },
    validateControls() {
      const enabledTabs = this.getEnabledTabs()
      let isValid = true

      for (const tab of enabledTabs) {
        const errorMsg = tab.validateFn?.()
        if (errorMsg) {
          const tabLabel = TAB_CONFIG.find((t) => t.id === tab.id)?.label || tab.label
          this.$toast.error(tabLabel.toUpperCase() + ': \n' + errorMsg)
          isValid = false
        }
      }

      return isValid
    },
    async loadMasterData() {
      try {
        await this.loadDeviceType()
        this.customerTypeList = CUSTOMER_TYPES
        if (this.$refs.thongTinChung) {
          await this.$refs.thongTinChung.loadMasterData()
        }
      } catch (error) {
        console.error('Error loading master data:', error)
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu danh mục')
      }
    },
    // Event handlers for component events
    onEquipmentCategoryChanged(chungLoaiId) {
      if (chungLoaiId) {
        const chungLoaiList = this.$refs.thongTinChung?.chungLoaiList || []
        const chungloai = chungLoaiList.find((item) => item.ID == Number(chungLoaiId))
        if (chungloai) {
          this.currentItem.hangsx_id = chungloai.HANGSANXUAT_ID
          this.currentItem.cs_dinhdanh = chungloai.CS_DINHDANH
        }
      }
    },
    async updateParentDeviceList() {
      // Chế độ xem chỉ đọc → không cần danh sách thiết bị cha
      if (this.currentMode === 'VIEW') {
        this.parentDeviceList = []
        return
      }

      const deviceTypeInfo = this.getDeviceTypeInfo || {}

      // Xác định nhóm thiết bị cha cần lấy danh mục
      let parentGroup = null

      // Card mạng: line/power/console card hoặc thiết bị mạng
      if (
        deviceTypeInfo.isLinecard ||
        deviceTypeInfo.isPowercard ||
        deviceTypeInfo.isConsolecard ||
        deviceTypeInfo.isNetwork
      ) {
        parentGroup = 'NETwork'
      }

      // Khay đĩa, bộ điều khiển thuộc nhóm tủ đĩa
      if (deviceTypeInfo.isKhayDia || deviceTypeInfo.isBoDieuKhien) {
        parentGroup = 'TUDIA'
      }

      // Không xác định được nhóm → không gọi API
      if (!parentGroup) {
        this.parentDeviceList = []
        return
      }

      try {
        const response = await danhMucApi.getParentDeviceList(this.$root.context, parentGroup)
        this.parentDeviceList = response || []
      } catch (error) {
        console.error('Error loading Parent Device list:', error)
        this.parentDeviceList = []
      }
    },
    onChangeTab: function(index) {
      this.currentTabIndex = index
    },
    async loadDeviceType() {
      try {
        const response = await danhMucApi.getDeviceType(this.$root.context)
        this.deviceTypeList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại thiết bị')
      }
    }
  }
}
</script>
<style scoped>
.close {
  cursor: pointer !important;
}
</style>
