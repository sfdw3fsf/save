<template src="./index.template.html"></template>
<script>
import { validationMixin, ValidationRulesFactory } from '../../validation.js'
import { TreeView } from '@syncfusion/ej2-navigations'
import CaNhanQuanLySelector from './components/CaNhanQuanLySelector.vue'
import TreeSelector from './components/TreeSelector.vue'
import DanhMucApi from '../../../api/DanhMuc.api.js'
import ThietBiApi from '../../../api/ThietBi.api.js'
import moment from 'moment'

export default {
  name: 'ThongTinKiemKe',
  mixins: [validationMixin],
  components: {
    CaNhanQuanLySelector,
    TreeSelector
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      danhMuc: {
        donViQuanLyList: [],
        phongBanQuanLyList: [],
        nguoiQuanLyList: [],
        nhiemVuList: []
      },
      validationRules: {
        ngay_kh: ValidationRulesFactory.createDatePickerRule('Ngày không đúng định dạng'),
        ngay_baohanh: ValidationRulesFactory.createDatePickerRule('Ngày không đúng định dạng'),
        ngay_lapdat: ValidationRulesFactory.createDatePickerRule('Ngày không đúng định dạng'),
        ngay_sudung: ValidationRulesFactory.createDatePickerRule('Ngày không đúng định dạng'),
      },
      canhanQuanLyKey: 0
    }
  },
  computed: {
    canhanQuanLySelectorKey() {
      // Tạo key để force re-render
      if (!Array.isArray(this.currentItem.nguoiqly_id) || this.currentItem.nguoiqly_id.length === 0) {
        return `canhan-${this.canhanQuanLyKey}-empty`
      }
      const ids = this.currentItem.nguoiqly_id.map((item) => item.MA_NV || item.NHANVIEN_ID || '').join('-')
      return `canhan-${this.canhanQuanLyKey}-${ids}`
    }
  },
  watch: {
    'currentItem.donviqly_id': async function (newVal, oldVal) {
      if (newVal) {
        await this.getPhongBanQuanLyList(newVal)
      } else {
        this.danhMuc.phongBanQuanLyList = []
      }
    },
    'currentItem.phongbanqly_id': async function (newVal, oldVal) {
      if (oldVal && newVal !== oldVal) {
        this.currentItem.nguoiqly_id = null
      }
      if (newVal) {
        await this.getNguoiQuanLyList(newVal)
      } else {
        this.danhMuc.nguoiQuanLyList = []
      }
    }
  },
  methods: {
    async loadDanhMuc () {
      const promises = []
      promises.push(this.getDonViQuanLyList())
      promises.push(this.getNhiemVuList())

      if (this.currentItem.donviqly_id) {
        promises.push(this.getPhongBanQuanLyList(this.currentItem.donviqly_id))
      }
      if (this.currentItem.phongbanqly_id) {
        promises.push(this.getNguoiQuanLyList(this.currentItem.phongbanqly_id))
      }
      await Promise.all(promises)
    },

    async getDonViQuanLyList() {
      try {
        const response = await DanhMucApi.getDonViQuanLyList(this.$root.context)
        this.danhMuc.donViQuanLyList = response || []
      } catch (error) {
        console.error('Lỗi lấy thông tin đơn vị: ', error)
      }
    },
    async getPhongBanQuanLyList(donViId) {
      try {
        const response = await DanhMucApi.getPhongBanQuanLyList(this.$root.context, donViId)
        this.danhMuc.phongBanQuanLyList = response || []
      } catch (error) {
        console.error('Lỗi lấy thông tin phòng ban: ', error)
      }
    },
    async getNguoiQuanLyList(id) {
      try {
        const response = await DanhMucApi.getNguoiQuanLyList(this.$root.context, id)
        this.danhMuc.nguoiQuanLyList = response || []
      } catch (error) {
        console.error('Lỗi lấy thông tin người quản lý: ', error)
      }
    },

    async getNhiemVuList() {
      try {
        const response = await DanhMucApi.getNhiemVuList(this.$root.context)
        this.danhMuc.nhiemVuList = response || []
      } catch (error) {
        console.error('Lỗi tải danh mục nhiệm vụ', error)
      }
    },

    onNguoiQuanLyChange(nguoiQuanLyId) {
      this.$set(this.currentItem, 'nguoiqly_id', nguoiQuanLyId)

      this.canhanQuanLyKey++
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },

    // ==== CRUD ====

    async saveCaNhanQuanLy() {
      try {
        const payload = this.preparePayloadForCaNhanQuanLy()
        const response = await ThietBiApi.upsertCaNhanQuanLy(this.$root.context, payload)
      } catch (error) {
        this.$toast.error('Lỗi khi lưu thông tin cá nhân quản lý')
      }
    },

    async saveThongTinTaiSan() {
      try {
        const payload = this.preparePayloadForTaiSan()
        const response = await ThietBiApi.upsertThongTinTaiSan(this.$root.context, payload)
      } catch (error) {
        this.$toast.error('Lỗi khi lưu thông tin tài sản')
      }
    },

    async getCaNhanQuanLy(id) {
      try {
        const response = await ThietBiApi.getCaNhanQuanLy(this.$root.context, id)
        return response
      } catch {
        this.$toast.error('Lỗi khi tải thông tin cá nhân quản lý')
      }
    },

    async getThongTinTaiSan(id) {
      try {
        const response = await ThietBiApi.getThongTinTaiSan(this.$root.context, id)
        return response
      } catch {
        this.$toast.error('Lỗi khi tải thông tin tài sản')
      }
    },

    preparePayloadForCaNhanQuanLy(currentItem) {
      const items = []
      if (Array.isArray(currentItem.nguoiqly_id)) {
        const newItems = currentItem.nguoiqly_id.map((nv) => ({
          nhanvien_id: nv.NHANVIEN_ID,
          nhiemvu_id: nv.NHIEMVU_ID
        }))
        items.push(...newItems)
      }
      return {
        thietbiId: currentItem.id,
        items: items
      }
    },

    preparePayloadForTaiSan(currentItem) {
      return {
        thietBiId: currentItem.id ? Number(currentItem.id) : null,
        maDa: currentItem.ma_da,
        tenDa: currentItem.ten_da,
        soTheTs: currentItem.so_the_ts,
        taiSanId: currentItem.taisan_id ? Number(currentItem.taisan_id) : null,
        maVtu: currentItem.ma_vtu,
        ngayKh: currentItem.ngay_kh ? moment(currentItem.ngay_kh).format('DD/MM/YYYY') : null,
        ngayBaoHanh: currentItem.ngay_baohanh ? moment(currentItem.ngay_baohanh).format('DD/MM/YYYY') : null,
        ngayLapDat: currentItem.ngay_lapdat ? moment(currentItem.ngay_lapdat).format('DD/MM/YYYY') : null,
        ngaySuDung: currentItem.ngay_sudung ? moment(currentItem.ngay_sudung).format('DD/MM/YYYY') : null,
        donViQlyId: currentItem.donviqly_id ? Number(currentItem.donviqly_id) : null,
        phongBanQlyId: currentItem.phongbanqly_id ? Number(currentItem.phongbanqly_id) : null
      }
    },

    getValueForControl(controlKey) {
      const valueMap = {
        ngay_kh: this.currentItem.ngay_kh,
        ngay_baohanh: this.currentItem.ngay_baohanh,
        ngay_lapdat: this.currentItem.ngay_lapdat,
        ngay_sudung: this.currentItem.ngay_sudung
      }

      return valueMap[controlKey]
    }
  }
}
</script>
