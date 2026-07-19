<template src="./index.template.html"></template>
<script>
import api from '../../../api'
import { LOAI_DOI_TUONG_SD, DONVI_QUANLY, getDeviceUrls } from '../../../config.js'
export default {
  name: 'ThongTinChung',
  props: {
    ip_id: {
      type: Number,
      default: null
    },
    modalMode: {
      type: String,
      default: 'view'
    }
  },
  computed: {
    isDisableInput() {
      return this.modalMode == 'view'
    },
    ipInvalid() {
      return !this.validateIPFormat(this.currentItem.diaChiIp)
    }
  },
  data() {
    return {
      currentItem: {
        id: null,
        diaChiIp: null,
        idcId: null,
        haTangMangId: null,
        ipStoreId: null,
        loaiIpId: null,
        phanLoaiIpId: null,
        vrfId: null,
        zoneId: null,
        vlanId: null,
        subnetId: null,
        doiTuongSDId: null,
        suDung: true,
        quangBaIp: true,
        trangThaiCcdvId: null,
        thietBiId: null,
        mucDichSdId: null,
        noiBoId: 1, // 1: Nội bộ, 0: Khách hàng
        donViQuanLyId: null,
        ghiChu: '',
        vlanId: null,
        thietBiId: null,
        soCu: null
      },
      // Data sources for dropdowns
      dsVlan: [],
      dsThietBi: [],
      dsIdc: [],
      dsHaTangMang: [],
      dsIpStore: [],
      dsLoaiIp: [],
      dsPhanLoaiIp: [],
      dsVrf: [],
      dsZone: [],
      dsVlan: [],
      dsSubnet: [],
      dsDoiTuongSuDung: [],
      dsTrangThaiCcdv: [],
      dsThietBi: [],
      dsMucDichSuDung: [],
      dsNoiBoKhachHang: [],
      dsDonViQuanLy: [],
      dsQuyTrinh: []
    }
  },
  methods: {
    async loadFormData(id) {
      let currentId = id || this.ip_id
      if (currentId) {
        try {
          this.$root.showLoading(true)
          const data = { id: currentId }
          const response = await api.getIpAddressDetail(this.$root.context, data)
          if (response && response.error_code === 'BSS-00000000') {
            const rs = response.data[0]
            this.fillFormData(rs)
            if (rs.IDC_ID > 0) {
              this.onChangeIDC()
            }
            if (rs.VRF_ID > 0) {
              this.onChangeVrf()
            }
            if (rs.HATANGMANG_ID > 0 && rs.LOAIIP_ID > 0 && rs.PHANLOAIIP_ID > 0) {
              console.log('loadSubnet')
              this.loadSubnet()
            }
          } else {
            this.resetFormData()
          }
        } catch (e) {
          console.error('Error loading IP address detail:', e)
          this.$root.toastError('Có lỗi xảy ra khi tải chi tiết IP Address')
          this.resetFormData()
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.resetFormData()
      }
    },
    resetFormData() {
      this.currentItem = {
        idcId: null,
        haTangMangId: null,
        diaChiIp: null,
        loaiIpId: null,
        phanLoaiIpId: null,
        vrfId: null,
        zoneId: null,
        vlanId: null,
        subnetId: null,
        doiTuongSDId: null,
        suDung: true,
        quangBaIp: true,
        trangThaiCcdvId: null,
        thietBiId: null,
        mucDichSdId: null,
        noiBoId: 1,
        donViQuanLyId: null,
        ghiChu: '',
        id: null
      }
    },
    fillFormData(data) {
      if (!data) {
        this.resetFormData()
        return
      }
      this.currentItem = {
        idcId: data.IDC_ID ?? null,
        haTangMangId: data.HATANGMANG_ID ?? null,
        loaiIpId: data.LOAIIP_ID ?? null,
        phanLoaiIpId: data.PHANLOAIIP_ID ?? null,
        vrfId: data.VRF_ID ?? null,
        zoneId: data.ZONE_ID ?? null,
        vlanId: data.VLAN_ID ?? null,
        subnetId: data.SUBNET_ID ?? null,
        doiTuongSDId: data.DOITUONG_ID ?? null,
        suDung: data.SUDUNG === 1,
        quangBaIp: data.QUANGBA_IP === 1,
        trangThaiCcdvId: data.TT_CCDV_ID ?? null,
        // thietBiId: null,
        mucDichSdId: data.MUCDICHSD_ID ?? null,
        noiBoId: data.NOIBO ?? null,
        donViQuanLyId: data.DONVIQL_ID ?? null,
        ghiChu: data.GHICHU || '',
        diaChiIp: data.DIACHI_IP || '',
        id: data.IP_ID || null,
        soCu: data.MOTA || null
      }
    },
    openDetailLink() {
      // Handle opening detail link
      console.log('Opening detail link for:', this.currentItem)
      // You can navigate to another page or open a modal here
    },
    validateForm() {
      let msg = ''
      const requiredFields = [
        { field: 'diaChiIp', label: 'Địa chỉ IP' },
        { field: 'idcId', label: 'IDC' },
        { field: 'phanLoaiIpId', label: 'Phân loại IP' },
        { field: 'loaiIpId', label: 'Loại IP' },
        { field: 'subnetId', label: 'Subnet' },
        { field: 'trangThaiCcdvId', label: 'Trạng thái CCDV' },
        { field: 'noiBoId', label: 'Nội bộ/ khách hàng' },
        { field: 'donViQuanLyId', label: 'Đơn vị quản lý' },
        { field: 'doiTuongSDId', label: 'Đối tượng sử dụng IP' },
        { field: 'haTangMangId', label: 'Hạ tầng mạng' }
      ]
      requiredFields.forEach(({ field, label }) => {
        if (this.currentItem[field] === 0 || this.currentItem[field] === null) {
          msg += (msg ? '\n' : '') + `${label} không được để trống.`
        }
      })
      if (this.ipInvalid && this.currentItem.diaChiIp) {
        msg = 'Địa chỉ IP không đúng định dạng IPv4/IPv6.'
      }
      if (msg) {
        this.$root.toastError(msg)
        return false
      }
      return true
    },
    getFormData() {
      const data = { ...this.currentItem }
      data.suDung = data.suDung ? 1 : 0
      data.quangBaIp = data.quangBaIp ? 1 : 0
      const numericFields = [
        'idcId',
        'haTangMangId',
        'loaiIpId',
        'phanLoaiIpId',
        'vrfId',
        'zoneId',
        'vlanId',
        'subnetId',
        'trangThaiCcdvId',
        'thietBiId',
        'mucDichSdId',
        'donViQuanLyId',
        'doiTuongSDId',
        'noiBoId',
        'id'
      ]

      numericFields.forEach((field) => {
        if (data[field] !== null && data[field] !== '') {
          data[field] = Number(data[field])
        } else {
          data[field] = null
        }
      })

      return data
    },
    async loadDanhMuc() {
      try {
        this.loading(true)
        // this.dsIdc = await this.getDanhMucIDC('ALL_IDC')
        this.dsIDC = await this.getDanhMucIDC('IDC-IDC-BY-DONVI-ID', this.$root.token.getDonViID())
        this.dsTrangThaiCcdv = await this.getDanhMucIDC('TRANGTHAI_CCDV')
        this.dsLoaiIp = await this.getDanhMucIDC('LOAI_IP')
        this.dsPhanLoaiIp = await this.getDanhMucIDC('PHANLOAI_IP')
        this.dsDoiTuongSuDung = await this.getDanhMucIDC('IP_DOITUONGSD')
        // this.dsVlan = await this.getDanhMucIDC('HATANG_VLAN', this.currentItem.idcId)
        this.dsMucDichSuDung = await this.getDanhMucIDC('IDC-MUCDICH-SD-PHONGZONE')
        this.dsNoiBoKhachHang = LOAI_DOI_TUONG_SD
        this.dsDonViQuanLy = DONVI_QUANLY
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    onChangeIDC: async function() {
      if (this.currentItem.idcId != 0) {
        try {
          this.$root.showLoading(true)
          this.dsHaTangMang = await this.getDanhMucWithParams('HATANGMANG_BY_IDC_ID', this.currentItem.idcId)
          this.dsVrf = await this.getDanhMucWithParams('VRF-IDC', this.currentItem.idcId)
          this.dsVlan = await this.getDanhMucIDC('HATANG_VLAN', this.currentItem.idcId)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    onChangeVrf: async function() {
      if (this.currentItem.vrfId != 0) {
        try {
          this.$root.showLoading(true)
          this.dsZone = await this.getDanhMucWithParams('HATANG_ZONE', this.currentItem.vrfId)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    getDanhMucWithParams: async function(ten, thamSo1, thamSo2) {
      const response = await api.getDanhMucHaTang(this.$root.context, {
        loaiDanhMuc: ten,
        thamSo1: thamSo1,
        thamSo2: thamSo2
      })
      if (response && response.error == 200 && response.data) {
        return response.data
      }
      return []
    },
    getDanhMucIDC: async function(ten, thamSo1, thamSo2) {
      const response = await api.getDanhMuc(this.$root.context, {
        loaiDanhMuc: ten,
        thamSo1: thamSo1,
        thamSo2: thamSo2
      })
      if (response && response.error == 200 && response.data) {
        return response.data
      }
      return []
    },
    actionColumn: function(parent) {
      return function() {
        return {
          template: {
            template: `
                                <div class="action-buttons gap-1">
                                  <button class="btn btn-sm btn-outline-success" @click="onViewItem()" title="Xem" style="padding: 2px 6px !important;">
                                    <i class="fa fa-eye"></i>
                                  </button>
                                </div>
                            `,
            methods: {
              onViewItem() {
                parent.showDetail(this.data)
              }
            }
          }
        }
      }
    },
    showDetail: function(data) {
      const urls = getDeviceUrls(data.loaithietbi_id)
      if (!urls || !urls.detailUrl) {
        this.$root.toastWarning('Không tìm thấy loại thiết bị')
        return
      }
      this.$router.push(`${urls.detailUrl}?id=${data.id}`)
    },
    async loadSubnet() {
      if (
        Number(this.currentItem.haTangMangId) > 0 &&
        Number(this.currentItem.loaiIpId) > 0 &&
        Number(this.currentItem.phanLoaiIpId) > 0
      ) {
        try {
          this.$root.showLoading(true)
          const data = {
            haTangMangId: Number(this.currentItem.haTangMangId) || null,
            loaiIpId: Number(this.currentItem.loaiIpId) || null,
            phanLoaiIpId: Number(this.currentItem.phanLoaiIpId) || null
          }
          const response = await api.getListSubnet(this.$root.context, data)
          if (response && response.error_code === 'BSS-00000000') {
            this.dsSubnet = response.data
          } else {
            this.dsSubnet = []
          }
        } catch (e) {
          console.error('Error loading subnets:', e)
          this.dsSubnet = []
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.dsSubnet = []
      }
    },
    onChangeSubnet() {
      if (this.currentItem.subnetId) {
        const selectedSubnet = this.dsSubnet.find((x) => x.ID == this.currentItem.subnetId)
        if (selectedSubnet) {
          this.currentItem.vlanId = selectedSubnet.VLAN_ID
          this.currentItem.vrfId = selectedSubnet.VRF_ID
          if (Number(this.currentItem.vrfId) > 0) {
            this.onChangeVrf()
          }
          this.currentItem.zoneId = selectedSubnet.ZONE_ID
        }
      }
    },
    validateIPFormat(ip) {
      if (!ip || ip.trim() === '') return false
      const trimmedIp = ip.trim()
      const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
      const ipv6Patterns = [
        /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$/,
        /^::$/,
        /^::1$/,
        /^::([0-9a-fA-F]{1,4}:){0,6}[0-9a-fA-F]{1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,7}:$/,
        /^([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}$/,
        /^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}$/,
        /^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}$/,
        /^[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})$/,
        /^([0-9a-fA-F]{1,4}:){6}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
        /^::([0-9a-fA-F]{1,4}:){0,5}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
        /^fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}$/,
        /^::ffff:((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
      ]
      if (trimmedIp.includes('/')) {
        const [ipAddress, cidr] = trimmedIp.split('/')
        const cidrNum = Number(cidr)
        if (Number.isNaN(cidrNum)) return false
        if (ipv4Regex.test(ipAddress)) {
          const parts = ipAddress.split('.')
          if (parts.some((part) => part.length > 1 && part.startsWith('0'))) {
            return false
          }
          return cidrNum >= 0 && cidrNum <= 32
        }
        for (const pattern of ipv6Patterns) {
          if (pattern.test(ipAddress)) {
            if (ipAddress.includes('::')) {
              const doubleColonCount = (ipAddress.match(/::/g) || []).length
              if (doubleColonCount > 1) {
                return false
              }
            }
            return cidrNum >= 0 && cidrNum <= 128
          }
        }
        return false
      }
      if (ipv4Regex.test(trimmedIp)) {
        const parts = trimmedIp.split('.')
        if (parts.some((part) => part.length > 1 && part.startsWith('0'))) {
          return false
        }
        return true
      }
      for (const pattern of ipv6Patterns) {
        if (pattern.test(trimmedIp)) {
          if (trimmedIp.includes('::')) {
            const doubleColonCount = (trimmedIp.match(/::/g) || []).length
            if (doubleColonCount > 1) {
              return false
            }
          }
          return true
        }
      }
      return false
    },
    reLoadListDanhMuc() {
      this.dsHaTangMang = []
      this.dsVrf = []
      this.dsVlan = []
      this.dsLoaiIp = []
      this.dsPhanLoaiIp = []
      this.dsDoiTuongSuDung = []
      this.dsMucDichSuDung = []
      this.dsNoiBoKhachHang = []
      this.dsDonViQuanLy = []
    }
  }
}
</script>
<style scoped></style>
