<template src="./InThuXacNhanCongNo.html"></template>
<style scoped src="./InThuXacNhanCongNo.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import InThuXacNhanCongNoSapXep from '../InThuXacNhanCongNoSapXep/InThuXacNhanCongNoSapXep'
export default {
  components: { breadcrumb, InThuXacNhanCongNoSapXep },
  name: 'InThuXacNhanCongNo',
  mounted () {
    // this.post(this.$API_URL + 'executor/sel_lay_thang_no', {}).then(
    //   (response) => {
    //     this.sel_lay_thang_no = response.data.data
    //   }
    // )
    // this.post(this.$API_URL + 'executor/fn_chukyno_kyhoadon_sel', {
    //   namthang: '',
    // }).then((response) => {
    //   this.fn_chukyno_kyhoadon_sel = response.data.data
    // })
  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'IN THƯ XÁC NHẬN CÔNG NỢ',
        list: [
          { name: 'Gạch nợ tiền', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu phiếu thanh toán theo ngày',
            link: { name: 'Ui.buttons' }
          }
        ]
      }
    }
  },
  methods: {
    openSapXepForm: function () {

    },
    alert: function () {
      alert('Hello')
    }
  }
}
</script>
