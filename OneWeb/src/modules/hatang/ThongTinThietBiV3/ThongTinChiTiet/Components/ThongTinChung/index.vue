<template src="./index.template.html"></template>
<script>
import { validationMixin, ValidationRulesFactory } from '../../validation.js'
import danhMucApi from '../../../api/DanhMuc.api'
import { CUSTOMER_TYPES, SO_HUU_THIET_BI_LIST, PORT_TYPE } from '../../../const.js'
import { FIELD_VISIBILITY_CONFIG, checkFieldVisibility } from './fieldVisibility.js'
import { isFieldRequired } from './fieldValidation.js'

export default {
  name: 'ThongTinChung',
  mixins: [validationMixin],
  props: {
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
    deviceTypeList: {
      type: Array,
      default: () => []
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
  computed: {
    currentYear() {
      return new Date().getFullYear()
    }
  },
  data() {
    const selectValidator = (value) => !!value
    const textValidator = (value) => value && value.trim() !== ''
    const positiveIntegerValidator = (value) => {
      if (value === null || value === undefined || value === '') return false
      const num = Number(value)
      return Number.isInteger(num) && num > 0
    }
    const buildCondition = (controlKey, extraCondition) => () => {
      if (!this.isControlRequired(controlKey)) return false
      if (typeof extraCondition === 'function') {
        return extraCondition()
      }
      return true
    }

    return {
      // Danh mục cần gọi API
      mangThietBiList: [],
      heThongList: [],
      chungLoaiList: [],
      phanLoaiTBList: [],
      soHuuThietBiList: SO_HUU_THIET_BI_LIST,
      trangThaiVHList: [],
      // Danh mục gọi API nội bộ
      customerTypeList: [],
      manufacturerList: [],
      supplierList: [],
      deviceRoleList: [],
      OSList: [],
      IPAddressList: [],
      discTypeList: [],
      cardTypeList: [
        { ID: PORT_TYPE.INTERFACE.ID, TEN: PORT_TYPE.INTERFACE.NAME },
        { ID: PORT_TYPE.POWER.ID, TEN: PORT_TYPE.POWER.NAME },
        { ID: PORT_TYPE.CONSOLE.ID, TEN: PORT_TYPE.CONSOLE.NAME }
      ],
      validationRules: {
        select_mang_thiet_bi_id: ValidationRulesFactory.createConditionalRule(
          'Mảng thiết bị không được để trống',
          buildCondition('select_mang_thiet_bi_id'),
          selectValidator
        ),
        select_loaithietbi_id: ValidationRulesFactory.createConditionalRule(
          'Loại thiết bị không được để trống',
          buildCondition('select_loaithietbi_id'),
          selectValidator
        ),
        select_phan_loai_tb_id: ValidationRulesFactory.createConditionalRule(
          'Phân loại TB không được để trống',
          buildCondition('select_phan_loai_tb_id'),
          selectValidator
        ),
        select_chung_loai_id: ValidationRulesFactory.createConditionalRule(
          'Chủng loại thiết bị không được để trống',
          buildCondition('select_chung_loai_id'),
          selectValidator
        ),
        select_so_huu_thiet_bi_id: ValidationRulesFactory.createConditionalRule(
          'Sở hữu thiết bị không được để trống',
          buildCondition('select_so_huu_thiet_bi_id'),
          selectValidator
        ),
        txt_ten: ValidationRulesFactory.createConditionalRule(
          'Tên thiết bị không được để trống',
          buildCondition('txt_ten'),
          textValidator
        ),
        txt_kyhieu: ValidationRulesFactory.createConditionalRule(
          'Ký hiệu TB không được để trống',
          buildCondition('txt_kyhieu'),
          textValidator
        ),
        txt_serial_number: ValidationRulesFactory.createConditionalRule(
          'Serial number không được để trống',
          buildCondition('txt_serial_number'),
          textValidator
        ),
        select_trang_thai_vh_id: ValidationRulesFactory.createConditionalRule(
          'Trạng thái VH không được để trống',
          buildCondition('select_trang_thai_vh_id'),
          selectValidator
        ),
        select_hangsx_id: ValidationRulesFactory.createConditionalRule(
          'Hãng sản xuất không được để trống',
          buildCondition('select_hangsx_id'),
          selectValidator
        ),
        txt_cs_dinhdanh: ValidationRulesFactory.createConditionalRule(
          'Công suất định danh phải là số nguyên dương',
          buildCondition('txt_cs_dinhdanh', () => this.isFieldVisible('cs_dinhdanh')),
          positiveIntegerValidator
        ),
        select_nhacungcap_id: ValidationRulesFactory.createConditionalRule(
          'Nhà cung cấp không được để trống',
          buildCondition('select_nhacungcap_id'),
          selectValidator
        ),
        txt_khong_gian_lap_dat: ValidationRulesFactory.createConditionalRule(
          'Không gian lắp đặt phải là số nguyên dương',
          buildCondition('txt_khong_gian_lap_dat', () => this.isFieldVisible('khong_gian_lap_dat')),
          positiveIntegerValidator
        ),
        select_thietbicha_id: ValidationRulesFactory.createConditionalRule(
          'Thiết bị cha không được để trống',
          buildCondition('select_thietbicha_id', () => this.isFieldVisible('thietbicha_id')),
          selectValidator
        ),
        select_loaitudia_id: ValidationRulesFactory.createConditionalRule(
          'Loại tủ đĩa không được để trống',
          buildCondition('select_loaitudia_id', () => this.isFieldVisible('loaitudia_id')),
          selectValidator
        ),
        select_loai_card: ValidationRulesFactory.createConditionalRule(
          'Loại card không được để trống',
          () => this.isFieldVisible('loai_card'),
          (value) => !!value
        )
      }
    }
  },
  async created() {
    // await this.loadMasterData()
  },
  methods: {
    isControlRequired(controlKey) {
      return isFieldRequired(controlKey, this.deviceTypeInfo)
    },
    async loadMasterData() {
      try {
        await Promise.all([
          this.getMangThietBi(),
          this.getChungLoai(),
          this.getTrangThaiVH(),
          this.getCustomerTypeList(),
          this.getManufacturerList(),
          this.getSupplierList(),
          this.getDeviceRoleList(),
          this.getOSList(),
          this.getIPAddressList(),
          this.getLoaiTuDiaList()
        ])
      } catch (error) {
        console.error('Error loading master data:', error)
      }
    },
    // TODO: Gọi API lấy danh mục Mảng thiết bị
    async getMangThietBi() {
      try {
        const response = await danhMucApi.getMangThietBi(this.$root.context)
        this.mangThietBiList = response || []
      } catch (error) {
        console.error('Error loading Mảng thiết bị:', error)
      }
    },
    // Gọi API lấy danh mục Chủng loại
    async getChungLoai() {
      try {
        const response = await danhMucApi.getChungLoaiThietBi(this.$root.context)
        this.chungLoaiList = response || []
      } catch (error) {
        console.error('Error loading Chủng loại:', error)
      }
    },
    async getTrangThaiVH() {
      try {
        const response = await danhMucApi.getTrangThaiVH(this.$root.context)
        this.trangThaiVHList = response || []
      } catch (error) {
        console.error('Error loading Trạng thái VH:', error)
      }
    },
    // Gọi API lấy danh mục Loại khách hàng
    async getCustomerTypeList() {
      try {
        this.customerTypeList = CUSTOMER_TYPES || []
      } catch (error) {
        console.error('Error loading Loại khách hàng:', error)
        this.customerTypeList = []
      }
    },
    // Gọi API lấy danh mục Hãng sản xuất
    async getManufacturerList() {
      try {
        const response = await danhMucApi.getManufacturerList(this.$root.context)
        this.manufacturerList = response || []
      } catch (error) {
        console.error('Error loading Hãng sản xuất:', error)
      }
    },
    // Gọi API lấy danh mục Nhà cung cấp
    async getSupplierList() {
      try {
        const response = await danhMucApi.getSupplierList(this.$root.context)
        this.supplierList = response || []
      } catch (error) {
        console.error('Error loading Nhà cung cấp:', error)
      }
    },
    // Gọi API lấy danh mục Vai trò thiết bị
    async getDeviceRoleList() {
      try {
        const response = await danhMucApi.getDeviceRoleList(this.$root.context)
        this.deviceRoleList = response || []
      } catch (error) {
        console.error('Error loading Vai trò thiết bị:', error)
      }
    },
    // Gọi API lấy danh mục Hệ điều hành
    async getOSList() {
      try {
        const response = await danhMucApi.getOSList(this.$root.context)
        this.OSList = response || []
      } catch (error) {
        console.error('Error loading Hệ điều hành:', error)
      }
    },
    // Gọi API lấy danh sách IP Address
    async getIPAddressList() {
      try {
        const rs = await this.$root.context.get('/web-ecms/thong-tin-thiet-bi/0/thiet-bi-ip')
        this.IPAddressList = (rs && rs.data ? rs.data : []).map((item) => ({
          ID: item.ID,
          IP: item.IP
        }))
      } catch (error) {
        console.error('Error loading IP Address:', error)
        this.IPAddressList = []
      }
    },
    // Gọi API lấy danh mục Loại tủ đĩa
    async getLoaiTuDiaList() {
      try {
        const response = await danhMucApi.getLoaiTuDiaList(this.$root.context)
        this.discTypeList = response || []
      } catch (error) {
        console.error('Error loading Loại tủ đĩa:', error)
        this.discTypeList = []
      }
    },
    // Gọi API lấy danh mục Hệ thống theo Mảng thiết bị
    async getHeThongByMangThietBi(mangThietBiId) {
      try {
        if (!mangThietBiId) {
          this.heThongList = []
          return
        }
        const response = await danhMucApi.getHeThongByMangThietBi(this.$root.context, mangThietBiId)
        this.heThongList = response || []
      } catch (error) {
        console.error('Error loading Hệ thống:', error)
        this.heThongList = []
      }
    },
    // Gọi API lấy danh mục Phân loại TB theo Loại thiết bị
    async getPhanLoaiTB(loaiThietBiId) {
      try {
        if (!loaiThietBiId) {
          this.phanLoaiTBList = []
          return
        }
        const response = await danhMucApi.getPhanLoaiTB(this.$root.context, loaiThietBiId)
        this.phanLoaiTBList = response || []
      } catch (error) {
        console.error('Error loading Phân loại TB:', error)
        this.phanLoaiTBList = []
      }
    },
    getValueForControl(controlKey) {
      const valueMap = {
        select_mang_thiet_bi_id: this.currentItem.mang_thiet_bi_id,
        select_loaithietbi_id: this.currentItem.loaithietbi_id,
        select_phan_loai_tb_id: this.currentItem.phan_loai_tb_id,
        select_so_huu_thiet_bi_id: this.currentItem.so_huu_thiet_bi_id,
        txt_ten: this.currentItem.ten,
        txt_kyhieu: this.currentItem.kyhieu,
        txt_serial_number: this.currentItem.serial_number,
        select_trang_thai_vh_id: this.currentItem.trang_thai_vh_id,
        select_hangsx_id: this.currentItem.hangsx_id,
        txt_cs_dinhdanh: this.currentItem.cs_dinhdanh,
        select_nhacungcap_id: this.currentItem.nhacungcap_id,
        txt_khong_gian_lap_dat: this.currentItem.khong_gian_lap_dat,
        select_thietbicha_id: this.currentItem.thietbicha_id,
        select_loaitudia_id: this.currentItem.loaitudia_id,
        select_loai_card: this.currentItem.loai_card
      }

      return valueMap[controlKey]
    },
    normalizer(node) {
      return {
        id: node.ID,
        label: node.IP
      }
    },
    isFieldVisible(fieldKey) {
      const condition = FIELD_VISIBILITY_CONFIG[fieldKey]
      return checkFieldVisibility(this.deviceTypeInfo, condition)
    },
    buildUpsertThongTinChungPayload(currentItem) {
      let isInterface = 0
      let isPower = 0
      let isConsole = 0

      if (currentItem.loai_card) {
        if (currentItem.loai_card === PORT_TYPE.INTERFACE.ID) {
          isInterface = 1
        } else if (currentItem.loai_card === PORT_TYPE.POWER.ID) {
          isPower = 1
        } else if (currentItem.loai_card === PORT_TYPE.CONSOLE.ID) {
          isConsole = 1
        }
      }

      // Build payload theo schema Java (snake_case để khớp với @JsonProperty)
      const payload = {
        id: currentItem.id || null,
        mangtbi_id: currentItem.mang_thiet_bi_id || null,
        hethongtbi_id: currentItem.he_thong_id || null,
        loaithietbi_id: currentItem.loaithietbi_id || null,
        chungloaithietbi_id: currentItem.chung_loai_id || null,
        phanloaitb_id: currentItem.phan_loai_tb_id || null,
        thietbicha_id: currentItem.thietbicha_id || null,
        chuquan_id: currentItem.so_huu_thiet_bi_id || null,
        mathietbi: currentItem.mathietbi || null,
        mathietbi_infra: currentItem.mathietbi_infra || null,
        ten: currentItem.ten || null,
        ip_quantri: currentItem.ip_id || null,
        kyhieu: currentItem.kyhieu || null,
        serial_number: currentItem.serial_number || null,
        model: currentItem.model || null,
        part_number: currentItem.part_number || null,
        trangthaivh_id: currentItem.trang_thai_vh_id || null,
        loaitudia_id: currentItem.loaitudia_id || null,
        thietbi_idc: currentItem.thietbi_idc || null,
        hieu_luc: currentItem.hieu_luc !== undefined ? currentItem.hieu_luc : 1,
        full_depth: currentItem.full_depth !== undefined ? currentItem.full_depth : 0,
        ghi_chu: currentItem.ghi_chu || null,
        hangsx_id: currentItem.hangsx_id || null,
        namsx: currentItem.nam_san_xuat || null,
        cs_dinhdanh: currentItem.cs_dinhdanh || 0,
        vaitrothietbi_id: currentItem.vaitrothietbi_id || null,
        nhacc_id: currentItem.nhacungcap_id || null,
        cs_thucte: currentItem.cs_thucte || 0,
        hedieuhanh_id: currentItem.hedieuhanh_id || null,
        sl_rackunit: currentItem.khong_gian_lap_dat || 0,
        is_interface: isInterface,
        is_power: isPower,
        is_console: isConsole
      }

      return payload
    }
  }
}
</script>
<style scoped>
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
.w200 {
  width: 180px !important;
}
</style>
