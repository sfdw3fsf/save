<template src="./MapPaymentAccountToSubsType.html"></template>
<style scoped src="./MapPaymentAccountToSubsType.scss"></style>
<script>
import api from './MapPaymentAccountToSubsTypeAPI.js'
export default {
  components: {
    ModalThanhToanLoaiHinhThueBao: () =>
      import('./part/ModalThanhToanLoaiHinhThueBao.vue')
  },
  data () {
    return {
      header: {
        title: 'Nhập thông tin xác nhận quyền',
        list: [
          // Dữ liệu -> Quản lý thu nợ -> Khoản mục TT cho loại thuê bao
          { name: 'Dữ liệu', link: { name: 'Ui.cards' } },
          { name: 'Quản lý thu nợ', link: { name: 'Ui.buttons' } },
          {
            name: 'Khoản mục TT cho loại thuê bao',
            link: { name: 'Ui.buttons' }
          }
        ]
      },

      form_button: {
        danhSach: true,
        chapNhan: true,
        xuatExcel: true
      },
     
      showModal: true
    }
  },
  methods: {
  }
}
</script>
