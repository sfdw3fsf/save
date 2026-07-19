<template src="./index.template.html"></template>
<script>
export default {
  name: 'Subnet',
  computed: {},
  data() {
    return {
      dsSubnet: [
        {
          STT: 1,
          TEN_SUBNET: 'Subnet 1',
          SUBNET: '192.168.1.0/24',
          SUBNET_MASK: '255.255.255.0',
          GATEWAY: '192.168.1.1',
          VLAN: 'VLAN 100',
          HATANG_MANG: 'HTM_beo',
          TRANGTHAI_CCDV: 'Đã cung cấp'
        },
        {
          STT: 2,
          TEN_SUBNET: 'Subnet 2',
          SUBNET: '10.0.0.0/16',
          SUBNET_MASK: '255.255.0.0',
          GATEWAY: '10.0.0.1',
          VLAN: 'VLAN 200',
          HATANG_MANG: 'HTM_network1',
          TRANGTHAI_CCDV: 'Chưa cung cấp'
        }
      ]
    }
  },
  mounted() {
    this.loadSubnetData()
  },
  methods: {
    actionColumn: function(parent) {
      return function() {
        return {
          template: {
            template: `
              <div>
                <button class="btn btn-sm btn-outline-success" @click="onViewSubnetDetail(data)" title="Xem chi tiết Subnet">
                  <i class="fa fa-eye"></i>
                </button>
              </div>
            `,
            methods: {
              onViewSubnetDetail: (data) => {
                parent.onViewSubnetDetail(data)
              }
            }
          }
        }
      }
    },
    async loadSubnetData() {
      if (!this.id) {
        this.$root.$toastr.error('Không có ID IP Address để tải danh sách subnet')
        return
      }
      try {
        this.loading(true)
        const response = await api.getSubnetListByIpAddress(this.$root.context, this.id)
        if (response && response.data) {
          // Map data từ API sang format của DataGrid
          this.dsSubnet = response.data.map((item, index) => ({
            STT: index + 1,
            ID: item.ID || item.id,
            TEN_SUBNET: item.TEN_SUBNET || item.tenSubnet,
            SUBNET: item.SUBNET || item.subnet,
            SUBNET_MASK: item.SUBNET_MASK || item.subnetMask,
            GATEWAY: item.GATEWAY || item.gateway,
            VLAN: item.VLAN || item.vlan,
            HATANG_MANG: item.HATANG_MANG || item.haTangMang,
            TRANGTHAI_CCDV: item.TRANGTHAI_CCDV || item.trangThaiCCDV
          }))
        } else {
          this.dsSubnet = []
        }
      } catch (error) {
        console.error('Lỗi khi tải danh sách subnet:', error)
        this.$root.$toastr.error('Không thể tải danh sách subnet. Vui lòng thử lại!')
        this.dsSubnet = []
      } finally {
        this.loading(false)
      }
    },
    onViewSubnetDetail(data) {
      console.log('View subnet detail:', data)
      // TODO: Open subnet detail modal or navigate to detail page
    },
    grdSubnet_RowSelected(args) {
      console.log('Row selected:', args)
    }
  }
}
</script>
<style scoped></style>
