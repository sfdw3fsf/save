<template src="./index.template.html"></template>

<script>
import { validationMixin, ValidationRulesFactory } from '../../validation.js'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import danhMucApi from '../../../api/DanhMuc.api'
import api from './api.js'
export default {
  name: 'ThongTinLapDat',
  mixins: [validationMixin],
  components: {
    BssRequiredMarker
  },
  props: {
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
        isPowerCard: false,
        isConsoleCard: false
      })
    }
  },
  data() {
    return {
      viTriLapDat: {
        donViIDCId: null,
        idcId: null,
        toaNhaId: null,
        matSanId: null,
        phongId: null,
        rackId: null,
        uTrenId: null,
        uTren: null,
        uDuoiId: null,
        uDuoi: null,
        matTruoc: null,
        matSau: null,
        canhTrai: null,
        canhPhai: null
      },
      donViIDCList: [],
      IDCList: [],
      IDCBuildingList: [],
      IDCFloorList: [],
      IDCZoneList: [],
      rackList: [],
      UList: [],
      UpperUList: [],
      LowerUList: [],
      viTriTuDiaList: [],
      validationRules: !this.deviceTypeInfo.isTuDia ? {
        select_donvi_idc_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn đơn vị IDC'),
        select_idc_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn IDC'),
        select_building_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn toà nhà'),
        select_floor_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn mặt sàn'),
        select_zone_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn phòng/zone'),
        select_rack_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn rack'),
        select_up_unit_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn U trên'),
        select_down_unit_id: ValidationRulesFactory.createSelectRule('Vui lòng chọn U dưới')
      } : {}
    }
  },
  inject: ['mode'],
  methods: {
    async updateViTriLapDat() {
      try {
        if(this.deviceTypeInfo.isTuDia) return
        const check = this.validateViTriLapDatForm()
        if (!check.valid) {
          this.$root.toastError(check.message)
          return
        }
        const payload = {
          toaNhaId: this.viTriLapDat.toaNhaId,
          matSanId: this.viTriLapDat.matSanId,
          phongId: this.viTriLapDat.phongId,
          rackId: this.viTriLapDat.rackId,
          uTrenId: this.viTriLapDat.uTrenId,
          uDuoiId: this.viTriLapDat.uDuoiId,
          matTruoc: this.viTriLapDat.matTruoc ? true : false,
          matSau: this.viTriLapDat.matSau ? true : false,
          canhTrai: this.viTriLapDat.canhTrai ? true : false,
          canhPhai: this.viTriLapDat.canhPhai ? true : false
        }
        await api.updateViTriLapDatThietBi(this.$root.context, this.currentItem.id, payload)
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi cập nhật vị trí lắp đặt thiết bị')
      }
    },
    validateViTriLapDatForm() {
      const pos = this.viTriLapDat

      // Không được bỏ trống cả 2 phía trước – sau
      if (!pos.matTruoc && !pos.matSau) {
        return {
          valid: false,
          message: 'Lỗi khi cập nhật vị trí lắp đặt : Phải chọn ít nhất một: Mặt trước hoặc Mặt sau.'
        }
      }

      // Không được bỏ trống cả 2 cạnh trái – phải
      if (!pos.canhTrai && !pos.canhPhai) {
        return {
          valid: false,
          message: 'Lỗi khi cập nhật vị trí lắp đặt : Phải chọn ít nhất một: Bên trái hoặc Bên phải.'
        }
      }

      return { valid: true }
    },
    async loadDanhMuc() {
      await this.getDonViIDC()
      await this.getIDC(this.viTriLapDat.donViIDCId)
      await this.getIDCBuilding(this.viTriLapDat.idcId)
      await this.getIDCFloor(this.viTriLapDat.toaNhaId)
      await this.getIDCZone(this.viTriLapDat.matSanId)
      await this.getIDCRack(this.viTriLapDat.phongId)
      await this.getRackUnit(this.viTriLapDat.rackId)
    },
    async loadData() {
      if (!this.currentItem.id) {
        await this.getDonViIDC()
        return
      }
      if (this.deviceTypeInfo && !this.deviceTypeInfo.isTuDia) {
        await this.loadViTriLapDat()
        await this.loadDanhMuc()
      } else {
        await this.loadViTriTuDia()
      }
    },
    async loadViTriTuDia() {
      try {
        const response = await api.getDsViTriLapDatTuDia(this.$root.context, this.currentItem.id)
        this.viTriTuDiaList = response.data || []
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu vị trí lắp đặt ổ đĩa')
      }
    },
    async loadViTriLapDat() {
      try {
        const response = await api.getViTriLapDatThietBi(this.$root.context, this.currentItem.id)
        this.viTriLapDat = response.data || {}
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu đơn vị IDC')
      }
    },
    async getDonViIDC() {
      try {
        const response = await danhMucApi.getDonViIDC(this.$root.context)
        this.donViIDCList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu đơn vị IDC')
      }
    },
    async getIDC(donViId = null) {
      if (!donViId) {
        this.IDCList = []
        return
      }
      try {
        const response = await danhMucApi.getIDCByDonVi(this.$root.context, donViId)
        this.IDCList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu IDC')
      }
    },
    async getIDCBuilding(idcID = null) {
      if (!idcID) {
        this.IDCBuildingList = []
        return
      }
      try {
        const response = await danhMucApi.getIDCBuilding(this.$root.context, idcID)
        this.IDCBuildingList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu toà nhà IDC')
      }
    },
    async getIDCFloor(idcBuildingID = null) {
      if (!idcBuildingID) {
        this.IDCFloorList = []
        return
      }
      try {
        const response = await danhMucApi.getIDCFloor(this.$root.context, idcBuildingID)
        this.IDCFloorList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu tầng IDC')
      }
    },
    async getIDCZone(idcFloorID = null) {
      if (!idcFloorID) {
        this.IDCZoneList = []
        return
      }
      try {
        const response = await danhMucApi.getIDCZone(this.$root.context, idcFloorID)
        this.IDCZoneList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu vùng IDC')
      }
    },
    async getIDCRack(idcZoneID = null) {
      if (!idcZoneID) {
        this.rackList = []
        return
      }
      try {
        const response = await danhMucApi.getIDCRack(this.$root.context, idcZoneID)
        this.rackList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu rack IDC')
      }
    },
    async getRackUnit(rackID = null) {
      if (!rackID) {
        this.UList = []
        this.UpperUList = []
        this.LowerUList = []
        return
      }
      try {
        const response = await danhMucApi.getRackUnitByRackId(this.$root.context, rackID)
        this.UList = response || []
        this.UpperUList = response || []
        this.LowerUList = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu U rack IDC')
      }
    },
    onDonViIDCChange() {
      this.viTriLapDat.idcId = null
      this.viTriLapDat.toaNhaId = null
      this.viTriLapDat.matSanId = null
      this.viTriLapDat.phongId = null
      this.viTriLapDat.rackId = null
      this.viTriLapDat.uTrenId = null
      this.viTriLapDat.uDuoiId = null
      this.IDCList = []
      this.IDCBuildingList = []
      this.IDCFloorList = []
      this.IDCZoneList = []
      this.rackList = []
      this.UList = []
      this.UpperUList = []
      this.LowerUList = []
      this.getIDC(this.viTriLapDat.donViIDCId)
    },
    onIDCChange() {
      this.viTriLapDat.toaNhaId = null
      this.viTriLapDat.matSanId = null
      this.viTriLapDat.phongId = null
      this.viTriLapDat.rackId = null
      this.viTriLapDat.uTrenId = null
      this.viTriLapDat.uDuoiId = null
      this.IDCBuildingList = []
      this.IDCFloorList = []
      this.IDCZoneList = []
      this.rackList = []
      this.UList = []
      this.UpperUList = []
      this.LowerUList = []
      this.getIDCBuilding(this.viTriLapDat.idcId)
    },
    onBuildingChange() {
      this.viTriLapDat.matSanId = null
      this.viTriLapDat.phongId = null
      this.viTriLapDat.rackId = null
      this.viTriLapDat.uTrenId = null
      this.viTriLapDat.uDuoiId = null
      this.IDCFloorList = []
      this.IDCZoneList = []
      this.rackList = []
      this.UList = []
      this.UpperUList = []
      this.LowerUList = []
      this.getIDCFloor(this.viTriLapDat.toaNhaId)
    },
    onFloorChange() {
      this.viTriLapDat.phongId = null
      this.viTriLapDat.rackId = null
      this.viTriLapDat.uTrenId = null
      this.viTriLapDat.uDuoiId = null
      this.IDCZoneList = []
      this.rackList = []
      this.UList = []
      this.UpperUList = []
      this.LowerUList = []
      this.getIDCZone(this.viTriLapDat.matSanId)
    },
    onZoneChange() {
      this.viTriLapDat.rackId = null
      this.viTriLapDat.uTrenId = null
      this.viTriLapDat.uDuoiId = null
      this.rackList = []
      this.UList = []
      this.UpperUList = []
      this.LowerUList = []
      this.getIDCRack(this.viTriLapDat.phongId)
    },
    onRackChange() {
      this.viTriLapDat.uTrenId = null
      this.viTriLapDat.uDuoiId = null
      this.UList = []
      this.UpperUList = []
      this.LowerUList = []
      this.getRackUnit(this.viTriLapDat.rackId)
    },
    onUTrenChange() {
      const start = this.viTriLapDat.uTrenId
      // Lấy object U trên để biết VITRI
      const startObj = this.UList.find((u) => u.ID == start)
      if (!startObj) return
      // Filter lại danh sách U dưới theo VITRI
      this.LowerUList = this.UList.filter((u) => u.VITRI >= startObj.VITRI)
      // Nếu U dưới hiện tại < U trên → chọn lại
      const currentDownObj = this.UList.find((u) => u.ID == this.viTriLapDat.uDuoiId)
      if (!currentDownObj || currentDownObj.VITRI <= startObj.VITRI) {
        // Tìm U dưới đầu tiên hợp lệ
        this.viTriLapDat.uDuoiId = null
      }
    },
    onUDuoiChange() {
      const end = this.viTriLapDat.uDuoiId

      // Lấy object U dưới
      const endObj = this.UList.find((u) => u.ID == end)
      if (!endObj) return

      // Filter lại danh sách U trên theo VITRI
      this.UpperUList = this.UList.filter((u) => u.VITRI <= endObj.VITRI)

      // Nếu U trên > U dưới → chon lai
      const currentUpObj = this.UList.find((u) => u.ID == this.viTriLapDat.uTrenId)
      if (!currentUpObj || currentUpObj.VITRI > endObj.VITRI) {
        // Lấy U trên hợp lệ cuối cùng
        this.viTriLapDat.uTrenId = null
      }
    },
    getValueForControl(controlKey) {
      const valueMap = {
        select_donvi_idc_id: this.viTriLapDat.donViIDCId,
        select_idc_id: this.viTriLapDat.idcId,
        select_building_id: this.viTriLapDat.toaNhaId,
        select_floor_id: this.viTriLapDat.matSanId,
        select_zone_id: this.viTriLapDat.phongId,
        select_rack_id: this.viTriLapDat.rackId,
        select_up_unit_id: this.viTriLapDat.uTrenId,
        select_down_unit_id: this.viTriLapDat.uDuoiId,
        select_rack_pos_front: this.viTriLapDat.matTruoc,
        select_rack_pos_back: this.viTriLapDat.matSau,
        select_rack_pos_left: this.viTriLapDat.canhTrai,
        select_rack_pos_right: this.viTriLapDat.canhPhai
      }
      return valueMap[controlKey]
    },
    onLapDatRack() {
      //to do
    },
    onXemTrenSoDoRack() {
      //to do
    },
    templateViTriMatRack(parent) {
      const self = this
      return function() {
        return {
          template: {
            template: `
            <div style="
              display: grid;
              grid-template-columns: repeat(2, 1fr);
              gap: 6px;
              width: 150px;
              align-items: center;
            ">
              <label style="display: flex; align-items: center; gap: 4px;">
                <input 
                  type="checkbox"
                  :checked="data.matTruoc"
                  :disabled="!data.controls?.select_rack_pos_front?.enabled"
                /> Mặt trước
              </label>

              <label style="display: flex; align-items: center; gap: 4px;">
                <input 
                  type="checkbox"
                  :checked="data.matSau"
                  :disabled="!data.controls?.select_rack_pos_back?.enabled"
                /> Mặt sau
              </label>

              <label style="display: flex; align-items: center; gap: 4px;">
                <input 
                  type="checkbox"
                  :checked="data.canhTrai"
                  :disabled="!data.controls?.select_rack_pos_left?.enabled"
                /> Bên trái
              </label>

              <label style="display: flex; align-items: center; gap: 4px;">
                <input 
                  type="checkbox"
                  :checked="data.canhPhai"
                  :disabled="!data.controls?.select_rack_pos_right?.enabled"
                /> Bên phải
              </label>
            </div>
        `
          }
        }
      }
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
