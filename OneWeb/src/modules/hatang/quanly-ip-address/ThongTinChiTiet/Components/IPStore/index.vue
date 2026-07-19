<template src="./index.template.html"></template>
<script>
import api from '../../../api'

export default {
  name: 'IPStore',
  props: {
    subnetId: {
      type: Number,
      default: null
    }
  },
  computed: {},
  data() {
    return {
      dsIpStore: [
        {
          STT: 1,
          IP_STORE: 'SA-001',
          TRANGTHAI_CAPPHAT: 'Lấp đặt mới',
          NGUOI_CAP: 'SA2024001',
          MUCDICH_SUDUNG: 'IDC NTL',
          THOIGIAN_CAP: 'Tòa nhà A',
          THOIGIAN_THUHOI: 'NULL',
          DONVI_DUOCCAP: 'NULL',
          SO_CU: 'Hoạt động'
        }
      ]
    }
  },
  mounted() {
    this.loadIpStoreData()
  },
  watch: {
    subnetId() {
      this.loadIpStoreData()
    }
  },
  methods: {
    actionColumn: function(parent) {
      return function() {
        return {
          template: {
            template: `
              <div>
                <button class="btn btn-sm btn-outline-success" @click="onViewIpStoreDetail(data)" title="Xem chi tiết">
                  <i class="fa fa-eye"></i>
                </button>
              </div>
            `,
            methods: {
              onViewIpStoreDetail: (data) => {
                parent.onViewIpStoreDetail(data)
              }
            }
          }
        }
      }
    },
    async loadIpStoreData() {
      if (!this.subnetId) {
        this.$root.$toastr.error('Không có ID Subnet để tải danh sách IP Store')
        return
      }
      try {
        this.loading(true)
        const response = await api.getIpStoreListBySubnet(this.$root.context, this.subnetId)
        if (response && response.data) {
          // Map data từ API sang format của DataGrid
          this.dsIpStore = response.data.map((item, index) => ({
            STT: index + 1,
            ID: item.ID || item.id,
            IP_STORE: item.IP_STORE || item.ipStore,
            TRANGTHAI_CAPPHAT: item.TRANGTHAI_CAPPHAT || item.trangThaiCapPhat,
            NGUOI_CAP: item.NGUOI_CAP || item.nguoiCap,
            MUCDICH_SUDUNG: item.MUCDICH_SUDUNG || item.mucDichSuDung,
            THOIGIAN_CAP: item.THOIGIAN_CAP || item.thoiGianCap,
            THOIGIAN_THUHOI: item.THOIGIAN_THUHOI || item.thoiGianThuHoi,
            DONVI_DUOCCAP: item.DONVI_DUOCCAP || item.donViDuocCap,
            SO_CU: item.SO_CU || item.soCu
          }))
        } else {
          this.dsIpStore = []
        }
      } catch (error) {
        console.error('Lỗi khi tải danh sách IP Store:', error)
        this.$root.$toastr.error('Không thể tải danh sách IP Store. Vui lòng thử lại!')
        this.dsIpStore = []
      } finally {
        this.loading(false)
      }
    },
    onViewIpStoreDetail(data) {
      console.log('View IP Store detail:', data)
      // TODO: Open IP Store detail modal or navigate to detail page
    },
    grdIpStore_RowSelected(args) {
      console.log('Row selected:', args)
    }
  }
}
</script>
<style scoped></style>
