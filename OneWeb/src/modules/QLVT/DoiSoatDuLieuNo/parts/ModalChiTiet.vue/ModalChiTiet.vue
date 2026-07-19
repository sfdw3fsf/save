<template src="./ModalChiTiet.html"></template>

<script>
  export default {
    data() {
      return {
        tbMain_columns: [
          {fieldName: 'ma_tt', headerText: 'Mã Thanh toán ĐHSX'},
          {fieldName: 'ma_tt_ccbs', headerText: 'Mã Thanh toán CCBS'},
          {fieldName: 'ma_tb', headerText: 'Mã thuê bao'},
          {fieldName: 'sophieu', headerText: 'Số phiếu CCBS'},
          {fieldName: 'sophieu_hdsx', headerText: 'Số phiếu ĐHSX'},
          {fieldName: 'tientra_CCBS', headerText: 'Tiền trả CCBS'},
          {fieldName: 'tientra_HDSX', headerText: 'Tiền trả ĐHSX'},
          {fieldName: 'thangtra_ccbs', headerText: 'Tháng trả CCBS'},
          {fieldName: 'thungan_ccbs', headerText: 'Thu ngân CCBS'},
          {fieldName: 'chuky_ccbs', headerText: 'Chu kỳ CCBS'},
          {fieldName: 'ngaytt_ccbs', headerText: 'Ngày thanh toán CCBS'},
        ],
        tbMain_dataSource: [
          {
            ma_tt: 'HNI124144',
            ma_tt_ccbs: 'HNIDD0011',
            ma_tb: '918486486',
            sophieu: '24243434',
            sophieu_hdsx: '1412422',
            tientra_CCBS: '10.000',
            tientra_HDSX: '',
            thangtra_ccbs: '11/2023',
            thungan_ccbs: 'admin_hni',
            chuky_ccbs: '20231101',
            ngaytt_ccbs: '23/12/2023',
          }
        ]
      }
    },
    methods: {
      hideModal() {
      this.$refs.ModalChiTiet.hide()
    },
    showModal() {
      this.$refs.ModalChiTiet.show()
    },
    handleShowModal() {
      // this.showModal()
    },
    }
  }
</script>

<style lang="scss" scoped>

</style>