<template src="./index.template.html"></template>
<script>
import api from './api'
import ThongTinChiTietIPAddress from './ThongTinChiTiet'
import { DONVI_QUANLY } from './config.js'

export default {
  name: 'QuanLyIPAddress',
  components: {
    ThongTinChiTietIPAddress
  },
  watch: {},
  data() {
    return {
      currentTab: 1,
      showDetailPopup: false,
      selectedItem: {},
      searchItem: {
        loaiIpId: null,
        phanLoaiIpId: null,
        idcId: null,
        subnetId: null,
        trangThaiCcdvId: null,
        doiTuongSdId: null,
        donViQuanLyId: null,
        haTangMangId: null
      },
      dsLoaiIP: [],
      dsPhanLoaiIP: [],
      dsIDC: [],
      dsSubnet: [],
      dsTrangThaiCCDV: [],
      dsDoiTuongSuDung: [],
      dsDonViQuanLy: [],
      dsIpAddresses: [],
      dsHaTangMang: []
    }
  },
  created() {
    this.loadDanhMuc()
    this.loadIpAddresses()
  },
  methods: {
    actionColumn: function(parent) {
      return function() {
        return {
          template: {
            template: `
                            <div class="action-buttons gap-1">
                              <button class="btn btn-sm btn-outline-success" @click="onViewItem()" title="Xem" style="padding: 2px 6px !important;">
                                <i class="fa fa-eye"></i>
                              </button>
                              <!-- <button class="btn btn-sm btn-outline-primary" @click="onEditItem()" title="Sửa" style="padding: 2px 6px !important;">
                                <i class="icon nc-icon-outline one-edit"></i>
                              </button> -->
                              <!-- <button class="btn btn-sm btn-outline-danger" @click="onDeleteItem()" title="Xóa" style="padding: 2px 6px !important;">
                                <i class="fa fa-trash"></i>
                              </button> -->
                            </div>
                        `,
            methods: {
              onDeleteItem() {
                parent.delete(this.data)
              },
              onEditItem() {
                parent.edit(this.data)
              },
              onViewItem() {
                parent.view(this.data)
              }
            }
          }
        }
      }
    },
    onCreate() {
      this.$refs.ThongTinChiTiet.showModal(null, 'create')
    },
    view(data) {
      this.selectedItem = data
      this.$refs.ThongTinChiTiet.showModal(data, 'view')
    },
    edit(data) {
      this.selectedItem = data
      this.$refs.ThongTinChiTiet.showModal(data, 'edit')
    },
    delete(data) {
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc chắn muốn xóa bản ghi này?', {
          title: 'Xác nhận xóa',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          footerClass: 'p-2',
          hideHeaderClose: false,
          centered: true
        })
        .then((value) => {
          if (value) {
            // Call API delete here
            this.$root.toastSuccess('Xóa thành công!')
          }
        })
        .catch((err) => {
          // An error occurred
        })
    },
    grdItems_RowSelected(args) {
      console.log('Row selected', args)
    },
    onDoubleClick(args, data) {
      console.log('Double click args:', args)
      console.log('Double click data:', data)
      let rowData = null
      if (args && args.rowData) {
        rowData = args.rowData
      } else if (data) {
        rowData = data
      }

      if (rowData) {
        this.view(rowData)
      }
    },
    onSearch() {
      console.log('Search', this.searchItem)
      this.loadIpAddresses()
    },
    async loadDanhMuc() {
      try {
        this.loading(true)
        this.dsLoaiIP = await this.getDanhMucWithParams('LOAI_IP')
        this.dsLoaiIP = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsLoaiIP]
        this.searchItem.loaiIpId = 0
        this.dsIDC = await this.getDanhMucWithParams('IDC-IDC-BY-DONVI-ID', this.$root.token.getDonViID())
        this.dsIDC = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsIDC]
        this.searchItem.idcId = 0
        this.dsPhanLoaiIP = await this.getDanhMucWithParams('PHANLOAI_IP')
        this.dsPhanLoaiIP = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsPhanLoaiIP]
        this.searchItem.phanLoaiIpId = 0
        this.dsTrangThaiCCDV = await this.getDanhMucWithParams('TRANGTHAI_CCDV')
        this.dsTrangThaiCCDV = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsTrangThaiCCDV]
        this.searchItem.trangThaiCcdvId = 0
        this.dsSubnet = await this.getDanhMucWithParams('SUBNET')
        this.dsSubnet = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsSubnet]
        this.searchItem.subnetId = 0
        this.dsDoiTuongSuDung = await this.getDanhMucWithParams('IP_DOITUONGSD')
        this.dsDoiTuongSuDung = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsDoiTuongSuDung]
        this.searchItem.doiTuongSdId = 0
        this.dsDonViQuanLy = [{ ID: 0, TEN: '-- Tất cả --' }, ...DONVI_QUANLY]
        this.searchItem.donViQuanLyId = 0
        this.onChangeIDC()
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    onChangeIDC: async function() {
      if (this.searchItem.idcId > 0) {
        try {
          this.$root.showLoading(true)
          this.dsHaTangMang = await this.getDanhMucHaTang('HATANGMANG_BY_IDC_ID', this.searchItem.idcId)
          this.dsHaTangMang = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsHaTangMang]
          this.searchItem.haTangMangId = 0
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.dsHaTangMang = [{ ID: 0, TEN: '-- Tất cả --' }]
        this.searchItem.haTangMangId = 0
      }
    },
    getDanhMucWithParams: async function(ten, thamSo1, thamSo2) {
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
    getDanhMucHaTang: async function(ten, thamSo1, thamSo2) {
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
    async loadIpAddresses() {
      try {
        this.loading(true)
        const data = {
          loaiIpId: Number(this.searchItem.loaiIpId) === 0 ? null : Number(this.searchItem.loaiIpId),
          phanLoaiIpId: Number(this.searchItem.phanLoaiIpId) === 0 ? null : Number(this.searchItem.phanLoaiIpId),
          idcId: Number(this.searchItem.idcId) === 0 ? null : Number(this.searchItem.idcId),
          subnetId: Number(this.searchItem.subnetId) === 0 ? null : Number(this.searchItem.subnetId),
          trangThaiCcdvId:
            Number(this.searchItem.trangThaiCcdvId) === 0 ? null : Number(this.searchItem.trangThaiCcdvId),
          doiTuongSDId: Number(this.searchItem.doiTuongSdId) === 0 ? null : Number(this.searchItem.doiTuongSdId),
          donViQuanLyId: Number(this.searchItem.donViQuanLyId) === 0 ? null : Number(this.searchItem.donViQuanLyId),
          haTangMangId: Number(this.searchItem.haTangMangId) === 0 ? null : Number(this.searchItem.haTangMangId)
        }
        const response = await api.getListIpAddress(this.$root.context, data)
        console.log('Response:', response)
        if (response && response.error_code === 'BSS-00000000') {
          this.dsIpAddresses = response.data
        } else {
          this.dsIpAddresses = []
        }
      } catch (e) {
        console.error('Error loading IP addresses:', e)
        this.dsIpAddresses = []
        if (e.response && e.response.data && e.response.data.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        } else {
          this.$root.toastError('Có lỗi xảy ra khi tải danh sách IP Address')
        }
      } finally {
        this.loading(false)
      }
    },
    async loadSubnet() {
      if (
        Number(this.searchItem.haTangMangId) > 0 &&
        Number(this.searchItem.loaiIpId) > 0 &&
        Number(this.searchItem.phanLoaiIpId) > 0
      ) {
        try {
          this.$root.showLoading(true)
          const data = {
            haTangMangId: Number(this.searchItem.haTangMangId) || null,
            loaiIpId: Number(this.searchItem.loaiIpId) || null,
            phanLoaiIpId: Number(this.searchItem.phanLoaiIpId) || null
          }
          const response = await api.getListSubnet(this.$root.context, data)
          if (response && response.error_code === 'BSS-00000000') {
            this.dsSubnet = response.data
            this.dsSubnet = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.dsSubnet]
            this.searchItem.subnetId = 0
          } else {
            this.dsSubnet = [{ ID: 0, TEN: '-- Tất cả --' }]
            this.searchItem.subnetId = 0
          }
        } catch (e) {
          console.error('Error loading subnets:', e)
          this.dsSubnet = [{ ID: 0, TEN: '-- Tất cả --' }]
          this.searchItem.subnetId = 0
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.dsSubnet = [{ ID: 0, TEN: '-- Tất cả --' }]
        this.searchItem.subnetId = 0
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
