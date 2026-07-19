<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import ImageSelector from '../components/ImageSelector/ImageSelector.vue'
import api from './api'
import moment from 'moment'
import { buildController, rules as R, createControls } from './controls'

export default {
  name: 'ThietbiKhachHang',
  components: {
    ModalImportExcel,
    ImageSelector
  },
  data() {
    return {
      dataItems: [],
      selectedRows: [],

      searchParams: {
        ten: '',
        idDonVi: '',
        suDung: null
      },
      currentItem: {}
    }
  },
  watch: {
   
  },
  computed: {
 
  },
  created() {
    // Init controls controller with validation rules
    // this.controlsController = buildController(this.controls, {
    //   tenToaNha: [R.required()],
    //   mucDichSuDung: [R.required()],
    //   idDonViIDC: [R.required()],
    //   idIDC: [R.required()],
    //   thoiDiemVanHanh: [R.date()],
    //   dienTichTongThe: [R.number(), R.min(0)]
    // })

    // Gán validator thực tế cho rule vừa khai báo trong importConfig
    // const idx = this.importConfig.validationRules.findIndex((r) => r.field === 'IDC_ID' && r.validator === null)
    // if (idx !== -1) {
    //   this.importConfig.validationRules[idx].validator = this.validateIDCExists()
    // }

    // const idxTen = this.importConfig.validationRules.findIndex((r) => r.field === 'TEN' && r.validator === null)
    // if (idxTen !== -1) {
    //   this.importConfig.validationRules[idxTen].validator = this.validateTenToaNhaDuplicate()
    // }

    // const idxThoiDiem = this.importConfig.validationRules.findIndex(
    //   (r) => r.field === 'THOI_DIEM_VAN_HANH' && r.validator === null
    // )
    // if (idxThoiDiem !== -1) {
    //   this.importConfig.validationRules[idxThoiDiem].validator = this.validateDateFormat()
    // }
  },
  mounted: async function() {
    await this.loadData()
  },
  methods: {
    // ============= utilities =============

    fillDataForm: function(data) {
      this.currentItem = {
        id: data.ID_TOANHA,
        tenToaNha: data.TEN_TOANHA || '',
        idIDC: data.ID_IDC || null,
        tenIDC: data.TEN_IDC || '',
        idDonViIDC: data.ID_DONVI || null,
        mucDichSuDung: data.ID_MUCDICHSD || null,
        tenMucDichSuDung: data.TEN_MUCDICHSD || '',
        thoiDiemVanHanh: data.NGAY_SD || '',
        trangThaiVanHanh: data.ID_TRANGTHAI_VH || null,
        tenTrangThaiVanHanh: data.TEN_TRANGTHAI_VH || '',
        dienTichTongThe: data.DIENTICH || null,
        // ==== Location
        toaDoX: data.TOADO_X || '',
        toaDoY: data.TOADO_Y || '',
        tongDienTichPhongMay: data.DIENTICH_PHONGMAY || null,
        tongSoLuongRackThietKe: data.TONG_RACK_THIETKE || null,
        tongSoLuongRackLapDat: data.TONG_RACK_DA_LAPDAT || null,
        tongSoLuongRackConLai: data.TONG_RACK_CON || null,
        congSuatDinhDanh: data.CS_DINHDANH_TB || null,
        anhDaiDien: data.HINHANH || '',
        ghiChu: data.GHI_CHU || '',
        suDung: data.SUDUNG || 1
      }

      console.log('Current item:', this.currentItem)
      this.$nextTick(() => {
        // this.initFormData()
      })
    },

    formatCurrentItem: function(currentItem) {
      const res = {
        id: currentItem.id,
        tenToaNha: currentItem.tenToaNha,
        idIDC: currentItem.idIDC ? Number(currentItem.idIDC) : null,
        idDonViIDC: currentItem.idDonViIDC ? Number(currentItem.idDonViIDC) : null,
        mucDichSuDung: currentItem.mucDichSuDung ? Number(currentItem.mucDichSuDung) : null,
        thoiDiemVanHanh: currentItem.thoiDiemVanHanh ? moment(currentItem.thoiDiemVanHanh).format('DD/MM/YYYY') : null,
        trangThaiVanHanh: currentItem.trangThaiVanHanh ? Number(currentItem.trangThaiVanHanh) : null,
        dienTichTongThe: currentItem.dienTichTongThe ? Number(currentItem.dienTichTongThe) : null,
        // ==== Location
        toaDoX: Number(currentItem.toaDoX),
        toaDoY: Number(currentItem.toaDoY),
        anhDaiDien: currentItem.anhDaiDien,
        ghiChu: currentItem.ghiChu,
        suDung: currentItem.suDung ? Number(currentItem.suDung) : null,
        mota: currentItem.mota,
        tentat: currentItem.tentat
      }
      console.log('Formatted item to save:', res)
      return res
    },

    clearDataForm: function() {
      this.currentItem = { mucDichSuDung: 1, suDung: 1 }
      this.textThoiDiemVanHanh = ''
      this.thoiDiemVanHanhHasInteraction = false
    },

    // Validate form
    validateForm: function() {
      const dateValidation = this.validateThoiDiemVanHanhForSubmit()
      if (!dateValidation.valid) {
        this.$toast.error(dateValidation.error)
        return false
      }
      const { isValid, errors } = this.controlsController.validate(this.currentItem)
      if (!isValid) {
        this.$toast.error('Vui lòng kiểm tra lại các trường dữ liệu')
      }
      return isValid
    },

    disableControls: function() {
      this.controlsController?.setEnabledAll(false)
    },

    enableControls: function() {
      this.controlsController?.setEnabledAll(true)
      this.controls.id.enabled = false
    },

    setToDefault: function() {
      this.newBtn = true
      this.saveBtn = false
      this.editBtn = true
      this.cancelBtn = false
      this.deleteBtn = true
      this.tempImageFile = null
      this.disableControls()
    },

    setToNewOrEditMode: function() {
      this.newBtn = false
      this.saveBtn = true
      this.editBtn = false
      this.cancelBtn = true
      this.deleteBtn = false

      // this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords();
      this.enableControls()
    },

    onRowSelected: function(selectedRow) {
      console.log('Selected row:', selectedRow)
      this.fillDataForm(selectedRow.data)
    },
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="viewItem()">
                  Xem chi tiết HĐ
                </button>
              </div>`,
            computed: {
            },
            methods: {
              viewItem() {
                parent.loadHDDetail(this.data);
              }
            },
          },
        };
      };
    },
    loadHDDetail: async function(data) {
      try {
        console.log(data)
        this.$root.loading(true)
        const res = await api.getHDDetail(this.axios, data.HDTB_ID)
        if (res.data.error == 200) {
          this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      } finally {
        this.$root.loading(false)
      }
    },
    loadData: async function() {
      try {
        this.$root.loading(true)
        const res = await api.getList(this.axios, 43)
        console.log(res)
        if (res.data.error == 200) {
          this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      } finally {
        this.$root.loading(false)
      }
    },

    // ======== controls helpers for template ========
    isEnabled: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].enabled)
    },
    isInvalid: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].invalid)
    },

    // ======== datepicker validation ========
    async initFormData() {

    }
  }
}
</script>
<style scoped>
.date-picker .e-input-group {
  border-color: 1px solid red !important;
}

.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}

.e-error .e-input {
  border: 1px solid red !important;
}
</style>
