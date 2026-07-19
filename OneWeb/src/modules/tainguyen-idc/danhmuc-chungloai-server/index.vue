<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import api from './api'
import { buildController, rules as R, createControls } from '../danhmuc-idc/controls'

export default {
  name: 'DanhMucChungLoaiServer',
  data() {
    return {
      tab_index: 1,
      listHangSanXuat: [],
      listThongTinModule: [],
      listMucDichSuDung: [],
      listChuanDauNoi: [],
      dataItems: [],
      selectedRows: [],
      newBtn: true,
      saveBtn: false,
      editBtn: true,
      cancelBtn: false,
      deleteBtn: true,
      searchParams: {
        modelThietBi: '',
        hangSxId: '',
        suDung: null
      },
      columns: [
                  {fieldName: 'CHUNGLOAISVR_ID', headerText: 'ID', textAlign: 'Left', width: '80px'},
                  {fieldName: 'MODEL_THIETBI', headerText: 'Model thiết bị', textAlign: 'Left'},
                  {fieldName: 'HANGSX_ID', headerText: 'Hãng sản xuất', textAlign: 'Left', valueAccessor: mapToHangSanXuat},
                  {fieldName: 'CONGSUAT', headerText: 'Công suất', textAlign: 'Center', width: '100px'},
                  {fieldName: 'FULL_DEPTH', headerText: 'Full depth', textAlign: 'Center', width: '100px', valueAccessor: mapToFullDepth},
                  {fieldName: 'CPU_SOKHE', headerText: 'CPU Số khe', textAlign: 'Center', width: '100px'},
                  {fieldName: 'SOKHE_RAM', headerText: 'RAM Số khe', textAlign: 'Center', width: '100px'},
                  {fieldName: 'PP_SOCONG', headerText: 'PP Số cổng', textAlign: 'Center', width: '100px'},
                  {fieldName: 'SUDUNG', headerText: 'Hiệu lực', textAlign: 'Center', width: '100px', valueAccessor: mapToHieuLuc},
                ],
      controls: createControls([
        'chungLoaiSvrId',
        'maVatTu',
        'modelThietBi',
        'hangSxId',
        'congSuat',
        'fullDepth',
        'cpuSoKhe',
        'cpuSoSocket',
        'cpuModel',
        'soKheRam',
        'soKheSsd',
        'soKheHdd',
        'soKheNvme',
        'gpuModel',
        'gpuSoLuong',
        'soSlotFan',
        'soSlotPci',
        'dpSoCong',
        'dpTenCong',
        'dpLoaiCong',
        'dpBangThong',
        'dpTtModuleId',
        'dpMucDichSdId',
        'ppSoCong',
        'ppTenCong',
        'ppDongDien',
        'ppDienAp',
        'ppLoaiNd',
        'ppChuanDnId',
        'cpSl',
        'cpLoaiCong',
        'cpTenCong',
        'cpMucDichSdId',
        'hieuLuc',
        'ghiChu'
      ]),
      currentItem: {
        chungLoaiSvrId: null,
        maVatTu: null,
        modelThietBi: '',
        hangSxId: null,
        congSuat: null,
        fullDepth: 0,
        cpuSoKhe: null,
        cpuSoSocket: null,
        cpuModel: '',
        soKheRam: null,
        soKheSsd: null,
        soKheHdd: null,
        soKheNvme: null,
        gpuModel: '',
        gpuSoLuong: null,
        soSlotFan: null,
        soSlotPci: null,
        dpSoCong: null,
        dpTenCong: '',
        dpLoaiCong: '',
        dpBangThong: null,
        dpTtModuleId: null,
        dpMucDichSdId: null,
        ppSoCong: null,
        ppTenCong: '',
        ppDongDien: null,
        ppDienAp: null,
        ppLoaiNd: null,
        ppChuanDnId: null,
        cpMucDichSdId: null,
        cpLoaiCong: '',
        cpSl: null,
        cpTenCong: '',
        hieuLuc: 1,
        ghiChu: '',
        moTa: null,
        nguoiCn: null,
        ngayCn: null
      },
      fullDepthOptions: [
        { id: 0, text: 'Full' },
        { id: 1, text: 'Not full' }
      ],
      ppLoaiNdOptions: [
        { id: 1, text: '1 pha' },
        { id: 3, text: '3 pha' }
      ]
    }
  },
  computed: {},
  watch: {},
  created() {
    // Khai báo rules validation theo pattern controls controller
    this.controlsController = buildController(this.controls, {
      hangSxId: [R.required()],
      congSuat: [R.required(), R.number(), R.min(1)],
      cpuSoKhe: [R.required(), R.integer(), R.min(1)],
      soKheRam: [R.required(), R.integer(), R.min(1)],
      ppSoCong: [R.required(), R.integer(), R.min(1)]
    })
  },
  mounted: async function() {
    await this.loadDanhMuc()
    await this.loadData()
  },
  methods: {
    onChangeTab: function(idx) {
      this.tab_index = idx
    },
    fillDataForm: function(data) {
      this.currentItem = {
        chungLoaiSvrId: data.CHUNGLOAISVR_ID,
        maVatTu: data.MA_VT || null,
        modelThietBi: data.MODEL_THIETBI || '',
        hangSxId: data.HANGSX_ID || null,
        congSuat: data.CONGSUAT || null,
        fullDepth: typeof data.FULL_DEPTH === 'number' ? data.FULL_DEPTH : 0,
        cpuSoKhe: data.CPU_SOKHE || null,
        cpuSoSocket: data.CPU_SOSOCKET || null,
        cpuModel: data.CPU_MODEL || '',
        soKheRam: data.SOKHE_RAM || null,
        soKheSsd: data.SOKHE_SSD || null,
        soKheHdd: data.SOKHE_HDD || null,
        soKheNvme: data.SOKHE_NVME || null,
        gpuModel: data.GPU_MODEL || '',
        gpuSoLuong: data.GPU_SOLUONG || null,
        soSlotFan: data.SOSLOT_FAN || null,
        soSlotPci: data.SOSLOT_PCI || null,
        dpSoCong: data.DP_SOCONG || null,
        dpTenCong: data.DP_TENCONG || '',
        dpLoaiCong: data.DP_LOAICONG || '',
        dpBangThong: data.DP_BANGTHONG || null,
        dpTtModuleId: data.DP_TTMODULE_ID || null,
        dpMucDichSdId: data.DP_MUCDICHSD_ID || null,
        ppSoCong: data.PP_SOCONG || null,
        ppTenCong: data.PP_TENCONG || '',
        ppDongDien: data.PP_DONGDIEN || null,
        ppDienAp: data.PP_DIENAP || null,
        ppLoaiNd: data.PP_LOAIND || null,
        ppChuanDnId: data.PP_CHUANDN_ID || null,
        cpMucDichSdId: data.CP_MUCDICHSD_ID || null,
        cpLoaiCong: data.CP_LOAICONG || '',
        cpSl: data.CP_SL || null,
        cpTenCong: data.CP_TENCONG || '',
        hieuLuc: data.SUDUNG !== null && data.SUDUNG !== undefined ? data.SUDUNG : 1,
        ghiChu: data.GHICHU || '',
        moTa: data.MOTA || null,
        nguoiCn: data.NGUOI_CN || null,
        ngayCn: data.NGAY_CN || null
      }
    },

    clearDataForm: function() {
      this.currentItem = {
        chungLoaiSvrId: null,
        maVatTu: null,
        modelThietBi: '',
        hangSxId: null,
        congSuat: null,
        fullDepth: 0,
        cpuSoKhe: null,
        cpuSoSocket: null,
        cpuModel: '',
        soKheRam: null,
        soKheSsd: null,
        soKheHdd: null,
        soKheNvme: null,
        gpuModel: '',
        gpuSoLuong: null,
        soSlotFan: null,
        soSlotPci: null,
        dpSoCong: null,
        dpTenCong: '',
        dpLoaiCong: '',
        dpBangThong: null,
        dpTtModuleId: null,
        dpMucDichSdId: null,
        ppSoCong: null,
        ppTenCong: '',
        ppDongDien: null,
        ppDienAp: null,
        ppLoaiNd: null,
        ppChuanDnId: null,
        cpMucDichSdId: null,
        cpLoaiCong: '',
        cpSl: null,
        cpTenCong: '',
        hieuLuc: 1,
        ghiChu: '',
        moTa: null,
        nguoiCn: null,
        ngayCn: null
      }
    },

    validateForm: function() {
      const { isValid } = this.controlsController.validate({
        hangSxId: this.currentItem.hangSxId,
        congSuat: this.currentItem.congSuat,
        cpuSoKhe: this.currentItem.cpuSoKhe,
        soKheRam: this.currentItem.soKheRam,
        ppSoCong: this.currentItem.ppSoCong
      })
      if (!isValid) this.$toast.error('Vui lòng kiểm tra lại các trường dữ liệu')
      return isValid
    },

    disableControls: function() {
      this.controlsController?.setEnabledAll(false)
    },

    enableControls: function() {
      this.controlsController?.setEnabledAll(true)
      this.controls.chungLoaiSvrId.enabled = false
      this.controls.maVatTu.enabled = false
    },

    setToDefault: function() {
      this.newBtn = true
      this.saveBtn = false
      this.editBtn = true
      this.cancelBtn = false
      this.deleteBtn = true
      this.disableControls()
    },

    setToNewOrEditMode: function() {
      this.newBtn = false
      this.saveBtn = true
      this.editBtn = false
      this.cancelBtn = true
      this.deleteBtn = false
      this.enableControls()
    },

    loadDanhMuc: async function() {
      try {
        // Các mã danh mục (cần map theo BE)
        const [hangsx, ttModule, mucDich, chuanDn] = await Promise.all([
          api.getDanhMuc(this.axios, 'IDC-HANG-SX'),
          api.getDanhMuc(this.axios, 'IDC-THONGTIN-MODULE'),
          api.getDanhMuc(this.axios, 'IDC-MUCDICH-SD-CONG'),
          api.getDanhMuc(this.axios, 'IDC-CHUANDAU-DAUNOI')
        ])

        this.listHangSanXuat = hangsx || []
        this.listThongTinModule = ttModule || []
        this.listMucDichSuDung = mucDich || []
        this.listChuanDauNoi = chuanDn || []
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi tải danh mục')
      }
    },

    loadData: async function() {
      try {
        this.$root.loading(true)
        const res = await api.search(this.axios, this.searchParams)
        if (res.data.error == 200) {
          this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
        this.$toast.error('Lỗi khi tải dữ liệu')
      } finally {
        this.$root.loading(false)
      }
    },

    onRowSelected: function(selectedRow) {
      this.fillDataForm(selectedRow.data)
    },

    mapToHangSanXuat: function(field, data, column) {
      if (field === 'HANGSX_ID') {
        return this.listHangSanXuat.find((item) => item.id === data.HANGSX_ID)?.text || ''
      }
      return data[field]
    },

    mapToFullDepth: function(field, data, column) {
      if (field === 'FULL_DEPTH') {
        if (data.FULL_DEPTH === 0) return 'Full'
        if (data.FULL_DEPTH === 1) return 'Not full'
        return ''
      }
      return data[field]
    },

    mapToThongTinModule: function(field, data, column) {
      if (field === 'DP_TTMODULE_ID') {
        return this.listThongTinModule.find((item) => item.id === data.DP_TTMODULE_ID)?.text || ''
      }
      return data[field]
    },

    mapToMucDichSuDung: function(field, data, column) {
      if (field === 'DP_MUCDICHSD_ID' || field === 'CP_MUCDICHSD_ID') {
        const id = data[field]
        return this.listMucDichSuDung.find((item) => item.id === id)?.text || ''
      }
      return data[field]
    },

    mapToPpLoaiNd: function(field, data, column) {
      if (field === 'PP_LOAIND') {
        if (data.PP_LOAIND === 1) return '1 pha'
        if (data.PP_LOAIND === 3) return '3 pha'
        return ''
      }
      return data[field]
    },

    mapToChuanDauNoi: function(field, data, column) {
      if (field === 'PP_CHUANDN_ID') {
        return this.listChuanDauNoi.find((item) => item.id === data.PP_CHUANDN_ID)?.text || ''
      }
      return data[field]
    },

    mapToHieuLuc: function(field, data, column) {
      if (field === 'SUDUNG') {
        return data.SUDUNG === 1 ? 'Có hiệu lực' : 'Không hiệu lực'
      }
      return data[field]
    },

    onClickNhapMoi: function() {
      this.clearDataForm()
      this.setToNewOrEditMode()
    },

    onClickSua: function() {
      this.setToNewOrEditMode()
    },

    onClickGhiLai: async function() {
      if (this.validateForm()) {
        this.$root.loading(true)

        try {
          const payload = {
            CHUNGLOAISVR_ID: this.currentItem.chungLoaiSvrId,
            MA_VT: this.currentItem.maVatTu,
            MODEL_THIETBI: this.currentItem.modelThietBi || null,
            HANGSX_ID: this.currentItem.hangSxId || null,
            CONGSUAT: this.currentItem.congSuat || null,
            FULL_DEPTH: this.currentItem.fullDepth || 0,
            CPU_SOKHE: this.currentItem.cpuSoKhe || null,
            CPU_SOSOCKET: this.currentItem.cpuSoSocket || null,
            CPU_MODEL: this.currentItem.cpuModel || null,
            SOKHE_RAM: this.currentItem.soKheRam || null,
            SOKHE_SSD: this.currentItem.soKheSsd || null,
            SOKHE_HDD: this.currentItem.soKheHdd || null,
            SOKHE_NVME: this.currentItem.soKheNvme || null,
            GPU_MODEL: this.currentItem.gpuModel || null,
            GPU_SOLUONG: this.currentItem.gpuSoLuong || null,
            SOSLOT_FAN: this.currentItem.soSlotFan || null,
            SOSLOT_PCI: this.currentItem.soSlotPci || null,
            DP_SOCONG: this.currentItem.dpSoCong || null,
            DP_TENCONG: this.currentItem.dpTenCong || null,
            DP_TENCONG: this.currentItem.dpTenCong || null,
            DP_LOAICONG: this.currentItem.dpLoaiCong || null,
            DP_BANGTHONG: this.currentItem.dpBangThong || null,
            DP_TTMODULE_ID: this.currentItem.dpTtModuleId || null,
            DP_MUCDICHSD_ID: this.currentItem.dpMucDichSdId || null,
            PP_SOCONG: this.currentItem.ppSoCong || null,
            PP_TENCONG: this.currentItem.ppTenCong || null,
            PP_TENCONG: this.currentItem.ppTenCong || null,
            PP_DONGDIEN: this.currentItem.ppDongDien || null,
            PP_DIENAP: this.currentItem.ppDienAp || null,
            PP_LOAIND: this.currentItem.ppLoaiNd || null,
            PP_CHUANDN_ID: this.currentItem.ppChuanDnId || null,
            CP_MUCDICHSD_ID: this.currentItem.cpMucDichSdId || null,
            CP_LOAICONG: this.currentItem.cpLoaiCong || null,
            CP_SL: this.currentItem.cpSl || null,
            CP_TENCONG: this.currentItem.cpTenCong || null,
            CP_TENCONG: this.currentItem.cpTenCong || null,
            SUDUNG:
              this.currentItem.hieuLuc !== null && this.currentItem.hieuLuc !== undefined
                ? this.currentItem.hieuLuc
                : 1,
            GHICHU: this.currentItem.ghiChu || null,
            MOTA: null,
            NGUOI_CN: null,
            NGAY_CN: null
          }

          console.log('Payload to save:', payload)

          const res = await api.save(this.axios, payload)
          if (res.data.error == 200) {
            this.$toast.success('Lưu thành công')
            await this.loadData()
            this.setToDefault()
          }
        } catch (error) {
          this.$toast.error(error.data?.message_detail || 'Lỗi khi lưu dữ liệu')
        } finally {
          this.$root.loading(false)
        }
      }
    },

    onClickXoa: async function() {
      this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()
      if (this.selectedRows.length === 0) {
        this.$toast.warning('Vui lòng chọn ít nhất một bản ghi để xóa')
        return
      }

      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa ${this.selectedRows.length} bản ghi đã chọn?`,
        'Xác nhận xóa',
        {
          confirmButtonText: 'Xóa',
          cancelButtonText: 'Hủy',
          type: 'warning'
        }
      )

      if (confirmed) {
        try {
          const idsToDelete = this.selectedRows.map((row) => row.CHUNGLOAISVR_ID)
          const results = await Promise.all(idsToDelete.map((id) => api.delete(this.axios, id)))

          results.forEach((res, index) => {
            const id = idsToDelete[index]
            if (res.data && res.data.error == 200) {
              this.$toast.success('Xóa thành công id: ' + id)
            } else {
              this.$toast.error(res.data?.message_detail || 'Lỗi khi xóa dữ liệu')
            }
          })

          await this.loadData()
          this.selectedRows = []
        } catch (error) {
          this.$toast.error('Lỗi khi xóa dữ liệu')
          console.error(error)
        }
      }
    },

    onClickHuy: function() {
      this.setToDefault()
      this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()
      if (this.selectedRows.length > 0) {
        this.fillDataForm(this.selectedRows[0])
      } else {
        this.clearDataForm()
      }
    },

    onClickTimKiem: function() {
      this.loadData()
    },

    // helpers for template like IDC module
    isEnabled: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].enabled)
    },
    isInvalid: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].invalid)
    }
  }
}
</script>
<style scoped>
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}

.e-error .e-input {
  border: 1px solid red !important;
}

.legend-subtitle {
  font-weight: 500;
  padding-bottom: 5px;
  margin-bottom: 10px;
  margin-top: 15px;
  border-bottom: 1px solid #ccc;
  color: #666;
  font-size: 14px;
}
</style>
