<template src="./ds-thietbi-mang.html"></template>
<script>
export default {
  data() {
    return {
      dataSource: [
        { TEN: 1, TEN_IDC: 'TBM001', TEN_VRF: 'Router Cisco', TEN_THIETBI: 'Router', GHI_CHU: 'Hoạt động' },
        { TEN: 2, TEN_IDC: 'TBM002', TEN_VRF: 'Switch HP', TEN_THIETBI: 'Switch', GHI_CHU: 'Bảo trì' },
        { TEN: 3, TEN_IDC: 'TBM003', TEN_VRF: 'Firewall Fortinet', TEN_THIETBI: 'Firewall', GHI_CHU: 'Hoạt động' },
        { TEN: 4, TEN_IDC: 'TBM004', TEN_VRF: 'Access Point Ubiquiti', TEN_THIETBI: 'Access Point', GHI_CHU: 'Ngưng hoạt động' },
        { TEN: 5, TEN_IDC: 'TBM005', TEN_VRF: 'Modem TP-Link', TEN_THIETBI: 'Modem', GHI_CHU: 'Hoạt động' },
      ],
    }
  },
  methods: {
    openModal() {
      this.$refs.modalDsThietBiMang.show();
    },
    closeModal() {
      this.$refs.modalDsThietBiMang.hide();
    },

    onChoose() {
      this.$confirm('Bạn có chắc chắn muốn chọn thiết bị mạng này? ', 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ'
      }).then(async () => {
        this.$emit('chonThietBiMang', this.dataSource)
        this.closeModal()
      })
    }


  }
}
</script>

