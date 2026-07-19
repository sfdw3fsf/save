<template src="./template.html"></template>

<script>
import api from './api'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
export default {
  components: { breadcrumb },
  name: 'PopupNhapTienTrinhKhieuNai',
  props: {
    isPopup: {
      type: Boolean,
      default: true
    },
    inputData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      // data: { noidung: '' }
    }
  },
  computed: {},
  methods: {
    // onInit(data) {
    //   this.data = { ...data, noidung: data.noidung || '' }
    // },
    async onSave() {
      this.loading(true)
      try {
        const response = await api.sp_insert_update_tientrinh_kn(this.axios, {
          vtientrinhkn_id: this.inputData.tientrinhkn_id,
          vkhieunai_id: this.inputData.khieunai_id,
          vdonvi_id: this.$root.token.getDonViID(),
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vnguoidung_id: this.$root.token.getNguoiDungID(),
          vnoidung: this.inputData.noidung,
          vngay_cn: moment().format('DD/MM/YYYY hh:mm:ss'),
          vnguoi_cn: this.$root.token.getUserName(),
          vip_cn: await this.$root.token.getIP()
        })
        if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Cập nhật thành công')
          this.$emit('onSuccess')
        } else {
          this.$root.$toast.error('Cập nhật không thành công')
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Không thể cập nhật')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
