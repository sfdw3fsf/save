<template src="./template.html"> </template>

<script>
import api from './API'
export default {
  methods: {
    showModal() {
      this.$refs.ChiTietKenhTheoPhien.show()
    },
    async handleShowModal() {
      if (!this.input.vhdtb_id) {
        this.$toast.info('Không có thông tin hợp đồng')
        this.hideModal()
      }
      let res = await api.getDSThueBao(this.axios, this.input)
      if (res.data.error_code == 'BSS-00000204') {
        this.$toast.info('Không có thông tin thuê bao')
      } else if (res.data.error_code == 'BSS-00000000') {
        this.dsTB_data = res.data.data
      } else {
        this.$toast.error('Lỗi khi lấy danh sách thuê bao')
      }
    },
    async tbDanhSachThueBao_rowSelected(row) {
      if (row) {
        this.ttKyThuat = ''
        let res = await api.getThongSoKyThuat(this.axios, this.input)
        if (res.data.error_code == 'BSS-00000000') {
          res.data.data.forEach((item) => {
            this.ttKyThuat += `<b>${item.ten_truong}</b>: ${item.giatri ? item.giatri : ''}<br/>`
          })
        } else {
          this.$toast.error('Lỗi khi lấy thông số kỹ thuật')
        }
        res = await api.getDSPhieuTH(this.axios, {
          vphanvung_id: this.input.vphanvung_id,
          vhtb_id: this.input.vhdtb_id,
        })
        if(res.data.error_code == 'BSS-00000000') {
          this.dsPhieuTH = res.data.data
        }
      }
    },
    hideModal() {
      this.$refs.ChiTietKenhTheoPhien.hide()
    }
  },
  data() {
    return {
      input: {},
      dsTB_data: [],
      ttKyThuat: '',
      dsPhieuTH: [],
    }
  }
}
</script>

<style lang="scss" scoped src="./style.scss"></style>
