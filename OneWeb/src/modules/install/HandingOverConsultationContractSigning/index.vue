<template src="./index.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import moment from 'moment'
import PopupGiaoViec from '../UpdateOrdersMyVNPTFreedoo/popups/PopupGiaoViec'
import Vue from 'vue'

const PHIEU_MOI = 1
const PHIEU_TRA_LAI = 2

export default {
  name: 'HandingOverConsultationContractSigning',
  components: { breadcrumb, PopupGiaoViec },
  data () {
    return {
      header: {
        title: 'Cập nhật thông tin tư vấn',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'tsbtnXem', icon: 'nc-icon-glyph design_bullet-list-67', name: 'Danh sách' },
        { id: 'tsbtnGiaoViec', icon: 'one-file-edit2', name: 'Giao việc' }
      ],
      checkActions: [
        { value: PHIEU_MOI, name: 'Phiếu mới' },
        { value: PHIEU_TRA_LAI, name: 'Phiếu trả lại' }
      ],
      checkValue: PHIEU_MOI,
      timeout: null,
      debounceSearchTerm: '',
      tuVanFilter: {
        maGD: '',
        ngayYC: '',
        tenKH: '',
        soDT: '',
        diaChiKH: ''
      },
      giaoViec: {
        headers: [
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, visible: true },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, visible: true },
          { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true, visible: true },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true, visible: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, visible: true }
        ],
        dataSources: []
      },
      tuVan: {
        headers: [
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, visible: true },
          { fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true, visible: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, visible: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, visible: true },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu YC', allowFiltering: true, visible: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, visible: true },
          { fieldName: 'chitieu_tg', headerText: 'Chi tiêu TG (giờ)', allowFiltering: true, visible: true },
          { fieldName: 'thoigian_ck', headerText: 'Thời gian CK', allowFiltering: true, visible: true },
          { fieldName: 'gio_cl', headerText: 'Giờ còn lại', allowFiltering: true, visible: true }
        ],
        dataSources: []
      },
      dstthdId: '',
      currentPage: 0,
      position: { X: 'center', Y: 'top' },
      giaoViecModel: {
        phieuId: 0,
        ngayGiao: moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      },
      customTemplate: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
            data () {
              return { data: { data: {} } }
            }
          })
        }
      },
      countSelected: false,
      totalItems: 0,
      phieuId: null
    }
  },
  computed: {
    donViId () {
      return +this.$root.token.getDonViID()
    },
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    searchTerm: {
      get () {
        return this.debounceSearchTerm
      },
      set (val) {
        if (this.timeout) clearTimeout(this.timeout)
        this.timeout = setTimeout(() => {
          this.debounceSearchTerm = val
          return this.dataSources
        }, 500)
      }
    },
    dataSources () {
      const dataSources = this.tuVan.dataSources
      if (this.searchTerm && dataSources.length > 0) {
        return dataSources.filter(item => this.searchTerm.toLowerCase().split(' ').every(v => item.ma_gd.toLowerCase().includes(v)))
      } else {
        return dataSources
      }
    },
    aggregates () {
      return [
        {
          field: 'chitieu_tg',
          type: 'Custom',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.customAggregateFn
        }
      ]
    }
  },
  async created () {
    await this.frmCapNhatTT_TuVan_Load()
  },
  methods: {
    async frmCapNhatTT_TuVan_Load () {
      this.getTag()
      this.loading(true)
      try {
        await this.getDanhSachTuVan(0)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    getTag () {
      const tag = this.$route.query.tag
      if (tag && tag.length > 0) {
        const words = tag.split('+')
        if (words.length >= 1) {
          this.dstthdId = words[0]
          this.dstthdId = this.dstthdId.replaceAll(';', ',')
        }
      } else {
        this.dstthdId = '1'
      }
    },
    async getDanhSachTuVan (currentPage) {
      if (currentPage === 0) {
        try {
          const response = await api.getDanhSachTuVan(
            this.axios,
            {
              vtthd_id: +this.dstthdId,
              vdonvi_id: this.donViId,
              vkieu: 2,
              vttph_id: this.checkValue === PHIEU_MOI ? 1 : 3,
              vnhanvien_id: this.nhanVienId
            }
          )
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && data.error === '200') {
            this.tuVan.dataSources = data.data.map(tuVan => {
              tuVan.ngay_yc = moment(tuVan.ngay_yc).format('DD/MM/YYYY HH:mm:ss')
              return tuVan
            })
          } else {
            this.tuVan.dataSources = []
          }
        } catch (e) {
          this.$root.$toast.error('Không thể lấy danh sách tư vấn')
          this.tuVan.dataSources = []
        }
      }
    },
    async onChangeTrangThai (value) {
      if (value === PHIEU_MOI) {
        this.loading(true)
        try {
          await this.getDanhSachTuVan(0)
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else {
        this.setFilter({
          maGD: '',
          tenKH: '',
          soDT: '',
          diaChiKH: '',
          ngayYC: ''
        })
        this.tuVan.dataSources = []
        this.giaoViec.dataSources = []
      }
    },
    async onSelectedRowChanged (data) {
      const filter = {
        maGD: '',
        tenKH: '',
        soDT: '',
        diaChiKH: '',
        ngayYC: ''
      }
      if (data) {
        this.phieuId = data.phieu_id
        this.giaoViecModel.phieuId = this.phieuId
        filter.maGD = data.ma_gd
        filter.tenKH = data.ten_kh
        filter.soDT = data.so_dt
        filter.diaChiKH = data.diachi_kh
        filter.ngayYC = data.ngay_yc
        this.setFilter(filter)
        await this.getDanhSachGiaoViec(this.phieuId)
      } else {
        this.setFilter(filter)
        this.giaoViec.dataSources = []
      }
    },
    setFilter ({ maGD, tenKH, soDT, diaChiKH, ngayYC }) {
      this.tuVanFilter.maGD = maGD
      this.tuVanFilter.tenKH = tenKH
      this.tuVanFilter.soDT = soDT
      this.tuVanFilter.diaChiKH = diaChiKH
      this.tuVanFilter.ngayYC = ngayYC
    },
    async getDanhSachGiaoViec (phieuId) {
      this.loading(true)
      try {
        const response = await api.getDanhSachGiaoViec(
          this.axios,
          {
            phieu_id: phieuId,
            nhanvien_id: this.nhanVienId,
            kieu_id: 2
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.giaoViec.dataSources = data.data.filter(elm => +elm.nhiemvu_id === 21)
        } else {
          this.giaoViec.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách giao việc')
        this.giaoViec.dataSources = []
      } finally {
        this.loading(false)
      }
    },
    async onClickAction (id) {
      if (id === 'tsbtnXem') {
        this.loading(true)
        try {
          await this.getDanhSachTuVan(this.currentPage)
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else {
        await this.showPopupGiaoViec()
      }
    },
    async showPopupGiaoViec () {
      await this.$refs.giaoViec.init(0)
      this.$refs.popupGiaoViec.show()
    },
    onClosePopupGiaoViec () {
      this.$refs.giaoViec.resetFilter()
      this.$refs.popupGiaoViec.hide()
    },
    async onAfterClosePopupGiaoViec () {
      await this.getDanhSachGiaoViec(this.phieuId)
    },
    customAggregateFn () {
      return '0/0'
    }
  }
}
</script>
