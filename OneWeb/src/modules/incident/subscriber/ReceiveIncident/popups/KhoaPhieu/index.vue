<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx"
import API from '../../API'

export default {
    name: 'ModalXuatDuLieu',
    props: ['baohong_id', 'id'],
    data() {
        return {
            txtGhiChuKhoaPhieu: ''
        }
    },
    mounted() {
      this.txtGhiChuKhoaPhieu = ''
    },
    computed: {},
    methods: {
        onModalHidden() {
          this.$emit('close')
        },
        async onClickKhoaPhieuTam() {
          try {
            if (this.baohong_id = 0 || this.baohong_id == null || this.baohong_id == '') {
              this.$root.$toast.error('Chưa chọn thuê bao báo hỏng')
              return
            }

            //kiểm tra thiet bi tren su co cho HCM
            let rs = await API.sp_khoaphieu_baohong_ao_ipcc(this.axios, {
              p_baohong_id: this.baohong_id,
              p_ghichu: this.txtGhiChuKhoaPhieu,
              p_parars:""
            })

            if (rs.data.error_code && rs.data.error_code === 'BSS-00000000' ) {
              this.$bvModal.hide(this.id)

              return this.$toast.success('Khóa phiếu tạm thành công!')
            }

          } catch (e) {
            return this.$toast.error('Lỗi khóa phiếu : ' + e.message);
          }
        },
    }
}
</script>
<style>
    .tracuu-splitter-theo-port-olt-xuat-excel .gutter {
        background-color: transparent;
    }
</style>>
