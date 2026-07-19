<template src="./ModalLichSu.html"></template>

<script>
import { Icon } from '@iconify/vue2'
import api from './API.js'
export default {
  components: {
    Icon
  },
  data() {
    return {
      openBox1: true,
      loai_cn: 0,
      dsLichSu: [],
      tbColumns: [
        { fieldName: 'ma_toanha', headerText: 'Mã tòa nhà' },
        { fieldName: 'ten_toanha', headerText: 'Tên tòa nhà' },
        { fieldName: 'ngay_cn', headerText: 'Thời gian cập nhật' },
        { fieldName: 'nguoi_cn', headerText: 'Người cập nhật' },
        { fieldName: 'loai_cn', headerText: 'Loại cập nhật' }
      ],
      txtTenToaNha: '',
      txtMaToaNha: '',
      phanvung_id: null
    }
  },
  methods: {
    hideModal() {
      this.$refs.LichSuModal.hide()
      this.$emit('closeModal')
    },
    async mounted() {
      this.phanvung_id = (await this.$root.token.getPhanVungID()) * 1
    },
    showModal() {
      this.dsFile = []
      this.$refs.LichSuModal.show()
    },
    async handleShowModal() {
      // this.$emit('showModal')
      this.phanvung_id = (await this.$root.token.getPhanVungID()) * 1
      this.dsLichSu = []
      this.search_click()

    },
    async search_click() {
      // test trên side dev
      // let res = await fetch('https://api-onebss.vnpt.vn/web-thicong/capnhatthongtin/sp_biendong_toanha', {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //     Authorization:
      //       'Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZF9uaGFudmllbl9kaHN4IjoiOTUwOTQiLCJ1c2VyX3R0X2NjYnMiOiJ2bnB0cGF5X2huaSIsImlkZW50aWZ5IjoiNjU_NjQ2NzU2NjQ1NDE1MTM1NzM1NTQ1MTM9ND00NTw9Nj0xND0wPz0xND0wPzY8MDA1Nj83Pz81NjczNzw_ND8iLCJtYV90aW5oIjoiSE5JIiwidXNlcl9uYW1lIjoiYWRtaW5faG5pIiwiZ3JvdXBJZCI6IjAiLCJpc3MiOiJodHRwOi8vcXVhbnRyaS9vYXV0aC90b2tlbl9rZXkiLCJ1c2VyX2dwaG9uZSI6InR0dGgucHRwbTJfaG5pIiwiYXV0aG9yaXRpZXMiOlsiUFJFX0FVVEgiXSwiaWRfZG9udmkiOiIyODQ0MTIiLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sIm1hX25oYW52aWVuX2NjYnMiOiJ2bnB0cGF5X2huaSIsImRvbnZpX2RsX2lkIjoiMjg0NDEyIiwic2VsbGluZ0RlcGFydG1lbnRJZCI6IjAiLCJjc3Nfc2NoIjoiQ1NTX0hOSS4iLCJleHAiOjE3MDMxMzA3MjIsInVzZXJfdmkiOiIwIiwibWFfbnYiOiJhZG1pbiIsImlkX3Rpbmh0aGFuaCI6IjIxIiwiZW1haWwiOiJwaGFtcXVhbmdjdW9uZ0B2bnB0LnZuIiwibmd1b2lkdW5nX2lkIjoiMTIzNzM4IiwiZHNfcXV5ZW5fY2h1cXVhbiI6Im51bGwifQ.eZ8XMuIJfY4LLTFtpGYurEXRkKSUbxH7G8Apx-zBxMA-M2-23vdRUZHDy5DKrAyqWvRz5TIRTWaLfGadFjSiXVv5YbYocAOtG7CvTvk6qqX_hzilbx1JFZRd4bCSZkothYBWxsJMXZQ7tOOw7s8jFWeCByQTKq1DlheBab7k3i7SdjY4fBmmGDeArVny6NS7XyH2ptDh5OVMfziAD28ur1P4lgiEFJ_H8zontvFMaop2e3wVfdAVg08WNANO2DUoM2aF8WbfSxeLwbsPr8Cvu0Wld_oTo1owHJUsDgjG9iMowMDfywivv_RtCdV74cCpTJYfi6wh6Vg6beZoFY-ctg'
      //   },
      //   body: JSON.stringify({
      //     vphanvung_id: this.phanvung_id,
      //     vma_toanha: this.txtMaToaNha,
      //     vten_toanha: this.txtTenToaNha,
      //     vloai_cn: this.loai_cn
      //   })
      // })
      // let data = await res.json();
      // if(data.error_code == "BSS-00000000" && data.data.length > 0){
      //   this.dsLichSu = data.data.map(item => {
      //     return {
      //       ...item,
      //       loai_cn: item.loai_cn == 'INSERT' ? 'Thêm mới' : item.loai_cn == 'UPDATE' ? 'Cập nhật' : 'Xóa'
      //     }
      //   })
      // }
      // else
      //   this.$toast.error('Không tìm thấy dữ liệu')
      // test trên side thật
      let res = await api.lichsu_capnhat(this.axios,{
        vphanvung_id: this.phanvung_id,
        vma_toanha: this.txtMaToaNha,
        vten_toanha: this.txtTenToaNha,
        vloai_cn: this.loai_cn
      })
      if(res.data.error_code == "BSS-00000000" && res.data.data.length > 0){
        this.dsLichSu = res.data.data.map(item => {
          return {
            ...item,
            loai_cn: item.loai_cn == 'INSERT' ? 'Thêm mới' : item.loai_cn == 'UPDATE' ? 'Cập nhật' : 'Xóa'
          }
        })
      }
      else
        this.$toast.error('Không tìm thấy dữ liệu')
    }
  }
}
</script>

<style lang="scss" scoped></style>
